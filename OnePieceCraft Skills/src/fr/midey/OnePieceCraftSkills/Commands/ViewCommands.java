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
        	
            int observationPoints = plugin.getHakiPoints(target, "observation");
            int armementPoints = plugin.getHakiPoints(target, "armement");
            int roiPoints = plugin.getHakiPoints(target, "roi");
            int skillPoints = playerData.getSkillPoints();
            int level = playerData.getLevel();
            int weaponPoints = playerData.getWeaponPoints();
            int experience = playerData.getExperience();
            int experienceToNextLevel = playerData.getExperienceToNextLevel();
            String lowSkill = playerData.getWeaponSkillLow();
            String highSkil = playerData.getWeaponSkillHigh();
            
            sender.sendMessage(ChatColor.GOLD + "━━━━━━━━━━━━━━━ " + ChatColor.AQUA + "Informations de " + playerName + ChatColor.GOLD + " ━━━━━━━━━━━━━━━");
            sender.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.YELLOW + "Niveau: " + ChatColor.WHITE + level);
            sender.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.YELLOW + "Expérience : " + ChatColor.WHITE + experience + "/" + experienceToNextLevel);
            sender.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.YELLOW + "Points de compétences disponible : " + ChatColor.WHITE + skillPoints);
            sender.sendMessage(ChatColor.GOLD + "━━━ " + ChatColor.AQUA + "Haki" + ChatColor.GOLD + " ━━━");
            sender.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.YELLOW + "Observation: " + ChatColor.WHITE + observationPoints);
            sender.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.YELLOW + "Armement: " + ChatColor.WHITE + armementPoints);
            sender.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.YELLOW + "Roi: " + ChatColor.WHITE + roiPoints);
            sender.sendMessage(ChatColor.GOLD + "━━━ " + ChatColor.AQUA + "Maniement du sabre" + ChatColor.GOLD + " ━━━");
            sender.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.YELLOW + "Niveau : " + ChatColor.WHITE + weaponPoints);
            sender.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.YELLOW + "Technique de bas rang : " + ChatColor.WHITE + lowSkill);
            sender.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.YELLOW + "Technique de haut rang : " + ChatColor.WHITE + highSkil);
            sender.sendMessage(ChatColor.GOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "➤ " + ChatColor.GRAY + "Opération non supportée, contactez un administrateur.");
            return false;
        }
    }
    
    
}
