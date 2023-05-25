package fr.midey.MagicUHC.Magie.Feu;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import fr.midey.MagicUHC.MagicUHC;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class FireCooldown {

	double multiple;
	double timer;
	double timer2;
	BukkitTask task;
	BukkitTask task2;
	public void packetParticuleSender(double x, double y, double z, EnumParticle particle, Location loc) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.getLocation().distance(loc) > 30) continue;
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) x, (float) y, (float) z, 0, 0, 0, 0, 1);
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	public void followLocation(int x, int y , int z, Location loc, double dammage, double vitesse) {
		Vector v = loc.getDirection();
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			Location locPlayer = p.getLocation();
			if(locPlayer.getBlockX() == x
					&& locPlayer.getBlockY() == y
					&& locPlayer.getBlockZ() == z) {
				p.setVelocity(v.multiply(vitesse));
				p.playEffect(EntityEffect.HURT);
				if(p.getHealth() - dammage <= 0) p.setHealth(0);
				else p.setHealth(p.getHealth() - dammage);
			}
		}
	}
	
	public void drawCross(Location loc, Vector v,EnumParticle particle, float size , MagicUHC main, double i1, double ym1) {
		FireCooldown packet = new FireCooldown();
		packet.multiple = 3;
		packet.task = Bukkit.getScheduler().runTaskTimer(main, () -> {
			for(int boucle = 0; boucle < 5; boucle++) {
				double x = packet.multiple * v.getX() + loc.getX();
				double y = packet.multiple * v.getY() + loc.getY() + 1;
				double z = packet.multiple * v.getZ() + loc.getZ();
				//plus i est petit plus les particules feront une ligne verticale
				//plus ym est petit plus les particules feront une ligne horizontale
				for (float i = -size, ym = 0; i <= size && ym >= -size; i+=i1, ym-=ym1) {
					double a = x + i * v.getZ();
					double b = y + ym;
					double c = z - i * v.getX();
					Location bloc = new Location(loc.getWorld(), a, b, c, loc.getYaw(), loc.getPitch());
					packet.packetParticuleSender(a, b, c, particle, bloc);
					playerTouchCross(bloc, 0.001, 0);
				}
				for (float i = size, ym = 0; i >= -size && ym >= -size; i-=i1, ym-=ym1) {
					double a = x + i * v.getZ();
					double b = y + ym;
					double c = z - i * v.getX();
					Location bloc = new Location(loc.getWorld(), a, b, c, loc.getYaw(), loc.getPitch());
					packet.packetParticuleSender(a, b, c, particle, bloc);
					playerTouchCross(bloc, 0.001, 0);
				}
				packet.multiple+=0.4;
				if (packet.multiple > 20) packet.task.cancel();
			}
		}, 0, 0);
	}
	
	public void drawWall(Location loc, Vector v,EnumParticle particle, float size , MagicUHC main, double i1, double ym1) {
		FireCooldown packet = new FireCooldown();
		packet.multiple = 3;
		double x = packet.multiple * v.getX() + loc.getX();
		double y = packet.multiple * v.getY() + loc.getY() + 1;
		double z = packet.multiple * v.getZ() + loc.getZ();
		//plus i est petit plus les particules feront une ligne verticale
		//plus ym est petit plus les particules feront une ligne horizontale
		for (float i = -size, ym = 0; i <= size && ym >= -size; i+=i1, ym-=ym1) {
			double a = x + i * v.getZ();
			double b = y + ym;
			double c = z - i * v.getX();
			Location bloc = new Location(loc.getWorld(), a, b, c, loc.getYaw(), loc.getPitch());
			packet.packetParticuleSender(a, b, c, particle, bloc);
			playerTouchCross(bloc, 0.001, 1);
		}
		for (float i = size, ym = 0; i >= -size && ym >= -size; i-=i1, ym-=ym1) {
			double a = x + i * v.getZ();
			double b = y + ym;
			double c = z - i * v.getX();
			Location bloc = new Location(loc.getWorld(), a, b, c, loc.getYaw(), loc.getPitch());
			packet.packetParticuleSender(a, b, c, particle, bloc);
			playerTouchCross(bloc, 0.001, 1);
		}
	}
	
	public void playerTouchCross(Location loc, double damage, double propulsion) {
		for(Player players : Bukkit.getOnlinePlayers()) {
			if(loc.distance(players.getLocation()) <= 1 || loc.distance(players.getEyeLocation()) <= 1) {
				for(int i = 0; i<5; i++) {
					players.playEffect(EntityEffect.HURT);
					if(players.getHealth() - damage <= 0) players.setHealth(0);
					else players.setHealth(players.getHealth() - damage);
				}
				players.setVelocity(players.getLocation().getDirection().multiply(-1).setY(propulsion));
				players.setFireTicks(10*20);
			}
		}
	}
}
