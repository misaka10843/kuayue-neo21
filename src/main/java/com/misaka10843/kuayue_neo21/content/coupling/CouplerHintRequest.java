package com.misaka10843.kuayue_neo21.content.coupling;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record CouplerHintRequest(int entityId, BlockPos pos) implements CustomPacketPayload {
    public static final Type<CouplerHintRequest> TYPE = new Type<>(PortRegistration.resource("coupler_hint_request"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CouplerHintRequest> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, CouplerHintRequest::entityId, BlockPos.STREAM_CODEC, CouplerHintRequest::pos, CouplerHintRequest::new);

    @Override public Type<CouplerHintRequest> type() { return TYPE; }
}
