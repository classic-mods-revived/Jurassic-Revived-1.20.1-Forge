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
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class FossilGrinderScreen extends AbstractContainerScreen<FossilGrinderMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/fossil_grinder/fossil_grinder_gui.png");
    private static final ResourceLocation CUTTING_BLADES_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/generic/cutting_blades.png");
    private static final ResourceLocation POWER_BAR_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/generic/power_bar.png");
    private static final ResourceLocation SKULL_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/generic/skull.png");
    private EnergyDisplayTooltipArea energyInfoArea;

    public FossilGrinderScreen(FossilGrinderMenu menu, Inventory playerInventory, Component title) {
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
        guiGraphics.blit(SKULL_TEXTURE,  x + 57, y + 35, 0, 0, 16, 16, 16, 16);

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

            int cx1 = x + 89, cy1 = y + 34;
            int cx2 = x + 89, cy2 = y + 52;

            if (menu.isCrafting()) {
                drawBlade.accept(new int[]{cx1, cy1}, baseAngle);
                drawBlade.accept(new int[]{cx2, cy2}, -baseAngle);
            } else {
                drawBlade.accept(new int[]{cx1, cy1}, null);
                drawBlade.accept(new int[]{cx2, cy2}, null);
            }
        }

        if (Config.REQUIRE_POWER) {
            guiGraphics.blit(POWER_BAR_TEXTURE, x+159, y+10, 0, 0, 10, 66, 10, 66);

        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) /2;

        if (Config.REQUIRE_POWER) {
            energyInfoArea.render(guiGraphics);
        }
    }

    public static boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
