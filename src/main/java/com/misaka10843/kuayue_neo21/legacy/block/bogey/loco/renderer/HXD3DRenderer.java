package com.misaka10843.kuayue_neo21.legacy.block.bogey.loco.renderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import com.misaka10843.kuayue_neo21.client.LegacyBogeyRenderer;

public class HXD3DRenderer extends LegacyBogeyRenderer {

    private static ResourceLocation asBlockModelResource(String path) {
        return com.misaka10843.kuayue_neo21.content.PortRegistration.resource("block/" + path);
    }

    public static final PartialModel
            HXD3D_FRAME = PartialModel.of(asBlockModelResource("bogey/hxd3d/hxd3d_frame")),
            HXD3D_WHEEL = PartialModel.of(asBlockModelResource("bogey/hxd3d/hxd3d_wheel"));

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

        BogeyModelData frame = getTransform(HXD3D_FRAME, ms, inInstancedContraption);
        BogeyModelData[] wheels = getTransform(HXD3D_WHEEL, ms, inInstancedContraption, 3);

        if (!inContraption) {
            
            frame.rotateY(180).translate(0, 0.012, 0).render(ms, light, vb);

            
            if (!inInstancedContraption) ms.pushPose();
            wheels[0].translate(0, 0.905, -0.23).rotateY(180).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            if (!inInstancedContraption) ms.pushPose();
            wheels[1].translate(0, 0.905, 1.69).rotateY(180).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            if (!inInstancedContraption) ms.pushPose();
            wheels[2].translate(0, 0.905, -2.365).rotateY(180).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            return;
        }

        if (direction == Direction.NORTH || direction == Direction.WEST) {
            
            frame.rotateY(180).translate(0, 0.012, 0).render(ms, light, vb);

            
            if (!inInstancedContraption) ms.pushPose();
            wheels[0].translate(0, 0.905, -0.23).rotateY(180).rotateX(-wheelAngle).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            if (!inInstancedContraption) ms.pushPose();
            wheels[1].translate(0, 0.905, 1.69).rotateY(180).rotateX(-wheelAngle).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            if (!inInstancedContraption) ms.pushPose();
            wheels[2].translate(0, 0.905, -2.365).rotateY(180).rotateX(-wheelAngle).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            return;
        }

        
        frame.translate(0, 0.012, 0).render(ms, light, vb);

        
        if (!inInstancedContraption) ms.pushPose();
        wheels[0].translate(0, 0.905, 0.23).rotateX(wheelAngle).render(ms, light, vb);
        if (!inInstancedContraption) ms.popPose();

        if (!inInstancedContraption) ms.pushPose();
        wheels[1].translate(0, 0.905, 2.365).rotateX(wheelAngle).render(ms, light, vb);
        if (!inInstancedContraption) ms.popPose();

        if (!inInstancedContraption) ms.pushPose();
        wheels[2].translate(0, 0.905, -1.69).rotateX(wheelAngle).render(ms, light, vb);
        if (!inInstancedContraption) ms.popPose();
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

            BogeyModelData frame = getTransform(HXD3D_FRAME, ms, inInstancedContraption);
            BogeyModelData[] wheels = getTransform(HXD3D_WHEEL, ms, inInstancedContraption, 3);

            
            if (!inContraption) {
                
                frame.translate(0, 0.012, 0).render(ms, light, vb);

                
                if (!inInstancedContraption) ms.pushPose();
                wheels[0].translate(0, 0.905, 0.23).render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();

                if (!inInstancedContraption) ms.pushPose();
                wheels[1].translate(0, 0.905, -1.69).render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();

                if (!inInstancedContraption) ms.pushPose();
                wheels[2].translate(0, 0.905, 2.365).render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();
                return;
            }

            
            if (direction == Direction.NORTH || direction == Direction.WEST) {
                
                frame.translate(0, 0.012, 0).render(ms, light, vb);

                
                if (!inInstancedContraption) ms.pushPose();
                wheels[0].translate(0, 0.905, 0.23).rotateX(-wheelAngle).render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();

                if (!inInstancedContraption) ms.pushPose();
                wheels[1].translate(0, 0.905, -1.69).rotateX(-wheelAngle).render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();

                if (!inInstancedContraption) ms.pushPose();
                wheels[2].translate(0, 0.905, 2.365).rotateX(-wheelAngle).render(ms, light, vb);
                if (!inInstancedContraption) ms.popPose();

                return;
            }

            
            
            frame.rotateY(180).translate(0, 0.012, 0).render(ms, light, vb);

            
            if (!inInstancedContraption) ms.pushPose();
            wheels[0].translate(0, 0.905, -0.23).rotateX(-wheelAngle).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            if (!inInstancedContraption) ms.pushPose();
            wheels[1].translate(0, 0.905, -2.365).rotateX(-wheelAngle).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();

            if (!inInstancedContraption) ms.pushPose();
            wheels[2].translate(0, 0.905, 1.685).rotateX(-wheelAngle).render(ms, light, vb);
            if (!inInstancedContraption) ms.popPose();
        }


    }
    public static class Andesite extends HXD3DRenderer {
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

        public static class Backward extends HXD3DRenderer.Backward {
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
