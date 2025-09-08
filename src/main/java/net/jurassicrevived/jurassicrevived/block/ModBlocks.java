package net.jurassicrevived.jurassicrevived.block;

import net.jurassicrevived.jurassicrevived.JRMod;
import net.jurassicrevived.jurassicrevived.block.custom.DecoBlock;
import net.jurassicrevived.jurassicrevived.item.ModItems;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, JRMod.MOD_ID);

    public static final RegistryObject<Block> CAT_PLUSHIE = registerBlock("cat_plushie",
            () -> new DecoBlock(BlockBehaviour.Properties.of().noOcclusion()));

    public static final RegistryObject<Block> ROYAL_FERN = registerBlock("royal_fern",
            () -> new FlowerBlock(MobEffects.UNLUCK, 0, BlockBehaviour.Properties.copy(Blocks.ALLIUM)));
    public static final RegistryObject<Block> POTTED_ROYAL_FERN = BLOCKS.register("potted_royal_fern",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ROYAL_FERN, BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM)));

    public static final RegistryObject<Block> HORSETAIL_FERN = registerBlock("horsetail_fern",
            () -> new FlowerBlock(MobEffects.UNLUCK, 0, BlockBehaviour.Properties.copy(Blocks.ALLIUM)));
    public static final RegistryObject<Block> POTTED_HORSETAIL_FERN = BLOCKS.register("potted_horsetail_fern",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), HORSETAIL_FERN, BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM)));

    public static final RegistryObject<Block> WESTERN_SWORD_FERN = registerBlock("western_sword_fern",
            () -> new FlowerBlock(MobEffects.UNLUCK, 0, BlockBehaviour.Properties.copy(Blocks.ALLIUM)));
    public static final RegistryObject<Block> POTTED_WESTERN_SWORD_FERN = BLOCKS.register("potted_western_sword_fern",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), WESTERN_SWORD_FERN, BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM)));

    public static final RegistryObject<Block> GYPSUM_STONE_BRICKS = registerBlock("gypsum_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
