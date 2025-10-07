package net.cmr.jurassicrevived.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.cmr.jurassicrevived.Config;
import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.recipe.DNAHybridizerRecipe;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DNAHybridizerRecipeCategory implements IRecipeCategory<DNAHybridizerRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "dna_hybridizing");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/dna_hybridizer/dna_hybridizer_gui.png");
    public static final ResourceLocation ARROW_TEXTURE = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/generic/arrow.png");
    public static final ResourceLocation WHITE_ARROW_TEXTURE = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/generic/white_arrow.png");
    private static final ResourceLocation POWER_BAR_TEXTURE = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/generic/power_bar.png");

    public static final RecipeType<DNAHybridizerRecipe> DNA_HYBRIDIZING_RECIPE_RECIPE_TYPE =
            new RecipeType<>(UID, DNAHybridizerRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public DNAHybridizerRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.drawableBuilder(TEXTURE, 0, 0, 176, 80).setTextureSize(176, 166).build();
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.DNA_HYBRIDIZER.get()));
    }

    @Override
    public RecipeType<DNAHybridizerRecipe> getRecipeType() {
        return DNA_HYBRIDIZING_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.jurassicrevived.dna_hybridizer");
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
    public void draw(DNAHybridizerRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
        guiGraphics.blit(ARROW_TEXTURE,  76, 35, 0, 0, 24, 16, 24, 16);
        if (Config.REQUIRE_POWER) {
            guiGraphics.blit(POWER_BAR_TEXTURE,  159, 10, 0, 0, 10, 66, 10, 66);
            // Fill amount for JEI: show total required energy (2000 FE) relative to 16000 FE capacity
            // Our simple fill is purely visual for JEI, not tied to any BE
            int barX = 160;
            int barY = 11;
            int barW = 8;
            int barH = 64;

            int maxTicks = 200;
            long now = System.currentTimeMillis();
            int progress = (int)((now / 50L) % maxTicks); // ~20 TPS
            int arrowPixels = 24;
            int progFilled = progress * arrowPixels / maxTicks;
            if (progFilled > 0) {
                guiGraphics.blit(WHITE_ARROW_TEXTURE, 76, 35, 0, 0, progFilled, 16, 24, 16);
            }

            int requiredFE = 2000;
            int capacityFE = 16000;
            int filled = (int)(barH * (requiredFE / (float)capacityFE));
            // Render red fill similar to EnergyDisplayTooltipArea
            guiGraphics.fillGradient(barX, barY + (barH - filled), barX + barW, barY + barH, 0xffb51500, 0xff600b00);

            // Tooltip "(2000 / 16000 FE)" on hover over the energy area
            int mx = (int) mouseX;
            int my = (int) mouseY;
            if (mx >= barX && mx < barX + barW && my >= barY && my < barY + barH) {
                List<Component> tips = java.util.List.of(Component.literal("2000 / 16000 FE"));
                guiGraphics.renderTooltip(Minecraft.getInstance().font, tips, java.util.Optional.empty(), mx, my);
            }
        }
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DNAHybridizerRecipe recipe, IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.INPUT, 57, 26).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 57, 44).addIngredients(recipe.getIngredients().get(1));

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
