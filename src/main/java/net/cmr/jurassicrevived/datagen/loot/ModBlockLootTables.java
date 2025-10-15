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
        this.dropSelf(ModBlocks.GYPSUM_COBBLESTONE.get());
        this.dropSelf(ModBlocks.GYPSUM_STONE_BRICKS.get());
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

        this.dropSelf(ModBlocks.HATCHED_APATOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.HATCHED_ALBERTOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.HATCHED_BRACHIOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.HATCHED_CERATOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.HATCHED_COMPSOGNATHUS_EGG.get());
        this.dropSelf(ModBlocks.HATCHED_DILOPHOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.HATCHED_DIPLODOCUS_EGG.get());
        this.dropSelf(ModBlocks.HATCHED_GALLIMIMUS_EGG.get());
        this.dropSelf(ModBlocks.HATCHED_INDOMINUS_REX_EGG.get());
        this.dropSelf(ModBlocks.HATCHED_PARASAUROLOPHUS_EGG.get());
        this.dropSelf(ModBlocks.HATCHED_OURANOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.HATCHED_SPINOSAURUS_EGG.get());
        this.dropSelf(ModBlocks.HATCHED_TRICERATOPS_EGG.get());
        this.dropSelf(ModBlocks.HATCHED_TYRANNOSAURUS_REX_EGG.get());
        this.dropSelf(ModBlocks.HATCHED_VELOCIRAPTOR_EGG.get());

        this.dropSelf(ModBlocks.ROYAL_FERN.get());
        this.add(ModBlocks.POTTED_ROYAL_FERN.get(), createPotFlowerItemTable(ModBlocks.ROYAL_FERN.get()));

        this.dropSelf(ModBlocks.HORSETAIL_FERN.get());
        this.add(ModBlocks.POTTED_HORSETAIL_FERN.get(), createPotFlowerItemTable(ModBlocks.HORSETAIL_FERN.get()));

        this.dropSelf(ModBlocks.WESTERN_SWORD_FERN.get());
        this.add(ModBlocks.POTTED_WESTERN_SWORD_FERN.get(), createPotFlowerItemTable(ModBlocks.WESTERN_SWORD_FERN.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
