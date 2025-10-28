package net.cmr.jurassicrevived.block.entity.custom;

import net.cmr.jurassicrevived.Config;
import net.cmr.jurassicrevived.block.custom.DNAHybridizerBlock;
import net.cmr.jurassicrevived.block.custom.IncubatorBlock;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyStorage;
import net.cmr.jurassicrevived.recipe.IncubatorRecipe;
import net.cmr.jurassicrevived.screen.custom.IncubatorMenu;
import net.cmr.jurassicrevived.sounds.MachineHumLoopSound;
import net.cmr.jurassicrevived.util.InventoryDirectionEntry;
import net.cmr.jurassicrevived.util.InventoryDirectionWrapper;
import net.cmr.jurassicrevived.util.ModTags;
import net.cmr.jurassicrevived.util.WrappedHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
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

import java.util.Map;
import java.util.Optional;

public class IncubatorBlockEntity extends BlockEntity implements MenuProvider {
    private @Nullable MachineHumLoopSound humSound;

    public static void clientTick(Level level, BlockPos pos, BlockState state, IncubatorBlockEntity be) {
        if (!level.isClientSide) return;

        boolean lit = state.hasProperty(IncubatorBlock.LIT)
                && state.getValue(IncubatorBlock.LIT);

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

    public final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            // 3 independent egg slots
            return slot >= 0 && slot < 3 && stack.is(net.cmr.jurassicrevived.util.ModTags.Items.EGGS);
        }
    };

    private static final int SLOT_COUNT = 3;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            new InventoryDirectionWrapper(itemHandler,
                    new InventoryDirectionEntry(Direction.DOWN, 0, false),
                    new InventoryDirectionEntry(Direction.DOWN, 1, false),
                    new InventoryDirectionEntry(Direction.DOWN, 2, false),
                    new InventoryDirectionEntry(Direction.NORTH, 0, false),
                    new InventoryDirectionEntry(Direction.NORTH, 1, false),
                    new InventoryDirectionEntry(Direction.NORTH, 2, false),
                    new InventoryDirectionEntry(Direction.SOUTH, 0, false),
                    new InventoryDirectionEntry(Direction.SOUTH, 1, false),
                    new InventoryDirectionEntry(Direction.SOUTH, 2, false),
                    new InventoryDirectionEntry(Direction.EAST, 0, false),
                    new InventoryDirectionEntry(Direction.EAST, 1, false),
                    new InventoryDirectionEntry(Direction.EAST, 2, false),
                    new InventoryDirectionEntry(Direction.WEST, 0, false),
                    new InventoryDirectionEntry(Direction.WEST, 1, false),
                    new InventoryDirectionEntry(Direction.WEST, 2, false),
                    new InventoryDirectionEntry(Direction.UP, 0, true),
                    new InventoryDirectionEntry(Direction.UP, 1, true),
                    new InventoryDirectionEntry(Direction.UP, 2, true)).directionsMap;


    protected final ContainerData data;
    // Per-slot progress
    private final int[] progress = new int[SLOT_COUNT];
    private final int[] maxProgress = new int[]{200, 200, 200};

    private static final float ENERGY_TRANSFER_RATE = (float) Config.fePerSecond / 20f;

    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();
    private LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.empty();

    // Expose a receive-only view to neighbors. Internal code uses ENERGY_STORAGE directly.
    private final IEnergyStorage EXTERNAL_ENERGY_CAP = new IEnergyStorage() {
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return ENERGY_STORAGE == null ? 0 : ENERGY_STORAGE.receiveEnergy(maxReceive, simulate);
        }
        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return 0; // block external pulls
        }
        @Override
        public int getEnergyStored() {
            return ENERGY_STORAGE == null ? 0 : ENERGY_STORAGE.getEnergyStored();
        }
        @Override
        public int getMaxEnergyStored() {
            return ENERGY_STORAGE == null ? 0 : ENERGY_STORAGE.getMaxEnergyStored();
        }
        @Override
        public boolean canExtract() { return false; }
        @Override
        public boolean canReceive() { return ENERGY_STORAGE != null && ENERGY_STORAGE.canReceive(); }
    };

    private ModEnergyStorage createEnergyStorage() {
        if (Config.REQUIRE_POWER) {
            // Allow internal extraction; onEnergyChanged keeps client in sync
            return new ModEnergyStorage(16000, (int) ENERGY_TRANSFER_RATE) {
                @Override
                public void onEnergyChanged() {
                    setChanged();
                    if (getLevel() != null) {
                        getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                    }
                }
            };
        }
        return null;
    }

    public IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        if (!Config.REQUIRE_POWER) return null;
        // Always expose the wrapper so pipes/networks can't pull out
        return EXTERNAL_ENERGY_CAP;
    }

    public IncubatorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.INCUBATOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                // 0..2 progress, 3..5 maxProgress
                return switch (index) {
                    case 0,1,2 -> IncubatorBlockEntity.this.progress[index];
                    case 3,4,5 -> IncubatorBlockEntity.this.maxProgress[index - 3];
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0,1,2 -> IncubatorBlockEntity.this.progress[index] = value;
                    case 3,4,5 -> IncubatorBlockEntity.this.maxProgress[index - 3] = value;
                }
            }

            @Override
            public int getCount() {
                return 6;
            }
        };
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public boolean isEmptyForDrop() {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        for (int i = 0; i < SLOT_COUNT; i++) {
            if (this.progress[i] != 0) return false;
        }
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.jurassicrevived.incubator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new IncubatorMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }
            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDirection = this.getBlockState().getValue(IncubatorBlock.FACING);

                if (side == Direction.DOWN || side == Direction.UP) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDirection) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            } else {
                return LazyOptional.empty();
            }
        }

        if (cap == ForgeCapabilities.ENERGY) {
            if (Config.REQUIRE_POWER && ENERGY_STORAGE != null) {
                return lazyEnergy.cast();
            }
            return LazyOptional.empty();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        if (Config.REQUIRE_POWER && ENERGY_STORAGE != null) {
            lazyEnergy = LazyOptional.of(() -> ENERGY_STORAGE);
        } else {
            lazyEnergy = LazyOptional.empty();
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergy.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        for (int i = 0; i < SLOT_COUNT; i++) {
            tag.putInt("incubator.progress." + i, progress[i]);
        }
        if (Config.REQUIRE_POWER) {
            tag.putInt("incubator.energy", this.ENERGY_STORAGE != null ? this.ENERGY_STORAGE.getEnergyStored() : 0);
        }
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        for (int i = 0; i < SLOT_COUNT; i++) {
            progress[i] = tag.getInt("incubator.progress." + i);
        }
        if (Config.REQUIRE_POWER && this.ENERGY_STORAGE != null) {
            this.ENERGY_STORAGE.setEnergy(tag.getInt("incubator.energy"));
        }
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (Config.REQUIRE_POWER) {
            pullEnergyFromNeighbors();
        }

        boolean anyActive = false;
        for (int slot = 0; slot < SLOT_COUNT; slot++) {
            if (canProcessSlot(slot)) {
                anyActive = true;
                break;
            }
        }

        // update block lit state once per tick based on activity
        if (state.getValue(IncubatorBlock.LIT) != anyActive) {
            level.setBlockAndUpdate(pos, state.setValue(IncubatorBlock.LIT, anyActive));
        }
        // Consume energy once per tick if any slot is active
        if (Config.REQUIRE_POWER && anyActive) {
            if (!consumeEnergyPerTick(10)) {
                // Not enough energy; do not progress any slot
                setChanged(level, pos, state);
                return;
            }
        }

        boolean changed = false;

        for (int slot = 0; slot < SLOT_COUNT; slot++) {
            if (canProcessSlot(slot)) {
                progress[slot]++;
                changed = true;
                if (progress[slot] >= maxProgress[slot]) {
                    processSlot(slot);
                    progress[slot] = 0;
                    changed = true;
                }
            } else {
                if (progress[slot] != 0) {
                    progress[slot] = 0;
                    changed = true;
                }
            }
        }

        if (changed) {
            setChanged(level, pos, state);
        }
    }

    private void processSlot(int slot) {
        ItemStack stack = itemHandler.getStackInSlot(slot);
        if (stack.isEmpty()) return;

        SimpleContainer container = new SimpleContainer(1);
        container.setItem(0, stack);

        level.getRecipeManager()
                .getRecipeFor(IncubatorRecipe.Type.INSTANCE, container, level)
                .ifPresent(recipe -> {
                    ItemStack result = recipe.assemble(container, level.registryAccess());
                    if (!result.isEmpty()) {
                        // Replace in-place: decrement input by 1, then insert result once
                        ItemStack current = itemHandler.getStackInSlot(slot);
                        if (!current.isEmpty()) {
                            current.shrink(1);
                            itemHandler.setStackInSlot(slot, current); // update after shrink
                        }
                        ItemStack after = itemHandler.getStackInSlot(slot);
                        if (after.isEmpty()) {
                            itemHandler.setStackInSlot(slot, result.copy());
                        } else if (ItemStack.isSameItemSameTags(after, result) && after.getCount() < after.getMaxStackSize()) {
                            after.grow(result.getCount());
                            itemHandler.setStackInSlot(slot, after);
                        } else {
                            // If the slot is occupied by a different item, try to place result anyway (override)
                            itemHandler.setStackInSlot(slot, result.copy());
                        }
                    }
                });
    }

    private boolean canProcessSlot(int slot) {
        ItemStack stack = itemHandler.getStackInSlot(slot);
        if (stack.isEmpty()) return false;

        // Build a 1-slot container view for matching
        SimpleContainer container = new SimpleContainer(1);
        container.setItem(0, stack);

        return level.getRecipeManager()
                .getRecipeFor(IncubatorRecipe.Type.INSTANCE, container, level)
                .isPresent();
    }

    private void pullEnergyFromNeighbors() {
        if (this.ENERGY_STORAGE == null || level == null) return;
        int space = this.ENERGY_STORAGE.getMaxEnergyStored() - this.ENERGY_STORAGE.getEnergyStored();
        if (space <= 0) return;

        for (Direction dir : Direction.values()) {
            if (space <= 0) break;

            BlockPos neighborPos = worldPosition.relative(dir);
            BlockEntity neighbor = level.getBlockEntity(neighborPos);
            if (neighbor == null) continue;

            neighbor.getCapability(ForgeCapabilities.ENERGY, dir.getOpposite()).ifPresent(source -> {
                // How much we can pull this tick, bounded by our space and per-tick rate
                int request = Math.min((int) ENERGY_TRANSFER_RATE, this.ENERGY_STORAGE.getMaxEnergyStored() - this.ENERGY_STORAGE.getEnergyStored());
                if (request <= 0) return;

                // Check how much neighbor can provide
                int canExtract = source.extractEnergy(request, true);
                if (canExtract <= 0) return;

                // Receive into our buffer (simulate first)
                int canReceive = this.ENERGY_STORAGE.receiveEnergy(canExtract, true);
                if (canReceive <= 0) return;

                // Perform actual transfer
                int actuallyExtracted = source.extractEnergy(canReceive, false);
                if (actuallyExtracted <= 0) return;

                int actuallyReceived = this.ENERGY_STORAGE.receiveEnergy(actuallyExtracted, false);

                // If for some reason we couldn't take all we extracted, push back the leftover
                if (actuallyReceived < actuallyExtracted) {
                    source.receiveEnergy(actuallyExtracted - actuallyReceived, false);
                }
            });
        }
    }

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

    private boolean consumeEnergyPerTick(int fe) {
        if (!Config.REQUIRE_POWER || this.ENERGY_STORAGE == null) return true;
        if (fe <= 0) return true;
        if (ENERGY_STORAGE.getEnergyStored() >= fe) {
            ENERGY_STORAGE.extractEnergy(fe, false);
            return true;
        }
        return false;
    }
}