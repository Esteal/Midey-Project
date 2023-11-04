package fr.midey.OnePieceCraftSkills.Skills.UtilsSkill;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.Utils.SequenceManager;

public class SkillCommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, OnePieceCraftSkills plugin) {
    	
    	if (args.length >= 4 && args[0].equalsIgnoreCase("setSkill")) {
    		String sequence = args[1];
                    
    		StringBuilder skillBuilder = new StringBuilder();
    		for (int i = 2; i < args.length - 1; i++) {
    			skillBuilder.append(args[i]);
    			if (i != args.length - 2) {
    				skillBuilder.append(" ");
    			}
    		}
        	String skill = skillBuilder.toString();
    		Player player = Bukkit.getPlayerExact(args[args.length - 1]);
    		
    		new SequenceManager(plugin).setSequenceForSkill(plugin, player, sequence, skill);        
    		return true;
    	}
    	return false;
    }
}
