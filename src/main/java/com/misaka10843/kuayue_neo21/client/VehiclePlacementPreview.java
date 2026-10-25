package com.misaka10843.kuayue_neo21.client;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.misaka10843.kuayue_neo21.content.VehicleBlockItem;
import com.misaka10843.kuayue_neo21.content.VehiclePlacement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;

@EventBusSubscriber(modid = KuaYueneo21.MODID, value = Dist.CLIENT)
public final class VehiclePlacementPreview {
    private static VehiclePlacement.Placement preview(BlockHitResult hit) {
        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;
        if (player == null || minecraft.level == null || player.isShiftKeyDown() || minecraft.options.hideGui) return null;
        var stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof VehicleBlockItem item)) return null;
        var context = new BlockPlaceContext(player, InteractionHand.MAIN_HAND, stack, hit);
        var placement = item.preview(context);
        return placement != null && !placement.pos().equals(context.getClickedPos()) ? placement : null;
    }

    @SubscribeEvent
    public static void outline(RenderHighlightEvent.Block event) {
        var placement = preview(event.getTarget());
        if (placement == null) return;
        var shape = placement.state().getShape(Minecraft.getInstance().level, placement.pos(), CollisionContext.empty());
        if (shape.isEmpty()) return;
        Vec3 camera = event.getCamera().getPosition();
        var box = shape.bounds().move(placement.pos()).move(camera.scale(-1)).inflate(0.002);
        LevelRenderer.renderLineBox(event.getPoseStack(), event.getMultiBufferSource().getBuffer(RenderType.lines()), box, 0.25f, 0.85f, 1f, 0.9f);
    }

    @SubscribeEvent
    public static void arrow(RenderGuiEvent.Post event) {
        var minecraft = Minecraft.getInstance();
        if (!(minecraft.hitResult instanceof BlockHitResult hit)) return;
        var placement = preview(hit);
        if (placement == null) return;
        var delta = placement.pos().subtract(hit.getBlockPos());
        Direction forward = minecraft.player.getDirection();
        Direction right = forward.getClockWise();
        int horizontal = delta.getX() * right.getStepX() + delta.getZ() * right.getStepZ();
        int vertical = -(delta.getX() * forward.getStepX() + delta.getZ() * forward.getStepZ());
        if (delta.getY() != 0) vertical = -delta.getY();
        int dx = Math.abs(horizontal) >= Math.abs(vertical) ? Integer.signum(horizontal) : 0;
        int dy = dx == 0 ? Integer.signum(vertical) : 0;
        if (dx == 0 && dy == 0) return;
        var graphics = event.getGuiGraphics();
        int x = minecraft.getWindow().getGuiScaledWidth() / 2;
        int y = minecraft.getWindow().getGuiScaledHeight() / 2;
        for (int i = -7; i <= 7; i++) graphics.fill(x + dx * i, y + dy * i, x + dx * i + 2, y + dy * i + 2, 0xFF40D9FF);
        for (int i = 1; i <= 5; i++) {
            for (int side : new int[]{-1, 1}) {
                int px = x + dx * (7 - i) - dy * i * side;
                int py = y + dy * (7 - i) + dx * i * side;
                graphics.fill(px, py, px + 2, py + 2, 0xFF40D9FF);
            }
        }
    }
}
