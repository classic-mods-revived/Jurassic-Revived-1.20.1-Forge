package net.jurassicrevived.jurassicrevived.block.entity.custom;

import net.jurassicrevived.jurassicrevived.block.ModBlocks;
import net.jurassicrevived.jurassicrevived.block.custom.FossilCleanerBlock;
import net.jurassicrevived.jurassicrevived.recipe.FossilCleanerRecipe;
import net.jurassicrevived.jurassicrevived.screen.custom.FossilCleanerMenu;
import net.jurassicrevived.jurassicrevived.util.InventoryDirectionEntry;
import net.jurassicrevived.jurassicrevived.util.InventoryDirectionWrapper;
import net.jurassicrevived.jurassicrevived.util.WrappedHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.FluidTags;
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
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class FossilCleanerBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> {
                    yield stack.getItem() == Items.WATER_BUCKET
                            || stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM)
                            .map(fh -> {
                                for (int t = 0; t < fh.getTanks(); t++) {
                                    var fs = fh.getFluidInTank(t);
                                    if (!fs.isEmpty() && fs.getFluid().is(FluidTags.WATER)) return true;
                                }
                                return false;
                            })
                            .orElse(false);
                }
                case 1 -> stack.getItem() == ModBlocks.STONE_FOSSIL.get().asItem() || stack.getItem() == ModBlocks.DEEPSLATE_FOSSIL.get().asItem();
                case 2, 3, 4 -> true;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private static final int WATER_SLOT = 0;
    private static final int FOSSILBLOCK_SLOT = 1;
    private static final int OUTPUT_SLOT_1 = 2;
    private static final int OUTPUT_SLOT_2 = 3;
    private static final int OUTPUT_SLOT_3 = 4;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            new InventoryDirectionWrapper(itemHandler,
                    new InventoryDirectionEntry(Direction.DOWN, OUTPUT_SLOT_1, false),
                    new InventoryDirectionEntry(Direction.DOWN, OUTPUT_SLOT_2, false),
                    new InventoryDirectionEntry(Direction.DOWN, OUTPUT_SLOT_3, false),
                    new InventoryDirectionEntry(Direction.NORTH, OUTPUT_SLOT_1, false),
                    new InventoryDirectionEntry(Direction.NORTH, OUTPUT_SLOT_2, false),
                    new InventoryDirectionEntry(Direction.NORTH, OUTPUT_SLOT_3, false),
                    new InventoryDirectionEntry(Direction.SOUTH, OUTPUT_SLOT_1, false),
                    new InventoryDirectionEntry(Direction.SOUTH, OUTPUT_SLOT_2, false),
                    new InventoryDirectionEntry(Direction.SOUTH, OUTPUT_SLOT_3, false),
                    new InventoryDirectionEntry(Direction.EAST, OUTPUT_SLOT_1, false),
                    new InventoryDirectionEntry(Direction.EAST, OUTPUT_SLOT_2, false),
                    new InventoryDirectionEntry(Direction.EAST, OUTPUT_SLOT_3, false),
                    new InventoryDirectionEntry(Direction.WEST, OUTPUT_SLOT_1, false),
                    new InventoryDirectionEntry(Direction.WEST, OUTPUT_SLOT_2, false),
                    new InventoryDirectionEntry(Direction.WEST, OUTPUT_SLOT_3, false),
                    new InventoryDirectionEntry(Direction.UP, WATER_SLOT, true),
                    new InventoryDirectionEntry(Direction.UP, FOSSILBLOCK_SLOT, true)).directionsMap;

    private LazyOptional <IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 200;
    private int fluidCraftAmount = 250;

    private final FluidTank FLUID_TANK = createFluidTank();

    // Public-facing, input-only fluid handler: allows filling but never draining
    private final IFluidHandler INPUT_ONLY_HANDLER = new IFluidHandler() {
        @Override
        public int getTanks() {
            return FLUID_TANK.getTanks();
        }

        @Override
        public FluidStack getFluidInTank(int tank) {
            return FLUID_TANK.getFluidInTank(tank);
        }

        @Override
        public int getTankCapacity(int tank) {
            return FLUID_TANK.getTankCapacity(tank);
        }

        @Override
        public boolean isFluidValid(int tank, FluidStack stack) {
            // Same rule as the internal tank: water only
            return stack.getFluid() == Fluids.WATER;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            // Allow filling normally
            return FLUID_TANK.fill(resource, action);
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            // Block any external draining
            return FluidStack.EMPTY;
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            // Block any external draining
            return FluidStack.EMPTY;
        }
    };
    private FluidTank createFluidTank() {
        return new FluidTank(16000) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                if(!level.isClientSide()) {
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return stack.getFluid() == Fluids.WATER;
            }
        };
    }

    public FossilCleanerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FOSSIL_CLEANER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> FossilCleanerBlockEntity.this.progress;
                    case 1 -> FossilCleanerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> FossilCleanerBlockEntity.this.progress = pValue;
                    case 1 -> FossilCleanerBlockEntity.this.maxProgress = pValue;
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
        return Component.translatable("block.jurassicrevived.fossil_cleaner");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FossilCleanerMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {


        if(cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }

        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }
            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDirection = this.getBlockState().getValue(FossilCleanerBlock.FACING);
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

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        // Expose input-only fluid handler to the outside
        lazyFluidHandler = LazyOptional.of(() -> INPUT_ONLY_HANDLER);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("fossil_cleaning.progress", progress);
        pTag.putInt("fossil_cleaning.maxProgress", maxProgress);
        pTag = FLUID_TANK.writeToNBT(pTag);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        // Fix key to match saveAdditional
        progress = pTag.getInt("fossil_cleaning.progress");
        FLUID_TANK.readFromNBT(pTag);

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

    public void tick(Level level, BlockPos pPos, BlockState pState) {
        // Always try to pull fluid from the water slot into the tank first
        fillUpOnFluid();

        if (isOutputSlotEmptyOrReceivable() && hasRecipe()) {
            increaseCraftingProcess();
            setChanged(level, pPos, pState);

            if (hasProgressFinished()) {
                craftItem();
                extractFluid();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void extractFluid() {
        this.FLUID_TANK.drain(fluidCraftAmount, IFluidHandler.FluidAction.EXECUTE);
    }

    private void craftItem() {
        Optional<FossilCleanerRecipe> recipe = getCurrentRecipe();

        fillUpOnFluid();

        this.itemHandler.extractItem(FOSSILBLOCK_SLOT, 1, false);

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

    private void fillUpOnFluid() {
        if(hasFluidSourceInSlot(WATER_SLOT)) {
            transferItemFluidToTank(WATER_SLOT);
        }
    }

    private boolean hasEnoughFluidToCraft() {
        return this.FLUID_TANK.getFluidAmount() >= fluidCraftAmount;
    }

    private void transferItemFluidToTank(int waterSlot) {
        ItemStack stackInSlot = this.itemHandler.getStackInSlot(waterSlot);
        stackInSlot.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(iFluidHandlerItem -> {
            // Drain WATER specifically
            FluidStack simulatedDrain = iFluidHandlerItem.drain(
                    new FluidStack(Fluids.WATER, Integer.MAX_VALUE),
                    IFluidHandler.FluidAction.SIMULATE
            );
            if (simulatedDrain.isEmpty()) {
                return;
            }

            int space = this.FLUID_TANK.getSpace();
            if (space <= 0) {
                return;
            }

            FluidStack toMove = new FluidStack(Fluids.WATER, Math.min(simulatedDrain.getAmount(), space));

            int accepted = this.FLUID_TANK.fill(toMove, IFluidHandler.FluidAction.SIMULATE);
            if (accepted <= 0) {
                return;
            }

            FluidStack drained = iFluidHandlerItem.drain(new FluidStack(Fluids.WATER, accepted), IFluidHandler.FluidAction.EXECUTE);
            if (drained.isEmpty()) {
                return;
            }

            int filled = this.FLUID_TANK.fill(drained, IFluidHandler.FluidAction.EXECUTE);
            if (filled > 0) {
                // Important: do NOT delete the item; replace the slot with the capability's container state
                ItemStack container = iFluidHandlerItem.getContainer();
                this.itemHandler.setStackInSlot(WATER_SLOT, container);
            }
        });
    }

    private void fillTankWithFluid(FluidStack stack, @NotNull ItemStack container) {
        this.FLUID_TANK.fill(new FluidStack(stack.getFluid(), stack.getAmount()), IFluidHandler.FluidAction.EXECUTE);

        this.itemHandler.extractItem(WATER_SLOT, 1, false);
        this.itemHandler.insertItem(WATER_SLOT, container, false);
    }

    private boolean hasFluidSourceInSlot(int waterSlot) {
        return this.itemHandler.getStackInSlot(waterSlot).getCount() > 0 &&
                this.itemHandler.getStackInSlot(waterSlot).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
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
        Optional<FossilCleanerRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) return false;

        // Require enough fluid to craft
        if (!hasEnoughFluidToCraft()) return false;

        // Compute dynamic result and check it can be inserted
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        ItemStack dynamicResult = recipe.get().assemble(inventory, getLevel().registryAccess());
        return !dynamicResult.isEmpty() && canInsertOneIntoAnyOutput(dynamicResult.getItem());
    }

    private Optional<FossilCleanerRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(FossilCleanerRecipe.Type.INSTANCE, inventory, this.level);
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        // Use the current recipe’s dynamic output rather than a fixed result item
        Optional<FossilCleanerRecipe> recipe = getCurrentRecipe();
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
        int[] outputs = { OUTPUT_SLOT_1, OUTPUT_SLOT_2, OUTPUT_SLOT_3 };

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

    public FluidStack getFluid() {
        return FLUID_TANK.getFluid();
    }
}