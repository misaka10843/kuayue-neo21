package com.misaka10843.kuayue_neo21.client;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.misaka10843.kuayue_neo21.content.coupling.CouplerHintRequest;
import com.misaka10843.kuayue_neo21.content.coupling.CouplerHintResponse;
import com.misaka10843.kuayue_neo21.content.coupling.CouplerInteraction;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.ContraptionHandler;
import com.simibubi.create.content.contraptions.ContraptionHandlerClient;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = KuaYueneo21.MODID, value = Dist.CLIENT)
public final class CouplerOverlay {
    private record Target(int entityId, BlockPos pos) {}
    private static Target target;
    private static CouplerInteraction.Hint hint;
    private static long receivedAt;
    private static long requestedAt;

    private static boolean active(Minecraft mc) {
        return mc.player != null && mc.level != null && mc.screen == null && !mc.options.hideGui && !mc.player.isSpectator()
                && (AllItems.WRENCH.isIn(mc.player.getMainHandItem()) || AllItems.WRENCH.isIn(mc.player.getOffhandItem()));
    }

    @SubscribeEvent
    public static void tick(ClientTickEvent.Post event) {
        var mc = Minecraft.getInstance();
        if (!active(mc)) { target = null; hint = null; return; }
        var ray = ContraptionHandlerClient.getRayInputs(mc.player);
        var origin = ray.getFirst();
        var end = ray.getSecond();
        var bounds = new AABB(origin, end).inflate(16);
        double nearest = Double.POSITIVE_INFINITY;
        Target found = null;
        for (var reference : ContraptionHandler.loadedContraptions.get(mc.level).values()) {
            var entity = reference.get();
            if (entity == null || entity.getContraption() == null || !entity.getBoundingBox().intersects(bounds)) continue;
            var hit = ContraptionHandlerClient.rayTraceContraption(origin, end, entity);
            if (hit == null) continue;
            double distance = entity.toGlobalVector(hit.getLocation(), 1).distanceToSqr(origin);
            if (distance >= nearest) continue;
            nearest = distance;
            var info = entity.getContraption().getBlocks().get(hit.getBlockPos());
            found = entity instanceof CarriageContraptionEntity && info != null && CouplerInteraction.isCoupler(info.state())
                    ? new Target(entity.getId(), hit.getBlockPos().immutable()) : null;
        }
        boolean changed = !java.util.Objects.equals(target, found);
        if (changed) hint = null;
        target = found;
        if (target == null) return;
        long now = mc.level.getGameTime();
        if (changed || now - requestedAt >= 5 || now < requestedAt) {
            requestedAt = now;
            PacketDistributor.sendToServer(new CouplerHintRequest(target.entityId(), target.pos()));
        }
    }

    public static void accept(CouplerHintResponse response) {
        var mc = Minecraft.getInstance();
        if (!active(mc) || target == null || target.entityId() != response.entityId() || !target.pos().equals(response.pos())) return;
        var values = CouplerInteraction.Hint.values();
        if (response.hint() < 0 || response.hint() >= values.length) return;
        hint = values[response.hint()];
        receivedAt = mc.level.getGameTime();
    }

    @SubscribeEvent
    public static void render(RenderGuiEvent.Post event) {
        var mc = Minecraft.getInstance();
        if (!active(mc) || target == null || hint == null || mc.level.getGameTime() - receivedAt > 10) return;
        var key = switch (hint) {
            case CAN_DIVIDE -> "can_divide";
            case CAN_SELECT -> "can_select";
            case UNSAFE -> "unsafe";
            case FAILED, CANNOT_DIVIDE -> "cannot_divide";
        };
        var graphics = event.getGuiGraphics();
        graphics.renderTooltip(mc.font, Component.translatable("gui.kuayue.coupler." + key),
                graphics.guiWidth() / 2 + 12, graphics.guiHeight() / 2);
    }
}
