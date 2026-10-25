package com.misaka10843.kuayue_neo21.legacy.block.bogey.loco.renderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import com.misaka10843.kuayue_neo21.client.LegacyBogeyRenderer;

public class JY290Renderer extends LegacyBogeyRenderer {

    private static ResourceLocation asBlockModelResource(String path) {
        return com.misaka10843.kuayue_neo21.content.PortRegistration.resource("block/" + path);
    }

    public static final PartialModel
            JY290_FRAME = PartialModel.of(asBlockModelResource("bogey/jy290/jy290_frame")),
            JY290_WHEEL = PartialModel.of(asBlockModelResource("bogey/jy290/jy290_wheel"));

    private static final double FRAME_TRANS_Y = -0.07;
    private static final double WHEEL_TRANS_Y = 0.775;
    private static final double WHEEL_TRANS_Z = 1.195;

    @Override
    public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {

        Direction direction =
                bogeyData.contains(LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY)
                        ? NBTHelper.readEnum(
                        bogeyData,
                        LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY,
                        Direction.class)
                        : Direction.NORTH;

        boolean inInstancedContraption = vb == null;

        BogeyModelData frame = getTransform(JY290_FRAME, ms, inInstancedContraption);
        BogeyModelData[] wheels = getTransform(JY290_WHEEL, ms, inInstancedContraption, 2);

        if (!inContraption) {
            
            frame.rotateY(180).translate(0, FRAME_TRANS_Y, 0).render(ms, light, vb);

            
            if (!inInstancedContraption) ms.pushPose();
            wheels[0].translate(0, WHEEL_TRANS_Y, -WHEEL_TRANS_Z).rotateY(180).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            if (!inInstancedContraption) ms.pushPose();
            wheels[1].translate(0, WHEEL_TRANS_Y, WHEEL_TRANS_Z).rotateY(180).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            return;
        }

        if (direction == Direction.NORTH || direction == Direction.WEST) {
            
            frame.rotateY(180).translate(0, FRAME_TRANS_Y, 0).render(ms, light, vb);

            
            if (!inInstancedContraption) ms.pushPose();
            wheels[0].translate(0, WHEEL_TRANS_Y, -WHEEL_TRANS_Z).rotateY(180).rotateX(-wheelAngle).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            if (!inInstancedContraption) ms.pushPose();
            wheels[1].translate(0, WHEEL_TRANS_Y, WHEEL_TRANS_Z).rotateY(180).rotateX(-wheelAngle).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            return;
        }

        
        frame.translate(0, FRAME_TRANS_Y, 0).render(ms, light, vb);

        
        if (!inInstancedContraption) ms.pushPose();
        wheels[0].translate(0, WHEEL_TRANS_Y, -WHEEL_TRANS_Z).rotateX(wheelAngle).render(ms, light, vb);
        if (!inInstancedContraption) ms.popPose();

        if (!inInstancedContraption) ms.pushPose();
        wheels[1].translate(0, WHEEL_TRANS_Y, WHEEL_TRANS_Z).rotateX(wheelAngle).render(ms, light, vb);
        if (!inInstancedContraption) ms.popPose();
    }



    public static class Backward extends LegacyBogeyRenderer {

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {

            Direction direction =
                    bogeyData.contains(LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY)
                            ? NBTHelper.readEnum(
                            bogeyData,
                            LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY,
                            Direction.class)
                            : Direction.NORTH;

            boolean inInstancedContraption = vb == null;

            BogeyModelData frame = getTransform(JY290_FRAME, ms, inInstancedContraption);
            BogeyModelData[] wheels = getTransform(JY290_WHEEL, ms, inInstancedContraption, 2);

            
            if (!inContraption) {
                
                frame.translate(0, FRAME_TRANS_Y, 0).render(ms, light, vb);

                
                if (!inInstancedContraption) ms.pushPose();
                wheels[0].translate(0, WHEEL_TRANS_Y, WHEEL_TRANS_Z).render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();

                if (!inInstancedContraption) ms.pushPose();
                wheels[1].translate(0, WHEEL_TRANS_Y, -WHEEL_TRANS_Z).render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();

                return;
            }

            
            if (direction == Direction.NORTH || direction == Direction.WEST) {
                
                frame.translate(0, FRAME_TRANS_Y, 0).render(ms, light, vb);

                
                if (!inInstancedContraption) ms.pushPose();
                wheels[0].translate(0, WHEEL_TRANS_Y, WHEEL_TRANS_Z).rotateX(wheelAngle).render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();

                if (!inInstancedContraption) ms.pushPose();
                wheels[1].translate(0, WHEEL_TRANS_Y, -WHEEL_TRANS_Z).rotateX(wheelAngle).render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();

                return;
            }

            
            
            frame.rotateY(180).translate(0, FRAME_TRANS_Y, 0).render(ms, light, vb);

            
            if (!inInstancedContraption) ms.pushPose();
            wheels[0].translate(0, WHEEL_TRANS_Y, WHEEL_TRANS_Z).rotateX(wheelAngle).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            if (!inInstancedContraption) ms.pushPose();
            wheels[1].translate(0, WHEEL_TRANS_Y, -WHEEL_TRANS_Z).rotateX(wheelAngle).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();
        }


    }
}
