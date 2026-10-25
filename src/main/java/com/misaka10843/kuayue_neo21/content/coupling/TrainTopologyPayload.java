package com.misaka10843.kuayue_neo21.content.coupling;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import java.util.UUID;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record TrainTopologyPayload(UUID front, UUID rear, int boundary, int spacing, boolean split) implements CustomPacketPayload {
    public static final Type<TrainTopologyPayload> TYPE = new Type<>(PortRegistration.resource("train_topology"));
    public static final StreamCodec<RegistryFriendlyByteBuf, TrainTopologyPayload> CODEC = StreamCodec.of(
            (buffer, payload) -> {
                buffer.writeUUID(payload.front); buffer.writeUUID(payload.rear);
                buffer.writeVarInt(payload.boundary); buffer.writeVarInt(payload.spacing); buffer.writeBoolean(payload.split);
            }, buffer -> new TrainTopologyPayload(buffer.readUUID(), buffer.readUUID(), buffer.readVarInt(), buffer.readVarInt(), buffer.readBoolean()));

    @Override public Type<TrainTopologyPayload> type() { return TYPE; }
}
