package net.cmr.jurassicrevived.entity.client;

import com.google.common.collect.Maps;
import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.entity.custom.DilophosaurusEntity;
import net.cmr.jurassicrevived.entity.variant.DilophosaurusVariant;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

import java.util.Map;

public class DilophosaurusModel extends GeoModel<DilophosaurusEntity> {

    private static final Map<DilophosaurusVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(DilophosaurusVariant.class), map -> {
                map.put(DilophosaurusVariant.MALE, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/entity/dilophosaurus.png"));
                map.put(DilophosaurusVariant.FEMALE, ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "textures/entity/dilophosaurus_female.png"));
            });

    // Model-local "currently applied" offsets; cleared before each entity render
    private float[] appliedYaw = null;
    private float[] appliedRoll = null;

    @Override
    public ResourceLocation getModelResource(DilophosaurusEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "geo/dilophosaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DilophosaurusEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(DilophosaurusEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "animations/dilophosaurus.animation.json");
    }

    @Override
    public void setCustomAnimations(DilophosaurusEntity entity, long id, AnimationState<DilophosaurusEntity> state) {
        super.setCustomAnimations(entity, id, state);

        String[] tailBones = { "TailBASE", "Tail2", "Tail3", "Tail4", "Tail5", "Tail6", "Tail7" };
        int n = tailBones.length;

        if (appliedYaw == null || appliedYaw.length != n) {
            appliedYaw = new float[n];
            appliedRoll = new float[n];
        }

        // 1) Clear previous offsets (from the last entity rendered with this model instance)
        for (int i = 0; i < n; i++) {
            if (appliedYaw[i] == 0.0f && appliedRoll[i] == 0.0f) continue;
            GeoBone bone = (GeoBone) getAnimationProcessor().getBone(tailBones[i]);
            if (bone == null) continue;
            if (appliedYaw[i] != 0.0f) bone.setRotY(bone.getRotY() - appliedYaw[i]);
            if (appliedRoll[i] != 0.0f) bone.setRotZ(bone.getRotZ() - appliedRoll[i]);
            appliedYaw[i] = 0.0f;
            appliedRoll[i] = 0.0f;
        }

        // 2) Interpolated sway for extra smoothness between ticks
        float sway = entity.getTailSwayOffset(state.getPartialTick()); // [-1, 1]

        // Tuning
        float maxYawDeg    = 22.0f;  // increased max sweep
        float swayGain     = 1.35f;  // amplifies overall power
        float rollFraction = 0.40f;  // slightly stronger roll for heft

        float deg2rad = (float)Math.PI / 180f;

        // Direction: positive sway (left turn) -> tail swings right (negative yaw)
        // Flip the sign here if the sway feels inverted
        float baseYaw = sway * maxYawDeg * deg2rad;
        float baseRoll = -baseYaw * rollFraction;

        float[] weights = { 1.00f, 0.78f, 0.58f, 0.42f, 0.30f, 0.22f, 0.16f };

        for (int i = 0; i < n; i++) {
            GeoBone bone = (GeoBone) getAnimationProcessor().getBone(tailBones[i]);
            if (bone == null) continue;

            float w = weights[i];
            float yaw  = baseYaw  * w;
            float roll = baseRoll * w;

            // OVERRIDE animations on Y/Z only: keep the model's predefined X bend intact
            // Do NOT reset rotX here, so the upward bend stays
            bone.setRotY(yaw);
            bone.setRotZ(roll);

            appliedYaw[i] = yaw;
            appliedRoll[i] = roll;
        }
        
        GeoBone head = (GeoBone) getAnimationProcessor().getBone("NeckBase");

        if (head != null) {
            var entityData = state.getData(DataTickets.ENTITY_MODEL_DATA);
            float clampedYawDeg = Mth.clamp(entityData.netHeadYaw(), -30.0f, 30.0f);
            head.setRotY(clampedYawDeg * Mth.DEG_TO_RAD);
        }
    }
}
