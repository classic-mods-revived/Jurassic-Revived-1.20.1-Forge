package net.cmr.jurassicrevived.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.cmr.jurassicrevived.JRMod;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class IncubatorRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private final ResourceLocation id;

    public IncubatorRecipe(ResourceLocation id, NonNullList<Ingredient> inputs, ItemStack output) {
        this.id = id;
        this.inputs = inputs;
        this.output = output;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if (level.isClientSide()) return false;
        // Accept match if ANY slot has the ingredient (we'll apply per-slot in BE)
        for (int i = 0; i < container.getContainerSize(); i++) {
            if (!inputs.isEmpty() && inputs.get(0).test(container.getItem(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputs;
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

    public static class Type implements RecipeType<IncubatorRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "incubating";
    }

    public static class Serializer implements RecipeSerializer<IncubatorRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "incubating");

        @Override
        public IncubatorRecipe fromJson(ResourceLocation id, JsonObject json) {
            JsonArray arr = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            inputs.set(0, Ingredient.fromJson(arr.get(0)));
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new IncubatorRecipe(id, inputs, output);
        }

        @Override
        public IncubatorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int size = buf.readInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(size, Ingredient.EMPTY);
            for (int i = 0; i < size; i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }
            ItemStack output = buf.readItem();
            return new IncubatorRecipe(id, inputs, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, IncubatorRecipe recipe) {
            buf.writeInt(recipe.inputs.size());
            for (Ingredient ing : recipe.inputs) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.output, false);
        }
    }
}