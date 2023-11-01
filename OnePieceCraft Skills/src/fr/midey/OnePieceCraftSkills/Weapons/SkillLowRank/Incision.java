package fr.midey.OnePieceCraftSkills.Weapons.SkillLowRank;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;

public class Incision {

	private OnePieceCraftSkills plugin;

	public Incision(OnePieceCraftSkills plugin) {
		this.plugin = plugin;
	}
	
	public void incision(Player player) {
		
		double distance = 3;
		Location playerLocation = player.getLocation();
		Vector playerDirection = playerLocation.getDirection();
		Location incisionLocation = playerLocation.clone().add(playerDirection.multiply(distance));
		
		Material blockOnIncision = incisionLocation.getBlock().getType();
		
		while(!(plugin.getAirAndFlowers().contains(blockOnIncision))) {
			distance -= 0.1;
			incisionLocation = playerLocation.clone().add(playerDirection.multiply(distance));
			blockOnIncision = incisionLocation.getBlock().getType();
		}
		player.getWorld().spawnParticle(Particle.CLOUD, playerLocation, 10, 0.5, 0.5, 0.5, 0);
		player.playSound(player, Sound.ENTITY_ENDER_DRAGON_FLAP, 1f, 1f);
		player.teleport(incisionLocation);
	}
}
