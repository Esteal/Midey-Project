package fr.midey.OnePieceCraftSkills.Weapons.SkillHighRank;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.Weapons.CheckSkill;

public class FlambageShot {

	private OnePieceCraftSkills plugin;
	private CheckSkill checkSkill;

	public FlambageShot(OnePieceCraftSkills plugin, CheckSkill checkSkill) {
		this.plugin = plugin;
		this.checkSkill = checkSkill;
	}

	public void flambageShot(Player player) {
		
		double distance = 2.4;
		if(player.isSprinting()) distance = 3.5;
		Location playerLocation = player.getLocation();
		playerLocation.setPitch(0);
		Vector playerDirection = playerLocation.getDirection();
		Location skillBeginnningLocation = playerLocation.clone().add(playerDirection.multiply(distance));
		World world = player.getWorld();
		
		new BukkitRunnable() {
			double y = 0;
			@Override
			public void run() {
				
				if(y >= 4) {
                	this.cancel();
                	return;
				}
				
				Location  particleLocation = skillBeginnningLocation.clone().add(0, y, 0);
				
				checkSkill.checkParticleCollision(particleLocation, player, 1.2, plugin.getPlayerData(player).getWeaponPoints(), 6, 0, true);
				world.spawnParticle(Particle.FLAME, particleLocation, 10, 0.2, 0.6, 0.2, 0);
				y+=0.6;
			}
		}.runTaskTimer(plugin, 0, 0);
	}

}
