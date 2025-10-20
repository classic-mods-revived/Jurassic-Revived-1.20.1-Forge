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
                .add(ModBlocks.GYPSUM_STONE.get())
                .add(ModBlocks.GYPSUM_COBBLESTONE.get())
                .add(ModBlocks.GYPSUM_STONE_BRICKS.get())
                .add(ModBlocks.SMOOTH_GYPSUM_STONE.get())
                .add(ModBlocks.REINFORCED_STONE.get())
                .add(ModBlocks.REINFORCED_STONE_BRICKS.get())
                .add(ModBlocks.STONE_FOSSIL.get())
                .add(ModBlocks.DEEPSLATE_FOSSIL.get())
                .add(ModBlocks.AMBER_ORE.get())
                .add(ModBlocks.DEEPSLATE_ICE_SHARD_ORE.get())
                .add(ModBlocks.LOW_SECURITY_FENCE_POLE.get())
                .add(ModBlocks.LOW_SECURITY_FENCE_WIRE.get())
                .add(ModBlocks.GENERATOR.get())
                .add(ModBlocks.DNA_EXTRACTOR.get())
                .add(ModBlocks.FOSSIL_GRINDER.get())
                .add(ModBlocks.FOSSIL_CLEANER.get())
                .add(ModBlocks.DNA_HYBRIDIZER.get())
                .add(ModBlocks.EMBRYONIC_MACHINE.get())
                .add(ModBlocks.EMBRYO_CALCIFICATION_MACHINE.get())
                .add(ModBlocks.INCUBATOR.get())
                .add(ModBlocks.MEDIUM_SECURITY_FENCE_POLE.get())
                .add(ModBlocks.MEDIUM_SECURITY_FENCE_WIRE.get());

        this.tag(ModTags.Blocks.HATCHED_EGGS)
                .add(ModBlocks.HATCHED_VELOCIRAPTOR_EGG.get())
                .add(ModBlocks.HATCHED_TYRANNOSAURUS_REX_EGG.get())
                .add(ModBlocks.HATCHED_TRICERATOPS_EGG.get())
                .add(ModBlocks.HATCHED_SPINOSAURUS_EGG.get())
                .add(ModBlocks.HATCHED_PARASAUROLOPHUS_EGG.get())
                .add(ModBlocks.HATCHED_OURANOSAURUS_EGG.get())
                .add(ModBlocks.HATCHED_INDOMINUS_REX_EGG.get())
                .add(ModBlocks.HATCHED_GALLIMIMUS_EGG.get())
                .add(ModBlocks.HATCHED_DIPLODOCUS_EGG.get())
                .add(ModBlocks.HATCHED_DILOPHOSAURUS_EGG.get())
                .add(ModBlocks.HATCHED_COMPSOGNATHUS_EGG.get())
                .add(ModBlocks.HATCHED_CERATOSAURUS_EGG.get())
                .add(ModBlocks.HATCHED_BRACHIOSAURUS_EGG.get())
                .add(ModBlocks.HATCHED_APATOSAURUS_EGG.get())
                .add(ModBlocks.HATCHED_ALBERTOSAURUS_EGG.get())
                .add(ModBlocks.HATCHED_APATOSAURUS_EGG.get())
                .add(ModBlocks.HATCHED_BARYONYX_EGG.get())
                .add(ModBlocks.HATCHED_CARNOTAURUS_EGG.get())
                .add(ModBlocks.HATCHED_CONCAVENATOR_EGG.get())
                .add(ModBlocks.HATCHED_DEINONYCHUS_EGG.get())
                .add(ModBlocks.HATCHED_EDMONTOSAURUS_EGG.get())
                .add(ModBlocks.HATCHED_GIGANOTOSAURUS_EGG.get())
                .add(ModBlocks.HATCHED_GUANLONG_EGG.get())
                .add(ModBlocks.HATCHED_HERRERASAURUS_EGG.get())
                .add(ModBlocks.HATCHED_MAJUNGASAURUS_EGG.get())
                .add(ModBlocks.HATCHED_PROCOMPSOGNATHUS_EGG.get())
                .add(ModBlocks.HATCHED_PROTOCERATOPS_EGG.get())
                .add(ModBlocks.HATCHED_RUGOPS_EGG.get())
                .add(ModBlocks.HATCHED_SHANTUNGOSAURUS_EGG.get())
                .add(ModBlocks.HATCHED_STEGOSAURUS_EGG.get())
                .add(ModBlocks.HATCHED_STYRACOSAURUS_EGG.get())
                .add(ModBlocks.HATCHED_THERIZINOSAURUS_EGG.get())
                .add(ModBlocks.HATCHED_DISTORTUS_REX_EGG.get());

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
    }

    @Override
    public String getName() {
        return "Block Tags";
    }
}
