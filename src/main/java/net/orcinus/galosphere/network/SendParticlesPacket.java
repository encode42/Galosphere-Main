package net.orcinus.galosphere.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.orcinus.galosphere.Galosphere;

public record SendParticlesPacket(BlockPos blockPos) implements CustomPacketPayload {
    public static final Type<SendParticlesPacket> TYPE = new Type<>(Galosphere.id("send_particles"));
    public static final StreamCodec<FriendlyByteBuf, SendParticlesPacket> STREAM_CODEC = CustomPacketPayload.codec(SendParticlesPacket::write, SendParticlesPacket::new);

    private SendParticlesPacket(FriendlyByteBuf friendlyByteBuf) {
        this(friendlyByteBuf.readBlockPos());
    }

    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(this.blockPos);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}