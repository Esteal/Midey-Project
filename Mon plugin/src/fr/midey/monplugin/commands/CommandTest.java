package fr.midey.monplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandTest implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			if(cmd.getName().equalsIgnoreCase("test")) {
				player.sendMessage("Test réussi §7!");
				return true;
			}
			
			if(cmd.getName().equalsIgnoreCase("alert")){
				
				//alert --> aucun argument
				if(args.length == 0){
					player.sendMessage("la commande est : §4/alert <message> !");
				}
				
				//alert <texte ...>
				
				if(args.length >= 1) {
					
					StringBuilder bc = new StringBuilder();
					for(String part : args) {
						bc.append(part + " ");
						
					}
					Bukkit.broadcastMessage("§4[Broadcast]§f " + bc);
				}
				return true;
			}
		}
		return false;
	}

}