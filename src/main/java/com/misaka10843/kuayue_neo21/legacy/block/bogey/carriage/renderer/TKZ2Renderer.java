package com.misaka10843.kuayue_neo21.legacy.block.bogey.carriage.renderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import com.misaka10843.kuayue_neo21.client.LegacyBogeyRenderer;

public class TKZ2Renderer extends LegacyBogeyRenderer {

    private static ResourceLocation asBlockModelResource(String path) {
        return com.misaka10843.kuayue_neo21.content.PortRegistration.resource("block/" + path);
    }

    public static final PartialModel
            TKZ2_FRAME = PartialModel.of(asBlockModelResource("bogey/tkz2/tkz2_frame")),
            TKZ2_WHEEL = PartialModel.of(asBlockModelResource("bogey/tkz2/tkz2_wheel")),
            TKZ2_MOTOR_FRAME = PartialModel.of(asBlockModelResource("bogey/tkz2/tkz2_motor_frame")),
            TKZ2_MOTOR_WHEEL = PartialModel.of(asBlockModelResource("bogey/tkz2/tkz2_motor_wheel")),
            TKZ2_MOTOR_PONY_WHEEL = PartialModel.of(asBlockModelResource("bogey/tkz2/tkz2_motor_pony_wheel"));

    @Override
    public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {

        light *= 1.1;
        boolean inInstancedContraption = vb == null;

        BogeyModelData frame = getTransform(TKZ2_FRAME, ms, inInstancedContraption);
        frame.translate(0, 0.195, 0).render(ms, light, vb);

        BogeyModelData[] wheels =
                getTransform(TKZ2_WHEEL, ms, inInstancedContraption, 2);

        for (int side : Iterate.positiveAndNegative) {
            if (!inInstancedContraption) ms.pushPose();
            BogeyModelData wheel = wheels[(side + 1) / 2];
            wheel.translate(0, 0.695, ((double) side) * 0.97d).rotateX(wheelAngle * 1.22);
            wheel.render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();
        }
    }



    public static class Motor extends LegacyBogeyRenderer {

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {

            light *= 1.1;
            Direction direction =
                    bogeyData.contains(LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY)
                            ? NBTHelper.readEnum(
                            bogeyData,
                            LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY,
                            Direction.class)
                            : Direction.NORTH;

            boolean inInstancedContraption = vb == null;

            BogeyModelData frame = getTransform(TKZ2_MOTOR_FRAME, ms, inInstancedContraption);
            BogeyModelData wheel = getTransform(TKZ2_WHEEL, ms, inInstancedContraption);
            BogeyModelData motorWheel = getTransform(TKZ2_MOTOR_WHEEL, ms, inInstancedContraption);
            BogeyModelData motorPonyWheel = getTransform(TKZ2_MOTOR_PONY_WHEEL, ms, inInstancedContraption);

            if (!inContraption) {
                frame.translate(0, 0.129, 0).render(ms, light, vb);
                wheel.translate(0, 0.695, 0.9761).rotateX(wheelAngle * 1.22).render(ms, light, vb);
                motorWheel.translate(0, 0.695, -0.9761).rotateX(wheelAngle * 1.22).render(ms, light, vb);
                motorPonyWheel.translate(0, 0.7235, -1.94).rotateX(wheelAngle * 3.256).render(ms, light, vb);
                return;
            }

            if (direction == Direction.NORTH || direction == Direction.WEST) {
                frame.translate(0, 0.129, 0).render(ms, light, vb);
                wheel.translate(0, 0.695, 0.9761).rotateX(wheelAngle * 1.22).render(ms, light, vb);
                motorWheel.translate(0, 0.695, -0.9761).rotateX(wheelAngle * 1.22).render(ms, light, vb);
                motorPonyWheel.translate(0, 0.7235, -1.94).rotateX(wheelAngle * 3.256).render(ms, light, vb);
                return;
            }

            frame.rotateY(180).translate(0, 0.129, 0).render(ms, light, vb);
            wheel.rotateY(180).translate(0, 0.695, 0.9761).rotateX(-wheelAngle * 1.22).render(ms, light, vb);
            motorWheel.rotateY(180).translate(0, 0.695, -0.9761).rotateX(-wheelAngle * 1.22).render(ms, light, vb);
            motorPonyWheel.rotateY(180).translate(0, 0.7235, -1.94).rotateX(-wheelAngle * 3.256).render(ms, light, vb);
        }


    }

    public static class MotorBackward extends LegacyBogeyRenderer {

        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            light *= 1.1;
            Direction direction =
                    bogeyData.contains(LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY)
                            ? NBTHelper.readEnum(
                            bogeyData,
                            LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY,
                            Direction.class)
                            : Direction.NORTH;

            boolean inInstancedContraption = vb == null;

            BogeyModelData frame = getTransform(TKZ2_MOTOR_FRAME, ms, inInstancedContraption);
            BogeyModelData wheel = getTransform(TKZ2_WHEEL, ms, inInstancedContraption);
            BogeyModelData motorWheel = getTransform(TKZ2_MOTOR_WHEEL, ms, inInstancedContraption);
            BogeyModelData motorPonyWheel = getTransform(TKZ2_MOTOR_PONY_WHEEL, ms, inInstancedContraption);

            if (!inContraption) {
                frame.rotateY(180).translate(0, 0.129, 0).render(ms, light, vb);
                wheel.rotateY(180).translate(0, 0.695, 0.9761).rotateX(wheelAngle * 1.22).render(ms, light, vb);
                motorWheel.rotateY(180).translate(0, 0.695, -0.9761).rotateX(wheelAngle * 1.22).render(ms, light, vb);
                motorPonyWheel.rotateY(180).translate(0, 0.7235, -1.94).rotateX(wheelAngle * 3.256).render(ms, light, vb);
                return;
            }

            if (direction == Direction.NORTH || direction == Direction.WEST) {
                frame.rotateY(180).translate(0, 0.129, 0).render(ms, light, vb);
                wheel.rotateY(180).translate(0, 0.695, 0.9761).rotateX(-wheelAngle * 1.22).render(ms, light, vb);
                motorWheel.rotateY(180).translate(0, 0.695, -0.9761).rotateX(-wheelAngle * 1.22).render(ms, light, vb);
                motorPonyWheel.rotateY(180).translate(0, 0.7235, -1.94).rotateX(-wheelAngle * 3.256).render(ms, light, vb);
                return;
            }

            frame.translate(0, 0.129, 0).render(ms, light, vb);
            wheel.translate(0, 0.695, 0.9761).rotateX(wheelAngle * 1.22).render(ms, light, vb);
            motorWheel.translate(0, 0.695, -0.9761).rotateX(wheelAngle * 1.22).render(ms, light, vb);
            motorPonyWheel.translate(0, 0.7235, -1.94).rotateX(wheelAngle * 3.256).render(ms, light, vb);
        }


    }
}
