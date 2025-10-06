package net.cmr.jurassicrevived.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.List;
import java.util.function.Supplier;

public class CustomGenderedSpawnEggItem extends ForgeSpawnEggItem {
    private static final String KEY_SELECTED_VARIANT = "SelectedVariant";
    private static final String KEY_VARIANT = "Variant";
    private static final String KEY_ENTITY_TAG = "EntityTag";
    private static final int VARIANT_COUNT = 2; // 0=Male, 1=Female

    public CustomGenderedSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type,
                                      int backgroundColor,
                                      int highlightColor,
                                      Item.Properties properties) {
        super(type, backgroundColor, highlightColor, properties);
    }

    // --- Selection stored on the item via root NBT ---

    private static int getSelectedVariant(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains(KEY_SELECTED_VARIANT)) return 0;
        return Math.floorMod(tag.getInt(KEY_SELECTED_VARIANT), VARIANT_COUNT);
    }

    private static void setSelectedVariant(ItemStack stack, int variant) {
        int v = Math.floorMod(variant, VARIANT_COUNT);
        stack.getOrCreateTag().putInt(KEY_SELECTED_VARIANT, v);
    }

    private static void cycleVariant(ItemStack stack) {
        setSelectedVariant(stack, (getSelectedVariant(stack) + 1) % VARIANT_COUNT);
    }

    // --- Inject the chosen Variant into the Spawn Egg's EntityTag so the mob reads it ---

    private static void ensureEntityDataHasVariant(ItemStack stack) {
        final int variant = getSelectedVariant(stack);
        CompoundTag root = stack.getOrCreateTag();
        CompoundTag entityTag = root.getCompound(KEY_ENTITY_TAG);
        entityTag.putInt(KEY_VARIANT, variant);
        root.put(KEY_ENTITY_TAG, entityTag);
    }

    // --- Interactions ---

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // Shift-right-click in air toggles variant selection
        if (player.isSecondaryUseActive()) {
            if (!level.isClientSide) {
                cycleVariant(stack);
                // Send a notify sound from the server to the client(s)
                player.playNotifySound(
                        SoundEvents.EXPERIENCE_ORB_PICKUP,
                        SoundSource.PLAYERS,
                        0.5f, 1.1f
                );
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }

        return super.use(level, player, hand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();

        // If sneaking and using on a block, toggle gender and DO NOT spawn
        if (player != null && player.isSecondaryUseActive()) {
            if (!level.isClientSide) {
                cycleVariant(context.getItemInHand());
                // Use notify sound from the server (sends to nearby clients)
                player.playNotifySound(
                    SoundEvents.EXPERIENCE_ORB_PICKUP,
                    SoundSource.PLAYERS,
                    0.5f, 1.1f
            );
        }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        // If actually spawning (not holding secondary-use), inject the Variant
        if (player == null || !player.isSecondaryUseActive()) {
            ensureEntityDataHasVariant(context.getItemInHand());
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        int v = getSelectedVariant(stack);
        String genderText = (v == 0) ? "Male" : "Female";

        tooltip.add(Component.translatable("tooltip.jurassicrevived.gender", genderText));
        tooltip.add(Component.translatable("tooltip.jurassicrevived.gender.hint", "Shift-Right-Click"));
    }

    @Override
    public Component getName(ItemStack stack) {
        Component base = super.getName(stack);
        boolean male = getSelectedVariant(stack) == 0;
        Component gender = Component.literal(male ? "Male" : "Female")
                .withStyle(male ? ChatFormatting.AQUA : ChatFormatting.LIGHT_PURPLE);

        // Example: "Velociraptor Spawn Egg (Male)"
        return base.copy().append(Component.literal(" (")).append(gender).append(Component.literal(")"));
    }
}