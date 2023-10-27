package fr.midey.NefaziaAtouts;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SimpleAtouts implements CommandExecutor {
	
	 @Override
	    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	        if (cmd.getName().equalsIgnoreCase("atouts") && sender instanceof Player) {
	            Player player = (Player) sender;

	            if (args.length != 2)
	                return true;

	            String atout = args[0].toLowerCase();
	            String action = args[1].toLowerCase();

	            if (action.equals("enable")) {
	                switch (atout) {
	                    case "speed":
	                    	if (player.hasPermission("atouts.speed.enable"))
	                    		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
	                        break;
	                    case "force":
	                    	if (player.hasPermission("atouts.force.enable"))
	                    		player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0, false, false));
	                        break;
	                    case "fireres":
	                    	if (player.hasPermission("atouts.fireres.enable"))
	                    		player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
	                        break;
	                    default:
	                    	break;
	                }
	            } else if (action.equals("disable")) {
	                switch (atout) {
	                    case "speed":
	                    	if (player.hasPermission("atouts.speed.disable"))
	                    		player.removePotionEffect(PotionEffectType.SPEED);
	                        break;
	                    case "force":
	                    	if (player.hasPermission("atouts.force.disable"))
	                    		player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
	                        break;
	                    case "fireres":
	                    	if (player.hasPermission("atouts.fireres.disable"))
	                    		player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
	                        break;
	                    default:
	                    	break;
	                }
	            }
	            return true;
	        }

	        return false;
	    }
}
