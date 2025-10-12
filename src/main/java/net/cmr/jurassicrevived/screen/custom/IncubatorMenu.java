package net.cmr.jurassicrevived.screen.custom;

import net.cmr.jurassicrevived.block.entity.custom.IncubatorBlockEntity;
import net.cmr.jurassicrevived.screen.ModMenuTypes;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

public class IncubatorMenu extends AbstractContainerMenu {
    public final IncubatorBlockEntity blockEntity;
    public final Level level;
    public final ContainerData data;

    public IncubatorMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(buf.readBlockPos()), new SimpleContainerData(6));
    }

    public IncubatorMenu(int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.INCUBATOR_MENU.get(), containerId);
        this.blockEntity = (IncubatorBlockEntity) entity;
        this.level = inventory.player.level();
        this.data = data;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        // 3 independent egg slots
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 0, 50, 35) {
            @Override public boolean mayPlace(ItemStack stack) { return stack.is(ModTags.Items.EGGS); }
            @Override
            public boolean mayPickup(Player playerIn) {
                return true; // Explicitly allow players (and JEI) to take items from this slot
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 1, 80, 35) {
            @Override public boolean mayPlace(ItemStack stack) { return stack.is(ModTags.Items.EGGS); }
            @Override
            public boolean mayPickup(Player playerIn) {
                return true; // Explicitly allow players (and JEI) to take items from this slot
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 2, 110, 35) {
            @Override public boolean mayPlace(ItemStack stack) { return stack.is(ModTags.Items.EGGS); }
            @Override
            public boolean mayPickup(Player playerIn) {
                return true; // Explicitly allow players (and JEI) to take items from this slot
            }
        });

        addDataSlots(data);
    }

    public boolean isCrafting(int slot) {
        return data.get(slot) > 0;
    }

    // Returns 0..1 float progress for given slot
    public float getProgressRatio(int slot) {
        int p = this.data.get(slot);
        int m = this.data.get(3 + slot);
        if (m == 0) return 0f;
        return Math.min(1f, Math.max(0f, (float) p / (float) m));
    }

    // Legacy integer width (0..24) if needed
    public int getScaledArrowProgress(int slot) {
        int width = 24;
        return Math.round(getProgressRatio(slot) * width);
    }

    // ... existing constants and quickMoveStack remain the same, but TE_INVENTORY_SLOT_COUNT = 3
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 3;

    public ItemStack quickMoveStack(Player player, int index) {
        Slot source = slots.get(index);
        if (source == null || !source.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = source.getItem();
        ItemStack copy = stack.copy();

        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(stack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(stack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }
        if (stack.getCount() == 0) {
            source.set(ItemStack.EMPTY);
        } else {
            source.setChanged();
        }
        source.onTake(player, stack);
        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(this.level, this.blockEntity.getBlockPos()),
                player, this.blockEntity.getBlockState().getBlock());
    }

    public void addPlayerInventory(Inventory inv) {
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 9; l++) {
                this.addSlot(new Slot(inv, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    public void addPlayerHotbar(Inventory inv) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inv, i, 8 + i * 18, 142));
        }
    }
}
