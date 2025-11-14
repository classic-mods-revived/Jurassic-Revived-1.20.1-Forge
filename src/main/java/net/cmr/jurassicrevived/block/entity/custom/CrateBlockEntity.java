package net.cmr.jurassicrevived.block.entity.custom;

import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.screen.custom.CrateMenu;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class CrateBlockEntity extends BlockEntity implements MenuProvider {
    private final int size;
    public final ItemStackHandler itemHandler;
    private final LazyOptional<IItemHandler> handlerLazy;

    public CrateBlockEntity(BlockPos pos, BlockState state, int size) {
        super(ModBlockEntities.CRATE_BE.get(), pos, state);
        this.size = size;

        this.itemHandler = new ItemStackHandler(size) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };

        // Now itemHandler is non-null, safe to use in LazyOptional
        this.handlerLazy = LazyOptional.of(() -> this.itemHandler);
    }

    // Returns true if nothing meaningful is stored (no items, no progress)
    public boolean isEmptyForDrop() {
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int getSize() {
        return size;
    }

    // Optional: used by your capability wiring/event handling
    public IItemHandler getItemHandler(Direction side) {
        return this.itemHandler; // same from any side; adjust if you want sided rules
    }

    public void dropContents(Level level, BlockPos pos) {
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(level, pos, container);
    }

    public int redstoneSignal() {
        int filled = 0;
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) filled++;
        }
        if (itemHandler.getSlots() == 0) return 0;
        return Math.round((filled / (float) itemHandler.getSlots()) * 15f);
    }

    // --------------------
    // NBT persistence (1.20.1 style)
    // --------------------

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("crate.size", this.size);
        tag.put("crate.inventory", itemHandler.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        // 'size' is final, so we don't reassign it from NBT.
        if (tag.contains("crate.inventory")) {
            itemHandler.deserializeNBT(tag.getCompound("crate.inventory"));
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(
                size <= 9
                        ? "block.jurassicrevived.wood_crate"
                        : "block.jurassicrevived.iron_crate"
        );
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new CrateMenu(id, inv, this);
    }

    

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
         if (cap == ForgeCapabilities.ITEM_HANDLER) {
             return handlerLazy.cast();
         }
         return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        handlerLazy.invalidate();
    }

    
}
