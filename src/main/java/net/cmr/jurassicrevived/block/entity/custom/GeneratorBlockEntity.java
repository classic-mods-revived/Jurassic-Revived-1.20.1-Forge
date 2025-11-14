package net.cmr.jurassicrevived.block.entity.custom;

import net.cmr.jurassicrevived.Config;
import net.cmr.jurassicrevived.block.custom.GeneratorBlock;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyStorage;
import net.cmr.jurassicrevived.screen.custom.GeneratorMenu;
import net.cmr.jurassicrevived.sounds.MachineHumLoopSound;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GeneratorBlockEntity extends BlockEntity implements MenuProvider {
    private @Nullable MachineHumLoopSound humSound;

    public static void clientTick(Level level, BlockPos pos, BlockState state, GeneratorBlockEntity be) {
        if (!level.isClientSide) return;

        boolean lit = state.hasProperty(GeneratorBlock.LIT)
                && state.getValue(GeneratorBlock.LIT);

        if (lit) {
            if (be.humSound == null || be.humSound.isStopped()) {
                be.humSound = new MachineHumLoopSound(level, pos);
                Minecraft.getInstance().getSoundManager().play(be.humSound);
            }
        } else {
            if (be.humSound != null && !be.humSound.isStopped()) {
                be.humSound.stopPlaying();
            }
            be.humSound = null;
        }
    }

    public final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public static final int INPUT_SLOT = 0;

    protected final ContainerData data;
    // Store "elapsed" instead of "remaining" so UI stays compatible with vanilla-style logic
    private int burnTime = 0;        // elapsed ticks on current fuel
    private int burnTimeTotal = 0;   // total ticks for current fuel
    private boolean isBurning = false;

    private static final float ENERGY_TRANSFER_RATE = Config.fePerSecond / 20f;

    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();
    private LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.empty();

    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(256000, (int) ENERGY_TRANSFER_RATE) {
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                // Disallow receiving power
                return 0;
            }

            @Override
            public boolean canReceive() {
                // Disallow receiving power
                return false;
            }

            @Override
            public void onEnergyChanged() {
                setChanged();
                if (getLevel() != null) {
                    getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }
        };
    }

    public GeneratorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.GENERATOR_BE.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> GeneratorBlockEntity.this.burnTime;       // elapsed
                    case 1 -> GeneratorBlockEntity.this.burnTimeTotal;   // total
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int i1) {
                switch (i) {
                    case 0 -> GeneratorBlockEntity.this.burnTime = i1;
                    case 1 -> GeneratorBlockEntity.this.burnTimeTotal = i1;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    private void generateEnergyTick() {
        // Generate up to transfer rate per tick
        int space = this.ENERGY_STORAGE.getMaxEnergyStored() - this.ENERGY_STORAGE.getEnergyStored();
        int toAdd = Math.min(100, Math.max(0, space));
        if (toAdd > 0) {
            // Bypass external receive by directly adding to storage
            this.ENERGY_STORAGE.setEnergy(this.ENERGY_STORAGE.getEnergyStored() + toAdd);
            this.ENERGY_STORAGE.onEnergyChanged();

            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    }

    // Expose energy on all sides for pipes (Forge capability)
    @Override
    public void onLoad() {
        super.onLoad();
        lazyEnergy = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyEnergy.invalidate();
    }

    // Expose energy on all sides
    public IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        return this.ENERGY_STORAGE;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergy.cast();
        }
        return super.getCapability(cap, side);
    }

    // Side-agnostic item handler that accepts furnace fuels into INPUT_SLOT
    public IItemHandler getItemHandler(@Nullable Direction direction) {
        if (direction == null) return this.itemHandler;

        return new IItemHandler() {
            @Override
            public int getSlots() {
                return itemHandler.getSlots();
            }

            @Override
            public @NotNull ItemStack getStackInSlot(int slot) {
                return itemHandler.getStackInSlot(slot);
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if (slot != INPUT_SLOT || stack.isEmpty()) return stack;
                int burn = AbstractFurnaceBlockEntity.getFuel().getOrDefault(stack.getItem(), 0);
                if (burn <= 0) return stack;
                return itemHandler.insertItem(slot, stack, simulate);
            }

            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                return ItemStack.EMPTY;
            }

            @Override
            public int getSlotLimit(int slot) {
                return itemHandler.getSlotLimit(slot);
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                if (slot != INPUT_SLOT || stack.isEmpty()) return false;
                int burn = AbstractFurnaceBlockEntity.getFuel().getOrDefault(stack.getItem(), 0);
                return burn > 0;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.jurassicrevived.generator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new GeneratorMenu(i, inventory, this, this.data);
    }

    public boolean isEmptyForDrop() {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return !this.isBurning;
    }

    public void tick(Level level1, BlockPos blockPos, BlockState blockState) {
        boolean storageFull = this.ENERGY_STORAGE.getEnergyStored() >= this.ENERGY_STORAGE.getMaxEnergyStored();

        if (!storageFull && !isBurningFuel() && hasFuelItemInSlot()) {
            startBurning();
        }

        if (isBurningFuel() && !storageFull) {
            // Generate first so extractors can pull this tick
            int before = this.ENERGY_STORAGE.getEnergyStored();
            generateEnergyTick();
            this.burnTime++;
            if (this.burnTime >= this.burnTimeTotal) {
                this.isBurning = false;
            }
            // If energy changed, push immediately and notify clients
            if (!level1.isClientSide && this.ENERGY_STORAGE.getEnergyStored() != before) {
                pushEnergyToNeighbors();
                setChanged();
                level1.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        } else if (!isBurningFuel() && this.burnTime >= this.burnTimeTotal && this.burnTimeTotal > 0) {
            this.burnTime = 0;
            this.burnTimeTotal = 0;
        }

        if (!level1.isClientSide) {
            // Keep pipes flowing even when paused/full
            pushEnergyToNeighbors();
        }

        // Toggle LIT based on current generating state
        boolean shouldBeLit = isBurningFuel() && !storageFull;
        if (blockState.hasProperty(GeneratorBlock.LIT) && blockState.getValue(GeneratorBlock.LIT) != shouldBeLit) {
            level1.setBlockAndUpdate(blockPos, blockState.setValue(GeneratorBlock.LIT, shouldBeLit));
        }
    }

    private void pushEnergyToNeighbors() {
        if (this.ENERGY_STORAGE.getEnergyStored() <= 0 || level == null) return;

        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = worldPosition.relative(dir);
            BlockEntity neighbor = level.getBlockEntity(neighborPos);
            if (neighbor == null) continue;

            neighbor.getCapability(ForgeCapabilities.ENERGY, dir.getOpposite()).ifPresent(target -> {
                float offer = Math.min(ENERGY_TRANSFER_RATE, this.ENERGY_STORAGE.getEnergyStored());
                if (offer <= 0) return;

                float accepted = target.receiveEnergy((int) offer, true);
                if (accepted <= 0) return;

                float actuallyExtracted = this.ENERGY_STORAGE.extractEnergy((int) accepted, false);
                if (actuallyExtracted <= 0) return;

                float actuallyAccepted = target.receiveEnergy((int) actuallyExtracted, false);
                if (actuallyAccepted < actuallyExtracted) {
                    this.ENERGY_STORAGE.receiveEnergy((int) (actuallyExtracted - actuallyAccepted), false);
                }
            });
        }
    }

    private void finishBurning() {
        this.isBurning = false;
        // keep burnTimeTotal for one more tick so UI can render last frame; will reset on next start
    }

    private boolean isBurningFuel() {
        // burning when elapsed < total and flag is set
        return this.isBurning && this.burnTimeTotal > 0 && this.burnTime < this.burnTimeTotal;
    }

    private void startBurning() {
        ItemStack stack = this.itemHandler.getStackInSlot(INPUT_SLOT);
        if (stack.isEmpty()) {
            this.isBurning = false;
            return;
        }

        int burn = AbstractFurnaceBlockEntity.getFuel().getOrDefault(stack.getItem(), 0);
        if (burn <= 0) {
            this.isBurning = false;
            return;
        }

        this.itemHandler.extractItem(INPUT_SLOT, 1, false);

        // Start a fresh cycle
        this.burnTimeTotal = burn;
        this.burnTime = 0;
        this.isBurning = true;

        if (level != null && !level.isClientSide) {
            setChanged();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    private boolean hasFuelItemInSlot() {
        ItemStack stack = this.itemHandler.getStackInSlot(INPUT_SLOT);
        if (stack.isEmpty()) return false;
        return AbstractFurnaceBlockEntity.getFuel()
                .getOrDefault(stack.getItem(), 0) > 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("generator.inventory", itemHandler.serializeNBT());
        tag.putInt("generator.burn_time", this.burnTime);
        tag.putInt("generator.burn_time_total", this.burnTimeTotal);
        tag.putBoolean("generator.is_burning", this.isBurning);
        tag.putInt("generator.energy", this.ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("generator.inventory"));
        this.ENERGY_STORAGE.setEnergy(tag.getInt("generator.energy"));
        this.burnTime = tag.getInt("generator.burn_time");
        this.burnTimeTotal = tag.getInt("generator.burn_time_total");
        this.isBurning = tag.getBoolean("generator.is_burning");
        // Clamp elapsed to total to avoid UI flicker if saved mid-transition
        if (this.burnTimeTotal > 0) this.burnTime = Math.min(this.burnTime, this.burnTimeTotal);
        else this.burnTime = 0;
    }

    // Sync tank and other BE data to client
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }
}
