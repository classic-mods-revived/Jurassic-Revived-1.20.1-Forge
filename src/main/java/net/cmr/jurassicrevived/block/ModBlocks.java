package net.cmr.jurassicrevived.block;

import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.Config;
import net.cmr.jurassicrevived.block.custom.*;
import net.cmr.jurassicrevived.block.custom.PipeBlock;
import net.cmr.jurassicrevived.entity.ModEntities;
import net.cmr.jurassicrevived.item.ModItems;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
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

    // Helper: conditionally register a block item (hide power pipe when disabled)
    private static <T extends Block> void registerBlockItemConditional(String name, RegistryObject<T> block, Supplier<Boolean> include) {
        if (include.get()) {
            ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        }
    }

    public static final RegistryObject<Block> CAT_PLUSHIE = registerBlock("cat_plushie",
            () -> new DecoBlock(BlockBehaviour.Properties.of().noOcclusion().sound(SoundType.WOOL)));

    public static final RegistryObject<Block> TRASH_CAN = registerBlock("trash_can",
            () -> new TrashBlock(BlockBehaviour.Properties.of().noOcclusion()));

    public static final RegistryObject<Block> BENCH = registerBlock("bench",
            () -> new BenchBlock(BlockBehaviour.Properties.of().noOcclusion()));

    public static final RegistryObject<Block> LIGHT_POST = registerBlock("light_post",
            () -> new LightPostBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion().lightLevel(state -> 15)));

    public static final RegistryObject<Block> ITEM_PIPE = registerBlock("item_pipe",
            () -> new PipeBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion(), PipeBlock.Transport.ITEMS));

    public static final RegistryObject<Block> FLUID_PIPE = registerBlock("fluid_pipe",
            () -> new PipeBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion(), PipeBlock.Transport.FLUIDS));

    public static final RegistryObject<Block> POWER_PIPE = registerBlock("power_pipe",
            () -> new PipeBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion(), PipeBlock.Transport.ENERGY));


    public static final RegistryObject<Block> GENERATOR = registerBlock("generator",
            () -> new GeneratorBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> DNA_EXTRACTOR = registerBlock("dna_extractor",
            () -> new DNAExtractorBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> FOSSIL_GRINDER = registerBlock("fossil_grinder",
            () -> new FossilGrinderBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> FOSSIL_CLEANER = registerBlock("fossil_cleaner",
            () -> new FossilCleanerBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> DNA_HYBRIDIZER = registerBlock("dna_hybridizer",
            () -> new DNAHybridizerBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> EMBRYONIC_MACHINE = registerBlock("embryonic_machine",
            () -> new EmbryonicMachineBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> EMBRYO_CALCIFICATION_MACHINE = registerBlock("embryo_calcification_machine",
            () -> new EmbryoCalcificationMachineBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> INCUBATOR = registerBlock("incubator",
            () -> new IncubatorBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> WHITE_GENERATOR = registerBlock("white_generator",
            () -> new GeneratorBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> WHITE_DNA_EXTRACTOR = registerBlock("white_dna_extractor",
            () -> new DNAExtractorBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> WHITE_FOSSIL_GRINDER = registerBlock("white_fossil_grinder",
            () -> new FossilGrinderBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> WHITE_FOSSIL_CLEANER = registerBlock("white_fossil_cleaner",
            () -> new FossilCleanerBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> WHITE_DNA_HYBRIDIZER = registerBlock("white_dna_hybridizer",
            () -> new DNAHybridizerBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> WHITE_EMBRYONIC_MACHINE = registerBlock("white_embryonic_machine",
            () -> new EmbryonicMachineBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> WHITE_EMBRYO_CALCIFICATION_MACHINE = registerBlock("white_embryo_calcification_machine",
            () -> new EmbryoCalcificationMachineBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> WHITE_INCUBATOR = registerBlock("white_incubator",
            () -> new IncubatorBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));

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

    public static final RegistryObject<Block> GYPSUM_STONE = registerBlock("gypsum_stone",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> GYPSUM_COBBLESTONE = registerBlock("gypsum_cobblestone",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> GYPSUM_STONE_BRICKS = registerBlock("gypsum_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SMOOTH_GYPSUM_STONE = registerBlock("smooth_gypsum_stone",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CHISELED_GYPSUM_STONE = registerBlock("chiseled_gypsum_stone",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> GYPSUM_BRICK_STAIRS = registerBlock("gypsum_brick_stairs",
            () -> new StairBlock(ModBlocks.GYPSUM_STONE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> GYPSUM_BRICK_SLAB = registerBlock("gypsum_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> GYPSUM_BRICK_WALL = registerBlock("gypsum_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> FENCE_LIGHT = registerBlock("fence_light",
            () -> new FenceLightBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion().lightLevel(state -> 15)));

    public static final RegistryObject<Block> LOW_SECURITY_FENCE_POLE = registerBlock("low_security_fence_pole",
            () -> new FencePoleBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion(), FencePoleBlock.Tier.LOW));
    public static final RegistryObject<Block> LOW_SECURITY_FENCE_WIRE = registerBlock("low_security_fence_wire",
            () -> new FenceWireBlock(BlockBehaviour.Properties.of().strength(0.5F).noOcclusion(), FenceWireBlock.Tier.LOW));
    public static final RegistryObject<Block> MEDIUM_SECURITY_FENCE_POLE = registerBlock("medium_security_fence_pole",
            () -> new FencePoleBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion(), FencePoleBlock.Tier.MEDIUM));
    public static final RegistryObject<Block> MEDIUM_SECURITY_FENCE_WIRE = registerBlock("medium_security_fence_wire",
            () -> new FenceWireBlock(BlockBehaviour.Properties.of().strength(0.5F).noOcclusion(), FenceWireBlock.Tier.MEDIUM));

    public static final RegistryObject<Block> STONE_FOSSIL = registerBlock("stone_fossil",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_FOSSIL = registerBlock("deepslate_fossil",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> AMBER_ORE = registerBlock("amber_ore",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_ICE_SHARD_ORE = registerBlock("deepslate_ice_shard_ore",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> REINFORCED_STONE = registerBlock("reinforced_stone",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> REINFORCED_STONE_BRICKS = registerBlock("reinforced_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CHISELED_REINFORCED_STONE = registerBlock("chiseled_reinforced_stone",
            () -> new Block(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> REINFORCED_BRICK_STAIRS = registerBlock("reinforced_brick_stairs",
            () -> new StairBlock(ModBlocks.REINFORCED_STONE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> REINFORCED_BRICK_SLAB = registerBlock("reinforced_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> REINFORCED_BRICK_WALL = registerBlock("reinforced_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops()));


    public static final RegistryObject<Block> HATCHED_ALBERTOSAURUS_EGG = registerBlock("hatched_albertosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ALBERTOSAURUS));

    public static final RegistryObject<Block> HATCHED_APATOSAURUS_EGG = registerBlock("hatched_apatosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.APATOSAURUS));

    public static final RegistryObject<Block> HATCHED_BRACHIOSAURUS_EGG = registerBlock("hatched_brachiosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.BRACHIOSAURUS));

    public static final RegistryObject<Block> HATCHED_CERATOSAURUS_EGG = registerBlock("hatched_ceratosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CERATOSAURUS));

    public static final RegistryObject<Block> HATCHED_COMPSOGNATHUS_EGG = registerBlock("hatched_compsognathus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.COMPSOGNATHUS));

    public static final RegistryObject<Block> HATCHED_DILOPHOSAURUS_EGG = registerBlock("hatched_dilophosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DILOPHOSAURUS));

    public static final RegistryObject<Block> HATCHED_DIPLODOCUS_EGG = registerBlock("hatched_diplodocus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DIPLODOCUS));

    public static final RegistryObject<Block> HATCHED_GALLIMIMUS_EGG = registerBlock("hatched_gallimimus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GALLIMIMUS));

    public static final RegistryObject<Block> HATCHED_INDOMINUS_REX_EGG = registerBlock("hatched_indominus_rex_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.INDOMINUS_REX));

    public static final RegistryObject<Block> HATCHED_OURANOSAURUS_EGG = registerBlock("hatched_ouranosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.OURANOSAURUS));

    public static final RegistryObject<Block> HATCHED_PARASAUROLOPHUS_EGG = registerBlock("hatched_parasaurolophus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PARASAUROLOPHUS));

    public static final RegistryObject<Block> HATCHED_SPINOSAURUS_EGG = registerBlock("hatched_spinosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.SPINOSAURUS));

    public static final RegistryObject<Block> HATCHED_TRICERATOPS_EGG = registerBlock("hatched_triceratops_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TRICERATOPS));

    public static final RegistryObject<Block> HATCHED_TYRANNOSAURUS_REX_EGG = registerBlock("hatched_tyrannosaurus_rex_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TYRANNOSAURUS_REX));

    public static final RegistryObject<Block> HATCHED_VELOCIRAPTOR_EGG = registerBlock("hatched_velociraptor_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.VELOCIRAPTOR));

    public static final RegistryObject<Block> HATCHED_BARYONYX_EGG = registerBlock("hatched_baryonyx_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.BARYONYX));

    public static final RegistryObject<Block> HATCHED_CARNOTAURUS_EGG = registerBlock("hatched_carnotaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CARNOTAURUS));

    public static final RegistryObject<Block> HATCHED_CONCAVENATOR_EGG = registerBlock("hatched_concavenator_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CONCAVENATOR));

    public static final RegistryObject<Block> HATCHED_DEINONYCHUS_EGG = registerBlock("hatched_deinonychus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DEINONYCHUS));

    public static final RegistryObject<Block> HATCHED_EDMONTOSAURUS_EGG = registerBlock("hatched_edmontosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.EDMONTOSAURUS));

    public static final RegistryObject<Block> HATCHED_GIGANOTOSAURUS_EGG = registerBlock("hatched_giganotosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GIGANOTOSAURUS));

    public static final RegistryObject<Block> HATCHED_GUANLONG_EGG = registerBlock("hatched_guanlong_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GUANLONG));

    public static final RegistryObject<Block> HATCHED_HERRERASAURUS_EGG = registerBlock("hatched_herrerasaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.HERRERASAURUS));

    public static final RegistryObject<Block> HATCHED_MAJUNGASAURUS_EGG = registerBlock("hatched_majungasaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.MAJUNGASAURUS));

    public static final RegistryObject<Block> HATCHED_PROCOMPSOGNATHUS_EGG = registerBlock("hatched_procompsognathus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PROCOMPSOGNATHUS));

    public static final RegistryObject<Block> HATCHED_PROTOCERATOPS_EGG = registerBlock("hatched_protoceratops_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PROTOCERATOPS));

    public static final RegistryObject<Block> HATCHED_RUGOPS_EGG = registerBlock("hatched_rugops_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.RUGOPS));

    public static final RegistryObject<Block> HATCHED_SHANTUNGOSAURUS_EGG = registerBlock("hatched_shantungosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.SHANTUNGOSAURUS));

    public static final RegistryObject<Block> HATCHED_STEGOSAURUS_EGG = registerBlock("hatched_stegosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.STEGOSAURUS));

    public static final RegistryObject<Block> HATCHED_STYRACOSAURUS_EGG = registerBlock("hatched_styracosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.STYRACOSAURUS));

    public static final RegistryObject<Block> HATCHED_THERIZINOSAURUS_EGG = registerBlock("hatched_therizinosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.THERIZINOSAURUS));

    public static final RegistryObject<Block> HATCHED_DISTORTUS_REX_EGG = registerBlock("hatched_distortus_rex_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DISTORTUS_REX));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    // Overload that can skip item for placement/visibility when disabled
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, boolean registerItem) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        if (registerItem) {
            registerBlockItem(name, toReturn);
        } else {
            registerBlockItemConditional(name, toReturn, () -> Config.REQUIRE_POWER);
        }
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> {
            Item.Properties props = new Item.Properties();
            if (block.get() instanceof EggBlock) {
                props = props.rarity(Rarity.RARE).stacksTo(1);
            }
            return new BlockItem(block.get(), props);
        });
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
