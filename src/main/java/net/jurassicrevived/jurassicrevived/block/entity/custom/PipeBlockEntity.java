package net.jurassicrevived.jurassicrevived.block.entity.custom;

import net.jurassicrevived.jurassicrevived.block.custom.PipeBlock;
import net.jurassicrevived.jurassicrevived.block.custom.PipeBlock.ConnectionType;
import net.jurassicrevived.jurassicrevived.block.custom.PipeBlock.Transport;
import net.jurassicrevived.jurassicrevived.block.entity.custom.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.EmptyFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayDeque;
import java.util.HashSet;

public class PipeBlockEntity extends BlockEntity {

    private final Transport transport;

    public PipeBlockEntity(BlockPos pos, BlockState state) {
        super(resolveType(state), pos, state);
        this.transport = ((PipeBlock) state.getBlock()).getTransport();
    }

    private static net.minecraft.world.level.block.entity.BlockEntityType<PipeBlockEntity> resolveType(BlockState state) {
        PipeBlock block = (PipeBlock) state.getBlock();
        return switch (block.getTransport()) {
            case ITEMS -> ModBlockEntities.ITEM_PIPE_BE.get();
            case FLUIDS -> ModBlockEntities.FLUID_PIPE_BE.get();
            case ENERGY -> ModBlockEntities.POWER_PIPE_BE.get();
        };
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, PipeBlockEntity be) {
        if (level == null || level.isClientSide) return;

        // Visual heartbeat
        if (level.getGameTime() % 10 == 0 && level instanceof ServerLevel s) {
            s.sendParticles(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.7, pos.getZ() + 0.5, 1, 0, 0.02, 0, 0);
        }

        PipeBlock block = (PipeBlock) state.getBlock();
        int itemCap = block.getMaxItemsPerTick();
        int fluidCap = block.getMaxFluidPerTick();
        int energyCap = block.getMaxEnergyPerTick();

        switch (be.transport) {
            case ITEMS -> transferItems(level, pos, state, itemCap);
            case FLUIDS -> transferFluids(level, pos, state, fluidCap);
            case ENERGY -> transferEnergy(level, pos, state, energyCap);
        }
    }

    // ===== Network discovery =====

    private record PipeEndpoint(BlockPos pipePos, Direction side) {}

    private static class Network<T> {
        final java.util.List<PipeEndpoint> sources = new java.util.ArrayList<>();
        final java.util.List<PipeEndpoint> sinks = new java.util.ArrayList<>();
        final java.util.List<T> sinkCaps = new java.util.ArrayList<>();
    }

    private static boolean isSamePipe(BlockState state, PipeBlock.Transport transport) {
        return state.getBlock() instanceof PipeBlock pb && pb.getTransport() == transport;
    }

    private static <T> Network<T> discoverNetwork(Level level, BlockPos start, PipeBlock.Transport transport) {
        Network<T> net = new Network<>();
        ArrayDeque<BlockPos> q = new ArrayDeque<>();
        HashSet<BlockPos> seen = new HashSet<>();
        q.add(start);
        seen.add(start);

        while (!q.isEmpty()) {
            BlockPos p = q.removeFirst();
            BlockState st = level.getBlockState(p);
            if (!(st.getBlock() instanceof PipeBlock pb) || pb.getTransport() != transport) continue;

            for (Direction d : Direction.values()) {
                ConnectionType ct = st.getValue(PipeBlock.getProp(d));

                if (ct == ConnectionType.CONNECTOR_PULL) {
                    net.sources.add(new PipeEndpoint(p, d));
                } else if (ct == ConnectionType.CONNECTOR) {
                    net.sinks.add(new PipeEndpoint(p, d));
                }

                if (ct == ConnectionType.PIPE) {
                    BlockPos np = p.relative(d);
                    if (!seen.contains(np)) {
                        BlockState ns = level.getBlockState(np);
                        if (isSamePipe(ns, transport)) {
                            seen.add(np);
                            q.add(np);
                        }
                    }
                }
            }
        }
        return net;
    }

    // ===== Items via network =====

    private static void transferItems(Level level, BlockPos pos, BlockState state, int perTickLimit) {
        if (level.isClientSide || perTickLimit <= 0) return;

        Transport transport = ((PipeBlock) state.getBlock()).getTransport();
        var net = PipeBlockEntity.<IItemHandler>discoverNetwork(level, pos, transport);

        // Resolve sinks
        var outputs = new java.util.ArrayList<IItemHandler>();
        for (var ep : net.sinks) {
            BlockPos npos = ep.pipePos.relative(ep.side);
            BlockEntity be = level.getBlockEntity(npos);
            if (be == null) continue;
            IItemHandler out = getItemHandler(be, ep.side.getOpposite());
            if (out != null) outputs.add(out);
        }
        if (outputs.isEmpty()) return;

        int remaining = perTickLimit;

        // Pull from all sources discovered
        for (var ep : net.sources) {
            if (remaining <= 0) break;
            BlockPos npos = ep.pipePos.relative(ep.side);
            BlockEntity be = level.getBlockEntity(npos);
            if (be == null) continue;
            IItemHandler src = getItemHandler(be, ep.side.getOpposite());
            if (src == null) continue;

            for (int slot = 0; slot < src.getSlots() && remaining > 0; slot++) {
                ItemStack peek = src.extractItem(slot, remaining, true);
                if (peek.isEmpty()) continue;

                int toMove = Math.min(peek.getCount(), remaining);
                while (toMove > 0 && remaining > 0) {
                    int batch = Math.min(toMove, Math.max(1, peek.getMaxStackSize()));
                    ItemStack extracted = src.extractItem(slot, batch, false);
                    if (extracted.isEmpty()) break;

                    ItemStack rem = extracted;
                    for (IItemHandler out : outputs) {
                        if (rem.isEmpty()) break;
                        rem = ItemHandlerHelper.insertItem(out, rem, false);
                    }
                    int moved = extracted.getCount() - rem.getCount();
                    if (moved <= 0) break;
                    remaining -= moved;
                    toMove -= moved;
                }
            }
        }
    }

    // Try Forge ITEM_HANDLER first; if absent, wrap vanilla Container/WorldlyContainer
    private static IItemHandler getItemHandler(BlockEntity be, Direction face) {
        var cap = be.getCapability(ForgeCapabilities.ITEM_HANDLER, face);
        if (cap.isPresent()) return cap.orElse(null);

        if (be instanceof net.minecraft.world.level.block.entity.BaseContainerBlockEntity base) {
            Container inv = base;
            if (inv instanceof WorldlyContainer sided) {
                return new SidedInvWrapper(sided, face);
            }
            return new InvWrapper(inv);
        }
        return null;
    }

    // ===== Fluids via network =====

    private static void transferFluids(Level level, BlockPos pos, BlockState state, int perTickLimit) {
        if (level.isClientSide || perTickLimit <= 0) return;

        Transport transport = ((PipeBlock) state.getBlock()).getTransport();
        var net = PipeBlockEntity.<IFluidHandler>discoverNetwork(level, pos, transport);

        var outputs = new java.util.ArrayList<IFluidHandler>();
        for (var ep : net.sinks) {
            BlockPos npos = ep.pipePos.relative(ep.side);
            BlockEntity be = level.getBlockEntity(npos);
            if (be == null) continue;
            Direction face = ep.side.getOpposite();
            IFluidHandler out = be.getCapability(ForgeCapabilities.FLUID_HANDLER, face).orElse(null);
            if (out == null) out = be.getCapability(ForgeCapabilities.FLUID_HANDLER, null).orElse(null);
            if (out != null && !(out instanceof EmptyFluidHandler)) outputs.add(out);
        }
        if (outputs.isEmpty()) return;

        for (var ep : net.sources) {
            BlockPos npos = ep.pipePos.relative(ep.side);
            BlockEntity be = level.getBlockEntity(npos);
            if (be == null) continue;
            Direction face = ep.side.getOpposite();
            IFluidHandler src = be.getCapability(ForgeCapabilities.FLUID_HANDLER, face).orElse(null);
            if (src == null) src = be.getCapability(ForgeCapabilities.FLUID_HANDLER, null).orElse(null);
            if (src == null) continue;

            int remaining = perTickLimit;
            for (int tank = 0; tank < src.getTanks() && remaining > 0; tank++) {
                FluidStack available = src.getFluidInTank(tank);
                if (available.isEmpty()) continue;

                while (remaining > 0) {
                    int batch = Math.min(remaining, Math.min(1000, available.getAmount()));
                    if (batch <= 0) break;

                    FluidStack simDrain = src.drain(batch, IFluidHandler.FluidAction.SIMULATE);
                    if (simDrain.isEmpty()) break;

                    int toPush = simDrain.getAmount();
                    int pushed = 0;

                    for (IFluidHandler out : outputs) {
                        if (toPush <= 0) break;
                        FluidStack tryFill = new FluidStack(simDrain.getFluid(), toPush, simDrain.getTag());
                        int canFill = out.fill(tryFill, IFluidHandler.FluidAction.SIMULATE);
                        if (canFill > 0) {
                            FluidStack execDrain = src.drain(canFill, IFluidHandler.FluidAction.EXECUTE);
                            if (!execDrain.isEmpty()) {
                                int filled = out.fill(execDrain, IFluidHandler.FluidAction.EXECUTE);
                                pushed += filled;
                                toPush -= filled;
                                remaining -= filled;
                            }
                        }
                    }
                    if (pushed <= 0) break;
                }
            }
        }
    }

    // ===== Energy via network =====

    private static void transferEnergy(Level level, BlockPos pos, BlockState state, int perTickLimit) {
        if (perTickLimit <= 0) return;

        Transport transport = ((PipeBlock) state.getBlock()).getTransport();
        var net = PipeBlockEntity.<IEnergyStorage>discoverNetwork(level, pos, transport);

        var outputs = new java.util.ArrayList<IEnergyStorage>();
        for (var ep : net.sinks) {
            BlockPos npos = ep.pipePos.relative(ep.side);
            BlockEntity be = level.getBlockEntity(npos);
            if (be == null) continue;
            Direction face = ep.side.getOpposite();
            IEnergyStorage out = be.getCapability(ForgeCapabilities.ENERGY, face).orElse(null);
            if (out == null) out = be.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
            if (out != null) outputs.add(out);
        }
        if (outputs.isEmpty()) return;

        for (var ep : net.sources) {
            BlockPos npos = ep.pipePos.relative(ep.side);
            BlockEntity be = level.getBlockEntity(npos);
            if (be == null) continue;
            Direction face = ep.side.getOpposite();
            IEnergyStorage src = be.getCapability(ForgeCapabilities.ENERGY, face).orElse(null);
            if (src == null) src = be.getCapability(ForgeCapabilities.ENERGY, null).orElse(null);
            if (src == null) continue;

            int remaining = perTickLimit;
            final int batchSize = Math.max(1, Math.min(1000, remaining));

            while (remaining > 0) {
                int canExtract = src.extractEnergy(Math.min(batchSize, remaining), true);
                if (canExtract <= 0) break;

                int toSend = canExtract;
                int sent = 0;

                for (IEnergyStorage out : outputs) {
                    if (toSend <= 0) break;
                    int canReceive = out.receiveEnergy(toSend, true);
                    if (canReceive > 0) {
                        int actuallyExtracted = src.extractEnergy(canReceive, false);
                        if (actuallyExtracted > 0) {
                            int received = out.receiveEnergy(actuallyExtracted, false);
                            sent += received;
                            toSend -= received;
                            remaining -= received;
                        }
                    }
                }
                if (sent <= 0) break;
            }
        }
    }
}