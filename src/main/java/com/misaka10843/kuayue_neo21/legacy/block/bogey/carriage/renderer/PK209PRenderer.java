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

public class PK209PRenderer extends LegacyBogeyRenderer {

    private static ResourceLocation asBlockModelResource(String path) {
        return com.misaka10843.kuayue_neo21.content.PortRegistration.resource("block/" + path);
    }

    public static final PartialModel PK209P_MAIN = PartialModel.of(asBlockModelResource("bogey/pk209p/pk209p_main"));
    public static final PartialModel PK209P_WHEEL = PartialModel.of(asBlockModelResource("bogey/pk209p/pk209p_wheel"));
    public static final PartialModel PK209P_WHEEL2 = PartialModel.of(asBlockModelResource("bogey/pk209p/pk209p_wheel2"));
    public static final PartialModel PK209P_MOTORWHEEL = PartialModel.of(asBlockModelResource("bogey/pk209p/pk209_motorwheel"));
    public static final PartialModel PK209P_NO_MOTOR = PartialModel.of(asBlockModelResource("bogey/pk209p/pk209_nomotor"));


    @Override
    public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms,
                       int light, VertexConsumer vb, boolean inContraption) {

        Direction direction =
                bogeyData.contains(LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY)
                        ? NBTHelper.readEnum(
                        bogeyData,
                        LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY,
                        Direction.class)
                        : Direction.NORTH;

        boolean inInstancedContraption = vb == null;

        BogeyModelData main = getTransform(PK209P_MAIN, ms, inInstancedContraption);

        BogeyModelData wheel = getTransform(PK209P_WHEEL, ms, inInstancedContraption);

        BogeyModelData wheel2 = getTransform(PK209P_WHEEL2, ms, inInstancedContraption);

        BogeyModelData motorWheel = getTransform(PK209P_MOTORWHEEL, ms, inInstancedContraption);

        if (direction == Direction.SOUTH || direction == Direction.EAST) {
            if (inContraption) {
                main.translate(0, 0.91, 0).render(ms, light, vb);
                wheel.translate(0, 0.8, 1.2).rotateX(wheelAngle).render(ms, light, vb);
                wheel2.translate(0, 0.8, -1.2).rotateX(wheelAngle).render(ms, light, vb);
                motorWheel
                        .translate(1.117, 0.82, 2.165)
                        .rotateX(wheelAngle * 3.256)
                        .render(ms, light, vb);
            } else {
                main.rotateY(180).translate(0, 0.91, 0).render(ms, light, vb);
                wheel.translate(0, 0.8, -1.2)
                        .rotateY(180)
                        .rotateX(-wheelAngle)
                        .render(ms, light, vb);
                wheel2.translate(0, 0.8, 1.2)
                        .rotateY(180)
                        .rotateX(-wheelAngle)
                        .render(ms, light, vb);
                motorWheel
                        .translate(-1.117, 0.82, -2.165)
                        .rotateY(180)
                        .rotateX(-wheelAngle * 3.256)
                        .render(ms, light, vb);
            }
        } else {
            main.translate(0, 0.91, 0).render(ms, light, vb);
            wheel.translate(0, 0.8, 1.2).rotateX(wheelAngle).render(ms, light, vb);
            wheel2.translate(0, 0.8, -1.2).rotateX(wheelAngle).render(ms, light, vb);
            motorWheel
                    .translate(1.117, 0.82, 2.165)
                    .rotateX(wheelAngle * 3.256)
                    .render(ms, light, vb);
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

            boolean forwards = LegacyBogeyRenderer.isForwards(bogeyData, inContraption);

            Direction direction =
                    bogeyData.contains(LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY)
                            ? NBTHelper.readEnum(
                            bogeyData,
                            LegacyBogeyRenderer.BOGEY_ASSEMBLY_DIRECTION_KEY,
                            Direction.class)
                            : Direction.NORTH;

            boolean inInstancedContraption = vb == null;
            
            BogeyModelData main = getTransform(PK209P_MAIN, ms, inInstancedContraption);
            
            BogeyModelData wheel = getTransform(PK209P_WHEEL, ms, inInstancedContraption);
            
            BogeyModelData wheel2 = getTransform(PK209P_WHEEL2, ms, inInstancedContraption);
            
            BogeyModelData motorWheel = getTransform(PK209P_MOTORWHEEL, ms, inInstancedContraption);

            
            if (direction == Direction.SOUTH || direction == Direction.EAST) {
                if (inContraption) {
                    main.rotateY(180).translate(0, 0.91, 0).render(ms, light, vb);
                    wheel.translate(0, 0.8, -1.2)
                            .rotateY(180)
                            .rotateX(-wheelAngle)
                            .render(ms, light, vb);
                    wheel2.translate(0, 0.8, 1.2)
                            .rotateY(180)
                            .rotateX(-wheelAngle)
                            .render(ms, light, vb);
                    motorWheel
                            .translate(-1.117, 0.82, -2.165)
                            .rotateY(180)
                            .rotateX(-wheelAngle * 3.256)
                            .render(ms, light, vb);
                } else {
                    main.translate(0, 0.91, 0).render(ms, light, vb);
                    wheel.translate(0, 0.8, 1.2).rotateX(wheelAngle).render(ms, light, vb);
                    wheel2.translate(0, 0.8, -1.2).rotateX(wheelAngle).render(ms, light, vb);
                    motorWheel
                            .translate(1.117, 0.82, 2.165)
                            .rotateX(wheelAngle * 3.256)
                            .render(ms, light, vb);
                }
            } else {
                main.rotateY(180).translate(0, 0.91, 0).render(ms, light, vb);
                wheel.translate(0, 0.8, -1.2)
                        .rotateY(180)
                        .rotateX(-wheelAngle)
                        .render(ms, light, vb);
                wheel2.translate(0, 0.8, 1.2)
                        .rotateY(180)
                        .rotateX(-wheelAngle)
                        .render(ms, light, vb);
                motorWheel
                        .translate(-1.117, 0.82, -2.165)
                        .rotateY(180)
                        .rotateX(-wheelAngle * 3.256)
                        .render(ms, light, vb);
            }
        }
    }

    public static class NoMotor extends LegacyBogeyRenderer {


        @Override
        public void render(
                CompoundTag bogeyData,
                float wheelAngle,
                PoseStack ms,
                int light,
                VertexConsumer vb,
                boolean inContraption) {

            boolean inInstancedContraption = vb == null;
            
            BogeyModelData main = getTransform(PK209P_NO_MOTOR, ms, inInstancedContraption);
            
            BogeyModelData[] wheel2s = getTransform(PK209P_WHEEL2, ms, inInstancedContraption, 2);

            main.translate(0, 0.91, 0).render(ms, light, vb);

            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption) ms.pushPose();
                BogeyModelData wheel = wheel2s[(side + 1) / 2];
                wheel.translate(0, 0.8, ((double) side) * 1.2d).rotateX(wheelAngle);
                wheel.render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();
            }
        }
    }

    public static class Andesite extends PK209PRenderer {
        @Override
        public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
            ms.pushPose();
            ms.scale(1.2F, 1, 1);
            super.render(bogeyData, wheelAngle, ms, light, vb, inContraption);
            ms.popPose();
        }

        public static class Backward extends PK209PRenderer.Backward {
            @Override
            public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
                ms.pushPose();
                ms.scale(1.2F, 1, 1);
                super.render(bogeyData, wheelAngle, ms, light, vb, inContraption);
                ms.popPose();
            }
        }

        public static class NoMotor extends PK209PRenderer.NoMotor {
            @Override
            public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
                ms.pushPose();
                ms.scale(1.2F, 1, 1);
                super.render(bogeyData, wheelAngle, ms, light, vb, inContraption);
                ms.popPose();
            }
        }
    }
}