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

        private final NonNullList<Ingredient> inputItems; // 0..7 normal, 8 mirrored catalyst for convenience
        private final Ingredient catalyst;
        private final ItemStack output;
        private final ResourceLocation id;

        public DNAHybridizerRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputItems) {
            this(id, output, inputItems, inputItems.size() > 8 ? inputItems.get(8) : Ingredient.EMPTY);
        }

        public DNAHybridizerRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputItems, Ingredient catalyst) {
            this.inputItems = inputItems;
            this.output = output;
            this.id = id;
            this.catalyst = catalyst == null ? Ingredient.EMPTY : catalyst;
        }

        // Optional accessor if you want it
        public Ingredient getCatalyst() { return catalyst; }

        @Override
        public boolean matches(SimpleContainer container, Level level) {
            if (level.isClientSide()) return false;

        java.util.List<Ingredient> required = new java.util.ArrayList<>();
        for (int i = 0; i < 8 && i < inputItems.size(); i++) {
            var ing = inputItems.get(i);
            if (!ing.isEmpty()) required.add(ing);
        }
        if (!catalyst.isEmpty()) required.add(catalyst);

        java.util.List<ItemStack> inputs = new java.util.ArrayList<>(9);
        for (int i = 0; i < Math.min(9, container.getContainerSize()); i++) {
            var s = container.getItem(i);
            if (!s.isEmpty()) inputs.add(s);
        }
        if (inputs.size() != required.size()) return false;

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
            if (!matched) return false;
        }
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

                // Read normal ingredients array (0..7)
                JsonArray ingredientsJson = GsonHelper.getAsJsonArray(json, "ingredients");
                NonNullList<Ingredient> inputs = NonNullList.withSize(MAX_INPUT_SLOTS, Ingredient.EMPTY);
                int count = Math.min(ingredientsJson.size(), 8);
                for (int i = 0; i < count; i++) {
                    JsonElement el = ingredientsJson.get(i);
                    if (!el.isJsonNull()) {
                        inputs.set(i, Ingredient.fromJson(el));
                    }
                }

                // Read catalyst (required for slot 8); fall back to index 8 of array if older data
                Ingredient catalyst = Ingredient.EMPTY;
                if (json.has("catalyst")) {
                    catalyst = Ingredient.fromJson(json.get("catalyst"));
                } else if (ingredientsJson.size() > 8) {
                    JsonElement el = ingredientsJson.get(8);
                    if (!el.isJsonNull()) {
                        catalyst = Ingredient.fromJson(el);
                    }
                }
                inputs.set(8, catalyst);

                return new DNAHybridizerRecipe(id, output, inputs, catalyst);
            }

            @Override
            public DNAHybridizerRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
                // read 9 slots (0..7 normal + 8 catalyst)
                NonNullList<Ingredient> inputs = NonNullList.withSize(MAX_INPUT_SLOTS, Ingredient.EMPTY);
                for (int i = 0; i < 9; i++) {
                    inputs.set(i, Ingredient.fromNetwork(buf));
                }
                Ingredient catalyst = inputs.get(8);
                ItemStack output = buf.readItem();
                return new DNAHybridizerRecipe(id, output, inputs, catalyst);
            }

            @Override
            public void toNetwork(FriendlyByteBuf buf, DNAHybridizerRecipe recipe) {
                // write exactly 9 ingredients: 0..7 normal + 8 catalyst
                for (int i = 0; i < 9; i++) {
                    Ingredient ing = (i == 8 ? recipe.catalyst
                                             : (i < recipe.inputItems.size() ? recipe.inputItems.get(i) : Ingredient.EMPTY));
                    ing.toNetwork(buf);
                }
                buf.writeItemStack(recipe.getResultItem(null), false);
            }
        }
    }