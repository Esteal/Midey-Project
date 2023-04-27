package fr.midey.starwarsbattle.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import fr.midey.starwarsbattle.Main;
import fr.midey.starwarsbattle.state.OPState;

public class Commands implements CommandExecutor {

	private static Main main;
	public Commands(Main main) {
		Commands.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		main.saveDefaultConfig();
		Player p = (Player) sender;
		if (msg.equalsIgnoreCase("opp")) {
			main.setOPState(OPState.OP, p);
			p.sendMessage("Tu peux désormais modifier les réglages de la partie !");
			
		}
		return false;
	}
	
	public static void onOp() {
		main.saveDefaultConfig();
		ConfigurationSection opSection = main.getConfig().getConfigurationSection("opPlayer");
		for (String opKey : opSection.getKeys(false)) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				if (p.getDisplayName().equalsIgnoreCase(opKey)) {
					main.setOPState(OPState.OP, p);
				}
			}
		}
	}

}
