package net.jurassicrevived.jurassicrevived.block.entity.custom;

import net.jurassicrevived.jurassicrevived.item.ModItems;
import net.jurassicrevived.jurassicrevived.recipe.DNAExtractorRecipe;
import net.jurassicrevived.jurassicrevived.screen.custom.DNAExtractorMenu;
import net.jurassicrevived.jurassicrevived.util.ModTags;
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

import java.util.Optional;

public class DNAExtractorBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.getItem() == ModItems.AMPOULE.get();
                case 1 -> stack.is(ModTags.Items.TISSUES);
                case 2, 3, 4 -> true;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private static final int AMPOULE_SLOT = 0;
    private static final int MATERIAL_INPUT = 1;
    private static final int OUTPUT_SLOT_1 = 2;
    private static final int OUTPUT_SLOT_2 = 3;
    private static final int OUTPUT_SLOT_3 = 4;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;

    public DNAExtractorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DNA_EXTRACTOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> DNAExtractorBlockEntity.this.progress;
                    case 1 -> DNAExtractorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> DNAExtractorBlockEntity.this.progress = pValue;
                    case 1 -> DNAExtractorBlockEntity.this.maxProgress = pValue;
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

    @Override
    public Component getDisplayName() {
        return Component.literal("Gem Empowering Station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new DNAExtractorMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
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
        pTag.putInt("gem_empowering_station.progress", progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("gem_empowering_station.progress");

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
        Optional<DNAExtractorRecipe> recipe = getCurrentRecipe();

        this.itemHandler.extractItem(AMPOULE_SLOT, 1, false);
        this.itemHandler.extractItem(MATERIAL_INPUT, 1, false);

        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());
        Item resultItem = result.getItem();
        int resultCount = result.getCount();

        int targetSlot = getAvailableOutputSlotFor(resultItem);
        if (targetSlot != -1) {
            ItemStack current = this.itemHandler.getStackInSlot(targetSlot);

            if (current.isEmpty()) {
                // Place the result directly into the empty slot
                this.itemHandler.setStackInSlot(targetSlot, result.copy());
            } else {
                // Merge into existing stack, cap at max stack size
                int existing = current.getCount();
                int max = current.getMaxStackSize();
                int toPlace = Math.min(existing + resultCount, max);
                int remainder = (existing + resultCount) - toPlace;

                current.setCount(toPlace);
                this.itemHandler.setStackInSlot(targetSlot, current);

                // Try to place any remainder into another output slot (optional)
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
                            // If there's still remainder beyond toPlace2, it will be ignored here.
                            // You could loop until remainder is 0 if you want full spill behavior.
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
        Optional<DNAExtractorRecipe> recipe = getCurrentRecipe();

        return recipe.isPresent() && canInsertOneIntoAnyOutput(recipe.get().getResultItem(getLevel().registryAccess()).getItem());
    }

    private Optional<DNAExtractorRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(DNAExtractorRecipe.Type.INSTANCE, inventory, this.level);
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        // Use the current recipe’s output item rather than a hardcoded item
        Optional<DNAExtractorRecipe> recipe = getCurrentRecipe();
        return recipe.isPresent() && canInsertOneIntoAnyOutput(recipe.get().getResultItem(getLevel().registryAccess()).getItem());
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