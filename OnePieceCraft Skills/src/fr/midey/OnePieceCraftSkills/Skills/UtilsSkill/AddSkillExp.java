package fr.midey.OnePieceCraftSkills.Skills.UtilsSkill;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;

public class AddSkillExp {

	public void onCommand(CommandSender sender, Command command, String label, String[] args, OnePieceCraftSkills plugin) {
		
		try {
            int amount = Integer.parseInt(args[1]);
            
            StringBuilder skillBuilder = new StringBuilder();
    		for (int i = 2; i < args.length - 1; i++) {
    			skillBuilder.append(args[i]);
    			if (i != args.length - 2) {
    				skillBuilder.append(" ");
    			}
    		}
        	String skill = skillBuilder.toString();
    		Player player = Bukkit.getPlayerExact(args[args.length - 1]);
    		
    		PlayerData playerData = plugin.getPlayerData(player);
    		
        	switch (skill) {
        		case "Haki des rois":
        			playerData.setChanceToHakiDesRoisLevelUp(Math.min(100, playerData.getChanceToHakiDesRoisLevelUp() + amount));
        			break;
	 	        case "Haki de l'armement":
	 	        	playerData.setChanceToHakiArmementLevelUp(Math.min(100, playerData.getChanceToHakiArmementLevelUp() + amount));
	 	        	break;
	 	        case "Haki de l'observation":
	 	        	playerData.setChanceToHakiObservationLevelUp(Math.min(100, playerData.getChanceToHakiObservationLevelUp() + amount));
	 	        	break;
	 	        case "demon slash":
	 	        	playerData.setChanceToDemonSlashLevelUp(Math.min(100, playerData.getChanceToDemonSlashLevelUp() + amount));
	 	        	break;
	            case "slash":
	            	playerData.setChanceToSlashLevelUp(Math.min(100, playerData.getChanceToSlashLevelUp() + amount));
	            	break;
	            case "incision":
	            	playerData.setChanceToIncisionLevelUp(Math.min(100, playerData.getChanceToIncisionLevelUp() + amount));
	            	break;
	            case "flambage shoot":
	            	playerData.setChanceToFlambageShootLevelUp(Math.min(100, playerData.getChanceToFlambageShootLevelUp() + amount));
	            	break;
	            case "pas de lune":
	            	playerData.setChanceToPasDeluneLevelUp(Math.min(100, playerData.getChanceToPasDeluneLevelUp() + amount));
	            	break;
	            default:
	            	break;
        	}
        	
		} catch (NumberFormatException e) {
            sender.sendMessage("Veuillez entrer un nombre valide !");
        }
	}
}
