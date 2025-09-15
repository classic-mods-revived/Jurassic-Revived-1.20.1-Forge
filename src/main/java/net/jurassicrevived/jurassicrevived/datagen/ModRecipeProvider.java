package net.jurassicrevived.jurassicrevived.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.jurassicrevived.jurassicrevived.JRMod;
import net.jurassicrevived.jurassicrevived.block.ModBlocks;
import net.jurassicrevived.jurassicrevived.datagen.custom.DNAExtractingRecipeBuilder;
import net.jurassicrevived.jurassicrevived.datagen.custom.FossilGrindingRecipeBuilder;
import net.jurassicrevived.jurassicrevived.item.ModItems;
import net.jurassicrevived.jurassicrevived.util.ModTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
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

        oreSmelting(pWriter, GYPSUM_SMELTABLES, RecipeCategory.MISC, ModBlocks.GYPSUM_STONE.get(), 0.25f, 200, "gypsum_stone");
        oreBlasting(pWriter, GYPSUM_SMELTABLES, RecipeCategory.MISC, ModBlocks.GYPSUM_STONE.get(), 0.25f, 100, "gypsum_stone");
        
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.AMPOULE.get(), 3)
                .pattern("  A")
                .pattern(" B ")
                .pattern("B  ")
                .define('A', Items.IRON_INGOT)
                .define('B', Blocks.GLASS)
                .unlockedBy("has_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.IRON_INGOT, Blocks.GLASS).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SYRINGE.get(), 3)
                .pattern("  A")
                .pattern(" B ")
                .pattern("C  ")
                .define('A', Items.IRON_INGOT)
                .define('B', Blocks.GLASS)
                .define('C', Items.IRON_NUGGET)
                .unlockedBy("has_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.IRON_INGOT, Blocks.GLASS, Items.IRON_NUGGET).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CABLE.get(), 4)
                .pattern(" BA")
                .pattern("BAB")
                .pattern("AB ")
                .define('A', Items.COPPER_INGOT)
                .define('B', Items.IRON_NUGGET)
                .unlockedBy("has_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.COPPER_INGOT, Items.IRON_NUGGET).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SCREEN.get(), 2)
                .pattern("ABA")
                .pattern("ABA")
                .pattern(" C ")
                .define('A', Items.IRON_INGOT)
                .define('B', Blocks.REDSTONE_LAMP)
                .define('C', ModItems.CABLE.get())
                .unlockedBy("has_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.IRON_INGOT, Blocks.REDSTONE_LAMP, ModItems.CABLE.get()).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PROCESSOR.get())
                .pattern("ABA")
                .pattern("BCB")
                .pattern("ABA")
                .define('A', Items.GOLD_NUGGET)
                .define('B', Items.IRON_INGOT)
                .define('C', Items.REDSTONE)
                .unlockedBy("has_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.GOLD_NUGGET, Items.IRON_INGOT, Items.REDSTONE).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TIRE.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Items.INK_SAC)
                .define('B', Items.IRON_INGOT)
                .unlockedBy("has_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.INK_SAC, Items.IRON_INGOT).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CUTTING_BLADES.get(), 4)
                .pattern("A A")
                .pattern(" A ")
                .pattern("A A")
                .define('A', Items.IRON_INGOT)
                .unlockedBy("has_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.IRON_INGOT).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GYPSUM_STONE_BRICKS.get(), 4)
                .pattern("AA")
                .pattern("AA")
                .define('A', ModBlocks.GYPSUM_STONE.get())
                .unlockedBy("has_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModBlocks.GYPSUM_STONE.get()).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.REINFORCED_STONE.get(), 4)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', Blocks.STONE)
                .unlockedBy("has_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Blocks.STONE).build())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.REINFORCED_STONE_BRICKS.get(), 4)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', Blocks.STONE_BRICKS)
                .unlockedBy("has_ingredients", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Blocks.STONE_BRICKS).build())).save(pWriter);

        new DNAExtractingRecipeBuilder(ModItems.AMPOULE.get(), ModItems.VELOCIRAPTOR_TISSUE.get(), ModItems.VELOCIRAPTOR_DNA.get(), 1)
                .unlockedBy("has_ampoule", has(ModItems.AMPOULE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.AMPOULE.get(), ModItems.TYRANNOSAURUS_REX_TISSUE.get(), ModItems.TYRANNOSAURUS_REX_DNA.get(), 1)
                .unlockedBy("has_ampoule", has(ModItems.AMPOULE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.AMPOULE.get(), ModItems.TRICERATOPS_TISSUE.get(), ModItems.TRICERATOPS_DNA.get(), 1)
                .unlockedBy("has_ampoule", has(ModItems.AMPOULE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.AMPOULE.get(), ModItems.SPINOSAURUS_TISSUE.get(), ModItems.SPINOSAURUS_DNA.get(), 1)
                .unlockedBy("has_ampoule", has(ModItems.AMPOULE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.AMPOULE.get(), ModItems.PTERANODON_TISSUE.get(), ModItems.PTERANODON_DNA.get(), 1)
                .unlockedBy("has_ampoule", has(ModItems.AMPOULE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.AMPOULE.get(), ModItems.PARASAUROLOPHUS_TISSUE.get(), ModItems.PARASAUROLOPHUS_DNA.get(), 1)
                .unlockedBy("has_ampoule", has(ModItems.AMPOULE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.AMPOULE.get(), ModItems.INDOMINUS_REX_TISSUE.get(), ModItems.INDOMINUS_REX_DNA.get(), 1)
                .unlockedBy("has_ampoule", has(ModItems.AMPOULE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.AMPOULE.get(), ModItems.GALLIMIMUS_TISSUE.get(), ModItems.GALLIMIMUS_DNA.get(), 1)
                .unlockedBy("has_ampoule", has(ModItems.AMPOULE.get())).save(pWriter);
        //new DNAExtractingRecipeBuilder(ModItems.AMPOULE.get(), ModItems.DIPLODOCUS_TISSUE.get(), ModItems.DIPLODOCUS.get(), 1)
        //        .unlockedBy("has_ampoule", has(ModItems.AMPOULE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.AMPOULE.get(), ModItems.DILOPHOSAURUS_TISSUE.get(), ModItems.DILOPHOSAURUS_DNA.get(), 1)
                .unlockedBy("has_ampoule", has(ModItems.AMPOULE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.AMPOULE.get(), ModItems.COMPSOGNATHUS_TISSUE.get(), ModItems.COMPSOGNATHUS_DNA.get(), 1)
                .unlockedBy("has_ampoule", has(ModItems.AMPOULE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.AMPOULE.get(), ModItems.CERATOSAURUS_TISSUE.get(), ModItems.CERATOSAURUS_DNA.get(), 1)
                .unlockedBy("has_ampoule", has(ModItems.AMPOULE.get())).save(pWriter);
        new DNAExtractingRecipeBuilder(ModItems.AMPOULE.get(), ModItems.BRACHIOSAURUS_TISSUE.get(), ModItems.BRACHIOSAURUS_DNA.get(), 1)
                .unlockedBy("has_ampoule", has(ModItems.AMPOULE.get())).save(pWriter);

        DNAExtractingRecipeBuilder
                .amberRandomDNAUniform(
                        ModItems.AMPOULE.get(),
                        ModItems.MOSQUITO_IN_AMBER.get(),
                        ModItems.VELOCIRAPTOR_DNA.get(), 1).addDNAWeight(ModItems.INDOMINUS_REX_DNA.get(), 0)
                .unlockedBy("has_ampoule", has(ModItems.MOSQUITO_IN_AMBER.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "random_dna"));

        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.VELOCIRAPTOR_SKULL_FOSSIL.get(), ModItems.VELOCIRAPTOR_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.TYRANNOSAURUS_REX_SKULL_FOSSIL.get(), ModItems.TYRANNOSAURUS_REX_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.TRICERATOPS_SKULL_FOSSIL.get(), ModItems.TRICERATOPS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.SPINOSAURUS_SKULL_FOSSIL.get(), ModItems.SPINOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.PTERANODON_SKULL_FOSSIL.get(), ModItems.PTERANODON_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.PARASAUROLOPHUS_SKULL_FOSSIL.get(), ModItems.PARASAUROLOPHUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.GALLIMIMUS_SKULL_FOSSIL.get(), ModItems.GALLIMIMUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        //FossilGrindingRecipeBuilder.fossilWeighted(ModItems.DIPLODOCUS_SKULL_FOSSIL.get(), ModItems.DIPLODOCUS_TISSUE.get())
        //        .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.DILOPHOSAURUS_SKULL_FOSSIL.get(), ModItems.DILOPHOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.COMPSOGNATHUS_SKULL_FOSSIL.get(), ModItems.COMPSOGNATHUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.CERATOSAURUS_SKULL_FOSSIL.get(), ModItems.CERATOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);
        FossilGrindingRecipeBuilder.fossilWeighted(ModItems.BRACHIOSAURUS_SKULL_FOSSIL.get(), ModItems.BRACHIOSAURUS_TISSUE.get())
                .unlockedBy("has_fossil", has(ModTags.Items.FOSSILS)).saveFossil(pWriter);

        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_VELOCIRAPTOR_SKULL.get(), ModItems.VELOCIRAPTOR_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_TYRANNOSAURUS_REX_SKULL.get(), ModItems.TYRANNOSAURUS_REX_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_TRICERATOPS_SKULL.get(), ModItems.TRICERATOPS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_SPINOSAURUS_SKULL.get(), ModItems.SPINOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_PTERANODON_SKULL.get(), ModItems.PTERANODON_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_PARASAUROLOPHUS_SKULL.get(), ModItems.PARASAUROLOPHUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_INDOMINUS_REX_SKULL.get(), ModItems.INDOMINUS_REX_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_GALLIMIMUS_SKULL.get(), ModItems.GALLIMIMUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        //FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_DIPLODOCUS_SKULL.get(), ModItems.DIPLODOCUS_TISSUE.get())
        //        .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_DILOPHOSAURUS_SKULL.get(), ModItems.DILOPHOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_COMPSOGNATHUS_SKULL.get(), ModItems.COMPSOGNATHUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_CERATOSAURUS_SKULL.get(), ModItems.CERATOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);
        FossilGrindingRecipeBuilder.skullDirect(ModItems.FRESH_BRACHIOSAURUS_SKULL.get(), ModItems.BRACHIOSAURUS_TISSUE.get())
                .unlockedBy("has_skull", has(ModTags.Items.SKULLS)).saveSkull(pWriter);



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
                weights.addProperty(net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(net.jurassicrevived.jurassicrevived.item.ModItems.CRUSHED_FOSSIL.get()).toString(), 30);
                weights.addProperty(net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(tissueOutput.asItem()).toString(), 10);
                json.add("weighted_outputs", weights);
            }

            @Override
            public ResourceLocation getId() {
                return ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "fossil_grinding/" + recipeName);
            }

            @Override
            public RecipeSerializer<?> getType() {
                return net.jurassicrevived.jurassicrevived.recipe.FossilGrinderRecipe.Serializer.INSTANCE;
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
                return net.jurassicrevived.jurassicrevived.recipe.FossilGrinderRecipe.Serializer.INSTANCE;
            }

            @Override
            public JsonObject serializeAdvancement() { return null; }

            @Override
            public ResourceLocation getAdvancementId() { return null; }
        });
    }
}
