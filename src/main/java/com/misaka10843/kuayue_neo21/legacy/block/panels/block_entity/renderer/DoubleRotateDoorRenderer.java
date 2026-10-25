package com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity.renderer;

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
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import com.misaka10843.kuayue_neo21.legacy.block.panels.block_entity.DoubleRotateDoorEntity;

public class DoubleRotateDoorRenderer implements BlockEntityRenderer<DoubleRotateDoorEntity> {

    public static float STEP = 0.025f;

    public DoubleRotateDoorRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(DoubleRotateDoorEntity pBlockEntity, float pPartialTick,
                       PoseStack pose, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

        pPackedLight *= 1;
        BlockState blockState = pBlockEntity.getBlockState();
        
        boolean isOpened = pBlockEntity.isOpen();
        
        DoorHingeSide hinge = pBlockEntity.getHinge();
        
        Direction facing = blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
        
        Couple<PartialModel> doorModels = pBlockEntity.getModels();

        if(doorModels == null) return;

        
        PartialModel left = doorModels.get(true);
        PartialModel right = doorModels.get(false);
        PartialModel frame = pBlockEntity.getFrameModel();

        float f = -facing.toYRot() - 90;

        SuperByteBuffer leftBuffer = left == null ? null : CachedBuffers.partial(left, blockState).light(pPackedLight);
        SuperByteBuffer rightBuffer = right == null ? null : CachedBuffers.partial(right, blockState).light(pPackedLight);

        pose.pushPose();

        pose.translate(0.5f, 0, 0.5f);
        
        if (hinge == DoorHingeSide.LEFT) {
            if (facing == Direction.NORTH)
                pose.translate(-1f, 0, 0);
            if (facing == Direction.SOUTH)
                pose.translate(1f, 0, 0);
            if (facing == Direction.WEST)
                pose.translate(0, 0, 1f);
            if (facing == Direction.EAST)
                pose.translate(0, 0, -1f);
        }
        
        pose.mulPose(Axis.YP.rotationDegrees(f + 90f));

        
        if (frame != null) {
            
            pose.translate(-0.5f, 1f, -0.5f);
            SuperByteBuffer frameBuffer = CachedBuffers.partial(frame, blockState).light(pPackedLight);
            frameBuffer.renderInto(pose, pBufferSource.getBuffer(RenderType.cutout()));
            pose.translate(0.5f, -1f, 0.5f);
        }

        pose.translate(0, 1, 0);
        
        if (leftBuffer != null)
            leftBuffer.translate(0.28125f, 0, 0.46875f);
        if (rightBuffer != null)
            rightBuffer.translate(-1.28125f, 0, 0.46875f);

        if(isOpened && pBlockEntity.counter < 1) {
            pBlockEntity.counter += STEP;
        } else if (!isOpened && pBlockEntity.counter > 0) {
            pBlockEntity.counter -= STEP;
        } else if (isOpened) {
            pBlockEntity.counter = 1f;
        } else {
            pBlockEntity.counter = 0f;
        }

        float rotation = (float) (Math.cos((pBlockEntity.counter + 1) * Math.PI) + 1) * 50f;
        if (leftBuffer != null)
            leftBuffer.rotateYDegrees(-rotation);
        if (rightBuffer != null)
            rightBuffer.rotateYDegrees(rotation);

        
        if (leftBuffer != null)
            leftBuffer.renderInto(pose, pBufferSource.getBuffer(RenderType.cutout()));
        if (rightBuffer != null)
            rightBuffer.renderInto(pose, pBufferSource.getBuffer(RenderType.cutout()));

        pose.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(DoubleRotateDoorEntity pBlockEntity) {
        return true;
    }
}
