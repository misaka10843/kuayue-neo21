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
    }
}
