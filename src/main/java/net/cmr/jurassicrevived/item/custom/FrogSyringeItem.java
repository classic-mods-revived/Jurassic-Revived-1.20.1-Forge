package net.cmr.jurassicrevived.item.custom;

import net.cmr.jurassicrevived.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FrogSyringeItem extends Item {
    public FrogSyringeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!(target instanceof Frog)) {
            return InteractionResult.PASS;
        }

        if (!player.level().isClientSide) {
            // consume one syringe
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            // give frog material
            ItemStack reward = new ItemStack(ModItems.FROG_MATERIAL.get());
            if (!player.addItem(reward)) {
                player.drop(reward, false);
            }
            // feedback
            player.level().playSound(null, target.blockPosition(), SoundEvents.HONEY_DRINK, SoundSource.PLAYERS, 0.7f, 1.2f);
        }

        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }
}
