package net.cmr.jurassicrevived.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class SprintingMeleeAttackGoal extends MeleeAttackGoal {
    private final LivingEntity entity;

    public SprintingMeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pCanUnseenMemory) {
        super(pMob, pSpeedModifier, pCanUnseenMemory);
        this.entity = pMob;
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

