package com.misaka10843.kuayue_neo21.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.simibubi.create.content.trains.bogey.BogeyRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.nbt.NBTHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Blocks;

public abstract class LegacyBogeyRenderer implements BogeyRenderer {
    public static final String BOGEY_ASSEMBLY_DIRECTION_KEY = "AssemblyDirection";
    private static final ThreadLocal<ModelSink> VISUAL_SINK = new ThreadLocal<>();

    public com.simibubi.create.content.trains.bogey.BogeyStyle.SizeRenderer sizeRenderer() {
        return new com.simibubi.create.content.trains.bogey.BogeyStyle.SizeRenderer(this,
                (context, partialTick, inContraption) -> new LegacyBogeyVisual(context, this, inContraption));
    }

    void renderVisual(CompoundTag data, float wheelAngle, PoseStack pose, boolean inContraption, ModelSink sink) {
        VISUAL_SINK.set(sink);
        pose.pushPose();
        try {
            render(data, wheelAngle, pose, 0, null, inContraption);
        } finally {
            pose.popPose();
            VISUAL_SINK.remove();
        }
    }

    @FunctionalInterface
    interface ModelSink { void accept(PartialModel model, PoseStack pose); }

    public static boolean isForwards(CompoundTag data, boolean inContraption) {
        Direction direction = data.contains(BOGEY_ASSEMBLY_DIRECTION_KEY)
                ? NBTHelper.readEnum(data, BOGEY_ASSEMBLY_DIRECTION_KEY, Direction.class) : Direction.NORTH;
        return direction == Direction.NORTH || direction == Direction.WEST;
    }

    @Override
    public final void render(CompoundTag data, float wheelAngle, float partialTick, PoseStack pose,
                             MultiBufferSource buffers, int light, int overlay, boolean inContraption) {
        pose.pushPose();
        render(data, wheelAngle, pose, light, buffers.getBuffer(RenderType.cutoutMipped()), inContraption);
        pose.popPose();
    }

    public abstract void render(CompoundTag data, float wheelAngle, PoseStack pose, int light, VertexConsumer consumer, boolean inContraption);

    protected BogeyModelData getTransform(PartialModel model, PoseStack pose, boolean instanced) {
        return new BogeyModelData(model);
    }

    protected BogeyModelData[] getTransform(PartialModel model, PoseStack pose, boolean instanced, int count) {
        BogeyModelData[] transforms = new BogeyModelData[count];
        for (int i = 0; i < count; i++) transforms[i] = new BogeyModelData(model);
        return transforms;
    }

    public static final class BogeyModelData {
        private final PartialModel model;
        private final PoseStack transform = new PoseStack();
        private final ModelSink visual = VISUAL_SINK.get();

        private BogeyModelData(PartialModel model) { this.model = model; }
        public BogeyModelData translate(double x, double y, double z) { transform.translate(x, y, z); return this; }
        public BogeyModelData rotateX(double degrees) { transform.mulPose(Axis.XP.rotationDegrees((float) degrees)); return this; }
        public BogeyModelData rotateY(double degrees) { transform.mulPose(Axis.YP.rotationDegrees((float) degrees)); return this; }
        public BogeyModelData rotateZ(double degrees) { transform.mulPose(Axis.ZP.rotationDegrees((float) degrees)); return this; }
        public BogeyModelData scale(float x, float y, float z) { transform.scale(x, y, z); return this; }
        public void render(PoseStack pose, int light, VertexConsumer consumer) {
            pose.pushPose();
            pose.mulPose(transform.last().pose());
            if (visual != null) visual.accept(model, pose);
            else CachedBuffers.partial(model, Blocks.AIR.defaultBlockState()).light(light).renderInto(pose, consumer);
            pose.popPose();
        }
    }
}
