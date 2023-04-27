package fr.midey.starcraft.formParticle;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class PacketParticuleSender {

	public void packetParticuleSender(double x, double y, double z, EnumParticle particle) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) x, (float) y, (float) z, 0, 0, 0, 0, 1);
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
			
		}
	}
}
