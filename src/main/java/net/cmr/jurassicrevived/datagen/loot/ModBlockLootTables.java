package net.cmr.jurassicrevived.datagen.loot;

import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.CAT_PLUSHIE.get());
        this.dropSelf(ModBlocks.TRASH_CAN.get());
        this.dropSelf(ModBlocks.BENCH.get());
        this.dropSelf(ModBlocks.FENCE_LIGHT.get());
        this.dropSelf(ModBlocks.LIGHT_POST.get());
        this.dropSelf(ModBlocks.GYPSUM_COBBLESTONE.get());
        this.dropSelf(ModBlocks.GYPSUM_STONE_BRICKS.get());
        this.dropSelf(ModBlocks.SMOOTH_GYPSUM_STONE.get());
        this.dropSelf(ModBlocks.CHISELED_GYPSUM_STONE.get());
        this.dropSelf(ModBlocks.GYPSUM_BRICK_STAIRS.get());
        this.dropSelf(ModBlocks.GYPSUM_BRICK_SLAB.get());
        this.dropSelf(ModBlocks.GYPSUM_BRICK_WALL.get());
        this.dropSelf(ModBlocks.STONE_FOSSIL.get());
        this.dropSelf(ModBlocks.DEEPSLATE_FOSSIL.get());
        this.dropSelf(ModBlocks.LOW_SECURITY_FENCE_POLE.get());
        this.dropSelf(ModBlocks.LOW_SECURITY_FENCE_WIRE.get());
        this.dropSelf(ModBlocks.MEDIUM_SECURITY_FENCE_POLE.get());
        this.dropSelf(ModBlocks.MEDIUM_SECURITY_FENCE_WIRE.get());

        this.dropSelf(ModBlocks.ITEM_PIPE.get());
        this.dropSelf(ModBlocks.POWER_PIPE.get());
        this.dropSelf(ModBlocks.FLUID_PIPE.get());

        this.add(ModBlocks.GYPSUM_STONE.get(),
                block -> createOreDrop(ModBlocks.GYPSUM_STONE.get(), ModBlocks.GYPSUM_COBBLESTONE.get().asItem()));
        this.add(ModBlocks.AMBER_ORE.get(),
                block -> createOreDrop(ModBlocks.AMBER_ORE.get(), ModItems.MOSQUITO_IN_AMBER.get()));
        this.add(ModBlocks.DEEPSLATE_ICE_SHARD_ORE.get(),
                block -> createOreDrop(ModBlocks.DEEPSLATE_FOSSIL.get(), ModItems.FROZEN_LEECH.get()));

        this.dropSelf(ModBlocks.REINFORCED_STONE.get());
        this.dropSelf(ModBlocks.REINFORCED_STONE_BRICKS.get());
        dropSelf(ModBlocks.CHISELED_REINFORCED_STONE.get());
        dropSelf(ModBlocks.REINFORCED_BRICK_STAIRS.get());
        dropSelf(ModBlocks.REINFORCED_BRICK_SLAB.get());
        dropSelf(ModBlocks.REINFORCED_BRICK_WALL.get());

        this.dropSelf(ModBlocks.APATOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.ALBERTOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.BRACHIOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.CERATOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.COMPSOGNATHUS_EGG.get());
        this.dropSelf(ModBlocks.DILOPHOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.DIPLODOCUS_EGG.get());
        this.dropSelf(ModBlocks.GALLIMIMUS_EGG.get());
        this.dropSelf(ModBlocks.INDOMINUS_REX_EGG.get());
        this.dropSelf(ModBlocks.PARASAUROLOPHUS_EGG.get());
        this.dropSelf(ModBlocks.OURANOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.SPINOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.TRICERATOPS_EGG.get());
        this.dropSelf(ModBlocks.TYRANNOSAURUS_REX_EGG.get());
        this.dropSelf(ModBlocks.VELOCIRAPTOR_EGG.get());
        this.dropSelf(ModBlocks.BARYONYX_EGG.get());
        this.dropSelf(ModBlocks.CARNOTAURUS_EGG.get());
        this.dropSelf(ModBlocks.CONCAVENATOR_EGG.get());
        this.dropSelf(ModBlocks.DEINONYCHUS_EGG.get());
        this.dropSelf(ModBlocks.EDMONTOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.GIGANOTOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.GUANLONG_EGG.get());
        this.dropSelf(ModBlocks.HERRERASAURUS_EGG.get());
        this.dropSelf(ModBlocks.MAJUNGASAURUS_EGG.get());
        this.dropSelf(ModBlocks.PROCOMPSOGNATHUS_EGG.get());
        this.dropSelf(ModBlocks.PROTOCERATOPS_EGG.get());
        this.dropSelf(ModBlocks.RUGOPS_EGG.get());
        this.dropSelf(ModBlocks.SHANTUNGOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.STEGOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.STYRACOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.THERIZINOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.DISTORTUS_REX_EGG.get());

        this.dropSelf(ModBlocks.INCUBATED_APATOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_ALBERTOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_BRACHIOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_CERATOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_COMPSOGNATHUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_DILOPHOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_DIPLODOCUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_GALLIMIMUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_INDOMINUS_REX_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_PARASAUROLOPHUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_OURANOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_SPINOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_TRICERATOPS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_TYRANNOSAURUS_REX_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_VELOCIRAPTOR_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_BARYONYX_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_CARNOTAURUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_CONCAVENATOR_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_DEINONYCHUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_EDMONTOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_GIGANOTOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_GUANLONG_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_HERRERASAURUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_MAJUNGASAURUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_PROCOMPSOGNATHUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_PROTOCERATOPS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_RUGOPS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_SHANTUNGOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_STEGOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_STYRACOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_THERIZINOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.INCUBATED_DISTORTUS_REX_EGG.get());

        this.dropSelf(ModBlocks.ROYAL_FERN.get());
        this.add(ModBlocks.POTTED_ROYAL_FERN.get(), createPotFlowerItemTable(ModBlocks.ROYAL_FERN.get()));

        this.dropSelf(ModBlocks.HORSETAIL_FERN.get());
        this.add(ModBlocks.POTTED_HORSETAIL_FERN.get(), createPotFlowerItemTable(ModBlocks.HORSETAIL_FERN.get()));

        this.dropSelf(ModBlocks.WESTERN_SWORD_FERN.get());
        this.add(ModBlocks.POTTED_WESTERN_SWORD_FERN.get(), createPotFlowerItemTable(ModBlocks.WESTERN_SWORD_FERN.get()));

        this.dropSelf(ModBlocks.ONYCHIOPSIS.get());
        this.add(ModBlocks.POTTED_ONYCHIOPSIS.get(), createPotFlowerItemTable(ModBlocks.ONYCHIOPSIS.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
