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

    public static final RegistryObject<Block> TANK = registerBlock("tank",
            () -> new TankBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().noOcclusion().noLootTable()));

    public static final RegistryObject<Block> POWER_CELL = registerBlock("power_cell",
            () -> new PowerCellBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().noOcclusion().noLootTable()));


    public static final RegistryObject<Block> GENERATOR = registerBlock("generator",
            () -> new GeneratorBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> DNA_EXTRACTOR = registerBlock("dna_extractor",
            () -> new DNAExtractorBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
    public static final RegistryObject<Block> DNA_ANALYZER = registerBlock("dna_analyzer",
            () -> new DNAAnalyzerBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
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
    public static final RegistryObject<Block> WHITE_DNA_ANALYZER = registerBlock("white_dna_analyzer",
            () -> new DNAAnalyzerBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(4f).noLootTable()));
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

    public static final RegistryObject<Block> ONYCHIOPSIS = registerBlock("onychiopsis",
            () -> new FlowerBlock(MobEffects.UNLUCK, 0, BlockBehaviour.Properties.copy(Blocks.ALLIUM)));
    public static final RegistryObject<Block> POTTED_ONYCHIOPSIS = BLOCKS.register("potted_onychiopsis",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ONYCHIOPSIS, BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM)));

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


    public static final RegistryObject<Block> ALBERTOSAURUS_EGG = registerBlock("albertosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ALBERTOSAURUS));

    public static final RegistryObject<Block> APATOSAURUS_EGG = registerBlock("apatosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.APATOSAURUS));

    public static final RegistryObject<Block> BRACHIOSAURUS_EGG = registerBlock("brachiosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.BRACHIOSAURUS));

    public static final RegistryObject<Block> CERATOSAURUS_EGG = registerBlock("ceratosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CERATOSAURUS));

    public static final RegistryObject<Block> COMPSOGNATHUS_EGG = registerBlock("compsognathus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.COMPSOGNATHUS));

    public static final RegistryObject<Block> DILOPHOSAURUS_EGG = registerBlock("dilophosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DILOPHOSAURUS));

    public static final RegistryObject<Block> DIPLODOCUS_EGG = registerBlock("diplodocus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DIPLODOCUS));

    public static final RegistryObject<Block> GALLIMIMUS_EGG = registerBlock("gallimimus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GALLIMIMUS));

    public static final RegistryObject<Block> INDOMINUS_REX_EGG = registerBlock("indominus_rex_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.INDOMINUS_REX));

    public static final RegistryObject<Block> OURANOSAURUS_EGG = registerBlock("ouranosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.OURANOSAURUS));

    public static final RegistryObject<Block> PARASAUROLOPHUS_EGG = registerBlock("parasaurolophus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PARASAUROLOPHUS));

    public static final RegistryObject<Block> SPINOSAURUS_EGG = registerBlock("spinosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.SPINOSAURUS));

    public static final RegistryObject<Block> TRICERATOPS_EGG = registerBlock("triceratops_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TRICERATOPS));

    public static final RegistryObject<Block> TYRANNOSAURUS_REX_EGG = registerBlock("tyrannosaurus_rex_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TYRANNOSAURUS_REX));

    public static final RegistryObject<Block> VELOCIRAPTOR_EGG = registerBlock("velociraptor_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.VELOCIRAPTOR));

    public static final RegistryObject<Block> BARYONYX_EGG = registerBlock("baryonyx_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.BARYONYX));

    public static final RegistryObject<Block> CARNOTAURUS_EGG = registerBlock("carnotaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CARNOTAURUS));

    public static final RegistryObject<Block> CONCAVENATOR_EGG = registerBlock("concavenator_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CONCAVENATOR));

    public static final RegistryObject<Block> DEINONYCHUS_EGG = registerBlock("deinonychus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DEINONYCHUS));

    public static final RegistryObject<Block> EDMONTOSAURUS_EGG = registerBlock("edmontosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.EDMONTOSAURUS));

    public static final RegistryObject<Block> GIGANOTOSAURUS_EGG = registerBlock("giganotosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GIGANOTOSAURUS));

    public static final RegistryObject<Block> GUANLONG_EGG = registerBlock("guanlong_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GUANLONG));

    public static final RegistryObject<Block> HERRERASAURUS_EGG = registerBlock("herrerasaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.HERRERASAURUS));

    public static final RegistryObject<Block> MAJUNGASAURUS_EGG = registerBlock("majungasaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.MAJUNGASAURUS));

    public static final RegistryObject<Block> PROCOMPSOGNATHUS_EGG = registerBlock("procompsognathus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PROCOMPSOGNATHUS));

    public static final RegistryObject<Block> PROTOCERATOPS_EGG = registerBlock("protoceratops_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PROTOCERATOPS));

    public static final RegistryObject<Block> RUGOPS_EGG = registerBlock("rugops_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.RUGOPS));

    public static final RegistryObject<Block> SHANTUNGOSAURUS_EGG = registerBlock("shantungosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.SHANTUNGOSAURUS));

    public static final RegistryObject<Block> STEGOSAURUS_EGG = registerBlock("stegosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.STEGOSAURUS));

    public static final RegistryObject<Block> STYRACOSAURUS_EGG = registerBlock("styracosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.STYRACOSAURUS));

    public static final RegistryObject<Block> THERIZINOSAURUS_EGG = registerBlock("therizinosaurus_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.THERIZINOSAURUS));

    public static final RegistryObject<Block> DISTORTUS_REX_EGG = registerBlock("distortus_rex_egg",
            () -> new EggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DISTORTUS_REX));



    public static final RegistryObject<Block> INCUBATED_ALBERTOSAURUS_EGG = registerBlock("incubated_albertosaurus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.ALBERTOSAURUS));

    public static final RegistryObject<Block> INCUBATED_APATOSAURUS_EGG = registerBlock("incubated_apatosaurus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.APATOSAURUS));

    public static final RegistryObject<Block> INCUBATED_BRACHIOSAURUS_EGG = registerBlock("incubated_brachiosaurus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.BRACHIOSAURUS));

    public static final RegistryObject<Block> INCUBATED_CERATOSAURUS_EGG = registerBlock("incubated_ceratosaurus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CERATOSAURUS));

    public static final RegistryObject<Block> INCUBATED_COMPSOGNATHUS_EGG = registerBlock("incubated_compsognathus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.COMPSOGNATHUS));

    public static final RegistryObject<Block> INCUBATED_DILOPHOSAURUS_EGG = registerBlock("incubated_dilophosaurus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DILOPHOSAURUS));

    public static final RegistryObject<Block> INCUBATED_DIPLODOCUS_EGG = registerBlock("incubated_diplodocus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DIPLODOCUS));

    public static final RegistryObject<Block> INCUBATED_GALLIMIMUS_EGG = registerBlock("incubated_gallimimus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GALLIMIMUS));

    public static final RegistryObject<Block> INCUBATED_INDOMINUS_REX_EGG = registerBlock("incubated_indominus_rex_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.INDOMINUS_REX));

    public static final RegistryObject<Block> INCUBATED_OURANOSAURUS_EGG = registerBlock("incubated_ouranosaurus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.OURANOSAURUS));

    public static final RegistryObject<Block> INCUBATED_PARASAUROLOPHUS_EGG = registerBlock("incubated_parasaurolophus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PARASAUROLOPHUS));

    public static final RegistryObject<Block> INCUBATED_SPINOSAURUS_EGG = registerBlock("incubated_spinosaurus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.SPINOSAURUS));

    public static final RegistryObject<Block> INCUBATED_TRICERATOPS_EGG = registerBlock("incubated_triceratops_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TRICERATOPS));

    public static final RegistryObject<Block> INCUBATED_TYRANNOSAURUS_REX_EGG = registerBlock("incubated_tyrannosaurus_rex_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.TYRANNOSAURUS_REX));

    public static final RegistryObject<Block> INCUBATED_VELOCIRAPTOR_EGG = registerBlock("incubated_velociraptor_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.VELOCIRAPTOR));

    public static final RegistryObject<Block> INCUBATED_BARYONYX_EGG = registerBlock("incubated_baryonyx_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.BARYONYX));

    public static final RegistryObject<Block> INCUBATED_CARNOTAURUS_EGG = registerBlock("incubated_carnotaurus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CARNOTAURUS));

    public static final RegistryObject<Block> INCUBATED_CONCAVENATOR_EGG = registerBlock("incubated_concavenator_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.CONCAVENATOR));

    public static final RegistryObject<Block> INCUBATED_DEINONYCHUS_EGG = registerBlock("incubated_deinonychus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DEINONYCHUS));

    public static final RegistryObject<Block> INCUBATED_EDMONTOSAURUS_EGG = registerBlock("incubated_edmontosaurus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.EDMONTOSAURUS));

    public static final RegistryObject<Block> INCUBATED_GIGANOTOSAURUS_EGG = registerBlock("incubated_giganotosaurus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GIGANOTOSAURUS));

    public static final RegistryObject<Block> INCUBATED_GUANLONG_EGG = registerBlock("incubated_guanlong_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.GUANLONG));

    public static final RegistryObject<Block> INCUBATED_HERRERASAURUS_EGG = registerBlock("incubated_herrerasaurus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.HERRERASAURUS));

    public static final RegistryObject<Block> INCUBATED_MAJUNGASAURUS_EGG = registerBlock("incubated_majungasaurus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.MAJUNGASAURUS));

    public static final RegistryObject<Block> INCUBATED_PROCOMPSOGNATHUS_EGG = registerBlock("incubated_procompsognathus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PROCOMPSOGNATHUS));

    public static final RegistryObject<Block> INCUBATED_PROTOCERATOPS_EGG = registerBlock("incubated_protoceratops_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.PROTOCERATOPS));

    public static final RegistryObject<Block> INCUBATED_RUGOPS_EGG = registerBlock("incubated_rugops_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.RUGOPS));

    public static final RegistryObject<Block> INCUBATED_SHANTUNGOSAURUS_EGG = registerBlock("incubated_shantungosaurus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.SHANTUNGOSAURUS));

    public static final RegistryObject<Block> INCUBATED_STEGOSAURUS_EGG = registerBlock("incubated_stegosaurus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.STEGOSAURUS));

    public static final RegistryObject<Block> INCUBATED_STYRACOSAURUS_EGG = registerBlock("incubated_styracosaurus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.STYRACOSAURUS));

    public static final RegistryObject<Block> INCUBATED_THERIZINOSAURUS_EGG = registerBlock("incubated_therizinosaurus_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.THERIZINOSAURUS));

    public static final RegistryObject<Block> INCUBATED_DISTORTUS_REX_EGG = registerBlock("incubated_distortus_rex_egg",
            () -> new IncubatedEggBlock(BlockBehaviour.Properties.of().strength(4f).requiresCorrectToolForDrops(), ModEntities.DISTORTUS_REX));


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
            if (block.get() instanceof IncubatedEggBlock) {
                props = props.rarity(Rarity.RARE).stacksTo(1);
            }
            if (block.get() instanceof EggBlock) {
                props = props.stacksTo(1);
            }
            return new BlockItem(block.get(), props);
        });
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
