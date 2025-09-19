package net.jurassicrevived.jurassicrevived.compat;

import mezz.jei.api.ingredients.IIngredientRenderer;
import net.jurassicrevived.jurassicrevived.screen.renderer.FluidTankRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class FluidStackSlotRenderer implements IIngredientRenderer<FluidStack> {
    private final FluidTankRenderer tankRenderer;

    public FluidStackSlotRenderer(FluidTankRenderer tankRenderer) {
        this.tankRenderer = tankRenderer;
    }

    @Override
    public int getWidth() {
        return tankRenderer.getWidth();
    }

    @Override
    public int getHeight() {
        return tankRenderer.getHeight();
    }

    @Override
    public void render(GuiGraphics guiGraphics, FluidStack ingredient) {
        // Slot renders at its own x,y; JEI calls us with the pose already translated.
        tankRenderer.render(guiGraphics, 0, 0, ingredient);
    }

    @Override
    public List<Component> getTooltip(FluidStack ingredient, TooltipFlag tooltipFlag) {
        return tankRenderer.getTooltip(ingredient, tooltipFlag);
    }
}
