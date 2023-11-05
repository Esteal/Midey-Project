package fr.midey.OnePieceCraftSkills.Skills.SkillHighRank;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.Skills.UtilsSkill.CheckSkill;
import fr.midey.OnePieceCraftSkills.Utils.ChanceToUpgradeSkill;

public class FlambageShoot {

	private OnePieceCraftSkills plugin;
	private CheckSkill checkSkill;

	public FlambageShoot(OnePieceCraftSkills plugin, CheckSkill checkSkill) {
		this.plugin = plugin;
		this.checkSkill = checkSkill;
	}

	public void flambageShoot(Player player) {
		
		double distance = 2.4;
		if(player.isSprinting()) distance = 3.5;
		doSouffleArdent(player);
		Location playerLocation = player.getLocation();
		playerLocation.setPitch(0);
		Vector playerDirection = playerLocation.getDirection();
		Location skillBeginnningLocation = playerLocation.clone().add(playerDirection.multiply(distance));
		World world = player.getWorld();
		int weaponPoints = plugin.getPlayerData(player).getFlambageShootLevel();
        if(ChanceToUpgradeSkill.chanceToUpgradeSkill(5))
        	plugin.getPlayerData(player).setDemonSlashLevel(weaponPoints + 1);
        
		new BukkitRunnable() {
			double y = 0;
			@Override
			public void run() {
				
				if(y >= 4) {
                	this.cancel();
                	return;
				}
				
				Location  particleLocation = skillBeginnningLocation.clone().add(0, y, 0);
				
				checkSkill.checkParticleCollision(particleLocation, player, 1.2, weaponPoints, 6, 0, true);
				world.spawnParticle(Particle.FLAME, particleLocation, 10, 0.2, 0.6, 0.2, 0);
				y+=0.6;
			}
		}.runTaskTimer(plugin, 0, 0);
	}

	public void doSouffleArdent(Player player) {
	    // Définition de la durée de vie de l'ArmorStand.
	    int durationInSeconds = 10;
	    // Distance maximale que le rayon peut parcourir.
	    int range = 10;
	    
	    // Création d'une tâche récurrente qui s'exécutera toutes les ticks (20 ticks = 1 seconde).
	    new BukkitRunnable() {
	        int ticksLived = 0; // Compteur de ticks pour le despawn

			public void run() {
	            // Si la durée de vie a expiré, annuler cette tâche et sortir.
	            if (ticksLived >= durationInSeconds * 20) {
	                this.cancel();
	                return;
	            }
	            
	            // Calcul de la position de départ en face du joueur.
	            Location startLocation = player.getLocation();
	            Vector direction = startLocation.getDirection();
	            
	            // Créer une ligne d'ArmorStands en utilisant la direction du regard.
	            for (int i = 0; i < range; i++) {
	                Location currentLocation = startLocation.clone().add(direction.clone().multiply(i));
	                // Vérifier si l'ArmorStand est dans un bloc solide, si oui, le break.
	                
	                ArmorStand armorStand = player.getWorld().spawn(currentLocation, ArmorStand.class);
	                armorStand.setGravity(false); // Empêcher l'ArmorStand de tomber.

	                // Appel de la fonction de vérification des dégâts (à implémenter).
	                // checkDamageToEntities(armorStand, player);
	                
	                // Supprimer l'ArmorStand après 1 tick.
	                Bukkit.getScheduler().runTaskLater(plugin, armorStand::remove, 5L);
	            }
	            
	            // Incrémenter le compteur de ticks.
	            ticksLived++;
	        }
	    }.runTaskTimer(plugin, 0, 5L); // Commence immédiatement, se répète toutes les ticks.
	}

}
