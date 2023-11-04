package fr.midey.OnePieceCraftSkills.Skills.UtilsSkill;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;

public class CheckSkill implements Listener{

    private OnePieceCraftSkills plugin;
    // Définit les niveaux de boost de dégâts
    private static final double[] DAMAGE_BOOST_LEVELS = {0, 0.15, 0.5, 1};

	public CheckSkill(OnePieceCraftSkills plugin) {
        this.plugin = plugin;
    }

	 // Bloque le changement d'items dans les mains du joueur
	 // et permet donc d'activer la compétence de haut rang du joueur
	/*@EventHandler
	 public void onItemSwich(PlayerSwapHandItemsEvent event) {
		 
		 event.setCancelled(true);
		 if(!isWeaponItem(event)) return;
		 Player player = event.getPlayer();
		 PlayerData playerData = plugin.getPlayerData(player);

		 if(playerData.getWeaponPoints() > 2) {
			 executeWeaponSkill(player, playerData, "high");
		 }
	 }*/
    
    /**
     * Vérifie si une particule entre en collision avec un joueur ou une autre entité vivante.
     * Si c'est le cas, applique des dégâts et d'autres effets en conséquence.
     *
     * @param particleLocation - La localisation de la particule.
     * @param player - Le joueur initiant l'action.
     * @param weaponPoint - Les points de l'arme utilisée.
     * @param damage - Les dégâts infligés.
     * @param kb - La force de knockback appliquée.
     */
	public boolean checkParticleCollision(Location particleLocation, Player player, double distanceFromEntity, int weaponPoint, double damage, double kb, boolean fire) {
        List<Entity> nearbyEntities = particleLocation.getWorld().getEntities().stream()
                .filter(entity -> entity.getLocation().distance(particleLocation) < 2)
                .collect(Collectors.toList());
        boolean hit = false;
        World world = player.getWorld();
        Vector playerDirection = player.getLocation().getDirection();
        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity) {
            	
                LivingEntity livingEntity = (LivingEntity) entity;
                
                boolean skill_1 = plugin.getEntityTouchByWeaponSkill_1().contains(livingEntity);
                boolean skill_2 = plugin.getEntityTouchByWeaponSkill_2().contains(livingEntity);
                
                if (skill_1 && skill_2) continue;
                else if(!skill_1) getTouchByWeaponSkill(livingEntity, plugin.getEntityTouchByWeaponSkill_1());
                else if(!skill_2) getTouchByWeaponSkill(livingEntity, plugin.getEntityTouchByWeaponSkill_2());

                if (livingEntity.getLocation().distance(particleLocation) < distanceFromEntity) {
                	
                	if (entity instanceof Player) {
                        Player otherPlayer = (Player) entity;
                        if (otherPlayer.equals(player)) continue;
                        
                        double newHealth = otherPlayer.getHealth() - (damage * DAMAGE_BOOST_LEVELS[weaponPoint] + damage);

                        if (newHealth < 0) otherPlayer.setHealth(0);
                        else otherPlayer.setHealth(newHealth);

                        otherPlayer.playSound(otherPlayer.getLocation(), Sound.ENTITY_PLAYER_HURT, 1.0f, 1.0f);
                        otherPlayer.playEffect(EntityEffect.HURT);
    		        	world.spawnParticle(Particle.REDSTONE, particleLocation, 10, 0.5, 0, 0.5, new DustOptions(Color.RED, 1));
                        otherPlayer.setVelocity(playerDirection.multiply(kb));
                        hit = true;
                        if (fire)
                        	otherPlayer.setFireTicks(20*5);
                    } else {
                        livingEntity.damage(damage * DAMAGE_BOOST_LEVELS[weaponPoint] + damage);
    		        	world.spawnParticle(Particle.REDSTONE, particleLocation, 10, 0.5, 0, 0.5, new DustOptions(Color.RED, 1));
                        livingEntity.setVelocity(playerDirection.multiply(kb));
                        hit = true;
                        if (fire)
                        	livingEntity.setFireTicks(20*5);
                    }
                }
            }
        }
        return hit;
	}
	
    // Enregistre l'entité touchée par la compétence et la retire après 1/2s évitant que l'entité se fasse one shot
	public void getTouchByWeaponSkill(LivingEntity livingEntity, List<LivingEntity> entityTouchByWeaponSkill) {
		entityTouchByWeaponSkill.add(livingEntity);
    	Bukkit.getScheduler().runTaskLater(plugin, () -> {
    		entityTouchByWeaponSkill.remove(livingEntity);
    	}, 10);
	}
}
