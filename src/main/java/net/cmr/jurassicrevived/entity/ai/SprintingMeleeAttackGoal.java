package net.cmr.jurassicrevived.entity.ai;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;

public class SprintingMeleeAttackGoal extends MeleeAttackGoal {
    private final LivingEntity entity;

    public SprintingMeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pCanUnseenMemory) {
        super(pMob, pSpeedModifier, pCanUnseenMemory);
        this.entity = pMob;
    }

    // Override to prevent attacking players in peaceful difficulty and prevent babies from attacking
    @Override
    public boolean canUse() {
        if (!super.canUse()) {
            return false;
        }

        // Prevent baby mobs from attacking
        if (this.mob instanceof AgeableMob ageableMob && ageableMob.isBaby()) {
            return false;
        }

        // Check if target is a player and difficulty is peaceful
        LivingEntity target = this.mob.getTarget();
        if (target instanceof Player && this.mob.level().getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        }

        return true;
    }

    // This method is called to start the goal
    @Override
    public void start() {
        super.start();
        this.entity.setSprinting(true); // Force the entity to sprint
    }

    // This method is called to tick the goal
    @Override
    public void tick() {
        super.tick();
        // The movement speed is handled by the default goal behavior
        // The sprint state is maintained from the start() method
    }

    // This method is called to end the goal
    @Override
    public void stop() {
        super.stop();
        this.entity.setSprinting(false); // Stop sprinting when the goal is finished
    }
}

