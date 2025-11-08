package net.cmr.jurassicrevived.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cmr.jurassicrevived.Config;
import net.cmr.jurassicrevived.JRMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ConditionalAddSpawns implements BiomeModifier {

    // Register a Codec<? extends BiomeModifier> in 1.20.1
    public static final DeferredRegister<Codec<? extends BiomeModifier>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, JRMod.MOD_ID);

    public static final RegistryObject<Codec<ConditionalAddSpawns>> SERIALIZER = SERIALIZERS.register(
            "conditional_add_spawns",
            () -> RecordCodecBuilder.create(instance ->
                    instance.group(
                            Biome.LIST_CODEC.fieldOf("biomes").forGetter(m -> m.delegate.biomes()),
                            MobSpawnSettings.SpawnerData.CODEC.listOf().fieldOf("spawners").forGetter(m -> m.delegate.spawners())
                    ).apply(instance, ConditionalAddSpawns::new)
            )
    );

    private final ForgeBiomeModifiers.AddSpawnsBiomeModifier delegate;

    public ConditionalAddSpawns(HolderSet<Biome> biomes, List<MobSpawnSettings.SpawnerData> spawners) {
        this.delegate = new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes, spawners);
    }

    @Override
    public void modify(@NotNull Holder<Biome> biome, @NotNull Phase phase, @NotNull ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (Config.NATURAL_DINOSAUR_SPAWNING) {
            delegate.modify(biome, phase, builder);
        }
    }

    @Override
    public @NotNull Codec<? extends BiomeModifier> codec() {
        return SERIALIZER.get();
    }
}