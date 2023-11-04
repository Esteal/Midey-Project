package fr.midey.OnePieceCraftSkills.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;

public class ViewCommands {

    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args, OnePieceCraftSkills plugin) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "➤ " + ChatColor.GRAY + "Utilisation incorrecte de la commande.");
            return false;
        }
        
        String playerName;
        
        if (args.length < 3)
            playerName = sender.getName();
        else  
        	playerName = args[2];
        
        Player target = Bukkit.getPlayerExact(playerName);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "➤ " + ChatColor.GRAY + "Joueur introuvable.");
            return false;
        }

        if ("view".equalsIgnoreCase(args[0]) && "points".equalsIgnoreCase(args[1])) {
        	PlayerData playerData = plugin.getPlayerData(target);
        	
            int skillPoints = playerData.getSkillPoints();
            int level = playerData.getLevel();
            int experience = playerData.getExperience();
            int experienceToNextLevel = playerData.getExperienceToNextLevel();
            double endurance = playerData.getEnduranceMax();
            
            sender.sendMessage(ChatColor.GOLD + "━━━━━━━━━━━━━━━ " + ChatColor.AQUA + "Informations de " + playerName + ChatColor.GOLD + " ━━━━━━━━━━━━━━━");
            sender.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.YELLOW + "Niveau: " + ChatColor.WHITE + level);
            sender.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.YELLOW + "Expérience : " + ChatColor.WHITE + experience + "/" + experienceToNextLevel);
            sender.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.YELLOW + "Endurance : " + ChatColor.WHITE + String.format("%.0f", endurance));
            sender.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.YELLOW + "Points de compétences disponible : " + ChatColor.WHITE + skillPoints);
            sender.sendMessage(ChatColor.GOLD + "━━━ " + ChatColor.AQUA + "Compétences" + ChatColor.GOLD + " ━━━");
            displaySkills(target, playerData);
            sender.sendMessage(ChatColor.GOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "➤ " + ChatColor.GRAY + "Opération non supportée, contactez un administrateur.");
            return false;
        }
    }
    
    public static void displaySkills(Player player, PlayerData playerData) {
        displaySkill(player, "Demon Slash", playerData.getDemonSlashLevel(), playerData.getChanceToDemonSlashLevelUp());
        displaySkill(player, "Slash", playerData.getSlashLevel(), playerData.getChanceToSlashLevelUp());
        displaySkill(player, "Incision", playerData.getIncisionLevel(), playerData.getChanceToIncisionLevelUp());
        displaySkill(player, "Pas de lune", playerData.getPasDeluneLevel(), playerData.getChanceToPasDeluneLevelUp());
        displaySkill(player, "Flambage Shoot", playerData.getFlambageShootLevel(), playerData.getChanceToFlambageShootLevelUp());
        displaySkill(player, "Haki des Rois", playerData.getHakiDesRoisLevel(), playerData.getChanceToHakiDesRoisLevelUp());
        displaySkill(player, "Haki de l'Armement", playerData.getHakiArmementLevel(), playerData.getChanceToHakiArmementLevelUp());
        displaySkill(player, "Haki de l'observation", playerData.getHakiObservationLevel(), playerData.getChanceToHakiObservationLevelUp());
    }

    private static void displaySkill(Player player, String skillName, int level, double chanceToLevelUp) {
        if (level != 0) {
            String message = ChatColor.GREEN + "➤ " + ChatColor.YELLOW + skillName + " : " + ChatColor.WHITE + level
                    + ChatColor.GRAY + " (" + String.format("%.2f", chanceToLevelUp) + "%)";
            player.sendMessage(message);
        }
    }
}
