package fr.midey.buildrush.SetTeams;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.midey.buildrush.BuildRush;
import fr.midey.buildrush.tools.ItemsConstructor;

public class setTeams implements CommandExecutor {

	private BuildRush main;

	public setTeams(BuildRush main) {
		this.main = main;
	}

	//Commande ouvrant le menu pour changer le nombre de joueur d'une team
	//Exemple : 2v2, 3v3, 4v4
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = ((Player) sender);
			if(msg.equalsIgnoreCase("teams")) {
				//Créer l'inventaire qui permettra de modifier le nombre de team
				Inventory inv = Bukkit.createInventory(player, 9, "Teams");
				ItemsConstructor wool = new ItemsConstructor(Material.WOOL);
				int nb = main.getNumberPerTeam();
				wool.applyName("§b" + nb +"v" + nb);
				inv.setItem(4, wool.getItem());
				player.openInventory(inv);
			}
		}
		return false;
	}

}
