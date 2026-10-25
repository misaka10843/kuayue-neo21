package com.misaka10843.kuayue_neo21.content.coupling;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import java.util.UUID;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record TrainImpactPayload(UUID first, UUID second, double firstSpeed, double secondSpeed) implements CustomPacketPayload {
    public static final Type<TrainImpactPayload> TYPE = new Type<>(PortRegistration.resource("train_impact"));
    public static final StreamCodec<RegistryFriendlyByteBuf, TrainImpactPayload> CODEC = StreamCodec.of(
            (buffer, payload) -> {
                buffer.writeUUID(payload.first); buffer.writeUUID(payload.second);
                buffer.writeDouble(payload.firstSpeed); buffer.writeDouble(payload.secondSpeed);
            }, buffer -> new TrainImpactPayload(buffer.readUUID(), buffer.readUUID(), buffer.readDouble(), buffer.readDouble()));

    @Override public Type<TrainImpactPayload> type() { return TYPE; }
}
