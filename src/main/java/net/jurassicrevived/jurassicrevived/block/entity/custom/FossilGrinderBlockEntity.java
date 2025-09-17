package net.jurassicrevived.jurassicrevived.block.entity.custom;

import net.jurassicrevived.jurassicrevived.block.custom.FossilGrinderBlock;
import net.jurassicrevived.jurassicrevived.item.ModItems;
import net.jurassicrevived.jurassicrevived.recipe.FossilGrinderRecipe;
import net.jurassicrevived.jurassicrevived.screen.custom.FossilGrinderMenu;
import net.jurassicrevived.jurassicrevived.util.InventoryDirectionEntry;
import net.jurassicrevived.jurassicrevived.util.InventoryDirectionWrapper;
import net.jurassicrevived.jurassicrevived.util.ModTags;
import net.jurassicrevived.jurassicrevived.util.WrappedHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class FossilGrinderBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.is(ModTags.Items.FOSSILS);
                case 1, 2, 3 -> true;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private static final int SKULL_SLOT = 0;
    private static final int OUTPUT_SLOT_1 = 1;
    private static final int OUTPUT_SLOT_2 = 2;
    private static final int OUTPUT_SLOT_3 = 3;

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
                    new InventoryDirectionEntry(Direction.UP, SKULL_SLOT, true)).directionsMap;


    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 200;

    public FossilGrinderBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FOSSIL_GRINDER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> FossilGrinderBlockEntity.this.progress;
                    case 1 -> FossilGrinderBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> FossilGrinderBlockEntity.this.progress = pValue;
                    case 1 -> FossilGrinderBlockEntity.this.maxProgress = pValue;
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
        return Component.translatable("block.jurassicrevived.fossil_grinder");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FossilGrinderMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }
            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDirection = this.getBlockState().getValue(FossilGrinderBlock.FACING);
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
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("fossil_grinder.progress", progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("fossil_grinder.progress");

    }

    public void tick(Level level, BlockPos pPos, BlockState pState) {
        if (isOutputSlotEmptyOrReceivable() && hasRecipe()) {
            increaseCraftingProcess();
            setChanged(level, pPos, pState);

            if (hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void craftItem() {
        Optional<FossilGrinderRecipe> recipe = getCurrentRecipe();

        this.itemHandler.extractItem(SKULL_SLOT, 1, false);

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
        Optional<FossilGrinderRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) return false;

        // Compute dynamic result and check it can be inserted
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        ItemStack dynamicResult = recipe.get().assemble(inventory, getLevel().registryAccess());
        return !dynamicResult.isEmpty() && canInsertOneIntoAnyOutput(dynamicResult.getItem());
    }

    private Optional<FossilGrinderRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(FossilGrinderRecipe.Type.INSTANCE, inventory, this.level);
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        // Use the current recipe’s dynamic output rather than a fixed result item
        Optional<FossilGrinderRecipe> recipe = getCurrentRecipe();
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
}