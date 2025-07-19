package net.orcinus.galosphere.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.orcinus.galosphere.GalosphereClient;
import net.orcinus.galosphere.network.BarometerPacket;
import net.orcinus.galosphere.network.PlayCooldownSoundPacket;
import net.orcinus.galosphere.network.SendParticlesPacket;
import net.orcinus.galosphere.network.SendPerspectivePacket;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class GClientNetwork {
	public static void clientInit() {
		ClientPlayNetworking.registerGlobalReceiver(SendParticlesPacket.TYPE, GClientNetwork::receiveParticles);

		ClientPlayNetworking.registerGlobalReceiver(SendPerspectivePacket.TYPE, GClientNetwork::receivePerspective);

		ClientPlayNetworking.registerGlobalReceiver(BarometerPacket.TYPE, GClientNetwork::receiveBarometer);

		ClientPlayNetworking.registerGlobalReceiver(PlayCooldownSoundPacket.TYPE, GClientNetwork::receiveCooldown);
	}

	public static void receiveParticles(SendParticlesPacket packet, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		BlockPos blockPos = packet.blockPos();
		client.execute(() -> {
			ClientLevel world = client.level;
			if (world == null) return;
			RandomSource random = world.getRandom();
			world.playLocalSound(blockPos, GSoundEvents.GLOW_FLARE_SPREAD, SoundSource.BLOCKS, 1, 1, false);
			boolean flag = world.getBlockState(blockPos).isCollisionShapeFullBlock(world, blockPos);
			int l2 = flag ? 40 : 20;
			float f9 = flag ? 0.45F : 0.25F;
			for (int k3 = 0; k3 < l2; ++k3) {
				float f12 = 2 * random.nextFloat() - 1;
				float f14 = 2 * random.nextFloat() - 1;
				float f15 = 2 * random.nextFloat() - 1;
				world.addParticle(ParticleTypes.GLOW, (double) blockPos.getX() + 0.5D + (double) (f12 * f9), (double) blockPos.getY() + 0.5D + (double) (f14 * f9), (double) blockPos.getZ() + 0.5D + (double) (f15 * f9), (double) (f12 * 0.07F), (double) (f14 * 0.07F), (double) (f15 * 0.07F));
			}
			world.playLocalSound(blockPos, GSoundEvents.GLOW_FLARE_SPREAD, SoundSource.BLOCKS, 1, 1, false);
		});
	}

	public static void receivePerspective(SendPerspectivePacket packet, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		client.execute(() -> {
			Level world = client.level;
			if (world != null) {
				Optional
					.ofNullable(world.getPlayerByUUID(packet.uuid())).filter(player -> player.equals(client.player)).flatMap(player -> Optional.ofNullable(client.level.getEntity(packet.id()))).ifPresent(entity -> {
						client.setCameraEntity(entity);
						if (!client.options.getCameraType().isFirstPerson()) {
							client.options.setCameraType(CameraType.FIRST_PERSON);
						}
					});
			}
		});
	}

	public static void receiveBarometer(BarometerPacket packet, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		client.execute(() -> GalosphereClient.clearWeatherTime = packet.time());
	}

	public static void receiveCooldown(PlayCooldownSoundPacket packet, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		client.execute(() -> {
			if (client.player != null) client.getSoundManager().play(SimpleSoundInstance.forUI(GSoundEvents.SALTBOUND_TABLET_COOLDOWN_OVER, 1));
		});
	}
}
