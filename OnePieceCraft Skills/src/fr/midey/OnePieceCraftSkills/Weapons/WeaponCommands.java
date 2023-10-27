package fr.midey.OnePieceCraftSkills.Weapons;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;

public class WeaponCommands{

    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args, OnePieceCraftSkills plugin) {
    	if (args.length < 4) {
            sender.sendMessage(ChatColor.RED + "➤ " + ChatColor.GRAY + "Utilisation incorrecte de la commande.");
            return false;
        }
    	
    	String skillRank = args[1];
    	String skill = "";
    	for(int i = 3; i < args.length; i++) {
    		if(i == args.length - 1) skill+=args[i]; 
    		else skill+=args[i] + " "; 
    	}
    	
    	Player player = (Player) sender;
    	PlayerData playerDataMap = plugin.getPlayerDataMap().getOrDefault(player.getUniqueId(), new PlayerData());
    	int weaponPoints = playerDataMap.getWeaponPoints();
    	if(weaponPoints > 0) {
    		
    		if (skillRank.equalsIgnoreCase("low")) {
    			if (plugin.getLowSkills().contains(skill)) {
    				playerDataMap.setWeaponSkillLow(skill);
		            sender.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.GRAY + "Low skill : "+ ChatColor.YELLOW + skill);
    			}
    		} 
    		
    		else if (skillRank.equalsIgnoreCase("high")) {
    			if (plugin.getHighSkills().contains(skill)) {
    				if (weaponPoints > 2) {
    					playerDataMap.setWeaponSkillHigh(skill);
    		            sender.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.GRAY + "High skill : " + ChatColor.YELLOW + skill);
    				}
    				else
    					sender.sendMessage(ChatColor.RED + "➤ " + ChatColor.GRAY + "Pas assez de maîtrise d'arme (Points requis : 2");
    			}
    			
    			else {
        			sender.sendMessage(ChatColor.RED + "➤ " + ChatColor.GRAY + "Nom de technique incorect, techniques disponibles : ");
        			for(String skills : plugin.getLowSkills())
        				sender.sendMessage(ChatColor.GRAY + skills + ChatColor.WHITE + " (low)");
        			for(String skills : plugin.getHighSkills())
        				sender.sendMessage(ChatColor.GRAY + skills + ChatColor.WHITE + " (high)");
    			}
    		}
    		
    		else 
    			sender.sendMessage(ChatColor.RED + "➤ " + ChatColor.GRAY + "/opc weaponskill <low|high> <Pseudo> <technique>");
    	}
    	
    	else
			sender.sendMessage(ChatColor.RED + "➤ " + ChatColor.GRAY + "Pas assez de maîtrise d'arme (Points minimum requis : 1 (low), 3 (high)");    	
    	
    	return false;
    }
}