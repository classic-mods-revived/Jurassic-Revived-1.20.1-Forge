package net.jurassicrevived.jurassicrevived.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.jurassicrevived.jurassicrevived.JRMod;
import net.jurassicrevived.jurassicrevived.block.ModBlocks;
import net.jurassicrevived.jurassicrevived.recipe.FossilCleanerRecipe;
import net.jurassicrevived.jurassicrevived.screen.renderer.FluidTankRenderer;
import net.jurassicrevived.jurassicrevived.util.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FossilCleanerRecipeCategory implements IRecipeCategory<FossilCleanerRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "fossil_cleaning");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/gui/fossil_cleaner/fossil_cleaner_gui.png");

    public static final RecipeType<FossilCleanerRecipe> FOSSIL_CLEANING_RECIPE_RECIPE_TYPE =
            new RecipeType<>(UID, FossilCleanerRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final FluidTankRenderer fluidRenderer;
    private static List<ItemStack> WATER_CONTAINERS_CACHE = null;

    public FossilCleanerRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.drawableBuilder(TEXTURE, 0, 0, 176, 80).setTextureSize(176, 166).build();
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.FOSSIL_CLEANER.get()));
        this.fluidRenderer = new FluidTankRenderer(16000, true, 16, 50);
        if (WATER_CONTAINERS_CACHE == null) {
            WATER_CONTAINERS_CACHE = buildWaterContainersList();
        }
    }

    @Override
    public RecipeType<FossilCleanerRecipe> getRecipeType() {
        return FOSSIL_CLEANING_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.jurassicrevived.fossil_cleaner");
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
    public void draw(FossilCleanerRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FossilCleanerRecipe recipe, IFocusGroup focuses) {

        // Single input: fossil block
        builder.addSlot(RecipeIngredientRole.INPUT, 57, 35).addIngredients(recipe.getIngredients().get(0));

        // Fluid "tank" visualization using custom renderer at (7, 8)
        IRecipeSlotBuilder tankSlot = builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 7, 8);
        tankSlot.addIngredient(ForgeTypes.FLUID_STACK, new FluidStack(Fluids.WATER, 250));
        tankSlot.setCustomRenderer(ForgeTypes.FLUID_STACK, new FluidStackSlotRenderer(fluidRenderer));

        // Water container acceptance list at (57, 61), discovered dynamically
        var waterItems = builder.addSlot(RecipeIngredientRole.INPUT, 57, 61).addItemStacks(WATER_CONTAINERS_CACHE);
        waterItems.addRichTooltipCallback((view, tooltip) -> {
            tooltip.add(Component.translatable("jurassicrevived.tooltip.accepts_any_water_container"));
        });

        // Output cycles all fossils with weight tooltips if present
        ItemStack[] candidates = net.minecraft.world.item.crafting.Ingredient.of(ModTags.Items.FOSSILS).getItems();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 103, 35)
                .addItemStacks(java.util.Arrays.asList(candidates))
                .addRichTooltipCallback((slotView, tooltip) -> {
                    slotView.getDisplayedIngredient(VanillaTypes.ITEM_STACK).ifPresent(stack -> {
                        if (recipe.hasAnyWeightsConfigured()) {
                            int weight = recipe.getWeightOrDefault(stack.getItem(), 1);
                            tooltip.add(Component.literal("Weight: " + weight));
                        }
                    });
                });
    }

    private static List<ItemStack> buildWaterContainersList() {
        var list = new ArrayList<ItemStack>();
        // Always include vanilla water bucket (already filled)
        list.add(new ItemStack(Items.WATER_BUCKET));

        var mc = Minecraft.getInstance();
        var level = mc.level;
        if (level == null) {
            return Collections.unmodifiableList(list);
        }

        final int REQUIRED_MB = 250;

        var itemRegistry = level.registryAccess().registryOrThrow(Registries.ITEM);
        for (Item item : itemRegistry) {
            if (item == Items.WATER_BUCKET) continue;

            ItemStack empty = new ItemStack(item);

            // 1.20.1: capability is ForgeCapabilities.FLUID_HANDLER_ITEM and returns a LazyOptional
            var fhOpt = empty.getCapability(net.minecraftforge.common.capabilities.ForgeCapabilities.FLUID_HANDLER_ITEM);
            if (!fhOpt.isPresent()) continue;

            var fh = fhOpt.orElse(null);
            if (fh == null || fh.getTanks() <= 0) continue;

            // 1) Already contains water?
            boolean hasWaterNow = false;
            for (int t = 0; t < fh.getTanks(); t++) {
                net.minecraftforge.fluids.FluidStack fs = fh.getFluidInTank(t);
                if (!fs.isEmpty() && fs.getFluid().is(net.minecraft.tags.FluidTags.WATER)) {
                    hasWaterNow = true;
                    break;
                }
            }
            if (hasWaterNow) {
                list.add(empty);
                continue;
            }

            // 2) Try to show 250 mB by using the utility path (works for many creative/infinite containers)
            ItemStack filled250 = tryFillContainerWithWater(empty, REQUIRED_MB);
            if (!filled250.isEmpty()) {
                list.add(filled250);
                continue;
            }

            // 3) If partial fill is not possible, try showing completely filled (so it isn't displayed empty)
            ItemStack filledFull = tryFillContainerWithWater(empty, Integer.MAX_VALUE);
            if (!filledFull.isEmpty()) {
                list.add(filledFull);
            }
        }
        return Collections.unmodifiableList(list);
    }

    // Use a temporary source handler pre-filled with water and ask FluidUtil to fill the container.
// Many mod containers (including creative/infinite ones) respect this code path even when direct fill() doesn't work.
    private static ItemStack tryFillContainerWithWater(ItemStack empty, int amountMb) {
        if (amountMb <= 0) return ItemStack.EMPTY;

        // Build a temporary source with the requested amount (or a large capacity for "full")
        int cap = (amountMb == Integer.MAX_VALUE) ? Integer.MAX_VALUE / 4 : amountMb;
        net.minecraftforge.fluids.capability.templates.FluidTank source =
                new net.minecraftforge.fluids.capability.templates.FluidTank(cap) {
                    @Override
                    public boolean isFluidValid(net.minecraftforge.fluids.FluidStack stack) {
                        return stack.getFluid().is(net.minecraft.tags.FluidTags.WATER);
                    }
                };

        int toInsert = (amountMb == Integer.MAX_VALUE) ? cap : amountMb;
        source.fill(new net.minecraftforge.fluids.FluidStack(net.minecraft.world.level.material.Fluids.WATER, toInsert),
                net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE);

        // Ask the utility to fill an item copy (doFill = true)
        net.minecraftforge.fluids.FluidActionResult res =
                net.minecraftforge.fluids.FluidUtil.tryFillContainer(empty.copy(), source, Integer.MAX_VALUE, null, true);

        if (res.isSuccess()) {
            ItemStack out = res.getResult();
            // Sanity check: ensure it's actually water-filled
            var fhOpt = out.getCapability(net.minecraftforge.common.capabilities.ForgeCapabilities.FLUID_HANDLER_ITEM);
            if (fhOpt.isPresent()) {
                var fh = fhOpt.orElse(null);
                if (fh != null) {
                    for (int t = 0; t < fh.getTanks(); t++) {
                        net.minecraftforge.fluids.FluidStack fs = fh.getFluidInTank(t);
                        if (!fs.isEmpty() && fs.getFluid().is(net.minecraft.tags.FluidTags.WATER)) {
                            return out;
                        }
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }
}
