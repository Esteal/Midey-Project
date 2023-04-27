package fr.midey.starcraft.formParticle;

import org.bukkit.Location;

import net.minecraft.server.v1_8_R3.EnumParticle;

public class Stars {
	
	private Location loc;
	private EnumParticle particle;
	
	public Stars(Location loc, EnumParticle particle) {
		this.loc = loc;
		this.particle = particle;
	}

	public void drawStars() {
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		PacketParticuleSender packet = new PacketParticuleSender();
		double angle = 0;
		for (int i = 0; i < 5; i++) {
			packet.packetParticuleSender(x + (float) Math.cos(angle), y, z + (float) Math.sin(angle), particle);
			for(float trail = 0.1f; trail<14.5 ; trail+=0.5f) {
				packet.packetParticuleSender(x + (float) Math.cos(angle) * trail / 2, y, z + (float) Math.sin(angle) * trail / 2, particle);
			}
			angle += 2 * Math.PI / 5;
		}
	}
}
