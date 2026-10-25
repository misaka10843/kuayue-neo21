package com.misaka10843.kuayue_neo21.legacy.block.bogey.loco.renderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import com.misaka10843.kuayue_neo21.client.LegacyBogeyRenderer;

public class DFH21Renderer extends LegacyBogeyRenderer {

    private static ResourceLocation asBlockModelResource(String path) {
        return com.misaka10843.kuayue_neo21.content.PortRegistration.resource("block/" + path);
    }

    protected static PartialModel DFH21_MAIN;
    protected static PartialModel DFH21_WHEEL;
    protected static PartialModel DFH21_TRANSMISSION_ROD;

    protected static PartialModel DFH21_MAIN_STANDARD;
    protected static PartialModel DFH21_WHEEL_STANDARD;
    protected static PartialModel DFH21_TRANSMISSION_ROD_STANDARD;

    public static final double dp = 2.4; 
    public static final double ad = 1.2; 

    static {
        DFH21_MAIN = PartialModel.of(asBlockModelResource("bogey/dfh21/dfh21_main"));
        DFH21_WHEEL = PartialModel.of(asBlockModelResource("bogey/dfh21/dfh21_wheel"));
        DFH21_TRANSMISSION_ROD = PartialModel.of(asBlockModelResource("bogey/dfh21/dfh21_transmission_rod"));

        DFH21_MAIN_STANDARD = PartialModel.of(asBlockModelResource("bogey/dfh21/dfh21_main_standard"));
        DFH21_WHEEL_STANDARD = PartialModel.of(asBlockModelResource("bogey/dfh21/dfh21_wheel_standard"));
        DFH21_TRANSMISSION_ROD_STANDARD = PartialModel.of(asBlockModelResource("bogey/dfh21/dfh21_transmission_rod_standard"));
    }


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

        BogeyModelData main = getTransform(DFH21_MAIN, ms, inInstancedContraption);
        BogeyModelData[] wheels = getTransform(DFH21_WHEEL, ms, inInstancedContraption, 2);
        BogeyModelData transmissionRod = getTransform(DFH21_TRANSMISSION_ROD, ms, inInstancedContraption);

        if (direction == Direction.SOUTH || direction == Direction.EAST) {
            if (inContraption) {
                main.translate(0, 0, 0).render(ms, light, vb);

                for (int side = -1; side < 1; side++) {
                    if (!inInstancedContraption) ms.pushPose();
                    BogeyModelData wheel = wheels[side + 1];
                    wheel.translate(0, 0.9, ((double) side) * dp + ad)
                            .rotateX(wheelAngle)
                            .render(ms, light, vb);
                    if (!inInstancedContraption) ms.popPose();
                }

                transmissionRod
                        .translate(0,0.85, 0)
                        .rotateZ(wheelAngle * 3.256)
                        .render(ms, light, vb);
            } else {
                main.rotateY(180).translate(0, 0, 0).render(ms, light, vb);

                for (int side = -1; side < 1; side++) {
                    if (!inInstancedContraption) ms.pushPose();
                    BogeyModelData wheel = wheels[side + 1];
                    wheel.translate(0, 0.9, ((double) side) * dp + ad)
                            .rotateY(180)
                            .rotateX(-wheelAngle)
                            .render(ms, light, vb);
                    if (!inInstancedContraption) ms.popPose();
                }

                transmissionRod
                        .translate(0,0.85, 0)
                        .rotateY(180)
                        .rotateZ(-wheelAngle * 3.256)
                        .render(ms, light, vb);
            }
        } else {
            main.rotateY(180).translate(0, 0, 0).render(ms, light, vb);

            for (int side = -1; side < 1; side++) {
                if (!inInstancedContraption) ms.pushPose();
                BogeyModelData wheel = wheels[side + 1];
                wheel.translate(0, 0.9, ((double) side) * dp + ad)
                        .rotateX(wheelAngle)
                        .render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();
            }

            transmissionRod
                    .translate(0,0.85, 0)
                    .rotateZ(wheelAngle * 3.256)
                    .render(ms, light, vb);
        }
    }

    public static class Backward extends DFH21Renderer {


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
            
            BogeyModelData main = getTransform(DFH21_MAIN, ms, inInstancedContraption);
            
            BogeyModelData[] wheels = getTransform(DFH21_WHEEL, ms, inInstancedContraption, 2);
            
            BogeyModelData transmissionRod = getTransform(DFH21_TRANSMISSION_ROD, ms, inInstancedContraption);

            
            if (direction == Direction.SOUTH || direction == Direction.EAST) {
                if (inContraption) {
                    main.rotateY(180).translate(0, 0, 0).render(ms, light, vb);

                    for (int side = -1; side < 1; side++) {
                        if (!inInstancedContraption) ms.pushPose();
                        BogeyModelData wheel = wheels[side + 1];
                        wheel.translate(0, 0.9, ((double) side) * dp + ad)
                                .rotateY(180)
                                .rotateX(-wheelAngle)
                                .render(ms, light, vb);
                        if (!inInstancedContraption) ms.popPose();
                    }

                    transmissionRod
                            .translate(0,0.85, 0)
                            .rotateY(180)
                            .rotateZ(-wheelAngle * 3.256)
                            .render(ms, light, vb);
                } else {
                    main.translate(0, 0, 0).render(ms, light, vb);

                    for (int side = -1; side < 1; side++) {
                        if (!inInstancedContraption) ms.pushPose();
                        BogeyModelData wheel = wheels[side + 1];
                        wheel.translate(0, 0.9, ((double) side) * dp + ad)
                                .rotateX(wheelAngle)
                                .render(ms, light, vb);
                        if (!inInstancedContraption) ms.popPose();
                    }

                    transmissionRod
                            .translate(0,0.85, 0)
                            .rotateZ(wheelAngle * 3.256)
                            .render(ms, light, vb);
                }
            } else {
                main.translate(0, 0, 0).render(ms, light, vb);

                for (int side = -1; side < 1; side++) {
                    if (!inInstancedContraption) ms.pushPose();
                    BogeyModelData wheel = wheels[side + 1];
                    wheel.translate(0, 0.9, ((double) side) * dp + ad)
                            .rotateY(180)
                            .rotateX(-wheelAngle)
                            .render(ms, light, vb);
                    if (!inInstancedContraption) ms.popPose();
                }

                transmissionRod
                        .translate(0,0.85, 0)
                        .rotateY(180)
                        .rotateZ(-wheelAngle * 3.256)
                        .render(ms, light, vb);
            }
        }
    }

    public static class Standard extends DFH21Renderer {


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

            BogeyModelData main = getTransform(DFH21_MAIN_STANDARD, ms, inInstancedContraption);
            BogeyModelData[] wheels = getTransform(DFH21_WHEEL_STANDARD, ms, inInstancedContraption, 2);
            BogeyModelData transmissionRod = getTransform(DFH21_TRANSMISSION_ROD_STANDARD, ms, inInstancedContraption);

            if (direction == Direction.SOUTH || direction == Direction.EAST) {
                if (inContraption) {
                    main.translate(0, 0, 0).render(ms, light, vb);

                    for (int side = -1; side < 1; side++) {
                        if (!inInstancedContraption) ms.pushPose();
                        BogeyModelData wheel = wheels[side + 1];
                        wheel.translate(0, 0.875, ((double) side) * dp + ad)
                                .rotateX(wheelAngle)
                                .render(ms, light, vb);
                        if (!inInstancedContraption) ms.popPose();
                    }

                    transmissionRod
                            .translate(0,0.85, 0)
                            .rotateZ(wheelAngle * 3.256)
                            .render(ms, light, vb);
                } else {
                    main.rotateY(180).translate(0, 0, 0).render(ms, light, vb);

                    for (int side = -1; side < 1; side++) {
                        if (!inInstancedContraption) ms.pushPose();
                        BogeyModelData wheel = wheels[side + 1];
                        wheel.translate(0, 0.875, ((double) side) * dp + ad)
                                .rotateY(180)
                                .rotateX(-wheelAngle)
                                .render(ms, light, vb);
                        if (!inInstancedContraption) ms.popPose();
                    }

                    transmissionRod
                            .translate(0,0.85, 0)
                            .rotateY(180)
                            .rotateZ(-wheelAngle * 3.256)
                            .render(ms, light, vb);
                }
            } else {
                main.translate(0, 0, 0).render(ms, light, vb);

                for (int side = -1; side < 1; side++) {
                    if (!inInstancedContraption) ms.pushPose();
                    BogeyModelData wheel = wheels[side + 1];
                    wheel.translate(0, 0.875, ((double) side) * dp + ad)
                            .rotateX(wheelAngle)
                            .render(ms, light, vb);
                    if (!inInstancedContraption) ms.popPose();
                }

                transmissionRod
                        .translate(0,0.85, 0)
                        .rotateZ(wheelAngle * 3.256)
                        .render(ms, light, vb);
            }
        }

        public static class Backward extends DFH21Renderer.Backward {


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
                
                BogeyModelData main = getTransform(DFH21_MAIN_STANDARD, ms, inInstancedContraption);
                
                BogeyModelData[] wheels = getTransform(DFH21_WHEEL_STANDARD, ms, inInstancedContraption, 2);
                
                BogeyModelData transmissionRod = getTransform(DFH21_TRANSMISSION_ROD_STANDARD, ms, inInstancedContraption);

                
                if (direction == Direction.SOUTH || direction == Direction.EAST) {
                    if (inContraption) {
                        main.rotateY(180).translate(0, 0, 0).render(ms, light, vb);

                        for (int side = -1; side < 1; side++) {
                            if (!inInstancedContraption) ms.pushPose();
                            BogeyModelData wheel = wheels[side + 1];
                            wheel.translate(0, 0.875, ((double) side) * dp + ad)
                                    .rotateY(180)
                                    .rotateX(-wheelAngle)
                                    .render(ms, light, vb);
                            if (!inInstancedContraption) ms.popPose();
                        }

                        transmissionRod
                                .translate(0,0.85, 0)
                                .rotateY(180)
                                .rotateZ(-wheelAngle * 3.256)
                                .render(ms, light, vb);
                    } else {
                        main.translate(0, 0, 0).render(ms, light, vb);

                        for (int side = -1; side < 1; side++) {
                            if (!inInstancedContraption) ms.pushPose();
                            BogeyModelData wheel = wheels[side + 1];
                            wheel.translate(0, 0.875, ((double) side) * dp + ad)
                                    .rotateX(wheelAngle)
                                    .render(ms, light, vb);
                            if (!inInstancedContraption) ms.popPose();
                        }

                        transmissionRod
                                .translate(0,0.85, 0)
                                .rotateZ(wheelAngle * 3.256)
                                .render(ms, light, vb);
                    }
                } else {
                    main.rotateY(0).translate(0, 0, 0).render(ms, light, vb);

                    for (int side = -1; side < 1; side++) {
                        if (!inInstancedContraption) ms.pushPose();
                        BogeyModelData wheel = wheels[side + 1];
                        wheel.translate(0, 0.875, ((double) side) * dp + ad)
                                .rotateY(180)
                                .rotateX(-wheelAngle)
                                .render(ms, light, vb);
                        if (!inInstancedContraption) ms.popPose();
                    }

                    transmissionRod
                            .translate(0,0.85, 0)
                            .rotateY(180)
                            .rotateZ(-wheelAngle * 3.256)
                            .render(ms, light, vb);
                }
            }
        }
    }

    public static class Andesite extends DFH21Renderer.Standard {
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

        public static class Backward extends DFH21Renderer.Standard.Backward {
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
