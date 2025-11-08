package net.cmr.jurassicrevived.entity.custom;

import net.cmr.jurassicrevived.entity.ModEntities;
import net.cmr.jurassicrevived.entity.ai.SprintingMeleeAttackGoal;
import net.cmr.jurassicrevived.entity.ai.SprintingPanicGoal;
import net.cmr.jurassicrevived.entity.variant.CarnotaurusVariant;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class CarnotaurusEntity extends Animal implements GeoEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT =
            SynchedEntityData.defineId(CarnotaurusEntity.class, EntityDataSerializers.INT);

    // Procedural tail sway state (client-side use for rendering)
    private float tailSwayOffset;   // Smoothed offset in range roughly [-1, 1]
    private float tailSwayVelocity; // Internal velocity for spring-damper
    private float tailSwayPrev;     // Previous frame value for interpolation

    public CarnotaurusEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SprintingPanicGoal(this, 1.15) {
            @Override
            public boolean canUse() {
                return CarnotaurusEntity.this.isBaby() && super.canUse();
            }
        });
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.goalSelector.addGoal(2, new FloatGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, BrachiosaurusEntity.class, (float) 20, 1, 1));
        this.goalSelector.addGoal(4, new SprintingMeleeAttackGoal(this, 1.1, false) {
            @Override
            public boolean canUse() {
                return !CarnotaurusEntity.this.isBaby() && super.canUse();
            }
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return 4;
            }
        });
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new FollowMobGoal(this, 1.2, (float) 20, (float) 10));
        this.targetSelector.addGoal(10, new NearestAttackableTargetGoal<>(this, Monster.class, true));
        this.targetSelector.addGoal(11, new NearestAttackableTargetGoal<>(this, Animal.class, 10, false, false,
                target -> target.getType() != this.getType()));
        this.targetSelector.addGoal(12, new NearestAttackableTargetGoal(this, TriceratopsEntity.class, false, false));
        this.targetSelector.addGoal(13, new NearestAttackableTargetGoal(this, GallimimusEntity.class, false, false));
        this.targetSelector.addGoal(14, new NearestAttackableTargetGoal(this, DilophosaurusEntity.class, false, false));
        this.targetSelector.addGoal(15, new NearestAttackableTargetGoal(this, CeratosaurusEntity.class, false, false));
        this.targetSelector.addGoal(16, new NearestAttackableTargetGoal(this, ParasaurolophusEntity.class, false, false));
        this.targetSelector.addGoal(17, new NearestAttackableTargetGoal(this, VelociraptorEntity.class, false, false));
        this.targetSelector.addGoal(18, new NearestAttackableTargetGoal(this, SpinosaurusEntity.class, false, false));
        this.targetSelector.addGoal(19, new NearestAttackableTargetGoal(this, IndominusRexEntity.class, false, false));
        this.targetSelector.addGoal(20, new NearestAttackableTargetGoal(this, Player.class, false, false));
        this.goalSelector.addGoal(21, new RandomLookAroundGoal(this));


    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 55D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ARMOR, 0D)
                .add(Attributes.FOLLOW_RANGE, 32D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0D)
                .add(Attributes.ATTACK_DAMAGE, 16D);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.BEEF);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        AgeableMob child = ModEntities.CARNOTAURUS.get().create(pLevel);
        if (child instanceof CarnotaurusEntity baby) {
            CarnotaurusVariant randomVariant = Util.getRandom(CarnotaurusVariant.values(), this.random);
            baby.setVariant(randomVariant);
        }
        return child;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (!level().isClientSide && hit && target instanceof LivingEntity) {
            if (this.level() instanceof ServerLevel serverLevel) {
                this.triggerAnim("attackController", "attack");
            }
        }
        return hit;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Walk/Run/Idle", state -> {
            if (state.isMoving())
                return state.setAndContinue(CarnotaurusEntity.this.isSprinting() ? RawAnimation.begin().then("anim.carnotaurus.run", Animation.LoopType.LOOP) : RawAnimation.begin().then("anim.carnotaurus.walk", Animation.LoopType.LOOP));

            return state.setAndContinue(RawAnimation.begin().then("anim.carnotaurus.idle", Animation.LoopType.LOOP));
        }));

        controllers.add(new AnimationController<>(this, "attackController", state -> PlayState.STOP)
                .triggerableAnim("attack", RawAnimation.begin().then("anim.carnotaurus.attack", Animation.LoopType.PLAY_ONCE)));

        controllers.add(new AnimationController<>(this, "mouthController", state -> PlayState.STOP)
                .triggerableAnim("mouth", RawAnimation.begin().then("anim.carnotaurus.mouth", Animation.LoopType.PLAY_ONCE)));
    }

    private float getSignedTurnDelta() {
        // Only consider the body (torso) rotation so head look does not affect tail sway
        return Mth.wrapDegrees(this.yBodyRot - this.yBodyRotO);
    }

    private int mouthAnimCooldown = 0;

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            var maxHealthAttr = getAttribute(Attributes.MAX_HEALTH);
            if (maxHealthAttr != null) {
                double baseAdult = 55;
                double desired = this.isBaby() ? baseAdult * 0.10D : baseAdult;
                if (maxHealthAttr.getBaseValue() != desired) {
                    double oldMax = maxHealthAttr.getBaseValue();
                    double healthRatio = this.getHealth() / (float) oldMax;
                    maxHealthAttr.setBaseValue(desired);
                    this.setHealth((float) (desired * Mth.clamp(healthRatio, 0.0F, 1.0F)));
                }
            }
        }

        if (!level().isClientSide) {
            if (mouthAnimCooldown > 0) {
                mouthAnimCooldown--;
            } else {
                this.triggerAnim("mouthController", "mouth");
                // 30s–60s in ticks
                mouthAnimCooldown = this.random.nextInt(1200 - 600 + 1) + 600;
            }
        }

        if (level().isClientSide) {
            // Capture previous for smooth interpolation between ticks
            this.tailSwayPrev = this.tailSwayOffset;
            updateProceduralTailSway();
        }
    }

    private void updateProceduralTailSway() {
        // Turn input derived from rotation deltas; works even when standing still and turning
        float turnDegrees = getSignedTurnDelta();

        // Deadzone to ignore tiny jitter so the tail can return to center cleanly
        float deadzoneDeg = 0.6f; // smaller deadzone for more responsiveness
        float turnInput = 0.0f;
        if (Math.abs(turnDegrees) >= deadzoneDeg) {
            // Higher sensitivity so small in-place turns still affect the model
            turnInput = Mth.clamp(turnDegrees / 15.0f, -1.0f, 1.0f);
        }

        // Target offset: keep intuitive sign (positive input -> positive sway)
        float target = turnInput;

        // One-pole low-pass (no bounce). Larger alpha => snappier and less "stiff".
        float alpha = 0.24f; // try 0.20–0.30 to taste

        tailSwayOffset += (target - tailSwayOffset) * alpha;

        // Snap tiny residuals to zero so it visibly settles
        if (Math.abs(tailSwayOffset) < 0.003f) {
            tailSwayOffset = 0.0f;
        }

        // No oscillation velocity retained
        tailSwayVelocity = 0.0f;

        tailSwayOffset = Mth.clamp(tailSwayOffset, -1.5f, 1.5f);
    }

    // Expose to the model for bone rotation
    public float getTailSwayOffset() {
        return tailSwayOffset;
    }

    // Interpolated sway for smooth rendering between ticks
    public float getTailSwayOffset(float partialTick) {
        return Mth.lerp(Mth.clamp(partialTick, 0.0f, 1.0f), tailSwayPrev, tailSwayOffset);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }

    public CarnotaurusVariant getVariant() {
        return CarnotaurusVariant.byId(this.getTypeVariant() & 255);
    }
    public int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(CarnotaurusVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    @Override
    public boolean canMate(Animal other) {
        if (!super.canMate(other)) return false;
        if (!(other instanceof CarnotaurusEntity that)) return false;
        return this.getVariant() != that.getVariant();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        CarnotaurusVariant variant = Util.getRandom(CarnotaurusVariant.values(), this.random);
        this.setVariant(variant);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(DATA_ID_TYPE_VARIANT, pCompound.getInt("Variant"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}