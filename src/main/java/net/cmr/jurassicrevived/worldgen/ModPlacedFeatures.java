package net.cmr.jurassicrevived.worldgen;

import net.cmr.jurassicrevived.JRMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> GYPSUM_STONE_PLACED_KEY = registerKey("gypsum_stone_placed");
    public static final ResourceKey<PlacedFeature> STONE_FOSSIL_PLACED_KEY = registerKey("stone_fossil_placed");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_FOSSIL_PLACED_KEY = registerKey("deepslate_fossil_placed");
    public static final ResourceKey<PlacedFeature> AMBER_ORE_PLACED_KEY = registerKey("amber_ore_placed");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_ICE_SHARD_ORE_PLACED_KEY = registerKey("deepslate_ice_shard_ore_placed");
    //public static final ResourceKey<PlacedFeature> hq_ORE_PLACED_KEY = registerKey("hq_ore_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, GYPSUM_STONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_GYPSUM_STONE_KEY),
                ModOrePlacement.commonOrePlacement(30, HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(64))));
        register(context, STONE_FOSSIL_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_STONE_FOSSIL_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(64))));
        register(context, DEEPSLATE_FOSSIL_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_DEEPSLATE_FOSSIL_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(0))));
        register(context, AMBER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_AMBER_ORE_KEY),
                ModOrePlacement.commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(32))));
        register(context, DEEPSLATE_ICE_SHARD_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_DEEPSLATE_ICE_SHARD_ORE_KEY),
                ModOrePlacement.commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(0))));
        //register(context, hq_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_hq_ORE_KEY),
        //        ModAquaticPlacements.fossilPlacement(6));
    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(JRMod.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
