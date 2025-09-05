package net.jurassicrevived.jurassicrevived.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.jurassicrevived.jurassicrevived.entity.custom.VelociraptorEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class VelociraptorRenderer extends GeoEntityRenderer<VelociraptorEntity> {

    public VelociraptorRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new VelociraptorModel());
    }

    @Override
    public void render(VelociraptorEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.4F, 0.4F, 0.4F);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
