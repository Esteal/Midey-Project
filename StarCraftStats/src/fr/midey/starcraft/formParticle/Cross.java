package fr.midey.starcraft.formParticle;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import fr.midey.starcraft.Stats;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class Cross {

	private EnumParticle particle;
	private Location loc;
	private float size;
	private Vector v;
	private Stats main;

	public Cross(Location loc, Vector v,EnumParticle particle, float size, Stats main) {
		this.loc = loc;
		this.particle = particle;
		this.size = size;
		this.v = v;
		this.main = main;
	}
	
	public void drawCross() {
		PacketParticuleSender packet = new PacketParticuleSender();
			double x = 4 * v.getX() + loc.getX();
			double y = 4 * v.getY() + loc.getY() + 2.6;
			double z = 4 * v.getZ() + loc.getZ();
			for (float i = -size, ym = 0; i <= size && ym >= -size; i+=0.2, ym-=0.1) {
					double a = x + i * v.getZ();
					double b = y + ym;
					double c = z - i * v.getX();
					Location bloc = new Location(loc.getWorld(), a, b, c);
					if (!(bloc.getBlock().getType() == Material.AIR)) continue;
					packet.packetParticuleSender(a, b, c, particle);
					playerTouchCross(a, b, c);
			}
			for (float i = size, ym = 0; i >= -size && ym >= -size; i-=0.2, ym-=0.1) {
				double a = x + i * v.getZ();
				double b = y + ym;
				double c = z - i * v.getX();
				Location bloc = new Location(loc.getWorld(), a, b, c);
				if (!(bloc.getBlock().getType() == Material.AIR)) continue;
				packet.packetParticuleSender(a, b, c, particle);
				playerTouchCross(a, b, c);
			}
	}
	
	public void playerTouchCross(double a, double b, double c) {
		
		a = (int) Math.round(a);
		b = (int) Math.round(b);
		c = (int) Math.round(c);

		for(Player players : Bukkit.getOnlinePlayers()) {
			if(main.getCooldownVie().containsKey(players)) continue;
			Location loc = players.getLocation();
			double x = loc.getBlockX();
			double y = loc.getBlockY();
			double z = loc.getBlockZ();
			if(x == a && c == z && (y == b || y == b - 1 || y == b - 2)) {
				main.getCooldownVie().put(players, 0);
				players.setVelocity(loc.getDirection().multiply(-3).setY(1));
				players.playEffect(EntityEffect.HURT);
				double vie = players.getHealth() - 8;
				if(vie <= 0) players.setHealth(0);
				else players.setHealth(players.getHealth() - 8);
			}
		}
	}
}
