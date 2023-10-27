package fr.midey.OnePieceCraftSkills.Weapons.SkillHighRank;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.Weapons.CheckSkill;

public class DemonSlash implements Listener {
	private final OnePieceCraftSkills plugin;
	private final CheckSkill checkSkill;

    public DemonSlash(OnePieceCraftSkills plugin, CheckSkill checkSkill) {
        this.plugin = plugin;
        this.checkSkill = checkSkill;
    }
    
    public void demonSlash(Player player) {
    	
        int weaponPoints = plugin.getPlayerData(player).getWeaponPoints();
        player.setWalkSpeed(0);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

			@Override
			public void run() {
				player.setWalkSpeed(0.2f);
            	plugin.getPlayerData(player).setInDemonSlash(true);
				new BukkitRunnable() {
		            double distance = 3;
		            double timer = 8;
		            boolean hited = false;
		            @Override
		            public void run() {
		                if (timer <= 0) {
		                	Bukkit.getScheduler().runTaskLater(plugin, () -> { plugin.getPlayerData(player).setInDemonSlash(false); }, 100);
		                    this.cancel();
		                    return;
		                }
		            	Location playerLocation = player.getLocation().add(0, 2.5, 0); // Ajusté pour centrer la croix sur le joueur
		                Vector playerDirection = playerLocation.getDirection().normalize();
		                World world = playerLocation.getWorld();
		                Location centerPoint = playerLocation.clone().add(playerDirection.multiply(distance));
		                double pitch = Math.toRadians(playerLocation.getPitch());
		                double yaw = Math.toRadians(playerLocation.getYaw());
		                double cosPitch = Math.cos(pitch);
		                double sinPitch = Math.sin(pitch);
		                double cosYaw = Math.cos(yaw);
		                double sinYaw = Math.sin(yaw);

		                
		                hited = checkSkill.checkParticleCollision(player.getLocation(), player, 2, weaponPoints, 3.25, 2.5, false);
		                
		                for (double i = -2; i <= 2; i += 0.05) {
		                    double dx1 = i * cosYaw - i * sinPitch * sinYaw;
		                    double dy1 = i * cosPitch;
		                    double dz1 = i * sinYaw + i * sinPitch * cosYaw;

		                    double dx2 = -i * cosYaw - i * sinPitch * sinYaw;
		                    double dy2 = i * cosPitch;
		                    double dz2 = -i * sinYaw + i * sinPitch * cosYaw;

		                    Location point1 = centerPoint.clone().add(dx1, dy1, dz1);
		                    Location point2 = centerPoint.clone().add(dx2, dy2, dz2);

		                    if((!plugin.getAirAndFlowers().contains(point1.getBlock().getType()) || !plugin.getAirAndFlowers().contains(point2.getBlock().getType())) && (i > -1 && i < 1)) {
		                    	this.cancel();
		                    	return;
		                    }
		                    
		                    
		                    if (hited) {
			                    world.spawnParticle(Particle.CRIT, point1, 1, 0, 0, 0, 0);
			                    world.spawnParticle(Particle.CRIT, point2, 1, 0, 0, 0, 0);
			                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
			                    	world.spawnParticle(Particle.CRIT, point1, 1, 0, 0, 0, 0);
				                    world.spawnParticle(Particle.CRIT, point2, 1, 0, 0, 0, 0);
			                    }, 5);
		                    }
		                    	
		                }
		                
		                player.setVelocity(player.getLocation().getDirection().multiply(3));
		                timer -= 1;
		                hited = false;
		            }
		        }.runTaskTimer(plugin, 0, 0); 
			}
        	
        }, 20);
    }
    
    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
    	
        if (!(event.getEntity() instanceof Player))
            return;
        Player player = (Player) event.getEntity();
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL && plugin.getPlayerData(player).isInDemonSlash()) {
			event.setCancelled(true);
        }   
    }
}

