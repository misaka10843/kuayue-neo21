package com.misaka10843.kuayue_neo21.legacy.block.bogey.loco.renderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import com.misaka10843.kuayue_neo21.client.LegacyBogeyRenderer;

public class DF21Renderer extends LegacyBogeyRenderer {

    private static ResourceLocation asBlockModelResource(String path) {
        return com.misaka10843.kuayue_neo21.content.PortRegistration.resource("block/" + path);
    }

    public static final PartialModel
            DF21_FRAME = PartialModel.of(asBlockModelResource("bogey/df21/df21_bogey_temple")),
            DF21_WHEEL = PartialModel.of(asBlockModelResource("bogey/df21/df21_wheel"));


    @Override
    public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {

        boolean forwards = LegacyBogeyRenderer.isForwards(bogeyData, inContraption);

        Direction direction =
                bogeyData.contains(LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY)
                        ? NBTHelper.readEnum(
                        bogeyData,
                        LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY,
                        Direction.class)
                        : Direction.NORTH;

        boolean inInstancedContraption = vb == null;

        BogeyModelData frame = getTransform(DF21_FRAME, ms, inInstancedContraption);
        BogeyModelData[] wheels = getTransform(DF21_WHEEL, ms, inInstancedContraption, 3);

        if (!inContraption) {
            frame.rotateY(180).translate(0, 0.225, 0.17).render(ms, light, vb);

            for (int side = -1; side < 2; side++) {
                if (!inInstancedContraption) ms.pushPose();
                BogeyModelData wheel = wheels[side + 1];
                wheel.translate(0, 0.88, ((double) side) * 1.805d)
                        .rotateX(wheelAngle)
                        .render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();
            }
            return;
        }

        if (direction == Direction.NORTH || direction == Direction.WEST) {
            frame.rotateY(180).translate(0, 0.225, 0.17).render(ms, light, vb);

            for (int side = -1; side < 2; side++) {
                if (!inInstancedContraption) ms.pushPose();
                BogeyModelData wheel = wheels[side + 1];
                wheel.translate(0, 0.88, ((double) side) * 1.805d)
                        .rotateX(wheelAngle)
                        .render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();
            }
            return;
        }

        frame.translate(0, 0.225, 0.17).render(ms, light, vb);

        for (int side = -1; side < 2; side++) {
            if (!inInstancedContraption) ms.pushPose();
            BogeyModelData wheel = wheels[side + 1];
            wheel.translate(0, 0.88, ((double) side) * 1.805d)
                    .rotateX(wheelAngle)
                    .render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();
        }

    }

    public static class Backward extends LegacyBogeyRenderer {


        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            boolean forwards = LegacyBogeyRenderer.isForwards(bogeyData, inContraption);

            Direction direction =
                    bogeyData.contains(LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY)
                            ? NBTHelper.readEnum(
                            bogeyData,
                            LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY,
                            Direction.class)
                            : Direction.NORTH;

            wheelAngle = -wheelAngle;
            boolean inInstancedContraption = vb == null;

            BogeyModelData frame = getTransform(DF21_FRAME, ms, inInstancedContraption);
            BogeyModelData[] wheels = getTransform(DF21_WHEEL, ms, inInstancedContraption, 3);

            if (!inContraption) {
                frame.translate(0, 0.225, 0.17).render(ms, light, vb);
                for (int side = -1; side < 2; side++) {
                    if (!inInstancedContraption) ms.pushPose();
                    BogeyModelData wheel = wheels[side + 1];
                    wheel.translate(0, 0.88, ((double) side) * 1.805d)
                            .rotateX(wheelAngle)
                            .render(ms, light, vb);
                    if (!inInstancedContraption) ms.popPose();
                }
                return;
            }

            if (direction == Direction.NORTH || direction == Direction.WEST) {
                frame.translate(0, 0.225, 0.17).render(ms, light, vb);

                for (int side = -1; side < 2; side++) {
                    if (!inInstancedContraption) ms.pushPose();
                    BogeyModelData wheel = wheels[side + 1];
                    wheel.translate(0, 0.88, ((double) side) * 1.805d)
                            .rotateX(-wheelAngle)
                            .render(ms, light, vb);
                    if (!inInstancedContraption) ms.popPose();
                }
                return;
            }

            frame.rotateY(180).translate(0, 0.225, 0.17).render(ms, light, vb);

            for (int side = -1; side < 2; side++) {
                if (!inInstancedContraption) ms.pushPose();
                BogeyModelData wheel = wheels[side + 1];
                wheel.translate(0, 0.88, ((double) side) * 1.805d)
                        .rotateX(-wheelAngle)
                        .render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();
            }
        }
    }
}
