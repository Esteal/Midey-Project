package fr.midey.OnePieceCraftSkills.Skills;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;

public class AddSkillPoints {

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
        			playerData.setHakiDesRoisLevel(Math.min(3, playerData.getHakiDesRoisLevel() + amount));
        			break;
	 	        case "Haki de l'armement":
	 	        	playerData.setHakiArmementLevel(Math.min(3, playerData.getHakiArmementLevel() + amount));
	 	        	break;
	 	        case "Haki de l'observation":
	 	        	playerData.setHakiObservationLevel(Math.min(3, playerData.getHakiObservationLevel() + amount));
	 	        	break;
	 	        case "demon slash":
	 	        	playerData.setDemonSlashLevel(Math.min(3, playerData.getDemonSlashLevel() + amount));
	 	        	break;
	            case "slash":
	            	playerData.setSlashLevel(Math.min(3, playerData.getSlashLevel() + amount));
	            	break;
	            case "incision":
	            	playerData.setIncisionLevel(Math.min(3, playerData.getIncisionLevel() + amount));
	            	break;
	            case "flambage shoot":
	            	playerData.setFlambageShootLevel(Math.min(3, playerData.getFlambageShootLevel() + amount));
	            	break;
	            case "pas de lune":
	            	playerData.setPasDeluneLevel(Math.min(3, playerData.getPasDeluneLevel() + amount));
	            	break;
	            default:
	            	break;
        	}
        	
		} catch (NumberFormatException e) {
            sender.sendMessage("Veuillez entrer un nombre valide !");
        }
	}

}
