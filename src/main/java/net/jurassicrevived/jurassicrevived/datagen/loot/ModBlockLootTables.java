package net.jurassicrevived.jurassicrevived.datagen.loot;

import net.jurassicrevived.jurassicrevived.block.ModBlocks;
import net.jurassicrevived.jurassicrevived.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.CAT_PLUSHIE.get());
        this.dropSelf(ModBlocks.GYPSUM_STONE_BRICKS.get());

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
