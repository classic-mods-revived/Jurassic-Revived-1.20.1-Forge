package net.cmr.jurassicrevived.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.cmr.jurassicrevived.JRMod;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class FossilGrinderRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;

    // Optional weighted outputs map. If present, assemble() rolls a weighted result.
    private final java.util.Map<Item, Integer> weightedOutputs;

    public FossilGrinderRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputItems) {
        this(id, output, inputItems, new java.util.HashMap<>());
    }

    public FossilGrinderRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputItems,
                               java.util.Map<Item, Integer> weightedOutputs) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
        this.weightedOutputs = weightedOutputs;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }
        // Single input slot (slot 0) for the grinder
        boolean inputOk = inputItems.get(0).test(pContainer.getItem(0));
        return inputOk;
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        // If weighted outputs are configured, pick a weighted random result
        if (!weightedOutputs.isEmpty()) {
            Item chosen = pickWeightedRandom(simpleContainer, RandomSource.create());
            return new ItemStack(chosen, 1);
        }
        // Otherwise, return the fixed output
        return output.copy();
    }

    private Item pickWeightedRandom(SimpleContainer container, RandomSource random) {
        long total = 0;
        java.util.List<Item> items = new java.util.ArrayList<>(weightedOutputs.keySet());
        int[] weights = new int[items.size()];
        for (int i = 0; i < items.size(); i++) {
            int w = Math.max(0, weightedOutputs.getOrDefault(items.get(i), 0));
            weights[i] = w;
            total += w;
        }
        if (total <= 0) {
            return items.get(random.nextInt(items.size()));
        }
        long roll;
        if (total <= Integer.MAX_VALUE) {
            roll = 1L + random.nextInt((int) total);
        } else {
            long r = Math.abs(random.nextLong());
            roll = 1L + (r % total);
        }
        long cumulative = 0;
        for (int i = 0; i < items.size(); i++) {
            cumulative += weights[i];
            if (roll <= cumulative) {
                return items.get(i);
            }
        }
        return items.get(items.size() - 1);
    }

    // --- Helpers for JEI UI & external readers ---
    public boolean hasAnyWeightsConfigured() {
        return !weightedOutputs.isEmpty();
    }

    public int getWeightOrDefault(Item item, int def) {
        return weightedOutputs.getOrDefault(item, def);
    }

    public java.util.Map<Item, Integer> getWeightedOutputs() {
        return java.util.Collections.unmodifiableMap(weightedOutputs);
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<FossilGrinderRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "fossil_grinding";
    }

    public static class Serializer implements RecipeSerializer<FossilGrinderRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID,"fossil_grinding");

        @Override
        public FossilGrinderRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            // Single input for the grinder
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            // Optional weighted_outputs object
            java.util.Map<Item, Integer> weights = new java.util.HashMap<>();
            if (json.has("weighted_outputs") && json.get("weighted_outputs").isJsonObject()) {
                JsonObject obj = json.getAsJsonObject("weighted_outputs");
                for (java.util.Map.Entry<String, JsonElement> e : obj.entrySet()) {
                    String key = e.getKey();
                    int weight = GsonHelper.convertToInt(e.getValue(), "weight");
                    ResourceLocation rl = ResourceLocation.tryParse(key);
                    if (rl != null) {
                        Item item = net.minecraftforge.registries.ForgeRegistries.ITEMS.getValue(rl);
                        if (item != null) {
                            weights.put(item, weight);
                        }
                    }
                }
            }

            return new FossilGrinderRecipe(id, output, inputs, weights);
        }

        @Override
        public FossilGrinderRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();

            // read weighted outputs
            int mapSize = buf.readVarInt();
            java.util.Map<Item, Integer> weights = new java.util.HashMap<>(mapSize);
            for (int i = 0; i < mapSize; i++) {
                ResourceLocation key = buf.readResourceLocation();
                int value = buf.readVarInt();
                Item item = net.minecraftforge.registries.ForgeRegistries.ITEMS.getValue(key);
                if (item != null) {
                    weights.put(item, value);
                }
            }

            return new FossilGrinderRecipe(id, output, inputs, weights);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, FossilGrinderRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(null), false);

            // write weighted outputs
            buf.writeVarInt(recipe.weightedOutputs.size());
            for (java.util.Map.Entry<Item, Integer> e : recipe.weightedOutputs.entrySet()) {
                ResourceLocation key = net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(e.getKey());
                buf.writeResourceLocation(key);
                buf.writeVarInt(e.getValue());
            }
        }
    }
}