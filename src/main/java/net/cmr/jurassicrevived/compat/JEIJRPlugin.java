package net.cmr.jurassicrevived.compat;



import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.cmr.jurassicrevived.recipe.DNAExtractorRecipe;
import net.cmr.jurassicrevived.recipe.DNAHybridizerRecipe;
import net.cmr.jurassicrevived.recipe.FossilCleanerRecipe;
import net.cmr.jurassicrevived.recipe.FossilGrinderRecipe;
import net.cmr.jurassicrevived.screen.custom.DNAExtractorScreen;
import net.cmr.jurassicrevived.screen.custom.DNAHybridizerScreen;
import net.cmr.jurassicrevived.screen.custom.FossilCleanerScreen;
import net.cmr.jurassicrevived.screen.custom.FossilGrinderScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
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
        registration.addRecipeCategories(new DNAHybridizerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<DNAExtractorRecipe> dnaExtractorRecipes = recipeManager.getAllRecipesFor(DNAExtractorRecipe.Type.INSTANCE);
        List<FossilGrinderRecipe> fossilGrinderRecipes = recipeManager.getAllRecipesFor(FossilGrinderRecipe.Type.INSTANCE);
        List<FossilCleanerRecipe> fossilCleanerRecipes = recipeManager.getAllRecipesFor(FossilCleanerRecipe.Type.INSTANCE);
        List<DNAHybridizerRecipe> dnaHybridizerRecipes = recipeManager.getAllRecipesFor(DNAHybridizerRecipe.Type.INSTANCE);

        registration.addRecipes(DNAExtractorRecipeCategory.DNA_EXTRACTOR_RECIPE_RECIPE_TYPE, dnaExtractorRecipes);
        registration.addRecipes(FossilGrinderRecipeCategory.FOSSIL_GRINDING_RECIPE_RECIPE_TYPE, fossilGrinderRecipes);
        registration.addRecipes(FossilCleanerRecipeCategory.FOSSIL_CLEANING_RECIPE_RECIPE_TYPE, fossilCleanerRecipes);
        registration.addRecipes(DNAHybridizerRecipeCategory.DNA_HYBRIDIZING_RECIPE_RECIPE_TYPE, dnaHybridizerRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(DNAExtractorScreen.class, 76, 35, 24, 16,
                DNAExtractorRecipeCategory.DNA_EXTRACTOR_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(FossilGrinderScreen.class, 76, 35, 24, 16,
                FossilGrinderRecipeCategory.FOSSIL_GRINDING_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(FossilCleanerScreen.class, 76, 35, 24, 16,
                FossilCleanerRecipeCategory.FOSSIL_CLEANING_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(DNAHybridizerScreen.class, 76, 35, 24, 16,
                DNAHybridizerRecipeCategory.DNA_HYBRIDIZING_RECIPE_RECIPE_TYPE);
    }
}
