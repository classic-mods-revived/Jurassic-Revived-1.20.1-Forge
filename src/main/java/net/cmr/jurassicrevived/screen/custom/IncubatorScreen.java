package net.cmr.jurassicrevived.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.cmr.jurassicrevived.Config;
import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.screen.renderer.EnergyDisplayTooltipArea;
import net.cmr.jurassicrevived.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class IncubatorScreen extends AbstractContainerScreen<IncubatorMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/incubator/incubator_gui.png");
    private static final ResourceLocation POWER_BAR_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/generic/power_bar.png");
    private static final ResourceLocation LIT_PROGRESS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID,"textures/gui/generic/lit_progress.png");
    private static final ResourceLocation EGG_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/generic/egg.png");
    private EnergyDisplayTooltipArea energyInfoArea;

    public IncubatorScreen(IncubatorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();

        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;

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

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

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

        guiGraphics.blit(EGG_TEXTURE,  x + 50, y + 35, 0, 0, 16, 16, 16, 16);
        guiGraphics.blit(EGG_TEXTURE,  x + 80, y + 35, 0, 0, 16, 16, 16, 16);
        guiGraphics.blit(EGG_TEXTURE,  x + 110, y + 35, 0, 0, 16, 16, 16, 16);

        // Per-slot progress bars
        renderProgressBar(guiGraphics, x + 50, y + 55, 0);
        renderProgressBar(guiGraphics, x + 80, y + 55, 1);
        renderProgressBar(guiGraphics, x + 110, y + 55, 2);

        if (Config.REQUIRE_POWER) {
            guiGraphics.blit(POWER_BAR_TEXTURE, x+159, y+10, 0, 0, 10, 66, 10, 66);

        }
    }

    private void renderProgressBar(GuiGraphics gg, int destX, int topY, int slot) {
        if (menu.isCrafting(slot)) {
            float progress = Mth.clamp(menu.getProgressRatio(slot), 0.0F, 1.0F);
            int minPixels = 2;
            int visible = Mth.clamp(Mth.ceil(progress * 14.0F), minPixels, 14);

            int texW = 14, texH = 14;
            int width = 14, height = visible;

            int srcU = 0;
            int srcV = texH - visible;

            int bottomY = topY + 14;
            int destY = bottomY - visible;

            gg.blit(LIT_PROGRESS_TEXTURE, destX, destY, srcU, srcV, width, height, texW, texH);
        }
    }

    @Override
    public void render(GuiGraphics gg, int mouseX, int mouseY, float partialTick) {
        renderBackground(gg);
        super.render(gg, mouseX, mouseY, partialTick);
        renderTooltip(gg, mouseX, mouseY);
        if (Config.REQUIRE_POWER) {
            energyInfoArea.render(gg);
        }
    }

    public static boolean isMouseAboveArea(int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(mouseX, mouseY, x + offsetX, y + offsetY, width, height);
    }
}
