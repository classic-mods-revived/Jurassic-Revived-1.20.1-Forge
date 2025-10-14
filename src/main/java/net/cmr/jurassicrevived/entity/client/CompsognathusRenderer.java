package net.cmr.jurassicrevived.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cmr.jurassicrevived.entity.custom.CompsognathusEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class CompsognathusRenderer extends GeoEntityRenderer<CompsognathusEntity> {

    public CompsognathusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CompsognathusModel());
    }

    @Override
    public void render(CompsognathusEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.scale(0.35F, 0.35F, 0.35F);
        if(entity.isBaby()) {
            poseStack.scale(0.4F, 0.4F, 0.4F);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
