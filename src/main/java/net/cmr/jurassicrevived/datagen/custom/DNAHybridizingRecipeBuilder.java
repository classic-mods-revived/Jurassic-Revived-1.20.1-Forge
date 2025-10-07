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
    private final Ingredient ingredientA;
    private final Ingredient ingredientB;
    private final Ingredient ingredientC;
    private final int count;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public DNAHybridizingRecipeBuilder(ItemLike a, ItemLike b, ItemLike c, ItemLike result, int count) {
        this.ingredientA = Ingredient.of(a);
        this.ingredientB = Ingredient.of(b);
        this.ingredientC = Ingredient.of(c);
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
        this.advancement.parent(ResourceLocation.fromNamespaceAndPath("jurassicrevived", "recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(RequirementsStrategy.OR);

        out.accept(new Result(
                id,
                this.result,
                this.count,
                this.ingredientA,
                this.ingredientB,
                this.ingredientC,
                this.advancement,
                ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "recipes/" + id.getPath())
        ));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final Ingredient a;
        private final Ingredient b;
        private final Ingredient c;
        private final int count;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id,
                      Item result,
                      int count,
                      Ingredient a,
                      Ingredient b,
                      Ingredient c,
                      Advancement.Builder advancement,
                      ResourceLocation advancementId) {
            this.id = id;
            this.result = result;
            this.count = count;
            this.a = a;
            this.b = b;
            this.c = c;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("type", "jurassicrevived:dna_hybridizing");

            JsonArray ingredients = new JsonArray();
            ingredients.add(a.toJson());
            ingredients.add(b.toJson());
            ingredients.add(c.toJson());
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
        public RecipeSerializer<?> getType() { return DNAHybridizerRecipe.Serializer.INSTANCE; }

        @Override
        public JsonObject serializeAdvancement() { return this.advancement.serializeToJson(); }

        @Override
        public ResourceLocation getAdvancementId() { return this.advancementId; }
    }
}