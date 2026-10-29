package com.misaka10843.kuayue_neo21.content.coupling;

import com.misaka10843.kuayue_neo21.KuaYueneo21;
import com.misaka10843.kuayue_neo21.client.ClientTrainCoupling;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = KuaYueneo21.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class CouplingNetwork {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar("1").playToClient(TrainTopologyPayload.TYPE, TrainTopologyPayload.CODEC,
                (payload, context) -> context.enqueueWork(() -> ClientTrainCoupling.apply(payload)));
        event.registrar("1").playToClient(TrainImpactPayload.TYPE, TrainImpactPayload.CODEC,
                (payload, context) -> context.enqueueWork(() -> ClientTrainCoupling.impact(payload)));
        event.registrar("1").playToClient(CouplerHintResponse.TYPE, CouplerHintResponse.CODEC,
                (payload, context) -> context.enqueueWork(() -> com.misaka10843.kuayue_neo21.client.CouplerOverlay.accept(payload)));
        event.registrar("1").playToServer(CouplerHintRequest.TYPE, CouplerHintRequest.CODEC,
                (payload, context) -> context.enqueueWork(() -> {
                    var player = context.player();
                    if (!com.simibubi.create.AllItems.WRENCH.isIn(player.getMainHandItem())
                            && !com.simibubi.create.AllItems.WRENCH.isIn(player.getOffhandItem())) return;
                    var entity = player.level().getEntity(payload.entityId());
                    if (!(entity instanceof com.simibubi.create.content.trains.entity.CarriageContraptionEntity carriage)
                            || carriage.getContraption() == null) return;
                    var point = carriage.toGlobalVector(net.minecraft.world.phys.Vec3.atCenterOf(payload.pos()), 1);
                    if (player.getEyePosition().distanceToSqr(point) > Math.pow(player.blockInteractionRange() + 2, 2)) return;
                    context.reply(new CouplerHintResponse(payload.entityId(), payload.pos(),
                            CouplerInteraction.hint(player, carriage, payload.pos()).ordinal()));
                }));
    }
}
