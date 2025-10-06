package net.cmr.jurassicrevived.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.screen.renderer.EnergyDisplayTooltipArea;
import net.cmr.jurassicrevived.screen.renderer.FluidTankRenderer;
import net.cmr.jurassicrevived.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class GeneratorScreen extends AbstractContainerScreen<GeneratorMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID,"textures/gui/generator/generator_gui.png");
    private static final ResourceLocation LIT_PROGRESS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID,"textures/gui/generic/lit_progress.png");
    private EnergyDisplayTooltipArea energyInfoArea;

    public GeneratorScreen(GeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        // Gets rid of title and inventory title
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;

        assignEnergyInfoArea();
    }

    private void renderEnergyAreaTooltip(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 156, 11, 8, 64)) {
            guiGraphics.renderTooltip(this.font, energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyDisplayTooltipArea(((width - imageWidth) / 2) + 156,
                ((height - imageHeight) / 2) + 11, menu.blockEntity.getEnergyStorage(null));
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltip(guiGraphics, pMouseX, pMouseY, x, y);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        energyInfoArea.render(guiGraphics);
    }

    private void renderFuelBurning(GuiGraphics guiGraphics, int x, int y) {
        if (this.menu.isBurning()) {
            float progress = Mth.clamp(this.menu.getFuelProgress(), 0.0F, 1.0F);
            int minPixels = 2;
            int visible = Mth.clamp(Mth.ceil(progress * 14.0F), minPixels, 14);

            int texW = 14, texH = 14;
            int width = 14, height = visible;

            int srcU = 0;
            int srcV = texH - visible;

            int destX = x + 80;
            int bottomY = y + 18 + 14;
            int destY = bottomY - visible;

            guiGraphics.blit(LIT_PROGRESS_TEXTURE, destX, destY, srcU, srcV, width, height, texW, texH);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
        renderFuelBurning(guiGraphics, x, y);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidTankRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}