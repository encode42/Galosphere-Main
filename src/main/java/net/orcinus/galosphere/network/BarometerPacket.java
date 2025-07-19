package net.orcinus.galosphere.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.orcinus.galosphere.Galosphere;

public record BarometerPacket(int time) implements CustomPacketPayload {
    public static final Type<BarometerPacket> TYPE = new Type<>(Galosphere.id("barometer_info"));
    public static final StreamCodec<FriendlyByteBuf, BarometerPacket> STREAM_CODEC = CustomPacketPayload.codec(BarometerPacket::write, BarometerPacket::new);

    private BarometerPacket(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(this.time);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}