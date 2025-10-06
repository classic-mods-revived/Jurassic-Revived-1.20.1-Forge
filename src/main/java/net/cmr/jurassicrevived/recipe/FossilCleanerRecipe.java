package net.cmr.jurassicrevived.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.util.ModTags;
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

public class FossilCleanerRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;

    // Weighted fossil outputs (picked at assemble-time)
    private final java.util.Map<Item, Integer> fossilWeights;

    public FossilCleanerRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputItems) {
        this(id, output, inputItems, new java.util.HashMap<>());
    }

    public FossilCleanerRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputItems,
                               java.util.Map<Item, Integer> fossilWeights) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
        this.fossilWeights = fossilWeights;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }
        // Single fossil block ingredient lives in handler slot 1
        return inputItems.get(0).test(pContainer.getItem(1));
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        // Produce a weighted random fossil from the FOSSILS tag
        ItemStack[] candidates = Ingredient.of(ModTags.Items.FOSSILS).getItems();
        if (candidates.length > 0) {
            Item chosen = pickWeightedRandomFossil(candidates, RandomSource.create());
            return new ItemStack(chosen, 1);
        }
        // Fallback to fixed output if tag is empty (should not occur in normal use)
        return output.copy();
    }

    private Item pickWeightedRandomFossil(ItemStack[] candidates, RandomSource random) {
        long total = 0;
        int[] weights = new int[candidates.length];
        for (int i = 0; i < candidates.length; i++) {
            Item item = candidates[i].getItem();
            int w = Math.max(0, fossilWeights.getOrDefault(item, 1));
            weights[i] = w;
            total += w;
        }
        if (total <= 0) {
            return candidates[random.nextInt(candidates.length)].getItem();
        }

        long roll;
        if (total <= Integer.MAX_VALUE) {
            roll = 1L + random.nextInt((int) total);
        } else {
            long r = Math.abs(random.nextLong());
            roll = 1L + (r % total);
        }

        long cumulative = 0;
        for (int i = 0; i < candidates.length; i++) {
            cumulative += weights[i];
            if (roll <= cumulative) {
                return candidates[i].getItem();
            }
        }
        return candidates[candidates.length - 1].getItem();
    }

    // --- Helpers for JEI UI & readers ---
    public boolean hasAnyWeightsConfigured() {
        return !fossilWeights.isEmpty();
    }

    public int getWeightOrDefault(Item item, int def) {
        return fossilWeights.getOrDefault(item, def);
    }

    public java.util.Map<Item, Integer> getWeightedOutputs() {
        return java.util.Collections.unmodifiableMap(fossilWeights);
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

    public static class Type implements RecipeType<FossilCleanerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "fossil_cleaning";
    }

    public static class Serializer implements RecipeSerializer<FossilCleanerRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "fossil_cleaning");

        @Override
        public FossilCleanerRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            // Single ingredient: the fossil block
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            inputs.set(0, Ingredient.fromJson(ingredients.get(0)));

            // Optional fossil_weights object
            java.util.Map<Item, Integer> weights = new java.util.HashMap<>();
            if (json.has("fossil_weights") && json.get("fossil_weights").isJsonObject()) {
                JsonObject obj = json.getAsJsonObject("fossil_weights");
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

            return new FossilCleanerRecipe(id, output, inputs, weights);
        }

        @Override
        public FossilCleanerRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();

            // read fossil weights
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

            return new FossilCleanerRecipe(id, output, inputs, weights);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, FossilCleanerRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(null), false);

            // write fossil weights
            buf.writeVarInt(recipe.fossilWeights.size());
            for (java.util.Map.Entry<Item, Integer> e : recipe.fossilWeights.entrySet()) {
                ResourceLocation key = net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(e.getKey());
                buf.writeResourceLocation(key);
                buf.writeVarInt(e.getValue());
            }
        }
    }
}