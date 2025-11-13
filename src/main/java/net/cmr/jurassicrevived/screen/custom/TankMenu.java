package net.cmr.jurassicrevived.screen.custom;

import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.block.entity.custom.TankBlockEntity;
import net.cmr.jurassicrevived.screen.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

public class TankMenu extends AbstractContainerMenu {
    public final TankBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    // Data indices
    // 0: fluid amount (low 16 bits)
    // 1: fluid amount (high 16 bits)
    // 2: tank capacity (low 16 bits)
    // 3: tank capacity (high 16 bits)

    public TankMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, resolveFromPacketOrLook(inv, extraData), new SimpleContainerData(4));
    }

    public TankMenu(int pContainerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.TANK_MENU.get(), pContainerId);
        checkContainerSize(inv, 2);
        this.level = inv.player.level();
        this.data = data;

        if (!(blockEntity instanceof TankBlockEntity t)) {
            throw new IllegalStateException("TankMenu: Expected TankBlockEntity, got " + (blockEntity == null ? "null" : blockEntity.getClass().getName()));
        }
        this.blockEntity = t;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.addSlot(new SlotItemHandler(this.blockEntity.itemHandler, 0, 44, 34));
        this.addSlot(new SlotItemHandler(this.blockEntity.itemHandler, 1, 116, 34) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        addDataSlots(this.data);
        // Initialize client with current values (server will tick-update via setChanged/sendBlockUpdated already)
        pushFluidDataToContainer();
    }

    private void pushFluidDataToContainer() {
        int amount = blockEntity.getTank(null).getFluidInTank(0).getAmount();
        int capacity = blockEntity.getTank(null).getTankCapacity(0);
        data.set(0, amount & 0xFFFF);
        data.set(1, (amount >>> 16) & 0xFFFF);
        data.set(2, capacity & 0xFFFF);
        data.set(3, (capacity >>> 16) & 0xFFFF);
    }

    // Accessors for the screen
    public int getSyncedFluidAmount() {
        return (data.get(1) << 16) | (data.get(0) & 0xFFFF);
    }

    public int getSyncedCapacity() {
        return (data.get(3) << 16) | (data.get(2) & 0xFFFF);
    }

    // Try buf -> BlockPos; if null or invalid, fall back to the BE under the player's crosshair
    private static BlockEntity resolveFromPacketOrLook(Inventory inv, FriendlyByteBuf buf) {
        Level lvl = inv.player.level();
        if (buf != null) {
            BlockPos pos = buf.readBlockPos();
            BlockEntity be = lvl.getBlockEntity(pos);
            if (be != null) return be;
        }
        if (lvl != null && inv.player != null && inv.player.pick(5.0D, 0.0F, false) != null) {
            var hit = inv.player.pick(5.0D, 0.0F, false);
            if (hit != null && hit.getType() == net.minecraft.world.phys.HitResult.Type.BLOCK) {
                BlockPos pos = ((net.minecraft.world.phys.BlockHitResult) hit).getBlockPos();
                BlockEntity be = lvl.getBlockEntity(pos);
                if (be != null) return be;
            }
        }
        throw new IllegalStateException("TankMenu: Could not resolve TankBlockEntity (no buffer and no targeted block entity).");
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 2;

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, ModBlocks.TANK.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}