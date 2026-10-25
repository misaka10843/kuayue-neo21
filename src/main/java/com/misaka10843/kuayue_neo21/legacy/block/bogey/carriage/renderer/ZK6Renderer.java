package com.misaka10843.kuayue_neo21.legacy.block.bogey.carriage.renderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import com.misaka10843.kuayue_neo21.client.LegacyBogeyRenderer;

public class ZK6Renderer extends LegacyBogeyRenderer {

    private static ResourceLocation asBlockModelResource(String path) {
        return com.misaka10843.kuayue_neo21.content.PortRegistration.resource("block/" + path);
    }

    public static final PartialModel
            ZK6_FRAME = PartialModel.of(asBlockModelResource("bogey/zk6/zk6_frame")),
            ZK6_WHEEL = PartialModel.of(asBlockModelResource("bogey/zk6/zk6_wheel"));

    @Override
    public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {
        boolean inInstancedContraption = vb == null;

        BogeyModelData frame = getTransform(ZK6_FRAME, ms, inInstancedContraption);
        frame.translate(0, 0.380, 0).render(ms, light, vb);

        BogeyModelData[] wheels =
                getTransform(ZK6_WHEEL, ms, inInstancedContraption, 2);

        if (!inInstancedContraption) ms.pushPose();
        wheels[0].translate(0, 0.745, 0.917d).rotateX(wheelAngle * 1.09).render(ms, light, vb);
        if (!inInstancedContraption) ms.popPose();

        if (!inInstancedContraption) ms.pushPose();
        wheels[1].translate(0, 0.745, -0.917d).rotateX(wheelAngle * 1.09).render(ms, light, vb);
        if (!inInstancedContraption) ms.popPose();
    }


    public static class Andesite extends ZK6Renderer {
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
