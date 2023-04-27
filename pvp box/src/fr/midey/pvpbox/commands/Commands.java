package fr.midey.pvpbox.commands;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import fr.midey.pvpbox.stuff.Stuff;

public class Commands implements CommandExecutor, Listener {

	public static Location arene = new Location(Bukkit.getWorld("world"), 0, 5, 0);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		// TODO Auto-generated method stub
		//Sender => personne qui execute la commande (console comme joueur)
		//cmd => commande execute (/kit par exemple)
		//msg => message de la commande (si /kit alors msg = kit)
		//args => toute la chaîne que va comporter la commande hormis la commande (si /kit archer alors args[0] = archer)
		
		Player player = (Player) sender;
		
		if(sender instanceof Player && msg.equalsIgnoreCase("kit")) {
			if(args.length == 0) {
				sender.sendMessage("Kits disponibles: archer berserker monk assassin");
				return true;
			}
			else if(args[0].equalsIgnoreCase("archer") && sender instanceof Player && msg.equalsIgnoreCase("kit")) {
				Stuff.archer(player);
				return true;
			}
			else if(args[0].equalsIgnoreCase("berserker") && sender instanceof Player && msg.equalsIgnoreCase("kit")) {
				Stuff.berserker(player);
				return true;
			}
			else if(args[0].equalsIgnoreCase("monk") && sender instanceof Player && msg.equalsIgnoreCase("kit")) {
				Stuff.monk(player);
				return true;
			}
			else if(args[0].equalsIgnoreCase("assassin") && sender instanceof Player && msg.equalsIgnoreCase("kit")) {
				Stuff.assassin(player);
				return true;
			}
		}
		else if(sender instanceof Player && msg.equalsIgnoreCase("set")) {
			
			if(args.length == 0) {
				player.sendMessage("/set <arena>");
				return true;
			}
			else if(args[0].equalsIgnoreCase("arena")) {
				arene = player.getLocation();
				player.sendMessage("Vous venez de définir un nouveau point d'apparition pour l'arêne");
				return true;
			}
		}
		
		else if(args.length == 0 && sender instanceof Player && msg.equalsIgnoreCase("feed")) {
			player.setFoodLevel(20);
			player.sendMessage("Vous avez été rassasié");
			return true;
		}
		else if(sender instanceof Player && msg.equalsIgnoreCase("health")) {
			if(args.length == 0) {
				player.sendMessage("/health <vie>");
			}
			else {
				Double t = Double.parseDouble(args[0]); 
				player.setHealth(t);
			}
		}
		return false;
	}
}
