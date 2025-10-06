package net.cmr.jurassicrevived.compat;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.recipe.DNAExtractorRecipe;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class DNAExtractorRecipeCategory implements IRecipeCategory<DNAExtractorRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "dna_extracting");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/dna_extractor/dna_extractor_gui.png");

    public static final RecipeType<DNAExtractorRecipe> DNA_EXTRACTOR_RECIPE_RECIPE_TYPE =
            new RecipeType<>(UID, DNAExtractorRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public DNAExtractorRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.drawableBuilder(TEXTURE, 0, 0, 176, 80).setTextureSize(176, 166).build();
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.DNA_EXTRACTOR.get()));
    }

    @Override
    public RecipeType<DNAExtractorRecipe> getRecipeType() {
        return DNA_EXTRACTOR_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.jurassicrevived.dna_extractor");
    }

    @Override
    public int getWidth() {
        return background.getWidth();
    }

    @Override
    public int getHeight() {
        return background.getHeight();
    }

    @Override
    public void draw(DNAExtractorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DNAExtractorRecipe recipe, IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.INPUT, 39, 35).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 57, 35).addIngredients(recipe.getIngredients().get(1));

        // If the second ingredient is Mosquito in Amber, cycle all DNA as possible outputs
        Ingredient second = recipe.getIngredients().size() > 1 ? recipe.getIngredients().get(1) : Ingredient.EMPTY;
        if (!second.isEmpty() && second.test(new ItemStack(ModItems.MOSQUITO_IN_AMBER.get()))) {
            ItemStack[] candidates = Ingredient.of(ModTags.Items.DNA).getItems();
            builder.addSlot(RecipeIngredientRole.OUTPUT, 103, 35)
                    .addItemStacks(java.util.Arrays.asList(candidates))
                    .addRichTooltipCallback((slotView, tooltip) -> {
                        slotView.getDisplayedIngredient(VanillaTypes.ITEM_STACK).ifPresent(stack -> {
                            if (recipe.hasAnyWeightsConfigured()) {
                                int weight = recipe.getWeightOrDefault(stack.getItem(), 1);
                                tooltip.add(Component.literal("Weight: " + weight));
                            }
                        });
                    });
        } else {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 103, 35).addItemStack(recipe.getResultItem(null));
        }
    }

}
