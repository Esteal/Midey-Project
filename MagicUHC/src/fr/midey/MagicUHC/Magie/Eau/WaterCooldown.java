package fr.midey.MagicUHC.Magie.Eau;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class WaterCooldown {
	public int cooldown;
	public double multiplicateur;
	public BukkitTask task;
	
	public void VerifyPropulsePlayer(int x, int y, int z, double propulseur, double dammage,Player originalPlayer, boolean direction, double director) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			Location locs = p.getLocation();
			if(p == originalPlayer) continue;
			if(locs.getBlockX() == x
				&& locs.getBlockY() == y
				&& locs.getBlockZ() == z) {
				p.playEffect(EntityEffect.HURT);
				if(p.getHealth() - dammage <= 0) p.setHealth(0);
				else p.setHealth(p.getHealth() - dammage);
				if(direction) p.setVelocity(locs.getDirection().multiply(director).setY(propulseur));
				else p.setVelocity(locs.getDirection().setY(propulseur));
			}
		}
	}
}
