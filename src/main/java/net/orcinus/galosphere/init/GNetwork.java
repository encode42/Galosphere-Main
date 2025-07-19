package net.orcinus.galosphere.init;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.orcinus.galosphere.network.BarometerPacket;
import net.orcinus.galosphere.network.PlayCooldownSoundPacket;
import net.orcinus.galosphere.network.SendParticlesPacket;
import net.orcinus.galosphere.network.SendPerspectivePacket;

public class GNetwork {

    public static void init() {
        PayloadTypeRegistry.playS2C().register(SendParticlesPacket.TYPE, SendParticlesPacket.STREAM_CODEC);

        PayloadTypeRegistry.playS2C().register(SendPerspectivePacket.TYPE, SendPerspectivePacket.STREAM_CODEC);

        PayloadTypeRegistry.playS2C().register(BarometerPacket.TYPE, BarometerPacket.STREAM_CODEC);

        PayloadTypeRegistry.playS2C().register(PlayCooldownSoundPacket.TYPE, PlayCooldownSoundPacket.STREAM_CODEC);
    }

}
