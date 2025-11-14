package net.cmr.jurassicrevived.block.entity.custom;

import net.cmr.jurassicrevived.Config;
import net.cmr.jurassicrevived.block.custom.DNAHybridizerBlock;
import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.energy.ModEnergyStorage;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.recipe.DNAHybridizerRecipe;
import net.cmr.jurassicrevived.screen.custom.DNAHybridizerMenu;
import net.cmr.jurassicrevived.sounds.MachineHumLoopSound;
import net.cmr.jurassicrevived.util.InventoryDirectionEntry;
import net.cmr.jurassicrevived.util.InventoryDirectionWrapper;
import net.cmr.jurassicrevived.util.ModTags;
import net.cmr.jurassicrevived.util.WrappedHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
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
import net.minecraft.world.item.crafting.Ingredient;
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

public class DNAHybridizerBlockEntity extends BlockEntity implements MenuProvider {
    private @Nullable MachineHumLoopSound humSound;

    public static void clientTick(Level level, BlockPos pos, BlockState state, DNAHybridizerBlockEntity be) {
        if (!level.isClientSide) return;

        boolean lit = state.hasProperty(DNAHybridizerBlock.LIT)
                && state.getValue(DNAHybridizerBlock.LIT);

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

    public final ItemStackHandler itemHandler = new ItemStackHandler(10) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            // slots 0..8 = inputs (DNA), slot 9 = output
            return switch (slot) {
                case 0, 1, 2, 3, 4, 5, 6, 7 -> stack.is(ModTags.Items.DNA);
                case 8 -> stack.getItem() == ModItems.FROG_DNA.get();
                case 9 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private static final int OUTPUT = 9;
    // convenient constants for inputs 0..8
    private static final int[] INPUT_SLOTS = {0,1,2,3,4,5,6,7,8};

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            new InventoryDirectionWrapper(itemHandler,
                    new InventoryDirectionEntry(Direction.DOWN, OUTPUT, false),
                    new InventoryDirectionEntry(Direction.NORTH, OUTPUT, false),
                    new InventoryDirectionEntry(Direction.SOUTH, OUTPUT, false),
                    new InventoryDirectionEntry(Direction.EAST, OUTPUT, false),
                    new InventoryDirectionEntry(Direction.WEST, OUTPUT, false),
                    // allow insertion to all 9 inputs from top
                    new InventoryDirectionEntry(Direction.UP, 0, true),
                    new InventoryDirectionEntry(Direction.UP, 1, true),
                    new InventoryDirectionEntry(Direction.UP, 2, true),
                    new InventoryDirectionEntry(Direction.UP, 3, true),
                    new InventoryDirectionEntry(Direction.UP, 4, true),
                    new InventoryDirectionEntry(Direction.UP, 5, true),
                    new InventoryDirectionEntry(Direction.UP, 6, true),
                    new InventoryDirectionEntry(Direction.UP, 7, true),
                    new InventoryDirectionEntry(Direction.UP, 8, true)
            ).directionsMap;


    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 3000;

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

    public DNAHybridizerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DNA_HYBRIDIZER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> DNAHybridizerBlockEntity.this.progress;
                    case 1 -> DNAHybridizerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> DNAHybridizerBlockEntity.this.progress = pValue;
                    case 1 -> DNAHybridizerBlockEntity.this.maxProgress = pValue;
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
        return Component.translatable("block.jurassicrevived.dna_hybridizer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new DNAHybridizerMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }
            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDirection = this.getBlockState().getValue(DNAHybridizerBlock.FACING);
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
        // Only create a non-empty LazyOptional if storage exists
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
        pTag.putInt("dna_hybridizer.progress", progress);
        if (Config.REQUIRE_POWER) {
            pTag.putInt("dna_hybridizer.energy", this.ENERGY_STORAGE.getEnergyStored());
        }
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("dna_hybridizer.progress");
        if (Config.REQUIRE_POWER) {
            this.ENERGY_STORAGE.setEnergy(pTag.getInt("dna_hybridizer.energy"));
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
            level.setBlockAndUpdate(pPos, pState.setValue(DNAHybridizerBlock.LIT, true));
            setChanged(level, pPos, pState);

            if (hasProgressFinished()) {
                craftItem();
                resetProgress();
                level.setBlockAndUpdate(pPos, pState.setValue(DNAHybridizerBlock.LIT, false));
            }
        } else {
            resetProgress();
            level.setBlockAndUpdate(pPos, pState.setValue(DNAHybridizerBlock.LIT, false));
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
        Optional<DNAHybridizerRecipe> recipeOpt = getCurrentRecipe();
        if (recipeOpt.isEmpty()) return;
        DNAHybridizerRecipe recipe = recipeOpt.get();

        // Build required list: normals (0..7) + catalyst if present
        java.util.List<Ingredient> required = new java.util.ArrayList<>();
        NonNullList<Ingredient> normals = recipe.getIngredients();
        for (int i = 0; i < Math.min(8, normals.size()); i++) {
            Ingredient ing = normals.get(i);
            if (!ing.isEmpty()) required.add(ing);
        }
        if (recipe.getCatalyst() != null && !recipe.getCatalyst().isEmpty()) {
            required.add(recipe.getCatalyst());
        }

        // Track matches -> slots to extract
        boolean[] used = new boolean[required.size()];
        java.util.List<Integer> toExtractSlots = new java.util.ArrayList<>();

        for (int slot : INPUT_SLOTS) {
            ItemStack s = itemHandler.getStackInSlot(slot);
            if (s.isEmpty()) continue;

            for (int i = 0; i < required.size(); i++) {
                if (!used[i] && required.get(i).test(s)) {
                    used[i] = true;
                    toExtractSlots.add(slot);
                    break;
                }
            }
        }

        for (boolean u : used) {
            if (!u) return; // missing one of the required ingredients/catalyst
        }

        for (int slot : toExtractSlots) {
            this.itemHandler.extractItem(slot, 1, false);
        }

        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }
        ItemStack result = recipe.assemble(inv, getLevel().registryAccess());
        ItemStack current = this.itemHandler.getStackInSlot(OUTPUT);
        if (current.isEmpty()) {
            this.itemHandler.setStackInSlot(OUTPUT, result.copy());
        } else if (ItemStack.isSameItemSameTags(current, result) && current.getCount() < current.getMaxStackSize()) {
            current.grow(Math.min(result.getCount(), current.getMaxStackSize() - current.getCount()));
            this.itemHandler.setStackInSlot(OUTPUT, current);
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
        Optional<DNAHybridizerRecipe> recipeOpt = getCurrentRecipe();
        if (recipeOpt.isEmpty()) return false;
        DNAHybridizerRecipe recipe = recipeOpt.get();

        // Build required list: normals (0..7) + catalyst if present
        java.util.List<Ingredient> required = new java.util.ArrayList<>();
        NonNullList<Ingredient> normals = recipe.getIngredients();
        for (int i = 0; i < Math.min(8, normals.size()); i++) {
            Ingredient ing = normals.get(i);
            if (!ing.isEmpty()) required.add(ing);
        }
        if (recipe.getCatalyst() != null && !recipe.getCatalyst().isEmpty()) {
            required.add(recipe.getCatalyst());
        }

        // Gather current inputs
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>(9);
        for (int slot : INPUT_SLOTS) {
            ItemStack s = itemHandler.getStackInSlot(slot);
            if (!s.isEmpty()) inputs.add(s);
        }

        if (inputs.size() != required.size()) return false;

        boolean[] used = new boolean[required.size()];
        for (ItemStack in : inputs) {
            boolean matched = false;
            for (int i = 0; i < required.size(); i++) {
                if (!used[i] && required.get(i).test(in)) {
                    used[i] = true;
                    matched = true;
                    break;
                }
            }
            if (!matched) return false;
        }

        // Output capacity check
        SimpleContainer checkInv = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            checkInv.setItem(i, itemHandler.getStackInSlot(i));
        }
        ItemStack plannedResult = recipe.assemble(checkInv, getLevel().registryAccess());
        ItemStack currentOut = itemHandler.getStackInSlot(OUTPUT);
        if (plannedResult.isEmpty()) return false;
        if (currentOut.isEmpty()) return true;
        if (!ItemStack.isSameItemSameTags(currentOut, plannedResult)) return false;
        return currentOut.getCount() + plannedResult.getCount() <= currentOut.getMaxStackSize();
    }

    private Optional<DNAHybridizerRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(DNAHybridizerRecipe.Type.INSTANCE, inventory, this.level);
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        // Use the current recipe’s dynamic output rather than a fixed result item
        Optional<DNAHybridizerRecipe> recipe = getCurrentRecipe();
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
        int[] outputs = { OUTPUT };

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