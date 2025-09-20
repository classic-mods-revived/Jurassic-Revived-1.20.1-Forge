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

    // Pinned buttons
    private Button doneButton;
    private Button cancelButton;

    // Scroll state
    private int scrollY = 0;
    private int contentHeight = 0;
    private int scrollAreaTop;
    private int scrollAreaBottom;
    private int scrollAreaLeft;
    private int scrollAreaRight;

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

        // Define scroll zone (full width minus small side padding), from below title to above pinned buttons
        int sidePad = 8;
        int titleBottom = 36; // space for the title area
        int pinnedButtonsHeight = 28; // room for pinned Done/Cancel
        scrollAreaLeft = sidePad;
        scrollAreaRight = this.width - sidePad;
        scrollAreaTop = titleBottom;
        scrollAreaBottom = this.height - pinnedButtonsHeight;

        // Create inputs at logical positions (unscrolled coordinates). We'll offset them in render.
        int y = 0;

        requirePowerButton = Button.builder(toggleLabel(), b -> {
            requirePower = !requirePower;
            b.setMessage(toggleLabel());
        }).bounds(left, y, 200, 20).build();
        addRenderableWidget(requirePowerButton);

        y += 60;
        itemsBox = new EditBox(this.font, left, y, 200, 20, Component.literal("item/sec"));
        itemsBox.setFilter(s -> s.isEmpty() || s.chars().allMatch(Character::isDigit));
        itemsBox.setValue(Integer.toString(itemsPerSec));
        addRenderableWidget(itemsBox);

        y += 60;
        mbBox = new EditBox(this.font, left, y, 200, 20, Component.literal("mB/sec"));
        mbBox.setFilter(s -> s.isEmpty() || s.chars().allMatch(Character::isDigit));
        mbBox.setValue(Integer.toString(mbPerSec));
        addRenderableWidget(mbBox);

        y += 60;
        feBox = new EditBox(this.font, left, y, 200, 20, Component.literal("FE/sec"));
        feBox.setFilter(s -> s.isEmpty() || s.chars().allMatch(Character::isDigit));
        feBox.setValue(Integer.toString(fePerSec));
        addRenderableWidget(feBox);

        // Compute total content height (logical coordinates)
        contentHeight = y + 20 + 16; // last control bottom + padding

        // Pinned buttons at the bottom
        int buttonsY = this.height - 24; // slight top padding from bottom
        doneButton = addRenderableWidget(Button.builder(Component.translatable("gui.done"), b -> saveAndClose())
                .bounds(left, buttonsY, 95, 20).build());
        cancelButton = addRenderableWidget(Button.builder(Component.translatable("gui.cancel"), b -> onClose())
                .bounds(left + 105, buttonsY, 95, 20).build());

        // Ensure initial scroll is valid
        clampScroll();
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

    // Adjust scroll bounds
    private void clampScroll() {
        int visible = Math.max(0, scrollAreaBottom - scrollAreaTop);
        int maxScroll = Math.max(0, contentHeight - visible);
        scrollY = clamp(scrollY, 0, maxScroll);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (mouseY >= scrollAreaTop && mouseY <= scrollAreaBottom) {
            // Negative delta means scroll down visually; increase scrollY
            int step = 20;
            scrollY = clamp(scrollY - (int)(delta * step), 0, Math.max(0, contentHeight - (scrollAreaBottom - scrollAreaTop)));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gfx);

        // Title
        int cx = this.width / 2;
        gfx.drawCenteredString(this.font, this.title, cx, 15, 0xFFFFFF);

        // Scissor to scroll area
        enableScissor(gfx, scrollAreaLeft, scrollAreaTop, scrollAreaRight, scrollAreaBottom);

        // Offset and render children that belong to the scroll zone
        int dy = scrollAreaTop - scrollY;
        // Move components vertically for rendering and hitboxes
        // We'll temporarily shift Y of scrollable widgets, render, then restore.
        int[] originalYs = new int[] {
            requirePowerButton.getY(), itemsBox.getY(), mbBox.getY(), feBox.getY()
        };

        requirePowerButton.setY(dy + 0);
        itemsBox.setY(dy + 60);
        mbBox.setY(dy + 120);
        feBox.setY(dy + 180);

        super.render(gfx, mouseX, mouseY, partialTick);

        // Draw helper texts relative to current positions
        gfx.drawCenteredString(this.font, "Max items transferred per second by item pipes", cx, itemsBox.getY() - 36, 0xA0A0A0);
        gfx.drawCenteredString(this.font, "Default: 64", cx, itemsBox.getY() - 24, 0xA0A0A0);
        gfx.drawCenteredString(this.font, "Range: 0 ~ 1024", cx, itemsBox.getY() - 12, 0xA0A0A0);
        gfx.drawCenteredString(this.font, "Max millibuckets transferred per second by fluid pipes", cx, mbBox.getY() - 36, 0xA0A0A0);
        gfx.drawCenteredString(this.font, "Default: 1,000", cx, mbBox.getY() - 24, 0xA0A0A0);
        gfx.drawCenteredString(this.font, "Range: 0 ~ 100,000", cx, mbBox.getY() - 12, 0xA0A0A0);
        gfx.drawCenteredString(this.font, "Max FE transferred per second by power pipes", cx, feBox.getY() - 36, 0xA0A0A0);
        gfx.drawCenteredString(this.font, "Default: 2,048", cx, feBox.getY() - 24, 0xA0A0A0);
        gfx.drawCenteredString(this.font, "Range: 0 ~ 2,097,152", cx, feBox.getY() - 12, 0xA0A0A0);

        // Restore original Y positions (logical coordinates)
        requirePowerButton.setY(originalYs[0]);
        itemsBox.setY(originalYs[1]);
        mbBox.setY(originalYs[2]);
        feBox.setY(originalYs[3]);

        disableScissor(gfx);

        // Draw a subtle separator above pinned buttons
        gfx.fill(scrollAreaLeft, scrollAreaBottom - 1, scrollAreaRight, scrollAreaBottom, 0x40FFFFFF);

        // Pinned buttons are already placed in init() at the bottom; draw tooltips etc.
        doneButton.render(gfx, mouseX, mouseY, partialTick);
        cancelButton.render(gfx, mouseX, mouseY, partialTick);
    }

    // Recompute layout on resize
    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        int left = this.width / 2 - 100;
        int sidePad = 8;
        int titleBottom = 36;
        int pinnedButtonsHeight = 28;

        scrollAreaLeft = sidePad;
        scrollAreaRight = this.width - sidePad;
        scrollAreaTop = titleBottom;
        scrollAreaBottom = this.height - pinnedButtonsHeight;

        // Reposition pinned buttons
        int buttonsY = this.height - 24;
        doneButton.setX(left);
        doneButton.setY(buttonsY);
        cancelButton.setX(left + 105);
        cancelButton.setY(buttonsY);

        clampScroll();
    }

    // Utility to enable scissor in GUI coordinates
    private void enableScissor(GuiGraphics gfx, int left, int top, int right, int bottom) {
        // Convert to window coords and apply scissor via GuiGraphics. This helper uses existing method.
        // If your version has GuiGraphics.enableScissor, prefer using it directly:
        gfx.enableScissor(left, top, right, bottom);
    }

    private void disableScissor(GuiGraphics gfx) {
        gfx.disableScissor();
    }
}
