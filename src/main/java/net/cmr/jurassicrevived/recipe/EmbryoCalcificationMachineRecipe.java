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

public class EmbryoCalcificationMachineRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;

    // Optional weights for dna items when amber is the input
    // private final java.util.Map<Item, Integer> dnaWeights;

    public EmbryoCalcificationMachineRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputItems) {
        // this(id, output, inputItems, new java.util.HashMap<>());
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
    }

    // public EmbryonicMachineRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputItems,
    //                               java.util.Map<Item, Integer> dnaWeights) {
    //     this.inputItems = inputItems;
    //     this.output = output;
    //     this.id = id;
    //     this.dnaWeights = dnaWeights;
    // }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        boolean ampouleslot = inputItems.get(0).test(pContainer.getItem(0));
        boolean materialslot = inputItems.get(1).test(pContainer.getItem(1));
        return ampouleslot && materialslot;
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        return output.copy();
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

    public static class Type implements RecipeType<EmbryoCalcificationMachineRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "embryonic_machining";
    }

    public static class Serializer implements RecipeSerializer<EmbryoCalcificationMachineRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID,"embryonic_machining");

        @Override
        public EmbryoCalcificationMachineRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            return new EmbryoCalcificationMachineRecipe(id, output, inputs);
        }

        @Override
        public EmbryoCalcificationMachineRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();

            return new EmbryoCalcificationMachineRecipe(id, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, EmbryoCalcificationMachineRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(null), false);
        }
    }
}