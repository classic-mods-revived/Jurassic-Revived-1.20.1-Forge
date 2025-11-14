package net.cmr.jurassicrevived.screen.custom;

import net.cmr.jurassicrevived.block.entity.custom.CrateBlockEntity;
import net.cmr.jurassicrevived.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.SlotItemHandler;

public class CrateMenu extends AbstractContainerMenu {
    public final CrateBlockEntity blockEntity;
    private final Level level;

    // Buffer ctor (used client-side)
    public CrateMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, (CrateBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos()));
    }

    // Direct ctor (server-side)
    public CrateMenu(int id, Inventory inv, CrateBlockEntity be) {
        super(resolveType(be), id);
        this.blockEntity = be;
        this.level = inv.player.level();

        // Player inventory + hotbar
        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        int size = be.getSize();
        if (size == 9) {
            // One row of 9 at (8,62)
            int startX = 8;
            int startY = 62;
            for (int c = 0; c < 9; c++) {
                int idx = c;
                this.addSlot(new SlotItemHandler(be.itemHandler, idx, startX + c * 18, startY));
            }
        } else {
            // 18 slots: two rows of 9, y=44 and y=62
            int startX = 8;
            int row1Y = 44;
            int row2Y = 62;
            for (int c = 0; c < 9; c++) {
                this.addSlot(new SlotItemHandler(be.itemHandler, c, startX + c * 18, row1Y));
            }
            for (int c = 0; c < 9; c++) {
                this.addSlot(new SlotItemHandler(be.itemHandler, 9 + c, startX + c * 18, row2Y));
            }
        }
    }

    private static MenuType<?> resolveType(CrateBlockEntity be) {
        return be.getSize() == 9
                ? ModMenuTypes.WOOD_CRATE_MENU.get()
                : ModMenuTypes.IRON_CRATE_MENU.get();
    }

    // Inventory move logic
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT =
            PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT =
            HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT; // 36
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;

    @Override
    public net.minecraft.world.item.ItemStack quickMoveStack(Player playerIn, int index) {
        Slot source = slots.get(index);
        if (source == null || !source.hasItem()) {
            return net.minecraft.world.item.ItemStack.EMPTY;
        }

        var sourceStack = source.getItem();
        var copy = sourceStack.copy();

        int teSlots = blockEntity.itemHandler.getSlots(); // 9 or 18
        int teFirst = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT; // 36
        int teEnd = teFirst + teSlots; // 45 or 54

        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // From player -> crate
            if (!moveItemStackTo(sourceStack, teFirst, teEnd, false)) {
                return net.minecraft.world.item.ItemStack.EMPTY;
            }
        } else if (index >= teFirst && index < teEnd) {
            // From crate -> player
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX,
                    VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return net.minecraft.world.item.ItemStack.EMPTY;
            }
        } else {
            return net.minecraft.world.item.ItemStack.EMPTY;
        }

        if (sourceStack.isEmpty()) {
            source.set(net.minecraft.world.item.ItemStack.EMPTY);
        } else {
            source.setChanged();
        }

        source.onTake(playerIn, sourceStack);
        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(
                ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player,
                blockEntity.getBlockState().getBlock()
        );
    }

    private void addPlayerInventory(Inventory inv) {
        for (int r = 0; r < 3; ++r) {
            for (int c = 0; c < 9; ++c) {
                this.addSlot(new Slot(inv, c + r * 9 + 9,
                        8 + c * 18,
                        84 + r * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inv) {
        for (int c = 0; c < 9; ++c) {
            this.addSlot(new Slot(inv, c,
                    8 + c * 18,
                    142));
        }
    }
}