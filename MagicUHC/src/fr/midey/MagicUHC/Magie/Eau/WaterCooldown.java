package fr.midey.MagicUHC.Magie.Eau;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class WaterCooldown {
	public int cooldown;
	public double multiplicateur;
	public BukkitTask task;
	public BukkitTask taskLater;
	
	public void propulsePlayer(int x, int y, int z, double propulseur, double dammage, Player originalPlayer) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			Location locs = p.getLocation();
			if(p == originalPlayer) continue;
			if(locs.getBlockX() == x
				&& locs.getBlockY() == y
				&& locs.getBlockZ() == z) {
				p.playEffect(EntityEffect.HURT);
				if(p.getHealth() - dammage <= 0) p.setHealth(0);
				else p.setHealth(p.getHealth() - dammage);
				p.setVelocity(locs.getDirection().multiply(0).setY(propulseur));
			}
		}
	}
	
	public void followLocation(int x, int y , int z, Location loc, Player originalPlayer, double dammage, double vitesse) {
		Vector v = loc.getDirection();
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if(p == originalPlayer && vitesse == 1.25 ) continue;
			Location locPlayer = p.getLocation();
			if(locPlayer.getBlockX() == x
					&& locPlayer.getBlockY() == y
					&& locPlayer.getBlockZ() == z) {
				p.setVelocity(v.multiply(vitesse));
				//p.playEffect(EntityEffect.HURT);
				if(p.getHealth() - dammage <= 0) p.setHealth(0);
				else p.setHealth(p.getHealth() - dammage);
			}
		}
	}
}
