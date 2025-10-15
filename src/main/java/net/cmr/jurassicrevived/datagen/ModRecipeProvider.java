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
    private static final List<ItemLike> GYPSUM_SMELTABLES = List.of(ModBlocks.GYPSUM_COBBLESTONE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

        ICondition requirePowerCondition = new ConfigCondition();

        oreSmelting(pWriter, GYPSUM_SMELTABLES, RecipeCategory.MISC, ModBlocks.GYPSUM_STONE.get(), 0.25f, 200, "gypsum_stone");
        oreBlasting(pWriter, GYPSUM_SMELTABLES, RecipeCategory.MISC, ModBlocks.GYPSUM_STONE.get(), 0.25f, 100, "gypsum_stone");
        
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

        DNAExtractingRecipeBuilder
                .amberRandomDNAUniform(
                        ModItems.TEST_TUBE.get(),
                        ModItems.MOSQUITO_IN_AMBER.get(),
                        ModItems.BRACHIOSAURUS_DNA.get(), 1).addDNAWeight(ModItems.INDOMINUS_REX_DNA.get(), 0)
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


        FossilCleaningRecipeBuilder.randomFossil(ModBlocks.STONE_FOSSIL.get(), ModItems.VELOCIRAPTOR_SKULL_FOSSIL.get(), 1)
                .addFossilWeight(ModItems.SPINOSAURUS_SKULL_FOSSIL.get(), 0)
                .unlockedBy("has_stone_fossil_block", has(ModBlocks.STONE_FOSSIL.get())).save(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "random_fossil_from_stone_fossil_from_fossil_cleaning"));
        FossilCleaningRecipeBuilder.randomFossil(ModBlocks.DEEPSLATE_FOSSIL.get(), ModItems.VELOCIRAPTOR_SKULL_FOSSIL.get(), 1)
                .unlockedBy("has_deepslate_fossil_block", has(ModBlocks.DEEPSLATE_FOSSIL.get())).save(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "random_fossil_from_deepslate_fossil_from_fossil_cleaning"));

        new DNAHybridizingRecipeBuilder(
                ModItems.TYRANNOSAURUS_REX_DNA.get(),
                ModItems.VELOCIRAPTOR_DNA.get(),
                ModItems.CERATOSAURUS_DNA.get(),
                ModItems.INDOMINUS_REX_DNA.get(), 1)
                .unlockedBy("has_dna", has(ModTags.Items.DNA))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "indominus_rex_dna_from_hybridizing"));

        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.ALBERTOSAURUS_DNA.get(), ModItems.ALBERTOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.APATOSAURUS_DNA.get(), ModItems.APATOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.BRACHIOSAURUS_DNA.get(), ModItems.BRACHIOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.CERATOSAURUS_DNA.get(), ModItems.CERATOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.COMPSOGNATHUS_DNA.get(), ModItems.COMPSOGNATHUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.DILOPHOSAURUS_DNA.get(), ModItems.DILOPHOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.DIPLODOCUS_DNA.get(), ModItems.DIPLODOCUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.GALLIMIMUS_DNA.get(), ModItems.GALLIMIMUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.INDOMINUS_REX_DNA.get(), ModItems.INDOMINUS_REX_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.OURANOSAURUS_DNA.get(), ModItems.OURANOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.PARASAUROLOPHUS_DNA.get(), ModItems.PARASAUROLOPHUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.SPINOSAURUS_DNA.get(), ModItems.SPINOSAURUS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.TRICERATOPS_DNA.get(), ModItems.TRICERATOPS_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.TYRANNOSAURUS_REX_DNA.get(), ModItems.TYRANNOSAURUS_REX_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);
        new EmbryonicMachiningRecipeBuilder(ModItems.SYRINGE.get(), ModItems.VELOCIRAPTOR_DNA.get(), ModItems.VELOCIRAPTOR_SYRINGE.get(), 1)
                .unlockedBy("has_syringe", has(ModItems.SYRINGE.get())).save(pWriter);

        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.ALBERTOSAURUS_SYRINGE.get(), Items.EGG, ModItems.ALBERTOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.APATOSAURUS_SYRINGE.get(), Items.EGG, ModItems.APATOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.BRACHIOSAURUS_SYRINGE.get(), Items.EGG, ModItems.BRACHIOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.CERATOSAURUS_SYRINGE.get(), Items.EGG, ModItems.CERATOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.COMPSOGNATHUS_SYRINGE.get(), Items.EGG, ModItems.COMPSOGNATHUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.DILOPHOSAURUS_SYRINGE.get(), Items.EGG, ModItems.DILOPHOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.DIPLODOCUS_SYRINGE.get(), Items.EGG, ModItems.DIPLODOCUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.GALLIMIMUS_SYRINGE.get(), Items.EGG, ModItems.GALLIMIMUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.INDOMINUS_REX_SYRINGE.get(), Items.EGG, ModItems.INDOMINUS_REX_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.OURANOSAURUS_SYRINGE.get(), Items.EGG, ModItems.OURANOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.PARASAUROLOPHUS_SYRINGE.get(), Items.EGG, ModItems.PARASAUROLOPHUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.SPINOSAURUS_SYRINGE.get(), Items.EGG, ModItems.SPINOSAURUS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.TRICERATOPS_SYRINGE.get(), Items.EGG, ModItems.TRICERATOPS_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.TYRANNOSAURUS_REX_SYRINGE.get(), Items.EGG, ModItems.TYRANNOSAURUS_REX_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);
        new EmbryoCalcificationMachiningRecipeBuilder(ModItems.VELOCIRAPTOR_SYRINGE.get(), Items.EGG, ModItems.VELOCIRAPTOR_EGG.get(), 1)
                .unlockedBy("has_syringes", has(ModTags.Items.SYRINGES)).save(pWriter);


        new IncubatorRecipeBuilder(ModItems.APATOSAURUS_EGG.get(), ModBlocks.HATCHED_APATOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModItems.ALBERTOSAURUS_EGG.get(), ModBlocks.HATCHED_ALBERTOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModItems.BRACHIOSAURUS_EGG.get(), ModBlocks.HATCHED_BRACHIOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModItems.CERATOSAURUS_EGG.get(), ModBlocks.HATCHED_CERATOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModItems.COMPSOGNATHUS_EGG.get(), ModBlocks.HATCHED_COMPSOGNATHUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModItems.DILOPHOSAURUS_EGG.get(), ModBlocks.HATCHED_DILOPHOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModItems.DIPLODOCUS_EGG.get(), ModBlocks.HATCHED_DIPLODOCUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModItems.PARASAUROLOPHUS_EGG.get(), ModBlocks.HATCHED_PARASAUROLOPHUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModItems.SPINOSAURUS_EGG.get(), ModBlocks.HATCHED_SPINOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModItems.OURANOSAURUS_EGG.get(), ModBlocks.HATCHED_OURANOSAURUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModItems.GALLIMIMUS_EGG.get(), ModBlocks.HATCHED_GALLIMIMUS_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModItems.INDOMINUS_REX_EGG.get(), ModBlocks.HATCHED_INDOMINUS_REX_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModItems.VELOCIRAPTOR_EGG.get(), ModBlocks.HATCHED_VELOCIRAPTOR_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModItems.TYRANNOSAURUS_REX_EGG.get(), ModBlocks.HATCHED_TYRANNOSAURUS_REX_EGG.get(), 1)
                .unlockedBy("has_eggs", has(ModTags.Items.EGGS)).save(pWriter);
        new IncubatorRecipeBuilder(ModItems.TRICERATOPS_EGG.get(), ModBlocks.HATCHED_TRICERATOPS_EGG.get(), 1)
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
