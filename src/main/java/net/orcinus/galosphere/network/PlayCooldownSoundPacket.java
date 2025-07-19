package net.orcinus.galosphere.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.orcinus.galosphere.Galosphere;

public record PlayCooldownSoundPacket() implements CustomPacketPayload {
    public static final Type<PlayCooldownSoundPacket> TYPE = new Type<>(Galosphere.id("play_cooldown_sound"));
    public static final StreamCodec<FriendlyByteBuf, PlayCooldownSoundPacket> STREAM_CODEC = CustomPacketPayload.codec(PlayCooldownSoundPacket::write, PlayCooldownSoundPacket::new);

    private PlayCooldownSoundPacket(FriendlyByteBuf buf) {
        this();
    }

    public void write(FriendlyByteBuf friendlyByteBuf) {
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
