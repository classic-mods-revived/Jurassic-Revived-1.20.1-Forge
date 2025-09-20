package net.jurassicrevived.jurassicrevived;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class JRConfigScreen extends Screen {
    private final Screen parent;

    private EditBox itemsBox;
    private EditBox mbBox;
    private EditBox feBox;
    private Button requirePowerButton;

    private boolean requirePower;
    private int itemsPerSec;
    private int mbPerSec;
    private int fePerSec;

    public JRConfigScreen(Screen parent) {
        super(Component.translatable("jurassicrevived.config.title"));
        this.parent = parent;
        this.requirePower = Config.REQUIRE_POWER;
        this.itemsPerSec = Config.itemsPerSecond;
        this.mbPerSec = Config.milliBucketsPerSecond;
        this.fePerSec = Config.fePerSecond;
    }

    @Override
    protected void init() {
        int left = this.width / 2 - 100;
        int y = this.height / 6;

        requirePowerButton = Button.builder(toggleLabel(), b -> {
            requirePower = !requirePower;
            b.setMessage(toggleLabel());
        }).bounds(left, y, 200, 20).build();
        addRenderableWidget(requirePowerButton);

        // Keep first box placement similar, then add extra spacing for the next boxes
        y += 60; // first box offset
        itemsBox = new EditBox(this.font, left, y, 200, 20, Component.literal("item/sec"));
        itemsBox.setFilter(s -> s.isEmpty() || s.chars().allMatch(Character::isDigit));
        itemsBox.setValue(Integer.toString(itemsPerSec));
        addRenderableWidget(itemsBox);

        y += 60; // increased spacing
        mbBox = new EditBox(this.font, left, y, 200, 20, Component.literal("mB/sec"));
        mbBox.setFilter(s -> s.isEmpty() || s.chars().allMatch(Character::isDigit));
        mbBox.setValue(Integer.toString(mbPerSec));
        addRenderableWidget(mbBox);

        y += 60; // increased spacing
        feBox = new EditBox(this.font, left, y, 200, 20, Component.literal("FE/sec"));
        feBox.setFilter(s -> s.isEmpty() || s.chars().allMatch(Character::isDigit));
        feBox.setValue(Integer.toString(fePerSec));
        addRenderableWidget(feBox);

        y += 44; // slightly more space before buttons
        addRenderableWidget(Button.builder(Component.translatable("gui.done"), b -> saveAndClose())
                .bounds(left, y, 95, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("gui.cancel"), b -> onClose())
                .bounds(left + 105, y, 95, 20).build());
    }

    private Component toggleLabel() {
        return Component.literal("Require Power: ").append(Component.literal(requirePower ? "Enabled" : "Disabled"));
    }

    private static int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    private void saveAndClose() {
        try {
            int items = Integer.parseInt(itemsBox.getValue().trim().isEmpty() ? "0" : itemsBox.getValue().trim());
            int mb = Integer.parseInt(mbBox.getValue().trim().isEmpty() ? "0" : mbBox.getValue().trim());
            int fe = Integer.parseInt(feBox.getValue().trim().isEmpty() ? "0" : feBox.getValue().trim());

            items = clamp(items, 0, 1000);
            mb = clamp(mb, 0, 100_000);
            fe = clamp(fe, 0, 2_048_000);

            Config.setRequirePower(requirePower);
            Config.setItemsPerSecond(items);
            Config.setMilliBucketsPerSecond(mb);
            Config.setFePerSecond(fe);
        } catch (Exception ignored) {
        }
        onClose();
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(parent);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gfx);
        super.render(gfx, mouseX, mouseY, partialTick);
        int cx = this.width / 2;
        gfx.drawCenteredString(this.font, this.title, cx, 15, 0xFFFFFF);
        gfx.drawCenteredString(this.font, "Max items transferred per second by item pipes", cx, itemsBox.getY() - 36, 0xA0A0A0);
        gfx.drawCenteredString(this.font, "Default: 64", cx, itemsBox.getY() - 24, 0xA0A0A0);
        gfx.drawCenteredString(this.font, "Range: 0 ~ 1024", cx, itemsBox.getY() - 12, 0xA0A0A0);
        gfx.drawCenteredString(this.font, "Max millibuckets transferred per second by fluid pipes", cx, mbBox.getY() - 36, 0xA0A0A0);
        gfx.drawCenteredString(this.font, "Default: 1,000", cx, mbBox.getY() - 24, 0xA0A0A0);
        gfx.drawCenteredString(this.font, "Range: 0 ~ 100,000", cx, mbBox.getY() - 12, 0xA0A0A0);
        gfx.drawCenteredString(this.font, "Max FE transferred per second by power pipes", cx, feBox.getY() - 36, 0xA0A0A0);
        gfx.drawCenteredString(this.font, "Default: 2,048", cx, feBox.getY() - 24, 0xA0A0A0);
        gfx.drawCenteredString(this.font, "Range: 0 ~ 2,097,152", cx, feBox.getY() - 12, 0xA0A0A0);
    }
}
