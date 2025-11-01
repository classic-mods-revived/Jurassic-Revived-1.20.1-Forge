package net.cmr.jurassicrevived.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.datagen.custom.*;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> GYPSUM_COBBLESTONE_SMELTABLES = List.of(ModBlocks.GYPSUM_COBBLESTONE.get());
    private static final List<ItemLike> GYPSUM_STONE_SMELTABLES = List.of(ModBlocks.GYPSUM_STONE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

        ICondition requirePowerCondition = new ConfigCondition();

        oreSmelting(pWriter, GYPSUM_COBBLESTONE_SMELTABLES, RecipeCategory.MISC, ModBlocks.GYPSUM_STONE.get(), 0.25f, 200, "gypsum_stone");
        oreBlasting(pWriter, GYPSUM_COBBLESTONE_SMELTABLES, RecipeCategory.MISC, ModBlocks.GYPSUM_STONE.get(), 0.25f, 100, "gypsum_stone");

        oreSmelting(pWriter, GYPSUM_STONE_SMELTABLES, RecipeCategory.MISC, ModBlocks.SMOOTH_GYPSUM_STONE.get(), 0.25f, 200, "smooth_gypsum_stone");
        oreBlasting(pWriter, GYPSUM_STONE_SMELTABLES, RecipeCategory.MISC, ModBlocks.SMOOTH_GYPSUM_STONE.get(), 0.25f, 100, "smooth_gypsum_stone");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GYPSUM_BRICK_STAIRS.get(), 4)
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .define('A', ModBlocks.GYPSUM_STONE_BRICKS.get())
                .unlockedBy("has_gypsum_stone_bricks", has(ModBlocks.GYPSUM_STONE_BRICKS.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GYPSUM_BRICK_SLAB.get(), 6)
                .pattern("AAA")
                .define('A', ModBlocks.GYPSUM_STONE_BRICKS.get())
                .unlockedBy("has_gypsum_stone_bricks", has(ModBlocks.GYPSUM_STONE_BRICKS.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GYPSUM_BRICK_WALL.get(), 6)
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModBlocks.GYPSUM_STONE_BRICKS.get())
                .unlockedBy("has_gypsum_stone_bricks", has(ModBlocks.GYPSUM_STONE_BRICKS.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CHISELED_GYPSUM_STONE.get(), 1)
                .pattern("A")
                .pattern("A")
                .define('A', ModBlocks.GYPSUM_BRICK_SLAB.get())
                .unlockedBy("has_gypsum_bricks_slab", has(ModBlocks.GYPSUM_STONE_BRICKS.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.REINFORCED_BRICK_STAIRS.get(), 4)
                .pattern("A  ")
                .pattern("AA ")
                .pattern("AAA")
                .define('A', ModBlocks.REINFORCED_STONE_BRICKS.get())
                .unlockedBy("has_reinforced_stone_bricks", has(ModBlocks.REINFORCED_STONE_BRICKS.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.REINFORCED_BRICK_SLAB.get(), 6)
                .pattern("AAA")
                .define('A', ModBlocks.REINFORCED_STONE_BRICKS.get())
                .unlockedBy("has_reinforced_stone_bricks", has(ModBlocks.REINFORCED_STONE_BRICKS.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.REINFORCED_BRICK_WALL.get(), 6)
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModBlocks.REINFORCED_STONE_BRICKS.get())
                .unlockedBy("has_reinforced_stone_bricks", has(ModBlocks.REINFORCED_STONE_BRICKS.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CHISELED_REINFORCED_STONE.get(), 1)
                .pattern("A")
                .pattern("A")
                .define('A', ModBlocks.REINFORCED_BRICK_SLAB.get())
                .unlockedBy("has_reinforced_bricks_slab", has(ModBlocks.REINFORCED_STONE_BRICKS.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TRASH_CAN.get(), 1)
                .pattern("AAA")
                .pattern("A A")
                .pattern("AAA")
                .define('A', Items.IRON_INGOT)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BENCH.get(), 1)
                .pattern("A  ")
                .pattern("AAA")
                .pattern("BBB")
                .define('A', Blocks.SPRUCE_PLANKS)
                .define('B', ModBlocks.REINFORCED_STONE_BRICKS.get())
                .unlockedBy("has_spruce_planks", has(Blocks.SPRUCE_PLANKS))
                .unlockedBy("has_reinforced_stone_bricks", has(ModBlocks.REINFORCED_STONE_BRICKS.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FENCE_LIGHT.get(), 1)
                .pattern("A")
                .pattern("B")
                .define('A', Blocks.GLOWSTONE)
                .define('B', Items.IRON_INGOT)
                .unlockedBy("has_glowstone", has(Blocks.GLOWSTONE))
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LIGHT_POST.get(), 1)
                .pattern("A")
                .pattern("B")
                .define('A', Blocks.GLOWSTONE)
                .define('B', ModBlocks.GYPSUM_STONE.get())
                .unlockedBy("has_glowstone", has(Blocks.GLOWSTONE))
                .unlockedBy("has_gypsum_stone", has(ModBlocks.GYPSUM_STONE.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TEST_TUBE.get(), 3)
                .pattern("  A")
                .pattern(" B ")
                .pattern("B  ")
                .define('A', Items.IRON_INGOT)
                .define('B', Blocks.GLASS)
                .unlockedBy("has_TEST_TUBE_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.IRON_INGOT, Blocks.GLASS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SYRINGE.get(), 3)
                .pattern("  A")
                .pattern(" B ")
                .pattern("C  ")
                .define('A', Items.IRON_INGOT)
                .define('B', Blocks.GLASS)
                .define('C', Items.IRON_NUGGET)
                .unlockedBy("has__syringe_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.IRON_INGOT, Blocks.GLASS, Items.IRON_NUGGET).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CABLE.get(), 4)
                .pattern(" BA")
                .pattern("BAB")
                .pattern("AB ")
                .define('A', Items.COPPER_INGOT)
                .define('B', Items.IRON_NUGGET)
                .unlockedBy("has_cable_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.COPPER_INGOT, Items.IRON_NUGGET).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SCREEN.get(), 2)
                .pattern("ABA")
                .pattern("ABA")
                .pattern(" C ")
                .define('A', Items.IRON_INGOT)
                .define('B', Blocks.REDSTONE_LAMP)
                .define('C', ModItems.CABLE.get())
                .unlockedBy("has_screen_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.IRON_INGOT, Blocks.REDSTONE_LAMP, ModItems.CABLE.get()).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PROCESSOR.get())
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', Items.GOLD_NUGGET)
                .define('B', Items.IRON_INGOT)
                .define('C', Items.REDSTONE)
                .unlockedBy("has_processor_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.GOLD_NUGGET, Items.IRON_INGOT, Items.REDSTONE).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TIRE.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Items.INK_SAC)
                .define('B', Items.IRON_INGOT)
                .unlockedBy("has_tire_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.INK_SAC, Items.IRON_INGOT).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CUTTING_BLADES.get(), 4)
                .pattern("A A")
                .pattern(" A ")
                .pattern("A A")
                .define('A', Items.IRON_INGOT)
                .unlockedBy("has_cutting_blades_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.IRON_INGOT).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GYPSUM_STONE_BRICKS.get(), 4)
                .pattern("AA")
                .pattern("AA")
                .define('A', ModBlocks.GYPSUM_STONE.get())
                .unlockedBy("has_gypsum_stone_bricks_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModBlocks.GYPSUM_STONE.get()).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.REINFORCED_STONE.get(), 6)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', Blocks.STONE)
                .unlockedBy("has_reinforced_stone_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Blocks.STONE).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.REINFORCED_STONE_BRICKS.get(), 6)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', Blocks.STONE_BRICKS)
                .unlockedBy("has_reinforced_stone_bricks_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Blocks.STONE_BRICKS).build())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LOW_SECURITY_FENCE_POLE.get(), 8)
                .pattern("ABA")
                .pattern(" B ")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.IRON_NUGGET)
                .unlockedBy("has_low_security_fence_pole_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.IRON_INGOT, Items.IRON_NUGGET).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LOW_SECURITY_FENCE_WIRE.get(), 16)
                .pattern("AAA")
                .pattern(" B ")
                .pattern("AAA")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.REDSTONE)
                .unlockedBy("has_low_security_fence_wire_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.IRON_INGOT, Items.REDSTONE).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MEDIUM_SECURITY_FENCE_POLE.get(), 8)
                .pattern("ABA")
                .pattern("ABA")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.IRON_NUGGET)
                .unlockedBy("has_medium_security_fence_pole_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.IRON_INGOT, Items.IRON_NUGGET).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MEDIUM_SECURITY_FENCE_WIRE.get(), 16)
                .pattern("AAA")
                .pattern("BBB")
                .pattern("AAA")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.REDSTONE)
                .unlockedBy("has_medium_security_fence_wire_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.IRON_INGOT, Items.REDSTONE).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CAT_PLUSHIE.get(), 1)
                .pattern("ABA")
                .pattern("CBD")
                .pattern("BBA")
                .define('A', Blocks.BLACK_WOOL)
                .define('B', Blocks.WHITE_WOOL)
                .define('C', Blocks.GREEN_WOOL)
                .define('D', Blocks.GRAY_WOOL)
                .unlockedBy("has_black_wool", has(Blocks.BLACK_WOOL))
                .unlockedBy("has_white_wool", has(Blocks.WHITE_WOOL))
                .unlockedBy("has_green_wool", has(Blocks.GREEN_WOOL))
                .unlockedBy("has_gray_wool", has(Blocks.GRAY_WOOL)).save(pWriter);

        ConditionalRecipe.builder()
                .addCondition(requirePowerCondition)
                .addRecipe(
                        (consumer) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GENERATOR.get(), 1)
                .pattern("ABA")
                .pattern("CDE")
                .pattern("ABA")
                .define('A', Blocks.IRON_BLOCK)
                .define('B', ModItems.CABLE.get())
                .define('C', Blocks.REDSTONE_BLOCK)
                .define('D', ModItems.PROCESSOR.get())
                .define('E', Items.COPPER_INGOT)
                .unlockedBy("has_generator_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Blocks.IRON_BLOCK, ModItems.CABLE.get(), Blocks.REDSTONE_BLOCK, ModItems.PROCESSOR.get(), Items.COPPER_INGOT).build())).save(consumer))
                .generateAdvancement()
                .build(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "generator_with_power_req"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DNA_EXTRACTOR.get(), 1)
                .pattern("AAA")
                .pattern("BCD")
                .pattern("AAA")
                .define('A', Items.LAPIS_LAZULI)
                .define('B', ModItems.SCREEN.get())
                .define('C', ModItems.CABLE.get())
                .define('D', ModItems.PROCESSOR.get())
                .unlockedBy("has_dna_extractor_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.LAPIS_LAZULI, ModItems.SCREEN.get(), ModItems.CABLE.get(), ModItems.PROCESSOR.get()).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DNA_ANALYZER.get(), 1)
                .pattern("AAA")
                .pattern("BCD")
                .pattern("EEE")
                .define('A', ModItems.TEST_TUBE.get())
                .define('B', ModItems.SCREEN.get())
                .define('C', ModItems.CABLE.get())
                .define('D', ModItems.PROCESSOR.get())
                .define('E', ModItems.SYRINGE.get())
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get()))
                .unlockedBy("has_screen", has(ModItems.SCREEN.get()))
                .unlockedBy("has_cable", has(ModItems.CABLE.get()))
                .unlockedBy("has_processor", has(ModItems.PROCESSOR.get()))
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FOSSIL_GRINDER.get(), 1)
                .pattern("ABA")
                .pattern("CDB")
                .pattern("AEA")
                .define('A', Items.IRON_INGOT)
                .define('B', Blocks.GLASS)
                .define('C', Items.LAPIS_LAZULI)
                .define('D', ModItems.CUTTING_BLADES.get())
                .define('E', Items.WATER_BUCKET)
                .unlockedBy("has_fossil_grinder_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.IRON_INGOT, Blocks.GLASS, Items.LAPIS_LAZULI, ModItems.CUTTING_BLADES.get(), Items.WATER_BUCKET).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FOSSIL_CLEANER.get(), 1)
                .pattern("ABA")
                .pattern("ACA")
                .pattern("AAA")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.IRON_NUGGET)
                .define('C', Blocks.GLASS)
                .unlockedBy("has_fossil_cleaner_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.IRON_INGOT, Items.IRON_NUGGET, Blocks.GLASS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DNA_HYBRIDIZER.get(), 1)
                .pattern("ABA")
                .pattern("CDC")
                .pattern("BEB")
                .define('A', ModItems.SCREEN.get())
                .define('B', Items.IRON_INGOT)
                .define('C', ModItems.CABLE.get())
                .define('D', ModItems.PROCESSOR.get())
                .define('E', Items.REDSTONE)
                .unlockedBy("has_dna_hybridizer_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.SCREEN.get(), Items.IRON_INGOT, ModItems.CABLE.get(), ModItems.PROCESSOR.get(), Items.REDSTONE).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.EMBRYONIC_MACHINE.get(), 1)
                .pattern("AAA")
                .pattern("BCB")
                .pattern("ADA")
                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.TEST_TUBE.get())
                .define('C', Items.IRON_NUGGET)
                .define('D', Items.REDSTONE)
                .unlockedBy("has_embryonic_machine_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of( Items.IRON_INGOT, ModItems.TEST_TUBE.get(), Items.IRON_NUGGET, Items.REDSTONE).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.EMBRYO_CALCIFICATION_MACHINE.get(), 1)
                .pattern("AB ")
                .pattern("CDE")
                .pattern("FAF")
                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.SYRINGE.get())
                .define('C', ModItems.SCREEN.get())
                .define('D', ModItems.CABLE.get())
                .define('E', ModItems.PROCESSOR.get())
                .define('F', Items.FLINT)
                .unlockedBy("has_embryonic_machine_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of( Items.IRON_INGOT, ModItems.SYRINGE.get(), ModItems.SCREEN.get(), ModItems.CABLE.get(),ModItems.PROCESSOR.get(), Items.IRON_NUGGET).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.INCUBATOR.get(), 1)
                .pattern("AAA")
                .pattern("BCB")
                .pattern("DED")
                .define('A', Blocks.GLASS)
                .define('B', Items.COPPER_INGOT)
                .define('C', Blocks.HAY_BLOCK)
                .define('D', Items.IRON_INGOT)
                .define('E', ModItems.CABLE.get())
                .unlockedBy("has_incubator_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of( Blocks.GLASS, Items.COPPER_INGOT, Blocks.HAY_BLOCK, Items.IRON_INGOT, ModItems.CABLE.get()).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WRENCH.get(), 1)
                .pattern(" A ")
                .pattern(" BA")
                .pattern("B  ")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.IRON_NUGGET)
                .unlockedBy("has_wrench_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.IRON_INGOT, Items.IRON_NUGGET).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ITEM_PIPE.get(), 8)
                .pattern("AAA")
                .define('A', Items.IRON_INGOT)
                .unlockedBy("has_item_pipe_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.CABLE.get()).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FLUID_PIPE.get(), 8)
                .pattern(" A ")
                .pattern("BBB")
                .pattern(" A ")
                .define('A', Items.WATER_BUCKET)
                .define('B', ModItems.CABLE.get())
                .unlockedBy("has_fluid_pipe_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.WATER_BUCKET, ModItems.CABLE.get()).build())).save(pWriter);

        ConditionalRecipe.builder()
                .addCondition(requirePowerCondition)
                .addRecipe(
                        (consumer) ->ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.POWER_PIPE.get(), 8)
                .pattern(" A ")
                .pattern("BBB")
                .pattern(" A ")
                .define('A', Items.REDSTONE)
                .define('B', ModItems.CABLE.get())
                .unlockedBy("has_power_pipe_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.REDSTONE, ModItems.CABLE.get()).build())).save(consumer))
                .generateAdvancement()
                .build(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "power_pipe_with_power_req"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WHITE_GENERATOR.get(), 1)
                .requires(ModBlocks.GENERATOR.get()).unlockedBy("has_generator", has(ModBlocks.GENERATOR.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.GENERATOR.get(), 1)
                .requires(ModBlocks.WHITE_GENERATOR.get()).unlockedBy("has_white_generator", has(ModBlocks.WHITE_GENERATOR.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "generator_from_white_generator"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WHITE_DNA_EXTRACTOR.get(), 1)
                .requires(ModBlocks.DNA_EXTRACTOR.get()).unlockedBy("has_dna_extractor", has(ModBlocks.DNA_EXTRACTOR.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.DNA_EXTRACTOR.get(), 1)
                .requires(ModBlocks.WHITE_DNA_EXTRACTOR.get()).unlockedBy("has_white_dna_extractor", has(ModBlocks.WHITE_DNA_EXTRACTOR.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "dna_extractor_from_white_dna_extractor"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WHITE_FOSSIL_GRINDER.get(), 1)
                .requires(ModBlocks.FOSSIL_GRINDER.get()).unlockedBy("has_fossil_grinder", has(ModBlocks.FOSSIL_GRINDER.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.FOSSIL_GRINDER.get(), 1)
                .requires(ModBlocks.WHITE_FOSSIL_GRINDER.get()).unlockedBy("has_white_fossil_grinder", has(ModBlocks.WHITE_FOSSIL_GRINDER.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "fossil_grinder_from_white_fossil_grinder"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WHITE_FOSSIL_CLEANER.get(), 1)
                .requires(ModBlocks.FOSSIL_CLEANER.get()).unlockedBy("has_fossil_cleaner", has(ModBlocks.FOSSIL_CLEANER.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.FOSSIL_CLEANER.get(), 1)
                .requires(ModBlocks.WHITE_FOSSIL_CLEANER.get()).unlockedBy("has_white_fossil_cleaner", has(ModBlocks.WHITE_FOSSIL_CLEANER.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "fossil_cleaner_from_white_fossil_cleaner"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WHITE_DNA_HYBRIDIZER.get(), 1)
                .requires(ModBlocks.DNA_HYBRIDIZER.get()).unlockedBy("has_dna_hybridizer", has(ModBlocks.DNA_HYBRIDIZER.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.DNA_HYBRIDIZER.get(), 1)
                .requires(ModBlocks.WHITE_DNA_HYBRIDIZER.get()).unlockedBy("has_white_dna_hybridizer", has(ModBlocks.WHITE_FOSSIL_CLEANER.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "dna_hybridizer_from_white_dna_hybridizer"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WHITE_EMBRYONIC_MACHINE.get(), 1)
                .requires(ModBlocks.EMBRYONIC_MACHINE.get()).unlockedBy("has_embryonic_machine", has(ModBlocks.EMBRYONIC_MACHINE.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.EMBRYONIC_MACHINE.get(), 1)
                .requires(ModBlocks.WHITE_EMBRYONIC_MACHINE.get()).unlockedBy("has_white_embryonic_machine", has(ModBlocks.WHITE_EMBRYONIC_MACHINE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "embryonic_machine_from_white_embryonic_machine"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WHITE_EMBRYO_CALCIFICATION_MACHINE.get(), 1)
                .requires(ModBlocks.EMBRYO_CALCIFICATION_MACHINE.get()).unlockedBy("has_embryo_calcification_machine", has(ModBlocks.EMBRYO_CALCIFICATION_MACHINE.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.EMBRYO_CALCIFICATION_MACHINE.get(), 1)
                .requires(ModBlocks.WHITE_EMBRYO_CALCIFICATION_MACHINE.get()).unlockedBy("has_white_embryo_calcification_machine", has(ModBlocks.WHITE_EMBRYO_CALCIFICATION_MACHINE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "embryo_calcification_machine_from_white_embryo_calcification_machine"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WHITE_INCUBATOR.get(), 1)
                .requires(ModBlocks.INCUBATOR.get()).unlockedBy("has_incubator", has(ModBlocks.INCUBATOR.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.INCUBATOR.get(), 1)
                .requires(ModBlocks.WHITE_INCUBATOR.get()).unlockedBy("has_white_incubator", has(ModBlocks.WHITE_INCUBATOR.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "incubator_from_white_incubator"));

        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.ALBERTOSAURUS_TISSUE.get(), ModItems.ALBERTOSAURUS_DNA.get(), 1)
                .unlockedBy("has_TEST_TUBE", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.APATOSAURUS_TISSUE.get(), ModItems.APATOSAURUS_DNA.get(), 1)
                .unlockedBy("has_TEST_TUBE", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.BRACHIOSAURUS_TISSUE.get(), ModItems.BRACHIOSAURUS_DNA.get(), 1)
                .unlockedBy("has_TEST_TUBE", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.CERATOSAURUS_TISSUE.get(), ModItems.CERATOSAURUS_DNA.get(), 1)
                .unlockedBy("has_TEST_TUBE", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.COMPSOGNATHUS_TISSUE.get(), ModItems.COMPSOGNATHUS_DNA.get(), 1)
                .unlockedBy("has_TEST_TUBE", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.DILOPHOSAURUS_TISSUE.get(), ModItems.DILOPHOSAURUS_DNA.get(), 1)
                .unlockedBy("has_TEST_TUBE", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.DIPLODOCUS_TISSUE.get(), ModItems.DIPLODOCUS_DNA.get(), 1)
                .unlockedBy("has_TEST_TUBE", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.GALLIMIMUS_TISSUE.get(), ModItems.GALLIMIMUS_DNA.get(), 1)
                .unlockedBy("has_TEST_TUBE", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.INDOMINUS_REX_TISSUE.get(), ModItems.INDOMINUS_REX_DNA.get(), 1)
                .unlockedBy("has_TEST_TUBE", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.OURANOSAURUS_TISSUE.get(), ModItems.OURANOSAURUS_DNA.get(), 1)
                .unlockedBy("has_TEST_TUBE", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.PARASAUROLOPHUS_TISSUE.get(), ModItems.PARASAUROLOPHUS_DNA.get(), 1)
                .unlockedBy("has_TEST_TUBE", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.SPINOSAURUS_TISSUE.get(), ModItems.SPINOSAURUS_DNA.get(), 1)
                .unlockedBy("has_TEST_TUBE", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.TRICERATOPS_TISSUE.get(), ModItems.TRICERATOPS_DNA.get(), 1)
                .unlockedBy("has_TEST_TUBE", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.TYRANNOSAURUS_REX_TISSUE.get(), ModItems.TYRANNOSAURUS_REX_DNA.get(), 1)
                .unlockedBy("has_TEST_TUBE", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.VELOCIRAPTOR_TISSUE.get(), ModItems.VELOCIRAPTOR_DNA.get(), 1)
                .unlockedBy("has_TEST_TUBE", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.BARYONYX_TISSUE.get(), ModItems.BARYONYX_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.CARNOTAURUS_TISSUE.get(), ModItems.CARNOTAURUS_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.CONCAVENATOR_TISSUE.get(), ModItems.CONCAVENATOR_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.DEINONYCHUS_TISSUE.get(), ModItems.DEINONYCHUS_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.EDMONTOSAURUS_TISSUE.get(), ModItems.EDMONTOSAURUS_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.GIGANOTOSAURUS_TISSUE.get(), ModItems.GIGANOTOSAURUS_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.GUANLONG_TISSUE.get(), ModItems.GUANLONG_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.HERRERASAURUS_TISSUE.get(), ModItems.HERRERASAURUS_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.MAJUNGASAURUS_TISSUE.get(), ModItems.MAJUNGASAURUS_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.PROCOMPSOGNATHUS_TISSUE.get(), ModItems.PROCOMPSOGNATHUS_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.PROTOCERATOPS_TISSUE.get(), ModItems.PROTOCERATOPS_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.RUGOPS_TISSUE.get(), ModItems.RUGOPS_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.SHANTUNGOSAURUS_TISSUE.get(), ModItems.SHANTUNGOSAURUS_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.STEGOSAURUS_TISSUE.get(), ModItems.STEGOSAURUS_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.STYRACOSAURUS_TISSUE.get(), ModItems.STYRACOSAURUS_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.THERIZINOSAURUS_TISSUE.get(), ModItems.THERIZINOSAURUS_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.DISTORTUS_REX_TISSUE.get(), ModItems.DISTORTUS_REX_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);

        new DNAAnalyzingRecipeBuilder(ModItems.TEST_TUBE.get(), ModItems.FROG_MATERIAL.get(), ModItems.FROG_DNA.get(), 1)
                .unlockedBy("has_test_tube", has(ModItems.TEST_TUBE.get())).save(pWriter);

        DNAExtractingRecipeBuilder
                .amberRandomDNAUniform(
                        ModItems.TEST_TUBE.get(),
                        ModItems.MOSQUITO_IN_AMBER.get(),
                        ModItems.BRACHIOSAURUS_DNA.get(), 1).addDNAWeight(ModItems.INDOMINUS_REX_DNA.get(), 0)
                .addDNAWeight(ModItems.DISTORTUS_REX_DNA.get(), 0)
                .unlockedBy("has_TEST_TUBE", has(ModItems.MOSQUITO_IN_AMBER.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "random_dna"));

        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.ALBERTOSAURUS_SKULL_FOSSIL.get(), ModItems.ALBERTOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.APATOSAURUS_SKULL_FOSSIL.get(), ModItems.APATOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.BRACHIOSAURUS_SKULL_FOSSIL.get(), ModItems.BRACHIOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.CERATOSAURUS_SKULL_FOSSIL.get(), ModItems.CERATOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.COMPSOGNATHUS_SKULL_FOSSIL.get(), ModItems.COMPSOGNATHUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.DILOPHOSAURUS_SKULL_FOSSIL.get(), ModItems.DILOPHOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.DIPLODOCUS_SKULL_FOSSIL.get(), ModItems.DIPLODOCUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.GALLIMIMUS_SKULL_FOSSIL.get(), ModItems.GALLIMIMUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.OURANOSAURUS_SKULL_FOSSIL.get(), ModItems.OURANOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.PARASAUROLOPHUS_SKULL_FOSSIL.get(), ModItems.PARASAUROLOPHUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.SPINOSAURUS_SKULL_FOSSIL.get(), ModItems.SPINOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.TRICERATOPS_SKULL_FOSSIL.get(), ModItems.TRICERATOPS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.TYRANNOSAURUS_REX_SKULL_FOSSIL.get(), ModItems.TYRANNOSAURUS_REX_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.VELOCIRAPTOR_SKULL_FOSSIL.get(), ModItems.VELOCIRAPTOR_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.BARYONYX_SKULL_FOSSIL.get(), ModItems.BARYONYX_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.CARNOTAURUS_SKULL_FOSSIL.get(), ModItems.CARNOTAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.CONCAVENATOR_SKULL_FOSSIL.get(), ModItems.CONCAVENATOR_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.DEINONYCHUS_SKULL_FOSSIL.get(), ModItems.DEINONYCHUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.EDMONTOSAURUS_SKULL_FOSSIL.get(), ModItems.EDMONTOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.GIGANOTOSAURUS_SKULL_FOSSIL.get(), ModItems.GIGANOTOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.GUANLONG_SKULL_FOSSIL.get(), ModItems.GUANLONG_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.HERRERASAURUS_SKULL_FOSSIL.get(), ModItems.HERRERASAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.MAJUNGASAURUS_SKULL_FOSSIL.get(), ModItems.MAJUNGASAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.PROCOMPSOGNATHUS_SKULL_FOSSIL.get(), ModItems.PROCOMPSOGNATHUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.PROTOCERATOPS_SKULL_FOSSIL.get(), ModItems.PROTOCERATOPS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.RUGOPS_SKULL_FOSSIL.get(), ModItems.RUGOPS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.SHANTUNGOSAURUS_SKULL_FOSSIL.get(), ModItems.SHANTUNGOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.STEGOSAURUS_SKULL_FOSSIL.get(), ModItems.STEGOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.STYRACOSAURUS_SKULL_FOSSIL.get(), ModItems.STYRACOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.THERIZINOSAURUS_SKULL_FOSSIL.get(), ModItems.THERIZINOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);

        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_ALBERTOSAURUS_SKULL.get(), ModItems.ALBERTOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_APATOSAURUS_SKULL.get(), ModItems.APATOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_BRACHIOSAURUS_SKULL.get(), ModItems.BRACHIOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_CERATOSAURUS_SKULL.get(), ModItems.CERATOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_COMPSOGNATHUS_SKULL.get(), ModItems.COMPSOGNATHUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_DILOPHOSAURUS_SKULL.get(), ModItems.DILOPHOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_DIPLODOCUS_SKULL.get(), ModItems.DIPLODOCUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_GALLIMIMUS_SKULL.get(), ModItems.GALLIMIMUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_INDOMINUS_REX_SKULL.get(), ModItems.INDOMINUS_REX_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_OURANOSAURUS_SKULL.get(), ModItems.OURANOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_PARASAUROLOPHUS_SKULL.get(), ModItems.PARASAUROLOPHUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_SPINOSAURUS_SKULL.get(), ModItems.SPINOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_TRICERATOPS_SKULL.get(), ModItems.TRICERATOPS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_TYRANNOSAURUS_REX_SKULL.get(), ModItems.TYRANNOSAURUS_REX_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_VELOCIRAPTOR_SKULL.get(), ModItems.VELOCIRAPTOR_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_BARYONYX_SKULL.get(), ModItems.BARYONYX_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_CARNOTAURUS_SKULL.get(), ModItems.CARNOTAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_CONCAVENATOR_SKULL.get(), ModItems.CONCAVENATOR_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_DEINONYCHUS_SKULL.get(), ModItems.DEINONYCHUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_EDMONTOSAURUS_SKULL.get(), ModItems.EDMONTOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_GIGANOTOSAURUS_SKULL.get(), ModItems.GIGANOTOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_GUANLONG_SKULL.get(), ModItems.GUANLONG_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_HERRERASAURUS_SKULL.get(), ModItems.HERRERASAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_MAJUNGASAURUS_SKULL.get(), ModItems.MAJUNGASAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_PROCOMPSOGNATHUS_SKULL.get(), ModItems.PROCOMPSOGNATHUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_PROTOCERATOPS_SKULL.get(), ModItems.PROTOCERATOPS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_RUGOPS_SKULL.get(), ModItems.RUGOPS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_SHANTUNGOSAURUS_SKULL.get(), ModItems.SHANTUNGOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_STEGOSAURUS_SKULL.get(), ModItems.STEGOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_STYRACOSAURUS_SKULL.get(), ModItems.STYRACOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_THERIZINOSAURUS_SKULL.get(), ModItems.THERIZINOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_DISTORTUS_REX_SKULL.get(), ModItems.DISTORTUS_REX_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);


        FossilCleaningRecipeBuilder.randomFossil(ModBlocks.STONE_FOSSIL.get(), ModItems.VELOCIRAPTOR_SKULL_FOSSIL.get(), 1)
                .addFossilWeight(ModItems.SPINOSAURUS_SKULL_FOSSIL.get(), 0)
                .unlockedBy("has_stone_fossil_block", has(ModBlocks.STONE_FOSSIL.get())).save(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "random_fossil_from_stone_fossil_from_fossil_cleaning"));
        FossilCleaningRecipeBuilder.randomFossil(ModBlocks.DEEPSLATE_FOSSIL.get(), ModItems.VELOCIRAPTOR_SKULL_FOSSIL.get(), 1)
                .unlockedBy("has_deepslate_fossil_block", has(ModBlocks.DEEPSLATE_FOSSIL.get())).save(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "random_fossil_from_deepslate_fossil_from_fossil_cleaning"));

        new DNAHybridizingRecipeBuilder(ModItems.INDOMINUS_REX_DNA.get(), 1)
                .addIngredient(ModItems.TYRANNOSAURUS_REX_DNA.get())
                .addIngredient(ModItems.VELOCIRAPTOR_DNA.get())
                .addIngredient(ModItems.CARNOTAURUS_DNA.get())
                .addIngredient(ModItems.THERIZINOSAURUS_DNA.get())
                .addIngredient(ModItems.MAJUNGASAURUS_DNA.get())
                .addIngredient(ModItems.RUGOPS_DNA.get())
                .addIngredient(ModItems.GIGANOTOSAURUS_DNA.get())
                .unlockedBy("has_dna", has(ModTags.Items.DNA)).save(pWriter);

        new DNAHybridizingRecipeBuilder(ModItems.DISTORTUS_REX_DNA.get(), 1)
                .addIngredient(ModItems.TYRANNOSAURUS_REX_DNA.get())
                .addIngredient(ModItems.BRACHIOSAURUS_DNA.get())
                .addIngredient(ModItems.VELOCIRAPTOR_DNA.get())
                .unlockedBy("has_dna", has(ModTags.Items.DNA)).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.ALBERTOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.ALBERTOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.APATOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.APATOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.BRACHIOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.BRACHIOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.CERATOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.CERATOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.COMPSOGNATHUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.COMPSOGNATHUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.DILOPHOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.DILOPHOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.DIPLODOCUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.DIPLODOCUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.GALLIMIMUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.GALLIMIMUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.INDOMINUS_REX_DNA.get(), ModItems.FROG_DNA.get(), ModItems.INDOMINUS_REX_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.OURANOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.OURANOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.PARASAUROLOPHUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.PARASAUROLOPHUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.SPINOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.SPINOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.TRICERATOPS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.TRICERATOPS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.TYRANNOSAURUS_REX_DNA.get(), ModItems.FROG_DNA.get(), ModItems.TYRANNOSAURUS_REX_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.VELOCIRAPTOR_DNA.get(), ModItems.FROG_DNA.get(), ModItems.VELOCIRAPTOR_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.BARYONYX_DNA.get(), ModItems.FROG_DNA.get(), ModItems.BARYONYX_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.CARNOTAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.CARNOTAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.CONCAVENATOR_DNA.get(), ModItems.FROG_DNA.get(), ModItems.CONCAVENATOR_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.DEINONYCHUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.DEINONYCHUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.EDMONTOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.EDMONTOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.GIGANOTOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.GIGANOTOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.GUANLONG_DNA.get(), ModItems.FROG_DNA.get(), ModItems.GUANLONG_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.HERRERASAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.HERRERASAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.MAJUNGASAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.MAJUNGASAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.PROCOMPSOGNATHUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.PROCOMPSOGNATHUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.PROTOCERATOPS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.PROTOCERATOPS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.RUGOPS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.RUGOPS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.SHANTUNGOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.SHANTUNGOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.STEGOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.STEGOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.STYRACOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.STYRACOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.THERIZINOSAURUS_DNA.get(), ModItems.FROG_DNA.get(), ModItems.THERIZINOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.DISTORTUS_REX_DNA.get(), ModItems.FROG_DNA.get(), ModItems.DISTORTUS_REX_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);

        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.ALBERTOSAURUS_SYRINGE.get(), Items.EGG, ModBlocks.ALBERTOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.APATOSAURUS_SYRINGE.get(), Items.EGG, ModBlocks.APATOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.BRACHIOSAURUS_SYRINGE.get(), Items.EGG, ModBlocks.BRACHIOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.CERATOSAURUS_SYRINGE.get(), Items.EGG, ModBlocks.CERATOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.COMPSOGNATHUS_SYRINGE.get(), Items.EGG, ModBlocks.COMPSOGNATHUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.DILOPHOSAURUS_SYRINGE.get(), Items.EGG, ModBlocks.DILOPHOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.DIPLODOCUS_SYRINGE.get(), Items.EGG, ModBlocks.DIPLODOCUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.GALLIMIMUS_SYRINGE.get(), Items.EGG, ModBlocks.GALLIMIMUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.INDOMINUS_REX_SYRINGE.get(), Items.EGG, ModBlocks.INDOMINUS_REX_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.OURANOSAURUS_SYRINGE.get(), Items.EGG, ModBlocks.OURANOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.PARASAUROLOPHUS_SYRINGE.get(), Items.EGG, ModBlocks.PARASAUROLOPHUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.SPINOSAURUS_SYRINGE.get(), Items.EGG, ModBlocks.SPINOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.TRICERATOPS_SYRINGE.get(), Items.EGG, ModBlocks.TRICERATOPS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.TYRANNOSAURUS_REX_SYRINGE.get(), Items.EGG, ModBlocks.TYRANNOSAURUS_REX_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.VELOCIRAPTOR_SYRINGE.get(), Items.EGG, ModBlocks.VELOCIRAPTOR_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.BARYONYX_SYRINGE.get(), Items.EGG, ModBlocks.BARYONYX_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.CARNOTAURUS_SYRINGE.get(), Items.EGG, ModBlocks.CARNOTAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.CONCAVENATOR_SYRINGE.get(), Items.EGG, ModBlocks.CONCAVENATOR_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.DEINONYCHUS_SYRINGE.get(), Items.EGG, ModBlocks.DEINONYCHUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.EDMONTOSAURUS_SYRINGE.get(), Items.EGG, ModBlocks.EDMONTOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.GIGANOTOSAURUS_SYRINGE.get(), Items.EGG, ModBlocks.GIGANOTOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.GUANLONG_SYRINGE.get(), Items.EGG, ModBlocks.GUANLONG_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.HERRERASAURUS_SYRINGE.get(), Items.EGG, ModBlocks.HERRERASAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.MAJUNGASAURUS_SYRINGE.get(), Items.EGG, ModBlocks.MAJUNGASAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.PROCOMPSOGNATHUS_SYRINGE.get(), Items.EGG, ModBlocks.PROCOMPSOGNATHUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.PROTOCERATOPS_SYRINGE.get(), Items.EGG, ModBlocks.PROTOCERATOPS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.RUGOPS_SYRINGE.get(), Items.EGG, ModBlocks.RUGOPS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.SHANTUNGOSAURUS_SYRINGE.get(), Items.EGG, ModBlocks.SHANTUNGOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.STEGOSAURUS_SYRINGE.get(), Items.EGG, ModBlocks.STEGOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.STYRACOSAURUS_SYRINGE.get(), Items.EGG, ModBlocks.STYRACOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.THERIZINOSAURUS_SYRINGE.get(), Items.EGG, ModBlocks.THERIZINOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.DISTORTUS_REX_SYRINGE.get(), Items.EGG, ModBlocks.DISTORTUS_REX_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);


        new IncubatorRecipeBuilder(ModBlocks.APATOSAURUS_EGG.get(), ModBlocks.INCUBATED_APATOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.ALBERTOSAURUS_EGG.get(), ModBlocks.INCUBATED_ALBERTOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.BRACHIOSAURUS_EGG.get(), ModBlocks.INCUBATED_BRACHIOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.CERATOSAURUS_EGG.get(), ModBlocks.INCUBATED_CERATOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.COMPSOGNATHUS_EGG.get(), ModBlocks.INCUBATED_COMPSOGNATHUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.DILOPHOSAURUS_EGG.get(), ModBlocks.INCUBATED_DILOPHOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.DIPLODOCUS_EGG.get(), ModBlocks.INCUBATED_DIPLODOCUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.PARASAUROLOPHUS_EGG.get(), ModBlocks.INCUBATED_PARASAUROLOPHUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.SPINOSAURUS_EGG.get(), ModBlocks.INCUBATED_SPINOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.OURANOSAURUS_EGG.get(), ModBlocks.INCUBATED_OURANOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.GALLIMIMUS_EGG.get(), ModBlocks.INCUBATED_GALLIMIMUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.INDOMINUS_REX_EGG.get(), ModBlocks.INCUBATED_INDOMINUS_REX_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.VELOCIRAPTOR_EGG.get(), ModBlocks.INCUBATED_VELOCIRAPTOR_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.TYRANNOSAURUS_REX_EGG.get(), ModBlocks.INCUBATED_TYRANNOSAURUS_REX_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.TRICERATOPS_EGG.get(), ModBlocks.INCUBATED_TRICERATOPS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.BARYONYX_EGG.get(), ModBlocks.INCUBATED_BARYONYX_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.CARNOTAURUS_EGG.get(), ModBlocks.INCUBATED_CARNOTAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.CONCAVENATOR_EGG.get(), ModBlocks.INCUBATED_CONCAVENATOR_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.DEINONYCHUS_EGG.get(), ModBlocks.INCUBATED_DEINONYCHUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.EDMONTOSAURUS_EGG.get(), ModBlocks.INCUBATED_EDMONTOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.GIGANOTOSAURUS_EGG.get(), ModBlocks.INCUBATED_GIGANOTOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.GUANLONG_EGG.get(), ModBlocks.INCUBATED_GUANLONG_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.HERRERASAURUS_EGG.get(), ModBlocks.INCUBATED_HERRERASAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.MAJUNGASAURUS_EGG.get(), ModBlocks.INCUBATED_MAJUNGASAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.PROCOMPSOGNATHUS_EGG.get(), ModBlocks.INCUBATED_PROCOMPSOGNATHUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.PROTOCERATOPS_EGG.get(), ModBlocks.INCUBATED_PROTOCERATOPS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.RUGOPS_EGG.get(), ModBlocks.INCUBATED_RUGOPS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.SHANTUNGOSAURUS_EGG.get(), ModBlocks.INCUBATED_SHANTUNGOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.STEGOSAURUS_EGG.get(), ModBlocks.INCUBATED_STEGOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.STYRACOSAURUS_EGG.get(), ModBlocks.INCUBATED_STYRACOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.THERIZINOSAURUS_EGG.get(), ModBlocks.INCUBATED_THERIZINOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModBlocks.DISTORTUS_REX_EGG.get(), ModBlocks.INCUBATED_DISTORTUS_REX_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer,
                                     List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime,
                    pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, JRMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
    // Fossil Grinder datagen helpers:
    // 1) Fossil input with weighted outputs: 30% bone meal, 30% flint, 30% crushed fossil, 10% tissue
    protected void fossilGrindingFossilWeighted(Consumer<FinishedRecipe> out, ItemLike fossilInput, ItemLike tissueOutput, String recipeName) {
        out.accept(new FinishedRecipe() {
            @Override
            public void serializeRecipeData(JsonObject json) {
                // type
                json.addProperty("type", "jurassicrevived:fossil_grinding");

                // ingredients (single input)
                JsonArray ingredients = new JsonArray();
                ingredients.add(Ingredient.of(fossilInput).toJson());
                json.add("ingredients", ingredients);

                // result (fallback result, not used when weighted_outputs is present)
                JsonObject result = new JsonObject();
                result.addProperty("item", net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(tissueOutput.asItem()).toString());
                result.addProperty("count", 1);
                json.add("result", result);

                // weighted outputs
                JsonObject weights = new JsonObject();
                weights.addProperty(net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(Items.BONE_MEAL).toString(), 30);
                weights.addProperty(net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(Items.FLINT).toString(), 30);
                weights.addProperty(net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(net.cmr.jurassicrevived.item.ModItems.CRUSHED_FOSSIL.get()).toString(), 30);
                weights.addProperty(net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(tissueOutput.asItem()).toString(), 10);
                json.add("weighted_outputs", weights);
            }

            @Override
            public ResourceLocation getId() {
                return ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "fossil_grinding/" + recipeName);
            }

            @Override
            public RecipeSerializer<?> getType() {
                return net.cmr.jurassicrevived.recipe.FossilGrinderRecipe.Serializer.INSTANCE;
            }

            @Override
            public JsonObject serializeAdvancement() { return null; }

            @Override
            public ResourceLocation getAdvancementId() { return null; }
        });
    }

    // 2) Skull item -> tissue directly
    protected void fossilGrindingSkullDirect(Consumer<FinishedRecipe> out, ItemLike skullInput, ItemLike tissueOutput, String recipeName) {
        out.accept(new FinishedRecipe() {
            @Override
            public void serializeRecipeData(JsonObject json) {
                json.addProperty("type", "jurassicrevived:fossil_grinding");

                JsonArray ingredients = new JsonArray();
                ingredients.add(Ingredient.of(skullInput).toJson());
                json.add("ingredients", ingredients);

                JsonObject result = new JsonObject();
                result.addProperty("item", net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(tissueOutput.asItem()).toString());
                result.addProperty("count", 1);
                json.add("result", result);
                // No weighted_outputs -> deterministic result
            }

            @Override
            public ResourceLocation getId() {
                return ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "fossil_grinding/" + recipeName);
            }

            @Override
            public RecipeSerializer<?> getType() {
                return net.cmr.jurassicrevived.recipe.FossilGrinderRecipe.Serializer.INSTANCE;
            }

            @Override
            public JsonObject serializeAdvancement() { return null; }

            @Override
            public ResourceLocation getAdvancementId() { return null; }
        });
    }
}
