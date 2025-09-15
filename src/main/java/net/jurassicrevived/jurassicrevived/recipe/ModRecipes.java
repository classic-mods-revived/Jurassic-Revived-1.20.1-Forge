package net.jurassicrevived.jurassicrevived.recipe;

import net.jurassicrevived.jurassicrevived.JRMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, JRMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<DNAExtractorRecipe>> DNA_EXTRACTOR_SERIALIZER =
            SERIALIZERS.register("dna_extracting", () -> DNAExtractorRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<FossilGrinderRecipe>> FOSSIL_GRINDER_SERIALIZER =
            SERIALIZERS.register("fossil_grinding", () -> FossilGrinderRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
