package fr.midey.OnePieceCraftSkills.Weapons;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.util.Vector;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;
import fr.midey.OnePieceCraftSkills.Endurance.EnduranceManager;
import fr.midey.OnePieceCraftSkills.Weapons.SkillHighRank.DemonSlash;
import fr.midey.OnePieceCraftSkills.Weapons.SkillHighRank.FlambageShot;
import fr.midey.OnePieceCraftSkills.Weapons.SkillLowRank.Incision;
import fr.midey.OnePieceCraftSkills.Weapons.SkillLowRank.Slash;

public class CheckSkill implements Listener{

    private OnePieceCraftSkills plugin;
    // Définit les niveaux de boost de dégâts
    private static final double[] DAMAGE_BOOST_LEVELS = {0.15, 0.5, 1};

    // Définit le coût et la surchauffe des compétences
    private int demonSlashCost = 65, demonSlashSurchauffe = 45; // Cout : 65 | surchauffe si moins de 45 d'endurance
    private int slashCost = 30, slashSurchauffe = 15; // Cout : 65 | surchauffe si moins de 15 d'endurance
    private int flambageShotCost = 65, flambageShotSurchauffe = 45; // Cout : 65 | surchauffe si moins de 45 d'endurance
    private int incisionCost = 20, incisionShotSurchauffe = 10; // Cout : 65 | surchauffe si moins de 45 d'endurance
    
	public CheckSkill(OnePieceCraftSkills plugin) {
        this.plugin = plugin;
    }
	
	//Permet d'activer la compétence de bas rang du joueur
	 @EventHandler
	 public void onPlayerInteract(PlayerInteractEvent event) {
		 if (!isWeaponItem(event)) return;
		 Player player = event.getPlayer();
		 PlayerData playerData = plugin.getPlayerData(player);

		 if (playerData.getWeaponPoints() > 0)
			 executeWeaponSkill(player, playerData, "low");
	 }

	 // Bloque le changement d'items dans les mains du joueur
	 // et permet donc d'activer la compétence de haut rang du joueur
	 @EventHandler
	 public void onItemSwich(PlayerSwapHandItemsEvent event) {
		 
		 event.setCancelled(true);
		 if(!isWeaponItem(event)) return;
		 Player player = event.getPlayer();
		 PlayerData playerData = plugin.getPlayerData(player);

		 if(playerData.getWeaponPoints() > 2) {
			 executeWeaponSkill(player, playerData, "high");
		 }
	 }
	 
	 private void executeWeaponSkill(Player player, PlayerData playerData, String level) {
		    // Déterminer la compétence en fonction du niveau
		    String skill = getWeaponSkillBasedOnLevel(playerData, level);
		    
		    if (skill == null) {
		        player.sendMessage(ChatColor.RED + "➤ " + ChatColor.GRAY + "Aucune compétence d'arme sélectionnée");
		        return;
		    }

		    EnduranceManager enduranceManager = plugin.getEnduranceManager();

		    switch (skill) {
		        case "slash":
		            executeSlashSkill(player, playerData, enduranceManager);
		            break;
		        case "pas de lune":
		            // pas de lune est entièrement géré par sa classe
		            break;
		        case "demon slash":
		            executeDemonSlashSkill(player, playerData, enduranceManager);
		            break;
		        case "flambage shoot":
		            executeFlambageShotSkill(player, playerData, enduranceManager);
		            break;
		        case "incision":
		            executeIncisionSkill(player, playerData, enduranceManager);
		            break;
		        default:
		            player.sendMessage(ChatColor.RED + "➤ " + ChatColor.GRAY + "Compétence invalide, veuillez contacter un administrateur");
		            break;
		    }
		}

		private String getWeaponSkillBasedOnLevel(PlayerData playerData, String level) {
		    if (level.equalsIgnoreCase("high")) return playerData.getWeaponSkillHigh();
		    return playerData.getWeaponSkillLow();
		}

		private void executeSlashSkill(Player player, PlayerData playerData, EnduranceManager enduranceManager) {
		    if (canExecuteSkill(enduranceManager, player, slashSurchauffe)) {
		        Slash slash = new Slash(plugin, this);
		        slash.createSlash(player, 3, playerData.getWeaponPoints(), playerData.getDirectionLowSkill());
		        enduranceManager.useEndurance(player, slashCost);
		        playerData.setDirectionLowSkill(playerData.getDirectionLowSkill() * -1);
		    }
		}

		private void executeDemonSlashSkill(Player player, PlayerData playerData, EnduranceManager enduranceManager) {
		    if (canExecuteSkill(enduranceManager, player, demonSlashSurchauffe)) {
		        DemonSlash demonSlash = new DemonSlash(plugin, this);
		        demonSlash.demonSlash(player);
		        enduranceManager.useEndurance(player, demonSlashCost);
		    }
		}

		private void executeFlambageShotSkill(Player player, PlayerData playerData, EnduranceManager enduranceManager) {
		    if (canExecuteSkill(enduranceManager, player, flambageShotSurchauffe)) {
		        FlambageShot flambageShot = new FlambageShot(plugin, this);
		        flambageShot.flambageShot(player);
		        enduranceManager.useEndurance(player, flambageShotCost);
		    }
		}

		private void executeIncisionSkill(Player player, PlayerData playerData, EnduranceManager enduranceManager) {
		    if (canExecuteSkill(enduranceManager, player, incisionShotSurchauffe)) {
		        enduranceManager.useEndurance(player, incisionCost);
		        Incision incision = new Incision(plugin);
		        incision.incision(player);
		    }
		}

		private boolean canExecuteSkill(EnduranceManager enduranceManager, Player player, int surchauffe) {
		    if (!enduranceManager.canUseSkill(player, surchauffe) || plugin.getPlayerData(player).isInCooldown()) {
		        putInCooldown(player);
		        return false;
		    }
		    return true;
		}
	
	public void putInCooldown(Player player) {
 		player.sendMessage(ChatColor.RED + "➤ " + ChatColor.GRAY + "Votre corps a surchauffé, vous aurez besoin de temps pour vous en remettre.");	
         plugin.getPlayerData(player).setInCooldown(true);
         Bukkit.getScheduler().runTaskLater(plugin, () -> {
     		player.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.GRAY + "Vous êtes de nouveau prêt à combattre !");
	            plugin.getPlayerData(player).setInCooldown(false);
         }, 20 * 15);
	}
	 
    public boolean isWeaponItem(PlayerInteractEvent event) {
        return event.getItem() != null
                && plugin.getTools().contains(event.getItem().getType())
                && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK);
    }
    
    public boolean isWeaponItem(PlayerSwapHandItemsEvent event) {
    	//regarde si le joueur a une compétence utilisable sans arme ex : flambage shoot
    	if(!plugin.getSwordSkills().contains(plugin.getPlayerData(event.getPlayer()).getWeaponSkillHigh()))
    		return true;
    	Bukkit.broadcastMessage("" + plugin.getSwordSkills().contains(plugin.getPlayerData(event.getPlayer()).getWeaponSkillHigh()));
    	
    	return event.getOffHandItem() != null
                && plugin.getTools().contains(event.getOffHandItem().getType());
    }
    
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
	public boolean checkParticleCollision(Location particleLocation, Player player, double distanceFromEntity,int weaponPoint, double damage, double kb, boolean fire) {
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
                        
                        double newHealth = otherPlayer.getHealth() - (damage * DAMAGE_BOOST_LEVELS[weaponPoint - 1] + damage);

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
                        livingEntity.damage(damage * DAMAGE_BOOST_LEVELS[weaponPoint - 1] + damage);
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
