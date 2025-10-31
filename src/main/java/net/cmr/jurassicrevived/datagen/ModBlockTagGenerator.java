package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, JRMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.CAT_PLUSHIE.get())
                .add(ModBlocks.TRASH_CAN.get())
                .add(ModBlocks.BENCH.get())
                .add(ModBlocks.FENCE_LIGHT.get())
                .add(ModBlocks.LIGHT_POST.get())
                .add(ModBlocks.GYPSUM_STONE.get())
                .add(ModBlocks.GYPSUM_COBBLESTONE.get())
                .add(ModBlocks.GYPSUM_STONE_BRICKS.get())
                .add(ModBlocks.SMOOTH_GYPSUM_STONE.get())
                .add(ModBlocks.CHISELED_GYPSUM_STONE.get())
                .add(ModBlocks.GYPSUM_BRICK_STAIRS.get())
                .add(ModBlocks.GYPSUM_BRICK_SLAB.get())
                .add(ModBlocks.GYPSUM_BRICK_WALL.get())
                .add(ModBlocks.GYPSUM_BRICK_STAIRS.get())
                .add(ModBlocks.GYPSUM_BRICK_SLAB.get())
                .add(ModBlocks.GYPSUM_BRICK_WALL.get())
                .add(ModBlocks.REINFORCED_STONE.get())
                .add(ModBlocks.REINFORCED_STONE_BRICKS.get())
                .add(ModBlocks.CHISELED_REINFORCED_STONE.get())
                .add(ModBlocks.REINFORCED_BRICK_STAIRS.get())
                .add(ModBlocks.REINFORCED_BRICK_SLAB.get())
                .add(ModBlocks.REINFORCED_BRICK_WALL.get())
                .add(ModBlocks.REINFORCED_BRICK_STAIRS.get())
                .add(ModBlocks.REINFORCED_BRICK_SLAB.get())
                .add(ModBlocks.REINFORCED_BRICK_WALL.get())
                .add(ModBlocks.STONE_FOSSIL.get())
                .add(ModBlocks.DEEPSLATE_FOSSIL.get())
                .add(ModBlocks.AMBER_ORE.get())
                .add(ModBlocks.DEEPSLATE_ICE_SHARD_ORE.get())
                .add(ModBlocks.LOW_SECURITY_FENCE_POLE.get())
                .add(ModBlocks.LOW_SECURITY_FENCE_WIRE.get())
                .add(ModBlocks.GENERATOR.get())
                .add(ModBlocks.DNA_EXTRACTOR.get())
                .add(ModBlocks.DNA_ANALYZER.get())
                .add(ModBlocks.FOSSIL_GRINDER.get())
                .add(ModBlocks.FOSSIL_CLEANER.get())
                .add(ModBlocks.DNA_HYBRIDIZER.get())
                .add(ModBlocks.EMBRYONIC_MACHINE.get())
                .add(ModBlocks.EMBRYO_CALCIFICATION_MACHINE.get())
                .add(ModBlocks.INCUBATOR.get())
                .add(ModBlocks.WHITE_GENERATOR.get())
                .add(ModBlocks.WHITE_DNA_EXTRACTOR.get())
                .add(ModBlocks.WHITE_DNA_ANALYZER.get())
                .add(ModBlocks.WHITE_FOSSIL_GRINDER.get())
                .add(ModBlocks.WHITE_FOSSIL_CLEANER.get())
                .add(ModBlocks.WHITE_DNA_HYBRIDIZER.get())
                .add(ModBlocks.WHITE_EMBRYONIC_MACHINE.get())
                .add(ModBlocks.WHITE_EMBRYO_CALCIFICATION_MACHINE.get())
                .add(ModBlocks.WHITE_INCUBATOR.get())
                .add(ModBlocks.MEDIUM_SECURITY_FENCE_POLE.get())
                .add(ModBlocks.MEDIUM_SECURITY_FENCE_WIRE.get());
        this.tag(ModTags.Blocks.INCUBATED_EGGS)
                .add(ModBlocks.INCUBATED_VELOCIRAPTOR_EGG.get())
                .add(ModBlocks.INCUBATED_TYRANNOSAURUS_REX_EGG.get())
                .add(ModBlocks.INCUBATED_TRICERATOPS_EGG.get())
                .add(ModBlocks.INCUBATED_SPINOSAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_PARASAUROLOPHUS_EGG.get())
                .add(ModBlocks.INCUBATED_OURANOSAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_INDOMINUS_REX_EGG.get())
                .add(ModBlocks.INCUBATED_GALLIMIMUS_EGG.get())
                .add(ModBlocks.INCUBATED_DIPLODOCUS_EGG.get())
                .add(ModBlocks.INCUBATED_DILOPHOSAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_COMPSOGNATHUS_EGG.get())
                .add(ModBlocks.INCUBATED_CERATOSAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_BRACHIOSAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_APATOSAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_ALBERTOSAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_APATOSAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_BARYONYX_EGG.get())
                .add(ModBlocks.INCUBATED_CARNOTAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_CONCAVENATOR_EGG.get())
                .add(ModBlocks.INCUBATED_DEINONYCHUS_EGG.get())
                .add(ModBlocks.INCUBATED_EDMONTOSAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_GIGANOTOSAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_GUANLONG_EGG.get())
                .add(ModBlocks.INCUBATED_HERRERASAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_MAJUNGASAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_PROCOMPSOGNATHUS_EGG.get())
                .add(ModBlocks.INCUBATED_PROTOCERATOPS_EGG.get())
                .add(ModBlocks.INCUBATED_RUGOPS_EGG.get())
                .add(ModBlocks.INCUBATED_SHANTUNGOSAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_STEGOSAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_STYRACOSAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_THERIZINOSAURUS_EGG.get())
                .add(ModBlocks.INCUBATED_DISTORTUS_REX_EGG.get());

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL);
                //.add(ModBlocks.hq_ORE.get());

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.STONE_FOSSIL.get())
                .add(ModBlocks.AMBER_ORE.get());
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.DEEPSLATE_FOSSIL.get())
                .add(ModBlocks.DEEPSLATE_ICE_SHARD_ORE.get());

        this.tag(ModTags.Blocks.AQUATIC_PLACEMENT_REPLACEABLES)
                .add(Blocks.STONE)
                .add(Blocks.GRANITE)
                .add(Blocks.DIORITE)
                .add(Blocks.ANDESITE)
                .add(Blocks.GRAVEL)
                .add(Blocks.DIRT)
                .add(Blocks.SAND)
                .add(Blocks.CLAY);

        tag(BlockTags.WALLS).add(ModBlocks.GYPSUM_BRICK_WALL.get());
    }

    @Override
    public String getName() {
        return "Block Tags";
    }
}
