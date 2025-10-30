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
import net.cmr.jurassicrevived.recipe.FossilGrinderRecipe;
import net.cmr.jurassicrevived.screen.renderer.EnergyDisplayTooltipArea;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.world.item.Item;

public class FossilGrinderRecipeCategory implements IRecipeCategory<FossilGrinderRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "fossil_grinding");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/fossil_grinder/fossil_grinder_gui.png");
    private static final ResourceLocation CUTTING_BLADES_TEXTURE = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/generic/cutting_blades.png");
    private static final ResourceLocation POWER_BAR_TEXTURE = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/generic/power_bar.png");

    public static final RecipeType<FossilGrinderRecipe> FOSSIL_GRINDING_RECIPE_RECIPE_TYPE =
            new RecipeType<>(UID, FossilGrinderRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public FossilGrinderRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.drawableBuilder(TEXTURE, 0, 0, 176, 80).setTextureSize(176, 166).build();
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.FOSSIL_GRINDER.get()));
    }

    @Override
    public RecipeType<FossilGrinderRecipe> getRecipeType() {
        return FOSSIL_GRINDING_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.jurassicrevived.fossil_grinder");
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
    public void draw(FossilGrinderRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);

        {
            final float scale = 1.25f;
            final int texSize = 16;

            float baseAngle = (System.currentTimeMillis() % 700L) / 700.0f * ((float)Math.PI * 2.0f);

            java.util.function.BiConsumer<int[], Float> drawBlade = (center, ang) -> {
                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate(center[0], center[1], 0);
                guiGraphics.pose().scale(scale, scale, 1.0f);
                if (ang != null) {
                    guiGraphics.pose().mulPose(com.mojang.math.Axis.ZP.rotation(-ang));
                }
                guiGraphics.pose().translate(-texSize / 2f, -texSize / 2f, 0);
                guiGraphics.blit(CUTTING_BLADES_TEXTURE, 0, 0, 0, 0, texSize, texSize, texSize, texSize);
                guiGraphics.pose().popPose();
            };

            int x = 0;
            int y = 0;
            int cx1 = x + 89, cy1 = y + 34;
            int cx2 = x + 89, cy2 = y + 52;

            drawBlade.accept(new int[]{cx1, cy1}, baseAngle);
            drawBlade.accept(new int[]{cx2, cy2}, -baseAngle);
        }

        if (Config.REQUIRE_POWER) {
            guiGraphics.blit(POWER_BAR_TEXTURE,  159, 10, 0, 0, 10, 66, 10, 66);
            int barX = 160;
            int barY = 11;
            int barW = 8;
            int barH = 64;


            int requiredFE = 1000;
            int capacityFE = 64000;
            int filled = (int)(barH * (requiredFE / (float)capacityFE));
            guiGraphics.fillGradient(barX, barY + (barH - filled), barX + barW, barY + barH, 0xffb51500, 0xff600b00);

            int mx = (int) mouseX;
            int my = (int) mouseY;
            if (mx >= barX && mx < barX + barW && my >= barY && my < barY + barH) {
                List<Component> tips = java.util.List.of(Component.literal("1000 / 64000 FE"));
                guiGraphics.renderTooltip(Minecraft.getInstance().font, tips, java.util.Optional.empty(), mx, my);
            }
        }
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FossilGrinderRecipe recipe, IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.INPUT, 57, 35).addIngredients(recipe.getIngredients().get(0));

        if (recipe.hasAnyWeightsConfigured()) {
            Map<Item, Integer> weights = recipe.getWeightedOutputs();
            List<ItemStack> stacks = new ArrayList<>(weights.size());
            for (Map.Entry<Item, Integer> e : weights.entrySet()) {
                stacks.add(new ItemStack(e.getKey()));
            }

            builder.addSlot(RecipeIngredientRole.OUTPUT, 103, 17)
                    .addItemStacks(stacks)
                    .addRichTooltipCallback((slotView, tooltip) -> {
                        slotView.getDisplayedIngredient(VanillaTypes.ITEM_STACK).ifPresent(stack -> {
                            int weight = recipe.getWeightOrDefault(stack.getItem(), 0);
                            tooltip.add(Component.literal("Weight: " + weight));
                        });
                    });
        } else {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 85, 17).addItemStack(recipe.getResultItem(null));
        }
    }

}
