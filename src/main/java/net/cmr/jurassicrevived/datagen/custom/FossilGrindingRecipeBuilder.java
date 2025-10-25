package net.cmr.jurassicrevived.datagen.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.recipe.FossilGrinderRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class FossilGrindingRecipeBuilder implements RecipeBuilder {
    private final Ingredient input;
    private final Item resultItem;
    private final int count;

    private final Map<ResourceLocation, Integer> weightedOutputs = new HashMap<>();
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    private FossilGrindingRecipeBuilder(Ingredient input, ItemLike resultItem, int count) {
        this.input = input;
        this.resultItem = resultItem.asItem();
        this.count = count;
    }

    // Factory: fossil input with weighted outcomes (30/30/30/10)
    public static FossilGrindingRecipeBuilder fossilWeighted(ItemLike fossilInput, ItemLike tissueOutput) {
        FossilGrindingRecipeBuilder b = new FossilGrindingRecipeBuilder(Ingredient.of(fossilInput), tissueOutput, 1);
        // Weighted outputs
        b.addWeightedOutput(Items.BONE_MEAL, 40);
        b.addWeightedOutput(ModItems.CRUSHED_FOSSIL.get(), 40);
        b.addWeightedOutput(tissueOutput.asItem(), 20);
        return b;
    }

    // Factory: skull input -> direct tissue (no weighted_outputs, deterministic)
    public static FossilGrindingRecipeBuilder skullDirect(ItemLike skullInput, ItemLike tissueOutput) {
        return new FossilGrindingRecipeBuilder(Ingredient.of(skullInput), tissueOutput, 1);
    }

    // Optional: allow custom weights if needed in the future
    public FossilGrindingRecipeBuilder addWeightedOutput(ItemLike item, int weight) {
        Item it = item.asItem();
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(it);
        if (id != null) {
            this.weightedOutputs.put(id, weight);
        }
        return this;
    }

    @Override
    public FossilGrindingRecipeBuilder unlockedBy(String criterionName, CriterionTriggerInstance trigger) {
        this.advancement.addCriterion(criterionName, trigger);
        return this;
    }

    @Override
    public FossilGrindingRecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.resultItem;
    }

    // Convenience overloads (derive id from result item)
    public void saveSkull(Consumer<FinishedRecipe> pWriter) {
        ResourceLocation baseId = computeBaseIdFromResult();
        saveSkull(pWriter, baseId);
    }

    public void saveFossil(Consumer<FinishedRecipe> pWriter) {
        ResourceLocation baseId = computeBaseIdFromResult();
        saveFossil(pWriter, baseId);
    }

    // Two-argument overloads (explicit base id)
    public void saveSkull(Consumer<FinishedRecipe> pWriter, ResourceLocation baseId) {
        ResourceLocation finalId = buildFinalId(baseId, "_from_skull");
        saveInternal(pWriter, finalId);
    }

    public void saveFossil(Consumer<FinishedRecipe> pWriter, ResourceLocation baseId) {
        ResourceLocation finalId = buildFinalId(baseId, "_from_fossil");
        saveInternal(pWriter, finalId);
    }

    @Override
    public void save(Consumer<FinishedRecipe> pWriter, ResourceLocation recipeId) {
        ResourceLocation finalId = buildFinalId(recipeId, "");
        saveInternal(pWriter, finalId);
    }

    // Build final id with optional prefix before "_from_fossil_grinding"
    private ResourceLocation buildFinalId(ResourceLocation recipeId, String prefix) {
        String ns = recipeId.getNamespace();
        String path = recipeId.getPath();
        String suffix = (prefix == null || prefix.isEmpty())
                ? "_from_fossil_grinding"
                : prefix + "_from_fossil_grinding";
        if (!path.endsWith(suffix)) {
            path = path + suffix;
        }
        return ResourceLocation.fromNamespaceAndPath(ns, path);
    }

    // New: derive a base id from the result item ("<modid>:<result_path>")
    private ResourceLocation computeBaseIdFromResult() {
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(this.resultItem);
        String path = (itemId != null) ? itemId.getPath() : this.resultItem.toString().replace(':', '_');
        // Ensure the recipe is written under your mod's namespace
        return ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, path);
    }

    private void saveInternal(Consumer<net.minecraft.data.recipes.FinishedRecipe> consumer, ResourceLocation finalId) {
        this.advancement.parent(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(finalId))
                .rewards(AdvancementRewards.Builder.recipe(finalId))
                .requirements(RequirementsStrategy.OR);

        consumer.accept(new Result(
                finalId,
                this.input,
                this.resultItem,
                this.count,
                new HashMap<>(this.weightedOutputs),
                this.advancement,
                ResourceLocation.fromNamespaceAndPath(finalId.getNamespace(), "recipes/" + finalId.getPath())
        ));
    }

    public static class Result implements net.minecraft.data.recipes.FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient input;
        private final Item result;
        private final int count;
        private final Map<ResourceLocation, Integer> weightedOutputs;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id,
                      Ingredient input,
                      Item result,
                      int count,
                      Map<ResourceLocation, Integer> weightedOutputs,
                      Advancement.Builder advancement,
                      ResourceLocation advancementId) {
            this.id = id;
            this.input = input;
            this.result = result;
            this.count = count;
            this.weightedOutputs = weightedOutputs;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("type", FossilGrinderRecipe.Serializer.ID.toString());
            JsonArray ingredients = new JsonArray();
            ingredients.add(input.toJson());
            json.add("ingredients", ingredients);
            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("item", ForgeRegistries.ITEMS.getKey(result).toString());
            resultObj.addProperty("count", count);
            json.add("result", resultObj);
            if (!weightedOutputs.isEmpty()) {
                JsonObject weights = new JsonObject();
                for (Map.Entry<ResourceLocation, Integer> e : weightedOutputs.entrySet()) {
                    weights.addProperty(e.getKey().toString(), e.getValue());
                }
                json.add("weighted_outputs", weights);
            }
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return FossilGrinderRecipe.Serializer.INSTANCE;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}