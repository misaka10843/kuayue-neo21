package com.misaka10843.kuayue_neo21.legacy.block.bogey.loco.renderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import com.misaka10843.kuayue_neo21.client.LegacyBogeyRenderer;

public class SS3Renderer extends LegacyBogeyRenderer {

    private static ResourceLocation asBlockModelResource(String path) {
        return com.misaka10843.kuayue_neo21.content.PortRegistration.resource("block/" + path);
    }
    public static final PartialModel
            SS3_FRAME = PartialModel.of(asBlockModelResource("bogey/ss3/ss3_frame")),
            SS3_WHEEL = PartialModel.of(asBlockModelResource("bogey/ss3/ss3_wheel"));


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
        double[] offsets = {-2.11, 1.96, -0.01};
        BogeyModelData frame = getTransform(SS3_FRAME, ms, inInstancedContraption);
        BogeyModelData[] wheels = getTransform(SS3_WHEEL, ms, inInstancedContraption, 3);

        if (direction == Direction.SOUTH || direction == Direction.EAST) {
            if (inContraption) {
                frame.translate(0, 0.012, 0).render(ms, light, vb);
                for (int side = -1; side < 2; side++) {
                    if (!inInstancedContraption) ms.pushPose();
                    BogeyModelData wheel = wheels[side + 1];
                    int index = side + 1;
                    double zOffset =  offsets[index];
                    wheel.translate(0, 0.88, zOffset)
                            .rotateX(wheelAngle)
                            .render(ms, light, vb);
                    if (!inInstancedContraption) ms.popPose();
                }
            } else {
                frame.rotateY(180).translate(0, 0.012, 0).render(ms, light, vb);

                for (int side = -1; side < 2; side++) {
                    if (!inInstancedContraption) ms.pushPose();
                    BogeyModelData wheel = wheels[side + 1];
                    int index = side + 1;
                    double zOffset =  offsets[index];
                    wheel.translate(0, 0.88, zOffset)
                            .rotateX(wheelAngle)
                            .render(ms, light, vb);
                    if (!inInstancedContraption) ms.popPose();
                }
            }
        } else {
            frame.rotateY(180).translate(0, 0.012, 0).render(ms, light, vb);

            for (int side = -1; side < 2; side++) {
                if (!inInstancedContraption) ms.pushPose();
                BogeyModelData wheel = wheels[side + 1];
                int index = side + 1;
                double zOffset =  offsets[index];
                wheel.translate(0, 0.88, zOffset)
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
            double[] offsets = {-0.01, 1.96, -2.11};

            BogeyModelData frame = getTransform(SS3_FRAME, ms, inInstancedContraption);
            BogeyModelData[] wheels = getTransform(SS3_WHEEL, ms, inInstancedContraption, 3);

            if (direction == Direction.SOUTH || direction == Direction.EAST) {
                if (inContraption) {
                    frame.rotateY(180).translate(0, 0.012, 0).render(ms, light, vb);

                    for (int side = -1; side < 2; side++) {
                        if (!inInstancedContraption) ms.pushPose();
                        BogeyModelData wheel = wheels[side + 1];
                        int index = side + 1;
                        double zOffset =  offsets[index];
                        wheel.translate(0, 0.88, zOffset)
                                .rotateX(wheelAngle)
                                .render(ms, light, vb);
                        if (!inInstancedContraption) ms.popPose();
                    }
                } else {
                    frame.translate(0, 0.012, 0).render(ms, light, vb);

                    for (int side = -1; side < 2; side++) {
                        if (!inInstancedContraption) ms.pushPose();
                        BogeyModelData wheel = wheels[side + 1];
                        int index = side + 1;
                        double zOffset =  offsets[index];
                        wheel.translate(0, 0.88, zOffset)
                                .rotateX(wheelAngle)
                                .render(ms, light, vb);
                        if (!inInstancedContraption) ms.popPose();
                    }
                }
            } else {
                frame.translate(0, 0.012, 0).render(ms, light, vb);

                for (int side = -1; side < 2; side++) {
                    if (!inInstancedContraption) ms.pushPose();
                    BogeyModelData wheel = wheels[side + 1];
                    int index = side + 1;
                    double zOffset =  offsets[index];
                    wheel.translate(0, 0.88, zOffset)
                            .rotateX(wheelAngle)
                            .render(ms, light, vb);
                    if (!inInstancedContraption) ms.popPose();
                }
            }
        }


    }
    public static class Andesite extends SS3Renderer {
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

        public static class Backward extends SS3Renderer.Backward {
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
