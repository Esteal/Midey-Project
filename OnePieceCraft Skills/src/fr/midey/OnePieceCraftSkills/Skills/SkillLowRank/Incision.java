package fr.midey.OnePieceCraftSkills.Skills.SkillLowRank;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;

public class Incision {

	private OnePieceCraftSkills plugin;

	public Incision(OnePieceCraftSkills plugin) {
		this.plugin = plugin;
	}
	
	
	/**
	 * 0 WP -> 5 blocs
	 * 1 WP -> 10 blocs
	 * 2 WP -> 15 blocs
	 * 3 WP -> 20 blocs
	 **/
	public void incision(Player player) {
		
		double distance = 5;
		double weaponPoints = plugin.getPlayerData(player).getIncisionLevel() + 1;
		Location playerLocation = player.getLocation();
		Vector playerDirection = playerLocation.getDirection();
		
		Block block = playerLocation.getBlock();
		double y = 1;
		for(double i = 1; i <= (distance * weaponPoints); i++) {
			if(!plugin.getAirAndFlowers().contains(block.getType())) {
				distance = i - 2;
				break;
			}
			Location newBlockLocation = playerLocation.clone().add(playerDirection.clone().multiply(i));
			block = newBlockLocation.getBlock();	
			y = i;
		}
		player.sendMessage(""+y);
		if(y == (distance * weaponPoints)) {
			distance = y;
		}
		
		Location incisionLocation = playerLocation.clone().add(playerDirection.clone().multiply(distance));		
		player.sendMessage("" + incisionLocation.distance(playerLocation));
		player.getWorld().spawnParticle(Particle.CLOUD, playerLocation, 10, 0.5, 0.5, 0.5, 0);
		player.playSound(player, Sound.ENTITY_ENDER_DRAGON_FLAP, 1f, 1f);
		player.teleport(incisionLocation);
	}
}
