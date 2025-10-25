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
import net.cmr.jurassicrevived.recipe.IncubatorRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IncubatorRecipeCategory implements IRecipeCategory<IncubatorRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "incubating");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/incubator/incubator_gui.png");
    private static final ResourceLocation LIT_PROGRESS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID,"textures/gui/generic/lit_progress.png");
    private static final ResourceLocation POWER_BAR_TEXTURE = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/generic/power_bar.png");

    public static final RecipeType<IncubatorRecipe> INCUBATING_RECIPE_RECIPE_TYPE =
            new RecipeType<>(UID, IncubatorRecipe.class);


    private final IDrawable background;
    private final IDrawable icon;

    public IncubatorRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.drawableBuilder(TEXTURE, 0, 0, 176, 80).setTextureSize(176, 166).build();
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.INCUBATOR.get()));
    }

    @Override
    public RecipeType<IncubatorRecipe> getRecipeType() {
        return INCUBATING_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.jurassicrevived.incubator");
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
    public void draw(IncubatorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics g, double mouseX, double mouseY) {
        background.draw(g);
        // Rising flame indicator on all three slots
        renderRisingFlame(g, 50);
        renderRisingFlame(g, 80);
        renderRisingFlame(g, 110);

        // 2-second toggle for which stack to paint on top of each slot
        boolean showOutput = ((System.currentTimeMillis() / 2000L) & 1L) == 1L;
        ItemStack out = recipe.getResultItem(null);
        ItemStack in = recipe.getIngredients().isEmpty() ? ItemStack.EMPTY
                : recipe.getIngredients().get(0).getItems().length > 0
                ? recipe.getIngredients().get(0).getItems()[0]
                : ItemStack.EMPTY;

        // Paint chosen stack at the three slot centers (icons are 16x16; slot centers are 50/80/110,35)
        if (!in.isEmpty() || !out.isEmpty()) {
            ItemStack toRender = showOutput && !out.isEmpty() ? out : in;
            // Render at all three positions so it looks like each slot is toggling
            drawStackIcon(g, toRender, 50, 35);
        }

        if (Config.REQUIRE_POWER) {
            g.blit(POWER_BAR_TEXTURE,  159, 10, 0, 0, 10, 66, 10, 66);
            int barX = 160, barY = 11, barW = 8, barH = 64;
            int requiredFE = 2000, capacityFE = 16000;
            int filled = (int)(barH * (requiredFE / (float)capacityFE));
            g.fillGradient(barX, barY + (barH - filled), barX + barW, barY + barH, 0xffb51500, 0xff600b00);

            int mx = (int) mouseX, my = (int) mouseY;
            if (mx >= barX && mx < barX + barW && my >= barY && my < barY + barH) {
                List<Component> tips = List.of(Component.literal("2000 / 16000 FE"));
                g.renderTooltip(Minecraft.getInstance().font, tips, java.util.Optional.empty(), mx, my);
            }
        }
    }

    private void drawStackIcon(GuiGraphics g, ItemStack stack, int x, int y) {
        if (stack.isEmpty()) return;
        g.renderItem(stack, x, y);
        g.renderItemDecorations(Minecraft.getInstance().font, stack, x, y);
    }

    // Render a rising furnace flame (14x14 sprite) at the given slot center-x (50/80/110)
    private void renderRisingFlame(GuiGraphics g, int slotPixelX) {
        long now = System.currentTimeMillis();
        // 10s slow cycle so all three flames look steady in JEI
        float t = (now % 10000L) / 10000f;
        int visible = Mth.clamp(Mth.ceil(t * 13.0F) + 1, 1, 14);

        int texW = 14, texH = 14;
        int srcU = 0;
        int srcV = texH - visible;

        int destX = slotPixelX;
        int bottomY = 55 + 14;
        int destY = bottomY - visible;

        // Use blit(destX, destY, srcU, srcV, width, height, texW, texH)
        g.blit(LIT_PROGRESS_TEXTURE, destX, destY, srcU, srcV, 14, visible, texW, texH);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IncubatorRecipe recipe, IFocusGroup focuses) {
        // Build static slots once; dynamic toggle is handled in draw()
        // Put the recipe’s input and output into each slot so JEI focus/tooltip works regardless of the overlay
        var ing = recipe.getIngredients().isEmpty() ? null : recipe.getIngredients().get(0);
        if ( ing != null ) {
            builder.addSlot(RecipeIngredientRole.INPUT, 50, 35).addIngredients(ing).addItemStack(recipe.getResultItem(null));
        } else {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 50, 35).addItemStack(recipe.getResultItem(null));
        }
    }
}
