package net.cmr.jurassicrevived.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class SprintingPanicGoal extends PanicGoal {
    private final LivingEntity entity;

    public SprintingPanicGoal(PathfinderMob mob, double speedModifier) {
        super(mob, speedModifier);
        this.entity = mob;
    }

    @Override
    public void start() {
        super.start();
        this.entity.setSprinting(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.entity.setSprinting(false);
    }
}
