package com.misaka10843.kuayue_neo21.legacy.block.bogey.loco.renderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import com.misaka10843.kuayue_neo21.client.LegacyBogeyRenderer;

public class SS8Renderer extends LegacyBogeyRenderer {

    private static ResourceLocation asBlockModelResource(String path) {
        return com.misaka10843.kuayue_neo21.content.PortRegistration.resource("block/" + path);
    }
    public static final PartialModel
            SS8_FRAME = PartialModel.of(asBlockModelResource("bogey/ss8/ss8_frame")),
            SS8_WHEEL = PartialModel.of(asBlockModelResource("bogey/ss8/ss8_wheel"));

    public static final double dp =2.604d;
    public static final double ad =1.304d;
    @Override
    public void render(
            CompoundTag bogeyData,
            float wheelAngle,
            PoseStack ms,
            int light,
            VertexConsumer vb,
            boolean inContraption) {

        Direction direction =
                bogeyData.contains(LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY)
                        ? NBTHelper.readEnum(
                        bogeyData,
                        LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY,
                        Direction.class)
                        : Direction.NORTH;

        boolean inInstancedContraption = vb == null;

        BogeyModelData frame = getTransform(SS8_FRAME, ms, inInstancedContraption);
        BogeyModelData[] wheels = getTransform(SS8_WHEEL, ms, inInstancedContraption, 2);

        if (direction == Direction.SOUTH || direction == Direction.EAST) {
            if (inContraption) {
                frame.translate(0, 0, 0).render(ms, light, vb);

                for (int side = -1; side < 1; side++) {
                    if (!inInstancedContraption) ms.pushPose();
                    BogeyModelData wheel = wheels[side + 1];
                    wheel.translate(0, 0.905, ((double) side) * dp+ad)
                            .rotateX(wheelAngle)
                            .render(ms, light, vb);
                    if (!inInstancedContraption) ms.popPose();
                }
            } else {
                frame.rotateY(180).translate(0, 0, 0).render(ms, light, vb);

                for (int side = -1; side < 1; side++) {
                    if (!inInstancedContraption) ms.pushPose();
                    BogeyModelData wheel = wheels[side + 1];
                    wheel.translate(0, 0.905, ((double) side) * dp+ad)
                            .rotateX(wheelAngle)
                            .render(ms, light, vb);
                    if (!inInstancedContraption) ms.popPose();
                }
            }
        } else {
            frame.rotateY(180).translate(0, 0, 0).render(ms, light, vb);

            for (int side = -1; side < 1; side++) {
                if (!inInstancedContraption) ms.pushPose();
                BogeyModelData wheel = wheels[side + 1];
                wheel.translate(0, 0.905, ((double) side) * dp+ad)
                        .rotateX(wheelAngle)
                        .render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();
            }
        }
    }

    public static class Backward extends LegacyBogeyRenderer {

        @Override
        public void render(
                CompoundTag bogeyData,
                float wheelAngle,
                PoseStack ms,
                int light,
                VertexConsumer vb,
                boolean inContraption) {

            Direction direction =
                    bogeyData.contains(LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY)
                            ? NBTHelper.readEnum(
                            bogeyData,
                            LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY,
                            Direction.class)
                            : Direction.NORTH;

            wheelAngle = -wheelAngle;
            boolean inInstancedContraption = vb == null;

            BogeyModelData frame = getTransform(SS8_FRAME, ms, inInstancedContraption);
            BogeyModelData[] wheels = getTransform(SS8_WHEEL, ms, inInstancedContraption, 2);

            if (direction == Direction.SOUTH || direction == Direction.EAST) {
                if (inContraption) {
                    frame.rotateY(180).translate(0, 0, 0).render(ms, light, vb);

                    for (int side = -1; side < 1; side++) {
                        if (!inInstancedContraption) ms.pushPose();
                        BogeyModelData wheel = wheels[side + 1];
                        wheel.translate(0, 0.905, ((double) side) * dp+ad)
                                .rotateX(-wheelAngle)
                                .render(ms, light, vb);
                        if (!inInstancedContraption) ms.popPose();
                    }
                } else {
                    frame.translate(0, 0, 0).render(ms, light, vb);

                    for (int side = -1; side < 1; side++) {
                        if (!inInstancedContraption) ms.pushPose();
                        BogeyModelData wheel = wheels[side + 1];
                        wheel.translate(0, 0.905, ((double) side) * dp+ad)
                                .rotateX(wheelAngle)
                                .render(ms, light, vb);
                        if (!inInstancedContraption) ms.popPose();
                    }
                }
            } else {
                frame.translate(0, 0, 0).render(ms, light, vb);

                for (int side = -1; side < 1; side++) {
                    if (!inInstancedContraption) ms.pushPose();
                    BogeyModelData wheel = wheels[side + 1];
                    wheel.translate(0, 0.905, ((double) side) * dp+ad)
                            .rotateX(-wheelAngle)
                            .render(ms, light, vb);
                    if (!inInstancedContraption) ms.popPose();
                }
            }
        }


    }
    public static class Andesite extends SS8Renderer {
        @Override
        public void render(
                CompoundTag bogeyData,
                float wheelAngle,
                PoseStack ms,
                int light,
                VertexConsumer vb,
                boolean inContraption) {
            ms.pushPose();
            ms.scale(1.2F, 1, 1);
            super.render(bogeyData, wheelAngle, ms, light, vb, inContraption);
            ms.popPose();
        }

        public static class Backward extends SS8Renderer.Backward {
            @Override
            public void render(
                    CompoundTag bogeyData,
                    float wheelAngle,
                    PoseStack ms,
                    int light,
                    VertexConsumer vb,
                    boolean inContraption) {
                ms.pushPose();
                ms.scale(1.2F, 1, 1);
                super.render(bogeyData, wheelAngle, ms, light, vb, inContraption);
                ms.popPose();
            }
        }
    }
}
