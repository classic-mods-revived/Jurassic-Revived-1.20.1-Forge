package net.cmr.jurassicrevived.compat;



import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.registration.*;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.recipe.*;
import net.cmr.jurassicrevived.screen.ModMenuTypes;
import net.cmr.jurassicrevived.screen.custom.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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
        registration.addRecipeCategories(new EmbryonicMachineRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new EmbryoCalcificationMachineRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new IncubatorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<DNAExtractorRecipe> dnaExtractorRecipes = recipeManager.getAllRecipesFor(DNAExtractorRecipe.Type.INSTANCE);
        List<FossilGrinderRecipe> fossilGrinderRecipes = recipeManager.getAllRecipesFor(FossilGrinderRecipe.Type.INSTANCE);
        List<FossilCleanerRecipe> fossilCleanerRecipes = recipeManager.getAllRecipesFor(FossilCleanerRecipe.Type.INSTANCE);
        List<DNAHybridizerRecipe> dnaHybridizerRecipes = recipeManager.getAllRecipesFor(DNAHybridizerRecipe.Type.INSTANCE);
        List<EmbryonicMachineRecipe> embryonicMachineRecipes = recipeManager.getAllRecipesFor(EmbryonicMachineRecipe.Type.INSTANCE);
        List<EmbryoCalcificationMachineRecipe> embryoCalcificationMachineRecipes = recipeManager.getAllRecipesFor(EmbryoCalcificationMachineRecipe.Type.INSTANCE);
        List<IncubatorRecipe> incubatorRecipes = recipeManager.getAllRecipesFor(IncubatorRecipe.Type.INSTANCE);

        registration.addRecipes(DNAExtractorRecipeCategory.DNA_EXTRACTOR_RECIPE_RECIPE_TYPE, dnaExtractorRecipes);
        registration.addRecipes(FossilGrinderRecipeCategory.FOSSIL_GRINDING_RECIPE_RECIPE_TYPE, fossilGrinderRecipes);
        registration.addRecipes(FossilCleanerRecipeCategory.FOSSIL_CLEANING_RECIPE_RECIPE_TYPE, fossilCleanerRecipes);
        registration.addRecipes(DNAHybridizerRecipeCategory.DNA_HYBRIDIZING_RECIPE_RECIPE_TYPE, dnaHybridizerRecipes);
        registration.addRecipes(EmbryonicMachineRecipeCategory.EMBRYONIC_MACHINING_RECIPE_RECIPE_TYPE, embryonicMachineRecipes);
        registration.addRecipes(EmbryoCalcificationMachineRecipeCategory.EMBRYO_CALCIFICATION_MACHINING_RECIPE_RECIPE_TYPE, embryoCalcificationMachineRecipes);
        registration.addRecipes(IncubatorRecipeCategory.INCUBATING_RECIPE_RECIPE_TYPE, incubatorRecipes);
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
        registration.addRecipeClickArea(EmbryonicMachineScreen.class, 76, 35, 24, 16,
                EmbryonicMachineRecipeCategory.EMBRYONIC_MACHINING_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(EmbryoCalcificationMachineScreen.class, 76, 35, 24, 16,
                EmbryoCalcificationMachineRecipeCategory.EMBRYO_CALCIFICATION_MACHINING_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(IncubatorScreen.class, 51, 56, 72, 16,
                IncubatorRecipeCategory.INCUBATING_RECIPE_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DNA_EXTRACTOR.get()), DNAExtractorRecipeCategory.DNA_EXTRACTOR_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.FOSSIL_GRINDER.get()), FossilGrinderRecipeCategory.FOSSIL_GRINDING_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.FOSSIL_CLEANER.get()), FossilCleanerRecipeCategory.FOSSIL_CLEANING_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DNA_HYBRIDIZER.get()), DNAHybridizerRecipeCategory.DNA_HYBRIDIZING_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.EMBRYONIC_MACHINE.get()), EmbryonicMachineRecipeCategory.EMBRYONIC_MACHINING_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.EMBRYO_CALCIFICATION_MACHINE.get()), EmbryoCalcificationMachineRecipeCategory.EMBRYO_CALCIFICATION_MACHINING_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.INCUBATOR.get()), IncubatorRecipeCategory.INCUBATING_RECIPE_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(
                DNAExtractorMenu.class,
                ModMenuTypes.DNA_EXTRACTOR_MENU.get(),
                DNAExtractorRecipeCategory.DNA_EXTRACTOR_RECIPE_RECIPE_TYPE,
                36, // The index of the FIRST recipe input slot in your Menu (slot 36)
                2,  // The NUMBER of recipe input slots (slots 36, 37)
                0,  // The index where the player inventory slots START (slot 0)
                36  // The NUMBER of player inventory slots to check (slots 0-35)
        );
        registration.addRecipeTransferHandler(
                FossilGrinderMenu.class,
                ModMenuTypes.FOSSIL_GRINDER_MENU.get(),
                FossilGrinderRecipeCategory.FOSSIL_GRINDING_RECIPE_RECIPE_TYPE,
                36, // The index of the FIRST recipe input slot in your Menu (slot 36)
                1,  // The NUMBER of recipe input slots (slots 36, 37)
                0,  // The index where the player inventory slots START (slot 0)
                36  // The NUMBER of player inventory slots to check (slots 0-35)
        );
        registration.addRecipeTransferHandler(
                FossilCleanerMenu.class,
                ModMenuTypes.FOSSIL_CLEANER_MENU.get(),
                FossilCleanerRecipeCategory.FOSSIL_CLEANING_RECIPE_RECIPE_TYPE,
                36, // The index of the FIRST recipe input slot in your Menu (slot 36)
                2,  // The NUMBER of recipe input slots (slots 36, 37)
                0,  // The index where the player inventory slots START (slot 0)
                36  // The NUMBER of player inventory slots to check (slots 0-35)
        );
        registration.addRecipeTransferHandler(
                DNAHybridizerMenu.class,
                ModMenuTypes.DNA_HYBRIDIZER_MENU.get(),
                DNAHybridizerRecipeCategory.DNA_HYBRIDIZING_RECIPE_RECIPE_TYPE,
                36, // The index of the FIRST recipe input slot in your Menu (slot 36)
                3,  // The NUMBER of recipe input slots (slots 36, 37)
                0,  // The index where the player inventory slots START (slot 0)
                36  // The NUMBER of player inventory slots to check (slots 0-35)
        );
        registration.addRecipeTransferHandler(
                EmbryonicMachineMenu.class,
                ModMenuTypes.EMBRYONIC_MACHINE_MENU.get(),
                EmbryonicMachineRecipeCategory.EMBRYONIC_MACHINING_RECIPE_RECIPE_TYPE,
                36, // The index of the FIRST recipe input slot in your Menu (slot 36)
                2,  // The NUMBER of recipe input slots (slots 36, 37)
                0,  // The index where the player inventory slots START (slot 0)
                36  // The NUMBER of player inventory slots to check (slots 0-35)
        );
        registration.addRecipeTransferHandler(
                EmbryoCalcificationMachineMenu.class,
                ModMenuTypes.EMBRYO_CALCIFICATION_MACHINE_MENU.get(),
                EmbryoCalcificationMachineRecipeCategory.EMBRYO_CALCIFICATION_MACHINING_RECIPE_RECIPE_TYPE,
                36, // The index of the FIRST recipe input slot in your Menu (slot 36)
                2,  // The NUMBER of recipe input slots (slots 36, 37)
                0,  // The index where the player inventory slots START (slot 0)
                36  // The NUMBER of player inventory slots to check (slots 0-35)
        );
        registration.addRecipeTransferHandler(
                IncubatorMenu.class,
                ModMenuTypes.INCUBATOR_MENU.get(),
                IncubatorRecipeCategory.INCUBATING_RECIPE_RECIPE_TYPE,
                36, // The index of the FIRST recipe input slot in your Menu (slot 36)
                3,  // The NUMBER of recipe input slots (slots 36, 37)
                0,  // The index where the player inventory slots START (slot 0)
                36  // The NUMBER of player inventory slots to check (slots 0-35)
        );
    }
}
