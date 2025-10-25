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
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class DNAHybridizerRecipe implements Recipe<SimpleContainer> {
    private static final int MAX_INPUT_SLOTS = 9;

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;

    public DNAHybridizerRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputItems) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if (level.isClientSide()) return false;

        // Build list of required ingredients (skip Ingredient.EMPTY placeholders)
        java.util.List<Ingredient> required = new java.util.ArrayList<>();
        for (Ingredient ing : inputItems) {
            if (!ing.isEmpty()) required.add(ing);
        }

        // Collect inputs from the first 9 slots (0..8)
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>(9);
        for (int i = 0; i < Math.min(9, container.getContainerSize()); i++) {
            ItemStack s = container.getItem(i);
            if (!s.isEmpty()) inputs.add(s);
        }

        // Quick size check: extra items present => fail; fewer items than required => fail
        if (inputs.size() != required.size()) return false;

        // Greedy multiset matching: each input must match a distinct required Ingredient
        boolean[] used = new boolean[required.size()];
        for (ItemStack in : inputs) {
            boolean matched = false;
            for (int i = 0; i < required.size(); i++) {
                if (!used[i] && required.get(i).test(in)) {
                    used[i] = true;
                    matched = true;
                    break;
                }
            }
            if (!matched) return false; // input not acceptable for any remaining ingredient
        }

        // All inputs matched exactly one required ingredient; success
        return true;
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess access) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) { return true; }

    @Override
    public ItemStack getResultItem(RegistryAccess access) { return output.copy(); }

    @Override
    public NonNullList<Ingredient> getIngredients() { return this.inputItems; }

    @Override
    public ResourceLocation getId() { return id; }

    @Override
    public RecipeSerializer<?> getSerializer() { return Serializer.INSTANCE; }

    @Override
    public RecipeType<?> getType() { return Type.INSTANCE; }

    public static class Type implements RecipeType<DNAHybridizerRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "dna_hybridizing";
    }

    public static class Serializer implements RecipeSerializer<DNAHybridizerRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "dna_hybridizing");

        @Override
        public DNAHybridizerRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            JsonArray ingredientsJson = GsonHelper.getAsJsonArray(json, "ingredients");
            // Support 1..9 ingredients; pad to 9 with Ingredient.EMPTY to mark optional/unused slots
            int count = Math.min(ingredientsJson.size(), MAX_INPUT_SLOTS);
            NonNullList<Ingredient> inputs = NonNullList.withSize(MAX_INPUT_SLOTS, Ingredient.EMPTY);
            for (int i = 0; i < count; i++) {
                JsonElement el = ingredientsJson.get(i);
                // Allow explicit null/empty entries to mark optional slots in data if desired
                if (!el.isJsonNull()) {
                    inputs.set(i, Ingredient.fromJson(el));
                }
            }
            return new DNAHybridizerRecipe(id, output, inputs);
        }

        @Override
        public DNAHybridizerRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int size = buf.readVarInt(); // number of slots serialized (should be <= 9)
            NonNullList<Ingredient> inputs = NonNullList.withSize(MAX_INPUT_SLOTS, Ingredient.EMPTY);
            for (int i = 0; i < size; i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }
            ItemStack output = buf.readItem();
            return new DNAHybridizerRecipe(id, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, DNAHybridizerRecipe recipe) {
            // Write only up to MAX_INPUT_SLOTS for safety
            int size = Math.min(recipe.getIngredients().size(), MAX_INPUT_SLOTS);
            buf.writeVarInt(size);
            for (int i = 0; i < size; i++) {
                recipe.getIngredients().get(i).toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(null), false);
        }
    }
}