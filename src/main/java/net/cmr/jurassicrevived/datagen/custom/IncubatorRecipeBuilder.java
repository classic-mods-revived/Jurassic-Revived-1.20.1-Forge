package net.cmr.jurassicrevived.datagen.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.cmr.jurassicrevived.recipe.IncubatorRecipe;
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

// Allows: new IncubatingRecipeBuilder(eggItem, hatchedBlock, 1).unlockedBy(...).save(pWriter);
public class IncubatorRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final Ingredient ingredient;
    private final int count;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public IncubatorRecipeBuilder(ItemLike ingredient, ItemLike result, int count) {
        this.ingredient = Ingredient.of(ingredient);
        this.result = result.asItem();
        this.count = count;
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
        String path = id.getPath();
        if (!path.endsWith("_from_incubating")) {
            path = path + "_from_incubating";
        }
        ResourceLocation finalId = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), path);

        this.advancement
                .parent(ResourceLocation.fromNamespaceAndPath("jurassicrevived", "recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(finalId))
                .rewards(AdvancementRewards.Builder.recipe(finalId))
                .requirements(RequirementsStrategy.OR);

        out.accept(new Result(
                finalId,
                this.result,
                this.count,
                this.ingredient,
                this.advancement,
                ResourceLocation.fromNamespaceAndPath(finalId.getNamespace(), "recipes/" + finalId.getPath())
        ));
    }

    public void save(Consumer<FinishedRecipe> out) {
        save(out, ForgeRegistries.ITEMS.getKey(this.result));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final Ingredient ingredient;
        private final int count;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Item result, int count, Ingredient ingredient,
                      Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.result = result;
            this.count = count;
            this.ingredient = ingredient;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray ingredients = new JsonArray();
            ingredients.add(ingredient.toJson());
            json.add("ingredients", ingredients);

            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            if (this.count > 1) {
                resultObj.addProperty("count", this.count);
            }
            json.add("result", resultObj);
        }

        @Override
        public ResourceLocation getId() { return this.id; }

        @Override
        public RecipeSerializer<?> getType() { return IncubatorRecipe.Serializer.INSTANCE; }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() { return this.advancement.serializeToJson(); }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() { return this.advancementId; }
    }
}