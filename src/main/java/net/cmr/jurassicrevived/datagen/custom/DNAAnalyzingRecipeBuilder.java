package net.cmr.jurassicrevived.datagen.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.cmr.jurassicrevived.recipe.DNAAnalyzerRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Consumer;


public class DNAAnalyzingRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final Ingredient ingredient;
    private final Ingredient secondIngredient;
    private final int count;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    // Optional weights for Amber -> DNA randomization
    // Store by item id to avoid classloading issues during data gen
    private final java.util.Map<ResourceLocation, Integer> dnaWeights = new java.util.HashMap<>();

    public DNAAnalyzingRecipeBuilder(ItemLike ingredient, ItemLike secondIngredient, ItemLike result, int count) {
        this.ingredient = Ingredient.of(ingredient);
        this.secondIngredient = Ingredient.of(secondIngredient);
        this.result = result.asItem();
        this.count = count;
    }

    // Fluent helpers to set explicit weights
    public DNAAnalyzingRecipeBuilder addDNAWeight(Item item, int weight) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        if (id != null) {
            dnaWeights.put(id, weight);
        }
        return this;
    }

    public DNAAnalyzingRecipeBuilder addDNAWeight(ResourceLocation itemId, int weight) {
        dnaWeights.put(itemId, weight);
        return this;
    }

    // Convenience: add uniform weight for every item currently in the given tag
    // In 1.20.1 data gen, iterate the items directly (no holder.value() call needed here)
    public DNAAnalyzingRecipeBuilder addAllItemsFromTagUniform(TagKey<Item> tag, int weight) {
        var tags = ForgeRegistries.ITEMS.tags();
        if (tags != null) {
            var entryList = tags.getTag(tag);
            for (Item item : entryList) {
                ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
                if (id != null) {
                    dnaWeights.put(id, weight);
                }
            }
        }
        return this;
    }
    
    public static DNAAnalyzingRecipeBuilder amberRandomDNAUniform(ItemLike testtube,
                                                                  ItemLike amber,
                                                                  ItemLike resultPlaceholder,
                                                                  int uniformWeightPerDNA) {
        return new DNAAnalyzingRecipeBuilder(testtube, amber, resultPlaceholder, 1)
                .addAllItemsFromTagUniform(net.cmr.jurassicrevived.util.ModTags.Items.DNA, uniformWeightPerDNA);
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        // Ensure the recipe id ends with "_from_dna_analyzing" (avoid double-appending)
        String path = pRecipeId.getPath();
        if (!path.endsWith("_from_dna_analyzing")) {
            path = path + "_from_dna_analyzing";
        }
        ResourceLocation finalId = ResourceLocation.fromNamespaceAndPath(pRecipeId.getNamespace(), path);

        this.advancement.parent(ResourceLocation.fromNamespaceAndPath("jurassicrevived", "recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(finalId))
                .rewards(AdvancementRewards.Builder.recipe(finalId))
                .requirements(RequirementsStrategy.OR);

        // Pass a snapshot of weights into the Result to avoid referencing outer instance
        pFinishedRecipeConsumer.accept(new Result(
                finalId,
                this.result,
                this.count,
                this.ingredient,
                this.secondIngredient,
                new java.util.HashMap<>(this.dnaWeights),
                this.advancement,
                ResourceLocation.fromNamespaceAndPath(finalId.getNamespace(), "recipes/" + finalId.getPath())
        ));
    }

    // Keep this if you use it elsewhere; not required by Result (Result serializes itself)
    private void serialize(JsonObject json) {
        // ... existing code writing "type", "ingredients", "result" ...

        if (!dnaWeights.isEmpty()) {
            JsonObject weights = new JsonObject();
            for (java.util.Map.Entry<ResourceLocation, Integer> e : dnaWeights.entrySet()) {
                weights.addProperty(e.getKey().toString(), e.getValue());
            }
            json.add("dna_weights", weights);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final Ingredient ingredient;
        private final Ingredient secondIngredient;
        private final int count;
        private final java.util.Map<ResourceLocation, Integer> dnaWeights; // snapshot from builder
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId,
                      Item pResult,
                      int pCount,
                      Ingredient ingredient,
                      Ingredient secondIngredient,
                      java.util.Map<ResourceLocation, Integer> dnaWeights,
                      Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.count = pCount;
            this.ingredient = ingredient;
            this.secondIngredient = secondIngredient;
            this.dnaWeights = dnaWeights;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonArray jsonarray = new JsonArray();
            jsonarray.add(ingredient.toJson());
            jsonarray.add(secondIngredient.toJson());
            pJson.add("ingredients", jsonarray);
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            if (this.count > 1) {
                jsonobject.addProperty("count", this.count);
            }

            pJson.add("result", jsonobject);

            if (!dnaWeights.isEmpty()) {
                JsonObject weights = new JsonObject();
                for (java.util.Map.Entry<ResourceLocation, Integer> e : dnaWeights.entrySet()) {
                    // Store weight as a number
                    weights.addProperty(e.getKey().toString(), e.getValue());
                }
                pJson.add("dna_weights", weights);
            }
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return DNAAnalyzerRecipe.Serializer.INSTANCE;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}