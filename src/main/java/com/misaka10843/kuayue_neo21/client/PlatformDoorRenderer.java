package com.misaka10843.kuayue_neo21.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.createmod.catnip.data.Couple;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import com.misaka10843.kuayue_neo21.content.infrastructure.PlatformDoorEntity;

public class PlatformDoorRenderer implements BlockEntityRenderer<PlatformDoorEntity> {

    public static float STEP = 0.025f;
    public PlatformDoorRenderer(BlockEntityRendererProvider.Context pContext) {}

    @Override
    public void render(PlatformDoorEntity pBlockEntity,
                       float pPartialTick,
                       PoseStack pose,
                       MultiBufferSource pBufferSource,
                       int pPackedLight, int pPackedOverlay) {

        pPackedLight *= 1;
        
        BlockState blockState = pBlockEntity.getBlockState();
        
        boolean isOpened = pBlockEntity.isOpen();
        
        Couple<PartialModel> doorModels = pBlockEntity.getModels();

        if(doorModels == null) return;

        
        PartialModel left = doorModels.get(true);
        PartialModel right = doorModels.get(false);
        PartialModel frame = pBlockEntity.getFrameModel();

        float f = -blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite().toYRot() - 90;

        SuperByteBuffer leftBuffer = left == null ? null : CachedBuffers.partial(left, blockState).light(pPackedLight);
        SuperByteBuffer rightBuffer = right == null ? null : CachedBuffers.partial(right, blockState).light(pPackedLight);

        pose.pushPose();

        pose.translate(0.5f, -1f, 0.5f);
        
        pose.mulPose(Axis.YP.rotationDegrees(f + 90f));

        
        if (frame != null) {
            
            pose.translate(-0.5f, 1f, -0.5f);
            SuperByteBuffer frameBuffer = CachedBuffers.partial(frame, blockState).light(pPackedLight);
            frameBuffer.renderInto(pose, pBufferSource.getBuffer(RenderType.cutout()));
            pose.translate(0.5f, -1f, 0.5f);
        }

        pose.translate(0, 1, 0);
        
        if (leftBuffer != null)
            leftBuffer.translate(-0.5f, 0, -0.5f);
        if (rightBuffer != null)
            rightBuffer.translate(-0.5f, 0, -0.5f);

        
        if(isOpened && pBlockEntity.counter < 1f) {
            
            pBlockEntity.counter += STEP;
        } else if (!isOpened && pBlockEntity.counter > 0.01f) {
            
            pBlockEntity.counter -= STEP;
        } else if (isOpened) {
            
            pBlockEntity.counter = 1f;
        } else {
            
            pBlockEntity.counter = 0f;
        }
        

        float offset = ((float) Math.cos((pBlockEntity.counter + 1) * Math.PI) + 1f) * .4f;
        
        if (leftBuffer != null)
            leftBuffer.translate(offset, 0, 0);
        if (rightBuffer != null)
            rightBuffer.translate(- offset, 0, 0);

        
        if (leftBuffer != null)
            leftBuffer.renderInto(pose, pBufferSource.getBuffer(RenderType.cutout()));
        if (rightBuffer != null)
            rightBuffer.renderInto(pose, pBufferSource.getBuffer(RenderType.cutout()));

        pose.popPose();
    }

    
    @Override
    public boolean shouldRenderOffScreen(PlatformDoorEntity pBlockEntity) {
        return true;
    }
}
