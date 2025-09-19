package net.jurassicrevived.jurassicrevived.compat;



import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.jurassicrevived.jurassicrevived.recipe.DNAExtractorRecipe;
import net.jurassicrevived.jurassicrevived.recipe.FossilCleanerRecipe;
import net.jurassicrevived.jurassicrevived.recipe.FossilGrinderRecipe;
import net.jurassicrevived.jurassicrevived.recipe.ModRecipes;
import net.jurassicrevived.jurassicrevived.screen.custom.DNAExtractorScreen;
import net.jurassicrevived.jurassicrevived.screen.custom.FossilCleanerScreen;
import net.jurassicrevived.jurassicrevived.screen.custom.FossilGrinderScreen;
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
        registration.addRecipeCategories(new FossilGrinderRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FossilCleanerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<DNAExtractorRecipe> dnaExtractorRecipes = recipeManager.getAllRecipesFor(DNAExtractorRecipe.Type.INSTANCE);
        List<FossilGrinderRecipe> fossilGrinderRecipes = recipeManager.getAllRecipesFor(FossilGrinderRecipe.Type.INSTANCE);
        List<FossilCleanerRecipe> fossilCleanerRecipes = recipeManager.getAllRecipesFor(FossilCleanerRecipe.Type.INSTANCE);

        registration.addRecipes(DNAExtractorRecipeCategory.DNA_EXTRACTOR_RECIPE_RECIPE_TYPE, dnaExtractorRecipes);
        registration.addRecipes(FossilGrinderRecipeCategory.FOSSIL_GRINDING_RECIPE_RECIPE_TYPE, fossilGrinderRecipes);
        registration.addRecipes(FossilCleanerRecipeCategory.FOSSIL_CLEANING_RECIPE_RECIPE_TYPE, fossilCleanerRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(DNAExtractorScreen.class, 70, 30, 25, 20,
                DNAExtractorRecipeCategory.DNA_EXTRACTOR_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(FossilGrinderScreen.class, 70, 30, 25, 20,
                FossilGrinderRecipeCategory.FOSSIL_GRINDING_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(FossilCleanerScreen.class, 70, 30, 25, 20,
                FossilCleanerRecipeCategory.FOSSIL_CLEANING_RECIPE_RECIPE_TYPE);
    }
}
