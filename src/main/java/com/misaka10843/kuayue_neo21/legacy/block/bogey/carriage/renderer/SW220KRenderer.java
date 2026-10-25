package com.misaka10843.kuayue_neo21.legacy.block.bogey.carriage.renderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.createmod.catnip.data.Iterate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import com.misaka10843.kuayue_neo21.client.LegacyBogeyRenderer;

public class SW220KRenderer extends LegacyBogeyRenderer {

    private static ResourceLocation asBlockModelResource(String path) {
        return com.misaka10843.kuayue_neo21.content.PortRegistration.resource("block/" + path);
    }

    public static PartialModel SW220K_FRAME =
            PartialModel.of(asBlockModelResource("bogey/sw220k/sw220k_frame"));
    public static PartialModel SW220K_WHEEL =
            PartialModel.of(asBlockModelResource("bogey/sw220k/sw220k_wheel"));

    public static double SW220K_FRAME_TRANS_Y = 0.925F;


    @Override
    public void render(
            CompoundTag bogeyData,
            float wheelAngle,
            PoseStack ms,
            int light,
            VertexConsumer vb,
            boolean inContraption) {

        boolean inInstancedContraption = vb == null;

        BogeyModelData frame = getTransform(SW220K_FRAME, ms, inInstancedContraption);
        BogeyModelData[] wheels = getTransform(SW220K_WHEEL, ms, inInstancedContraption, 2);

        frame.translate(0, SW220K_FRAME_TRANS_Y, 0).render(ms, light, vb);

        for (int side : Iterate.positiveAndNegative) {
            if (!inInstancedContraption) ms.pushPose();
            BogeyModelData wheel = wheels[(side + 1) / 2];
            wheel.translate(0, 0.805, ((double) side) * 1.25d).rotateX(wheelAngle);
            wheel.render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();
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

            boolean inInstancedContraption = vb == null;

            BogeyModelData frame = getTransform(SW220K_FRAME, ms, inInstancedContraption);
            BogeyModelData[] wheels = getTransform(SW220K_WHEEL, ms, inInstancedContraption, 2);

            
            frame.translate(0, SW220K_FRAME_TRANS_Y, 0).render(ms, light, vb);
            
            for (int side : Iterate.positiveAndNegative) {
                if (!inInstancedContraption) ms.pushPose();
                BogeyModelData wheel = wheels[(side + 1) / 2];
                wheel.translate(0, 0.805, ((double) side) * 1.25d).rotateX(wheelAngle);
                wheel.render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();
            }
        }
    }
    public static class Andesite extends SW220KRenderer {
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

        public static class Backward extends SW220KRenderer.Backward {
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
