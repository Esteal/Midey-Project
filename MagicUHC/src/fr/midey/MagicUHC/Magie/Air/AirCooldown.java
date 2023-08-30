package fr.midey.MagicUHC.Magie.Air;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class AirCooldown {

	List<Player> playersHit = new ArrayList<>();
	double multiple;
	double timer;
	double timer2;
	double radius;
	BukkitTask task;
	BukkitTask task2;
	boolean radPlus;
	
	public void packetParticuleSender(double x, double y, double z, EnumParticle particle, Location loc) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.getLocation().distance(loc) > 100) continue;
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) x, (float) y, (float) z, 0, 0, 0, 0, 1);
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
		}
	}
	public void packetParticuleSender(EnumParticle particle, Location loc) {
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.getLocation().distance(loc) > 100) continue;
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) x, (float) y, (float) z, 0, 0, 0, 0, 1);
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
		}
	}
}
