package net.cmr.jurassicrevived.block.entity.custom;

import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, JRMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<EggBlockEntity>> EGG_BE =
            BLOCK_ENTITIES.register("egg_be", () ->
                    BlockEntityType.Builder.of(EggBlockEntity::new,
                            ModBlocks.HATCHED_APATOSAURUS_EGG.get(),
                            ModBlocks.HATCHED_ALBERTOSAURUS_EGG.get(),
                            ModBlocks.HATCHED_VELOCIRAPTOR_EGG.get(),
                            ModBlocks.HATCHED_TYRANNOSAURUS_REX_EGG.get(),
                            ModBlocks.HATCHED_TRICERATOPS_EGG.get(),
                            ModBlocks.HATCHED_SPINOSAURUS_EGG.get(),
                            ModBlocks.HATCHED_PARASAUROLOPHUS_EGG.get(),
                            ModBlocks.HATCHED_INDOMINUS_REX_EGG.get(),
                            ModBlocks.HATCHED_GALLIMIMUS_EGG.get(),
                            ModBlocks.HATCHED_DIPLODOCUS_EGG.get(),
                            ModBlocks.HATCHED_OURANOSAURUS_EGG.get(),
                            ModBlocks.HATCHED_DILOPHOSAURUS_EGG.get(),
                            ModBlocks.HATCHED_COMPSOGNATHUS_EGG.get(),
                            ModBlocks.HATCHED_CERATOSAURUS_EGG.get(),
                            ModBlocks.HATCHED_BRACHIOSAURUS_EGG.get(),
                            ModBlocks.HATCHED_BARYONYX_EGG.get(),
                            ModBlocks.HATCHED_CARNOTAURUS_EGG.get(),
                            ModBlocks.HATCHED_CONCAVENATOR_EGG.get(),
                            ModBlocks.HATCHED_DEINONYCHUS_EGG.get(),
                            ModBlocks.HATCHED_EDMONTOSAURUS_EGG.get(),
                            ModBlocks.HATCHED_GIGANOTOSAURUS_EGG.get(),
                            ModBlocks.HATCHED_GUANLONG_EGG.get(),
                            ModBlocks.HATCHED_HERRERASAURUS_EGG.get(),
                            ModBlocks.HATCHED_MAJUNGASAURUS_EGG.get(),
                            ModBlocks.HATCHED_PROCOMPSOGNATHUS_EGG.get(),
                            ModBlocks.HATCHED_PROTOCERATOPS_EGG.get(),
                            ModBlocks.HATCHED_RUGOPS_EGG.get(),
                            ModBlocks.HATCHED_SHANTUNGOSAURUS_EGG.get(),
                            ModBlocks.HATCHED_STEGOSAURUS_EGG.get(),
                            ModBlocks.HATCHED_STYRACOSAURUS_EGG.get(),
                            ModBlocks.HATCHED_THERIZINOSAURUS_EGG.get(),
                            ModBlocks.HATCHED_DISTORTUS_REX_EGG.get()
                            ).build(null));

    public static final RegistryObject<BlockEntityType<DNAExtractorBlockEntity>> DNA_EXTRACTOR_BE =
            BLOCK_ENTITIES.register("dna_extractor_be", () ->
                    BlockEntityType.Builder.of(DNAExtractorBlockEntity::new,
                            ModBlocks.DNA_EXTRACTOR.get(),
                            ModBlocks.WHITE_DNA_EXTRACTOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<FossilGrinderBlockEntity>> FOSSIL_GRINDER_BE =
            BLOCK_ENTITIES.register("fossil_grinder_be", () ->
                    BlockEntityType.Builder.of(FossilGrinderBlockEntity::new,
                            ModBlocks.FOSSIL_GRINDER.get(),
                            ModBlocks.WHITE_FOSSIL_GRINDER.get()).build(null));
    public static final RegistryObject<BlockEntityType<FossilCleanerBlockEntity>> FOSSIL_CLEANER_BE =
            BLOCK_ENTITIES.register("fossil_cleaner_be", () ->
                    BlockEntityType.Builder.of(FossilCleanerBlockEntity::new,
                            ModBlocks.FOSSIL_CLEANER.get(),
                            ModBlocks.WHITE_FOSSIL_CLEANER.get()).build(null));
    public static final RegistryObject<BlockEntityType<DNAHybridizerBlockEntity>> DNA_HYBRIDIZER_BE =
            BLOCK_ENTITIES.register("dna_hybridizer_be", () ->
                    BlockEntityType.Builder.of(DNAHybridizerBlockEntity::new,
                            ModBlocks.DNA_HYBRIDIZER.get(),
                            ModBlocks.WHITE_DNA_HYBRIDIZER.get()).build(null));
    public static final RegistryObject<BlockEntityType<EmbryonicMachineBlockEntity>> EMBRYONIC_MACHINE_BE =
            BLOCK_ENTITIES.register("embryonic_machine_be", () ->
                    BlockEntityType.Builder.of(EmbryonicMachineBlockEntity::new,
                            ModBlocks.EMBRYONIC_MACHINE.get(),
                            ModBlocks.WHITE_EMBRYONIC_MACHINE.get()).build(null));
    public static final RegistryObject<BlockEntityType<EmbryoCalcificationMachineBlockEntity>> EMBRYO_CALCIFICATION_MACHINE_BE =
            BLOCK_ENTITIES.register("embryo_calcification_machine_be", () ->
                    BlockEntityType.Builder.of(EmbryoCalcificationMachineBlockEntity::new,
                            ModBlocks.EMBRYO_CALCIFICATION_MACHINE.get(),
                            ModBlocks.WHITE_EMBRYO_CALCIFICATION_MACHINE.get()).build(null));
    public static final RegistryObject<BlockEntityType<IncubatorBlockEntity>> INCUBATOR_BE =
            BLOCK_ENTITIES.register("incubator_be", () ->
                    BlockEntityType.Builder.of(IncubatorBlockEntity::new,
                            ModBlocks.INCUBATOR.get(),
                            ModBlocks.WHITE_INCUBATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<PipeBlockEntity>> ITEM_PIPE_BE =
            BLOCK_ENTITIES.register("item_pipe_be", () ->
                    BlockEntityType.Builder.of(PipeBlockEntity::new,
                            ModBlocks.ITEM_PIPE.get()).build(null));
    public static final RegistryObject<BlockEntityType<PipeBlockEntity>> FLUID_PIPE_BE =
            BLOCK_ENTITIES.register("fluid_pipe_be", () ->
                    BlockEntityType.Builder.of(PipeBlockEntity::new,
                            ModBlocks.FLUID_PIPE.get()).build(null));
    public static final RegistryObject<BlockEntityType<PipeBlockEntity>> POWER_PIPE_BE =
            BLOCK_ENTITIES.register("power_pipe_be", () ->
                    BlockEntityType.Builder.of(PipeBlockEntity::new,
                            ModBlocks.POWER_PIPE.get()).build(null));
    public static final RegistryObject<BlockEntityType<GeneratorBlockEntity>> GENERATOR_BE =
            BLOCK_ENTITIES.register("generator_be", () ->
                    BlockEntityType.Builder.of(GeneratorBlockEntity::new,
                            ModBlocks.GENERATOR.get(),
                            ModBlocks.WHITE_GENERATOR.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
