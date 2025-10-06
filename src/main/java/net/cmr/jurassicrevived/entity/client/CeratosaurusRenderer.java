package net.cmr.jurassicrevived.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cmr.jurassicrevived.entity.custom.CeratosaurusEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class CeratosaurusRenderer extends GeoEntityRenderer<CeratosaurusEntity> {

    public CeratosaurusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CeratosaurusModel());
    }

    @Override
    public void render(CeratosaurusEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(2.5F, 2.5F, 2.5F);
        if(entity.isBaby()) {
            poseStack.scale(0.4F, 0.4F, 0.4F);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
