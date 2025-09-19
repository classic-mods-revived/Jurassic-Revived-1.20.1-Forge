package net.jurassicrevived.jurassicrevived.datagen.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.jurassicrevived.jurassicrevived.recipe.FossilCleanerRecipe;
import net.jurassicrevived.jurassicrevived.util.ModTags;
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

// New builder name/API you can call from ModRecipeProvider in 1.21.1-style code.
public class FossilCleaningRecipeBuilder implements RecipeBuilder {
    private final Item resultPlaceholder;
    private final Ingredient fossilBlockIngredient;
    private final int count;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    // Weighted fossil outputs (by item id)
    private final java.util.Map<ResourceLocation, Integer> fossilWeights = new java.util.HashMap<>();

    private FossilCleaningRecipeBuilder(ItemLike fossilBlock, ItemLike resultPlaceholder, int count) {
        this.fossilBlockIngredient = Ingredient.of(fossilBlock);
        this.resultPlaceholder = resultPlaceholder.asItem();
        this.count = count;
    }

    // Factory: single fossil block input, random fossil output
    public static FossilCleaningRecipeBuilder randomFossil(ItemLike fossilBlock, ItemLike resultPlaceholder, int count) {
        return new FossilCleaningRecipeBuilder(fossilBlock, resultPlaceholder, count);
    }

    public FossilCleaningRecipeBuilder addFossilWeight(Item item, int weight) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        if (id != null) {
            fossilWeights.put(id, weight);
        }
        return this;
    }

    public FossilCleaningRecipeBuilder addFossilWeight(ResourceLocation itemId, int weight) {
        fossilWeights.put(itemId, weight);
        return this;
    }

    // Convenience: add uniform weight for every item in the FOSSILS tag
    public FossilCleaningRecipeBuilder addAllFossilsUniform(int weight) {
        return addAllItemsFromTagUniform(ModTags.Items.FOSSILS, weight);
    }

    // General helper for any tag
    public FossilCleaningRecipeBuilder addAllItemsFromTagUniform(TagKey<Item> tag, int weight) {
        var tags = ForgeRegistries.ITEMS.tags();
        if (tags != null) {
            var entryList = tags.getTag(tag);
            for (Item item : entryList) {
                ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
                if (id != null) {
                    fossilWeights.put(id, weight);
                }
            }
        }
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, CriterionTriggerInstance trigger) {
        this.advancement.addCriterion(name, trigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return resultPlaceholder;
    }

    @Override
    public void save(Consumer<FinishedRecipe> out, ResourceLocation recipeId) {
        String path = recipeId.getPath();
        if (!path.endsWith("_from_fossil_cleaning")) {
            path = path + "_from_fossil_cleaning";
        }
        ResourceLocation finalId = ResourceLocation.fromNamespaceAndPath(recipeId.getNamespace(), path);

        this.advancement.parent(ResourceLocation.fromNamespaceAndPath("jurassicrevived", "recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(finalId))
                .rewards(AdvancementRewards.Builder.recipe(finalId))
                .requirements(RequirementsStrategy.OR);

        out.accept(new Result(
                finalId,
                this.resultPlaceholder,
                this.count,
                this.fossilBlockIngredient,
                new java.util.HashMap<>(this.fossilWeights),
                this.advancement,
                ResourceLocation.fromNamespaceAndPath(finalId.getNamespace(), "recipes/" + finalId.getPath())
        ));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item resultPlaceholder;
        private final Ingredient fossilBlockIngredient;
        private final int count;
        private final java.util.Map<ResourceLocation, Integer> fossilWeights;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id,
                      Item resultPlaceholder,
                      int count,
                      Ingredient fossilBlockIngredient,
                      java.util.Map<ResourceLocation, Integer> fossilWeights,
                      Advancement.Builder advancement,
                      ResourceLocation advancementId) {
            this.id = id;
            this.resultPlaceholder = resultPlaceholder;
            this.count = count;
            this.fossilBlockIngredient = fossilBlockIngredient;
            this.fossilWeights = fossilWeights;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray ingredients = new JsonArray();
            ingredients.add(fossilBlockIngredient.toJson());
            json.add("ingredients", ingredients);

            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("item", ForgeRegistries.ITEMS.getKey(this.resultPlaceholder).toString());
            if (this.count > 1) {
                resultObj.addProperty("count", this.count);
            }
            json.add("result", resultObj);

            if (!fossilWeights.isEmpty()) {
                JsonObject weights = new JsonObject();
                for (java.util.Map.Entry<ResourceLocation, Integer> e : fossilWeights.entrySet()) {
                    weights.addProperty(e.getKey().toString(), e.getValue());
                }
                json.add("fossil_weights", weights);
            }
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return FossilCleanerRecipe.Serializer.INSTANCE;
        }

        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}