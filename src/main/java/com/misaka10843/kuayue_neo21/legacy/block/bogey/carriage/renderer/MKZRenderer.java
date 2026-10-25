package com.misaka10843.kuayue_neo21.legacy.block.bogey.carriage.renderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.createmod.catnip.data.Iterate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import com.misaka10843.kuayue_neo21.client.LegacyBogeyRenderer;

public class MKZRenderer extends LegacyBogeyRenderer {

    private static ResourceLocation asBlockModelResource(String path) {
        return com.misaka10843.kuayue_neo21.content.PortRegistration.resource("block/" + path);
    }

    public static final PartialModel
            MKZ_FRAME = PartialModel.of(asBlockModelResource("bogey/mkz/mkz_frame")),
            MKZ_WHEEL = PartialModel.of(asBlockModelResource("bogey/mkz/mkz_wheel"));


    @Override
    public void render(CompoundTag bogeyData, float wheelAngle, PoseStack ms, int light, VertexConsumer vb, boolean inContraption) {

        boolean inInstancedContraption = vb == null;

        BogeyModelData frame = getTransform(MKZ_FRAME, ms, inInstancedContraption);
        frame.translate(0, 0.320, 0).render(ms, light, vb);

        BogeyModelData[] wheels =
                getTransform(MKZ_WHEEL, ms, inInstancedContraption, 2);

        for (int side : Iterate.positiveAndNegative) {
            if (!inInstancedContraption) ms.pushPose();
            BogeyModelData wheel = wheels[(side + 1) / 2];
            wheel.translate(0, 0.695, ((double) side) * 0.733d).rotateX(wheelAngle * 1.22);
            wheel.render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();
        }
    }
}
