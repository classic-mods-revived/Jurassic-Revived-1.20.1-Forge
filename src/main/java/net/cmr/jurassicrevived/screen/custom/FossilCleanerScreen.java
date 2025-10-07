package net.cmr.jurassicrevived.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.cmr.jurassicrevived.Config;
import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.screen.renderer.EnergyDisplayTooltipArea;
import net.cmr.jurassicrevived.screen.renderer.FluidTankRenderer;
import net.cmr.jurassicrevived.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

import java.util.Optional;

public class FossilCleanerScreen extends AbstractContainerScreen<FossilCleanerMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/fossil_cleaner/fossil_cleaner_gui.png");
    private static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/generic/arrow.png");
    private static final ResourceLocation WHITE_ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/generic/white_arrow.png");
    private static final ResourceLocation POWER_BAR_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/generic/power_bar.png");
    private static final ResourceLocation SKULL_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/generic/skull.png");
    private FluidTankRenderer fluidRenderer;
    private EnergyDisplayTooltipArea energyInfoArea;

    public FossilCleanerScreen(FossilCleanerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();

        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;

        assignFluidRenderer();

        assignEnergyInfoArea();
    }

    private void renderEnergyAreaTooltip(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 160, 11, 8, 64) && Config.REQUIRE_POWER) {
            guiGraphics.renderTooltip(this.font, energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyDisplayTooltipArea(((width - imageWidth) / 2) + 160,
                ((height - imageHeight) / 2) + 11, menu.blockEntity.getEnergyStorage(null));
    }

    private void assignFluidRenderer() {
        fluidRenderer = new FluidTankRenderer(16000, true, 16, 50);
    }

    private void renderFluidTooltipArea(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y,
                                        FluidStack stack, int offsetX, int offsetY, FluidTankRenderer renderer) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, renderer)) {
            guiGraphics.renderTooltip(this.font, renderer.getTooltip(stack, TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        // Make hover area align with the tank's top-left corner (16x50)
        renderFluidTooltipArea(guiGraphics, pMouseX, pMouseY, x, y, menu.blockEntity.getFluid(), 7, 8, fluidRenderer);

        if (Config.REQUIRE_POWER) {
            renderEnergyAreaTooltip(guiGraphics, pMouseX, pMouseY, x, y);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) /2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 176, 166);
        guiGraphics.blit(ARROW_TEXTURE,  x + 76, y + 35, 0, 0, 24, 16, 24, 16);

        guiGraphics.blit(SKULL_TEXTURE,  x + 57, y + 35, 0, 0, 16, 16, 16, 16);

        fluidRenderer.render(guiGraphics, x + 7, y + 8, menu.blockEntity.getFluid());

        if (Config.REQUIRE_POWER) {
            guiGraphics.blit(POWER_BAR_TEXTURE, x+159, y+10, 0, 0, 10, 66, 10, 66);
        }
    }

    private void RenderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(WHITE_ARROW_TEXTURE, x+76, y + 35, 0, 0, menu.getScaledArrowProgress(), 16, 24, 16);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) /2;

        // Draw hover highlight ABOVE background + fluid, but BELOW tooltips
        if (MouseUtil.isMouseOver(mouseX, mouseY, x + 7, y + 8, 16, 50)) {
            renderHoverHighlight(guiGraphics, x + 7, y + 8, 16, 50);
        }

        // Draw tooltips last so they appear above everything
        renderTooltip(guiGraphics, mouseX, mouseY);

        RenderProgressArrow(guiGraphics, x, y);

        if (Config.REQUIRE_POWER) {
            energyInfoArea.render(guiGraphics);
        }
    }

    // Draws a translucent white overlay similar to vanilla slot hover highlight
    private static void renderHoverHighlight(GuiGraphics g, int x, int y, int w, int h) {
        // Push to a higher Z so it renders above the tank contents
        var pose = g.pose();
        pose.pushPose();
        pose.translate(0.0F, 0.0F, 200.0F); // large enough to be on top of background/fluid
        // Same color at top and bottom to avoid gradient banding; alpha ~0.5
        g.fillGradient(x, y, x + w, y + h, 0x80FFFFFF, 0x80FFFFFF);
        pose.popPose();
    }

    public static boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidTankRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }

    public static boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
