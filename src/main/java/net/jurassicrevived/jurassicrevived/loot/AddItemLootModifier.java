package net.jurassicrevived.jurassicrevived.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class AddItemLootModifier extends LootModifier {
    private final ItemStack stack;
    private final float chance;

    public static final Codec<AddItemLootModifier> CODEC = RecordCodecBuilder.create(inst ->
            LootModifier.codecStart(inst)
                    .and(ItemStack.CODEC.fieldOf("item").forGetter(m -> m.stack))
                    .and(Codec.FLOAT.fieldOf("chance").orElse(1.0f).forGetter(m -> m.chance))
                    .apply(inst, AddItemLootModifier::new)
    );

    protected AddItemLootModifier(LootItemCondition[] conditions, ItemStack stack, float chance) {
        super(conditions);
        this.stack = stack.copy();
        this.chance = chance;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getRandom().nextFloat() < chance) {
            generatedLoot.add(stack.copy());
        }
        return generatedLoot;
    }
}