package net.cmr.jurassicrevived.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.item.ModItems;
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

public class DNAAnalyzerRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;

    // Optional weights for dna items when amber is the input
    private final java.util.Map<Item, Integer> dnaWeights;

    public DNAAnalyzerRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputItems) {
        this(id, output, inputItems, new java.util.HashMap<>());
    }

    public DNAAnalyzerRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputItems,
                             java.util.Map<Item, Integer> dnaWeights) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
        this.dnaWeights = dnaWeights;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        boolean testtubeslot = inputItems.get(0).test(pContainer.getItem(0));
        boolean materialslot = inputItems.get(1).test(pContainer.getItem(1));
        return testtubeslot && materialslot;
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        // If the material input is Mosquito in Amber, output a random dna (weighted if configured)
        if (!simpleContainer.getItem(1).isEmpty()
                && simpleContainer.getItem(1).getItem() == ModItems.MOSQUITO_IN_AMBER.get()) {
            ItemStack[] candidates = Ingredient.of(ModTags.Items.DNA).getItems();
            if (candidates.length > 0) {
                Item chosen = pickWeightedRandomDNA(candidates, RandomSource.create());
                return new ItemStack(chosen, 1);
            }
        }
        return output.copy();
    }

    private Item pickWeightedRandomDNA(ItemStack[] candidates, RandomSource random) {
        // Sum weights
        long total = 0;
        int[] weights = new int[candidates.length];
        for (int i = 0; i < candidates.length; i++) {
            Item item = candidates[i].getItem();
            int w = Math.max(0, dnaWeights.getOrDefault(item, 1));
            weights[i] = w;
            total += w;
        }
        if (total <= 0) {
            // fallback uniform if all weights are zero/invalid
            return candidates[random.nextInt(candidates.length)].getItem();
        }

        // Roll in [1, total] using APIs available in 1.20.1
        long roll;
        if (total <= Integer.MAX_VALUE) {
            roll = 1L + random.nextInt((int) total);
        } else {
            // Very large totals unlikely, but handle safely
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

    // --- Helpers for JEI UI ---
    public boolean hasAnyWeightsConfigured() {
        return !dnaWeights.isEmpty();
    }

    public int getWeightOrDefault(Item item, int def) {
        return dnaWeights.getOrDefault(item, def);
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

    public static class Type implements RecipeType<DNAAnalyzerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "dna_analyzing";
    }

    public static class Serializer implements RecipeSerializer<DNAAnalyzerRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID,"dna_analyzing");

        @Override
        public DNAAnalyzerRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            // Optional weights object
            java.util.Map<Item, Integer> weights = new java.util.HashMap<>();
            if (json.has("dna_weights") && json.get("dna_weights").isJsonObject()) {
                JsonObject obj = json.getAsJsonObject("dna_weights");
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

            return new DNAAnalyzerRecipe(id, output, inputs, weights);
        }

        @Override
        public DNAAnalyzerRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();

            // read weights
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

            return new DNAAnalyzerRecipe(id, output, inputs, weights);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, DNAAnalyzerRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(null), false);

            // write weights
            buf.writeVarInt(recipe.dnaWeights.size());
            for (java.util.Map.Entry<Item, Integer> e : recipe.dnaWeights.entrySet()) {
                ResourceLocation key = net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(e.getKey());
                buf.writeResourceLocation(key);
                buf.writeVarInt(e.getValue());
            }
        }
    }
}