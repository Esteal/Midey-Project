package fr.midey.OnePieceCraftSkills.Weapons.SkillLowRank;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.Weapons.CheckSkill;

public class Slash {

    // Constantes pour les boosts de dégâts

    private final OnePieceCraftSkills plugin;
	private final CheckSkill checkSkill;

    public Slash(OnePieceCraftSkills plugin, CheckSkill checkSkill) {
        this.plugin = plugin;
        this.checkSkill = checkSkill;
    }

    public void createSlash(Player player, double radius, int weaponPoint, int direction) {
		Location playerLocation = player.getLocation();
		playerLocation.setY(playerLocation.getY() + 1.2);
		Vector playerDirection = playerLocation.getDirection();
		World world = player.getWorld();
		double yaw = Math.toRadians(playerLocation.getYaw());
		double pitch = Math.toRadians(playerLocation.getPitch());
		
		new BukkitRunnable() {
		    double angle = 0;
		    
		    @Override
		    public void run() {
		
		        double radian = Math.toRadians(angle);
		        double x = radius * Math.cos(radian) * direction;
		        double z = radius * Math.sin(radian);
		        double y = radius * Math.sin(pitch) * -1;
		
		        double rotatedX = x * Math.cos(yaw) - z * Math.sin(yaw);
		        double rotatedZ = x * Math.sin(yaw) + z * Math.cos(yaw);
		
		        Location particleLocation = playerLocation.clone().add(playerDirection.multiply(1.1))
		                .add(rotatedX, y + 0.5, rotatedZ);
		        
		        if (!plugin.getAirAndFlowers().contains(particleLocation.getBlock().getType()) || angle > 200) {
		        	this.cancel();
		            return;
		        }
		        
		        world.spawnParticle(Particle.CRIT, particleLocation, 20, 0.5, 0, 0.5, 0);
		        checkSkill.checkParticleCollision(particleLocation, player, 2, weaponPoint, 1.25, 1.1, false);
		        
		        angle += 30; // ajusté l'incrément d'angle
		    }
		}.runTaskTimer(plugin, 0, 0);
		world.playSound(player, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 2f, 1f);
    }
}
