package fr.midey.starcraft.commands;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.midey.starcraft.Stats;
import fr.midey.starcraft.itemsPackage.ItemWand;
import fr.midey.starcraft.itemsPackage.ItemsConstructor;

public class CommandsStats implements CommandExecutor {

	private static Stats main;
	
	public CommandsStats(Stats main) {
		CommandsStats.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(msg.equalsIgnoreCase("wand")) {
			if(sender instanceof Player) {
				Player p = (Player)sender;
				ItemWand it = new ItemWand(main); 
				p.getInventory().addItem(it.getWand());
			}
		}
		
		/*Affiche les stats du joueur dans un menu*/
		if(msg.equalsIgnoreCase("stats")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				Inventory statsMenu = Bukkit.createInventory(p, 9, "Stats de " + p.getDisplayName());
				ItemsConstructor Stats = new ItemsConstructor(Material.SIGN);
				Stats.applyName("§6§lStatistiques");
				Stats.applyLore("§e§lGrade: §f" + main.getPlayerGrade().get(p.getUniqueId()));
				DecimalFormat decimalFormat = new DecimalFormat("#.##");
				Stats.applyLore("§aResistance §f: " + decimalFormat.format(main.getStatsControler().getResistancePlayer().get(p) * 100) + "%");
				Stats.applyLore("§cForce §f: " + decimalFormat.format(main.getStatsControler().getForcePlayer().get(p) * 100) + "%");
				Stats.applyLore("§bRapidité §f: " + decimalFormat.format((main.getStatsControler().getSpeedPlayer().get(p) -0.2)*1000/3) + "%");
				statsMenu.setItem(0, Stats.getItem());
				p.openInventory(statsMenu);
			}
			
		}
		
		/*Change les stats du joueur*/
		if(msg.equalsIgnoreCase("set")) {
			if(args.length != 0) {
				try {
					Float f = Float.parseFloat(args[2]);
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (player.getName().equals(args[0])) {
							if(args[1].equals("force")) {
								main.getStatsControler().getForcePlayer().replace(player, f/100);
								main.getDbUpdate().updateBDD(player);
								sender.sendMessage("Vous avez mis à jour la state de force de " + args[0]);
								return true;
							}
							else if(args[1].equals("speed")) {
								main.getStatsControler().getSpeedPlayer().replace(player, (3*f/1000+0.2f));
								main.getDbUpdate().updateBDD(player);
								sender.sendMessage("Vous avez mis à jour la state de speed de " + args[0]);
								return true;
							}
							else if(args[1].equals("resistance")) {
								main.getStatsControler().getResistancePlayer().replace(player, f/100);
								main.getDbUpdate().updateBDD(player);
								sender.sendMessage("Vous avez mis à jour la state de resistance de " + args[0]);
								return true;
							}
							else {
								sender.sendMessage("State invalide");
								return true;
							}
						}
						sender.sendMessage("/set [player] [state] [value]");
					}
					sender.sendMessage("§4Nom invalide");
					return true;
				} catch (NumberFormatException e) {
					sender.sendMessage("§4Nombre invalide");
					return true;
				}
			}
			else {
				sender.sendMessage("/set [player] [state] [value]");
				return true;
			}
		}
		return true;
	}
}
