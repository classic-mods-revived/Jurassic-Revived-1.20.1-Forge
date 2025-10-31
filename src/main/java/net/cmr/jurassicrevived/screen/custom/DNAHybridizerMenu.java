package net.cmr.jurassicrevived.screen.custom;

import net.cmr.jurassicrevived.block.entity.custom.DNAHybridizerBlockEntity;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.screen.ModMenuTypes;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

public class DNAHybridizerMenu extends AbstractContainerMenu {
    public final DNAHybridizerBlockEntity blockEntity;
    public final Level level;
    public final ContainerData data;

    public DNAHybridizerMenu(int containerId, Inventory inventory, FriendlyByteBuf data) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(2));
    }

    public DNAHybridizerMenu(int containerId, Inventory inventory, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.DNA_HYBRIDIZER_MENU.get(), containerId);
        blockEntity = ((DNAHybridizerBlockEntity) entity);
        this.level = inventory.player.level();
        this.data = data;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 0, 8, 25) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModTags.Items.DNA);
            }
            @Override
            public boolean mayPickup(Player playerIn) {
                return true; // Explicitly allow players (and JEI) to take items from this slot
            }
        });

        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 1, 26, 25) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModTags.Items.DNA);
            }
            @Override
            public boolean mayPickup(Player playerIn) {
                return true; // Explicitly allow players (and JEI) to take items from this slot
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 2, 44, 25) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModTags.Items.DNA);
            }
            @Override
            public boolean mayPickup(Player playerIn) {
                return true; // Explicitly allow players (and JEI) to take items from this slot
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 3, 62, 25) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModTags.Items.DNA);
            }
            @Override
            public boolean mayPickup(Player playerIn) {
                return true; // Explicitly allow players (and JEI) to take items from this slot
            }
        });

        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 4, 8, 43) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModTags.Items.DNA);
            }
            @Override
            public boolean mayPickup(Player playerIn) {
                return true; // Explicitly allow players (and JEI) to take items from this slot
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 5, 26, 43) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModTags.Items.DNA);
            }
            @Override
            public boolean mayPickup(Player playerIn) {
                return true; // Explicitly allow players (and JEI) to take items from this slot
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 6, 44, 43) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModTags.Items.DNA);
            }
            @Override
            public boolean mayPickup(Player playerIn) {
                return true; // Explicitly allow players (and JEI) to take items from this slot
            }
        });

        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 7, 62, 43) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() == ModItems.FROG_DNA.get();
            }
            @Override
            public boolean mayPickup(Player playerIn) {
                return true; // Explicitly allow players (and JEI) to take items from this slot
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 8, 83, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
            @Override
            public boolean mayPickup(Player playerIn) {
                return true; // Explicitly allow players (and JEI) to take items from this slot
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 9, 134, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int arrowPixelSize = 24;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 10;  // must be the number of slots you have!

    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            // Restrict to the two input slots only (exclude output slots).
            int teInputStart = TE_INVENTORY_FIRST_SLOT_INDEX;          // handler indices 0-1
            int teInputEndExclusive = TE_INVENTORY_FIRST_SLOT_INDEX + 10;
            if (!moveItemStackTo(sourceStack, teInputStart, teInputEndExclusive, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(this.level, this.blockEntity.getBlockPos()),
                player, this.blockEntity.getBlockState().getBlock());
    }

    public void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 9; l++) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    public void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
