package net.cmr.jurassicrevived.compat;

import net.minecraft.client.gui.GuiGraphics;
import snownee.jade.api.ui.IBoxStyle;

// Simple solid border with configurable width and color (ARGB)
public final class ThickBorderBox implements IBoxStyle {
    private final float width;
    private final int borderColor; // ARGB, e.g. 0xFFFFFFFF for white

    public ThickBorderBox(float width) {
        this(width, 0xFFFFFFFF); // default opaque white
    }

    public ThickBorderBox(float width, int borderColor) {
        this.width = width;
        this.borderColor = borderColor;
    }

    @Override
    public float borderWidth() {
        return width;
    }

    @Override
    public void render(GuiGraphics g, float x, float y, float w, float h) {
        if (width <= 0) return;
        float r = x + w;
        float b = y + h;

        // top
        g.fill((int) x, (int) y, (int) r, (int) (y + width), borderColor);
        // bottom
        g.fill((int) x, (int) (b - width), (int) r, (int) b, borderColor);
        // left
        g.fill((int) x, (int) (y + width), (int) (x + width), (int) (b - width), borderColor);
        // right
        g.fill((int) (r - width), (int) (y + width), (int) r, (int) (b - width), borderColor);
    }
}
