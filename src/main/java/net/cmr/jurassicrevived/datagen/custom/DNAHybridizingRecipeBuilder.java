package net.cmr.jurassicrevived.datagen.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.cmr.jurassicrevived.recipe.DNAHybridizerRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class DNAHybridizingRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final java.util.List<Ingredient> ingredients = new java.util.ArrayList<>(9);
    private final int count;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    // New ctor: result-first, 1..n ingredients will be added via addIngredient
    public DNAHybridizingRecipeBuilder(ItemLike result, int count) {
        this.result = result.asItem();
        this.count = count;
    }

    // Fluent method to add up to 9 ingredients
    public DNAHybridizingRecipeBuilder addIngredient(ItemLike itemLike) {
        if (this.ingredients.size() >= 9) {
            throw new IllegalStateException("DNA hybridizing supports at most 9 ingredients");
        }
        this.ingredients.add(Ingredient.of(itemLike));
        return this;
    }

    // Optional: explicitly add an empty slot (skippable position)
    public DNAHybridizingRecipeBuilder addEmptyIngredient() {
        if (this.ingredients.size() >= 9) {
            throw new IllegalStateException("DNA hybridizing supports at most 9 ingredients");
        }
        this.ingredients.add(Ingredient.EMPTY);
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, CriterionTriggerInstance trigger) {
        this.advancement.addCriterion(name, trigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) { return this; }

    @Override
    public Item getResult() { return result; }

    @Override
    public void save(Consumer<FinishedRecipe> out, ResourceLocation id) {
        if (this.ingredients.isEmpty()) {
            throw new IllegalStateException("DNA hybridizing recipe must have at least 1 ingredient");
        }
        this.advancement.parent(ResourceLocation.fromNamespaceAndPath("jurassicrevived", "recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(RequirementsStrategy.OR);

        out.accept(new Result(
                id,
                this.result,
                this.count,
                new java.util.ArrayList<>(this.ingredients),
                this.advancement,
                ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "recipes/" + id.getPath())
        ));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final java.util.List<Ingredient> ingredients;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id,
                      Item result,
                      int count,
                      java.util.List<Ingredient> ingredients,
                      Advancement.Builder advancement,
                      ResourceLocation advancementId) {
            this.id = id;
            this.result = result;
            this.count = count;
            this.ingredients = ingredients;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("type", "jurassicrevived:dna_hybridizing");

            JsonArray ingredientsArr = new JsonArray();
            // Write provided ingredients in order; omit trailing empty slots
            for (Ingredient ing : ingredients) {
                if (ing == Ingredient.EMPTY) {
                    ingredientsArr.add(new JsonObject()); // represents empty/optional slot safely
                } else {
                    ingredientsArr.add(ing.toJson());
                }
            }
            json.add("ingredients", ingredientsArr);

            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            if (this.count > 1) resultObj.addProperty("count", this.count);
            json.add("result", resultObj);
        }

        @Override
        public ResourceLocation getId() { return this.id; }

        @Override
        public RecipeSerializer<?> getType() { return DNAHybridizerRecipe.Serializer.INSTANCE; }

        @Override
        public JsonObject serializeAdvancement() { return this.advancement.serializeToJson(); }

        @Override
        public ResourceLocation getAdvancementId() { return this.advancementId; }
    }
}