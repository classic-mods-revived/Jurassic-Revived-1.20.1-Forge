package net.cmr.jurassicrevived.block.entity.custom;

import net.cmr.jurassicrevived.Config;
import net.cmr.jurassicrevived.block.custom.DNAHybridizerBlock;
import net.cmr.jurassicrevived.block.custom.EmbryoCalcificationMachineBlock;
import net.cmr.jurassicrevived.block.custom.EmbryonicMachineBlock;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyStorage;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.recipe.EmbryonicMachineRecipe;
import net.cmr.jurassicrevived.screen.custom.EmbryonicMachineMenu;
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

public class EmbryonicMachineBlockEntity extends BlockEntity implements MenuProvider {
    private @Nullable MachineHumLoopSound humSound;

    public static void clientTick(Level level, BlockPos pos, BlockState state, EmbryonicMachineBlockEntity be) {
        if (!level.isClientSide) return;

        boolean lit = state.hasProperty(EmbryonicMachineBlock.LIT)
                && state.getValue(EmbryonicMachineBlock.LIT);

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

    public final ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.getItem() == ModItems.SYRINGE.get();
                case 1 -> stack.is(ModTags.Items.DNA);
                case 2 -> stack.is(ModItems.FROG_DNA.get());
                case 3 -> true;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private static final int SYRINGE_SLOT = 0;
    private static final int MATERIAL_INPUT = 1;
    private static final int FROG_INPUT = 2;
    private static final int OUTPUT_SLOT_1 = 3;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            new InventoryDirectionWrapper(itemHandler,
                    new InventoryDirectionEntry(Direction.DOWN, OUTPUT_SLOT_1, false),
                    new InventoryDirectionEntry(Direction.NORTH, OUTPUT_SLOT_1, false),
                    new InventoryDirectionEntry(Direction.SOUTH, OUTPUT_SLOT_1, false),
                    new InventoryDirectionEntry(Direction.EAST, OUTPUT_SLOT_1, false),
                    new InventoryDirectionEntry(Direction.WEST, OUTPUT_SLOT_1, false),
                    new InventoryDirectionEntry(Direction.UP, SYRINGE_SLOT, true),
                    new InventoryDirectionEntry(Direction.UP, FROG_INPUT, true),
                    new InventoryDirectionEntry(Direction.UP, MATERIAL_INPUT, true)).directionsMap;


    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 200;

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
            return new ModEnergyStorage(64000, (int) ENERGY_TRANSFER_RATE) {
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

    public EmbryonicMachineBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.EMBRYONIC_MACHINE_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> EmbryonicMachineBlockEntity.this.progress;
                    case 1 -> EmbryonicMachineBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> EmbryonicMachineBlockEntity.this.progress = pValue;
                    case 1 -> EmbryonicMachineBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
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
        return this.progress == 0;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.jurassicrevived.embryonic_machine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new EmbryonicMachineMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }
            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDirection = this.getBlockState().getValue(EmbryonicMachineBlock.FACING);
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
            // Only expose energy when power is required and storage exists
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
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("embryonic_machine.progress", progress);
        if (Config.REQUIRE_POWER) {
            pTag.putInt("embryonic_machine.energy", this.ENERGY_STORAGE.getEnergyStored());
        }
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("embryonic_machine.progress");
        if (Config.REQUIRE_POWER) {
            this.ENERGY_STORAGE.setEnergy(pTag.getInt("embryonic_machine.energy"));
        }
    }

    public void tick(Level level, BlockPos pPos, BlockState pState) {
        if (Config.REQUIRE_POWER) {
            pullEnergyFromNeighbors();
        }

        if (isOutputSlotEmptyOrReceivable() && hasRecipe()) {
            if (Config.REQUIRE_POWER && !consumeEnergyPerTick(10)) {
                // Not enough energy to continue; don't advance progress but keep state
                setChanged(level, pPos, pState);
                return;
            }
            increaseCraftingProcess();
            level.setBlockAndUpdate(pPos, pState.setValue(EmbryonicMachineBlock.LIT, true));
            setChanged(level, pPos, pState);

            if (hasProgressFinished()) {
                craftItem();
                resetProgress();
                level.setBlockAndUpdate(pPos, pState.setValue(EmbryonicMachineBlock.LIT, false));
            }
        } else {
            resetProgress();
            level.setBlockAndUpdate(pPos, pState.setValue(EmbryonicMachineBlock.LIT, false));
        }
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

    private void craftItem() {
        Optional<EmbryonicMachineRecipe> recipe = getCurrentRecipe();

        this.itemHandler.extractItem(SYRINGE_SLOT, 1, false);
        this.itemHandler.extractItem(MATERIAL_INPUT, 1, false);

        // Use dynamic assemble result based on current inventory (supports random dna for amber)
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        ItemStack result = recipe.get().assemble(inventory, getLevel().registryAccess());
        Item resultItem = result.getItem();
        int resultCount = result.getCount();

        int targetSlot = getAvailableOutputSlotFor(resultItem);
        if (targetSlot != -1) {
            ItemStack current = this.itemHandler.getStackInSlot(targetSlot);

            if (current.isEmpty()) {
                this.itemHandler.setStackInSlot(targetSlot, result.copy());
            } else {
                int existing = current.getCount();
                int max = current.getMaxStackSize();
                int toPlace = Math.min(existing + resultCount, max);
                int remainder = (existing + resultCount) - toPlace;

                current.setCount(toPlace);
                this.itemHandler.setStackInSlot(targetSlot, current);

                if (remainder > 0) {
                    int nextSlot = getAvailableOutputSlotFor(resultItem);
                    if (nextSlot != -1) {
                        ItemStack next = this.itemHandler.getStackInSlot(nextSlot);
                        if (next.isEmpty()) {
                            this.itemHandler.setStackInSlot(nextSlot, new ItemStack(resultItem, remainder));
                        } else {
                            int existing2 = next.getCount();
                            int toPlace2 = Math.min(existing2 + remainder, next.getMaxStackSize());
                            next.setCount(toPlace2);
                            this.itemHandler.setStackInSlot(nextSlot, next);
                        }
                    }
                }
            }
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasProgressFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProcess() {
        this.progress++;
    }

    private boolean hasRecipe() {
        Optional<EmbryonicMachineRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) return false;

        // Compute dynamic result and check it can be inserted
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        ItemStack dynamicResult = recipe.get().assemble(inventory, getLevel().registryAccess());
        return !dynamicResult.isEmpty() && canInsertOneIntoAnyOutput(dynamicResult.getItem());
    }

    private Optional<EmbryonicMachineRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(EmbryonicMachineRecipe.Type.INSTANCE, inventory, this.level);
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        // Use the current recipe’s dynamic output rather than a fixed result item
        Optional<EmbryonicMachineRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        ItemStack dynamicResult = recipe.get().assemble(inventory, getLevel().registryAccess());
        return !dynamicResult.isEmpty() && canInsertOneIntoAnyOutput(dynamicResult.getItem());
    }

    private boolean canInsertOneIntoAnyOutput(Item item) {
        return getAvailableOutputSlotFor(item) != -1;
    }

    private int getAvailableOutputSlotFor(Item item) {
        int[] outputs = { OUTPUT_SLOT_1 };

        // Prefer merging into existing stacks first
        for (int slot : outputs) {
            ItemStack stack = this.itemHandler.getStackInSlot(slot);
            if (!stack.isEmpty() && stack.is(item) && stack.getCount() < stack.getMaxStackSize()) {
                return slot;
            }
        }
        // Otherwise, use any empty output slot
        for (int slot : outputs) {
            if (this.itemHandler.getStackInSlot(slot).isEmpty()) {
                return slot;
            }
        }
        return -1;
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

    // Consume a fixed amount of FE this tick if available; returns true if deducted
    private boolean consumeEnergyPerTick(int fe) {
        if (fe <= 0) return true;
        if (ENERGY_STORAGE.getEnergyStored() >= fe) {
            ENERGY_STORAGE.extractEnergy(fe, false);
            return true;
        }
        return false;
    }
}