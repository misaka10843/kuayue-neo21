package com.misaka10843.kuayue_neo21.client;

import com.misaka10843.kuayue_neo21.content.bogey.VehicleBogeyBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public final class BogeyItemRenderer extends BlockEntityWithoutLevelRenderer {
    public BogeyItemRenderer() { super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()); }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
        if (!(stack.getItem() instanceof BlockItem item) || !(item.getBlock() instanceof VehicleBogeyBlock bogey)) return;
        pose.pushPose();
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(bogey.defaultBlockState(), pose, buffers, light, overlay);
        pose.translate(0.5, 0.5, 0.5);
        bogey.getDefaultStyle().render(bogey.getSize(), 0, pose, buffers, light, overlay, 0, new CompoundTag(), false);
        pose.popPose();
    }
}
