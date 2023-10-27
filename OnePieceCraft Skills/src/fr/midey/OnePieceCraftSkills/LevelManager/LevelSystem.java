package fr.midey.OnePieceCraftSkills.LevelManager;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;

public class LevelSystem implements Listener {

    private static OnePieceCraftSkills plugin;

    public LevelSystem(OnePieceCraftSkills plugin) {
        LevelSystem.plugin = plugin;
    }

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            int exp = (int) (Math.random() * 500) + 1; // entre 1 et 5
            addExperience(player, exp);
        }
    }

    @EventHandler
    public void onPlayerKill(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null && event.getEntity().getType() == EntityType.PLAYER) {
            Player player = event.getEntity().getKiller();
            addExperience(player, 100);
        }
    }

    public static void addExperience(Player player, int exp) {
    	UUID uuid = player.getUniqueId();
        PlayerData playerData = plugin.getPlayerDataMap().getOrDefault(uuid, new PlayerData());

        int playerExperience = playerData.getExperience();
        int playerLevel = playerData.getLevel();
        int playerSkillPoints= playerData.getSkillPoints();

        int currentLevel = playerLevel;
        int currentExp = playerExperience;
        int currentSkillPoints = playerSkillPoints;
        
        if (currentLevel < 10) {
            int expToNextLevel = playerData.getExperienceToNextLevel();
            currentExp += exp;

            while (currentExp >= expToNextLevel && currentLevel < 10) {
                currentLevel++;
                currentExp -= expToNextLevel;
                expToNextLevel = (int) (1000 * Math.pow(1.7, currentLevel - 1));

                currentSkillPoints++;  // Ajout d'un point de compétence à chaque montée de niveau

                player.sendMessage(ChatColor.GREEN + "Félicitations ! Vous êtes maintenant niveau " + currentLevel);
                player.sendMessage(ChatColor.GREEN + "Vous avez gagné un point de compétence ! "
                								   + "\nPoints de compétence actuels : " + currentSkillPoints);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
            }
            
            playerData.setLevel(currentLevel);
            playerData.setExperience(currentExp);
            playerData.setExperienceToNextLevel(expToNextLevel);
            playerData.setSkillPoints(currentSkillPoints);

        } else {
            // Le joueur ne peut plus monter de niveau, mais peut encore gagner de l'expérience
            currentExp += exp;
            playerData.setExperience(currentExp);
            playerData.setExperienceToNextLevel(-1);
        }
        plugin.getPlayerDataMap().put(uuid, playerData);
    }
}
