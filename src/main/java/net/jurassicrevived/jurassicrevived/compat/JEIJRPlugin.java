package net.jurassicrevived.jurassicrevived.compat;



import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.jurassicrevived.jurassicrevived.recipe.DNAExtractorRecipe;
import net.jurassicrevived.jurassicrevived.recipe.ModRecipes;
import net.jurassicrevived.jurassicrevived.screen.custom.DNAExtractorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIJRPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath("jurassicrevived", "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new DNAExtractorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<DNAExtractorRecipe> dnaExtractorRecipes = recipeManager.getAllRecipesFor(DNAExtractorRecipe.Type.INSTANCE);

        registration.addRecipes(DNAExtractorRecipeCategory.DNA_EXTRACTOR_RECIPE_RECIPE_TYPE, dnaExtractorRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(DNAExtractorScreen.class, 70, 30, 25, 20,
                DNAExtractorRecipeCategory.DNA_EXTRACTOR_RECIPE_RECIPE_TYPE);
    }
}
