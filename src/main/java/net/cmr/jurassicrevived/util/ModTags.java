package net.cmr.jurassicrevived.util;

import net.cmr.jurassicrevived.JRMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> TISSUES = tag("tissues");
        public static final TagKey<Item> DNA = tag("dna");
        public static final TagKey<Item> SYRINGES = tag("syringes");
        public static final TagKey<Item> EGGS = tag("eggs");
        public static final TagKey<Item> FOSSILS = tag("fossils");
        public static final TagKey<Item> SKULLS = tag("skulls");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> AQUATIC_PLACEMENT_REPLACEABLES = tag("aquatic_placement_replaceables");
        public static final TagKey<Block> HATCHED_EGGS = tag("hatched_eggs");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath("forge", name));
        }
    }
}
