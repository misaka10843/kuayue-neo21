package com.misaka10843.kuayue_neo21.content.coupling;

import com.misaka10843.kuayue_neo21.content.PortRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record CouplerHintResponse(int entityId, BlockPos pos, int hint) implements CustomPacketPayload {
    public static final Type<CouplerHintResponse> TYPE = new Type<>(PortRegistration.resource("coupler_hint_response"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CouplerHintResponse> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, CouplerHintResponse::entityId, BlockPos.STREAM_CODEC, CouplerHintResponse::pos,
            ByteBufCodecs.INT, CouplerHintResponse::hint, CouplerHintResponse::new);

    @Override public Type<CouplerHintResponse> type() { return TYPE; }
}
