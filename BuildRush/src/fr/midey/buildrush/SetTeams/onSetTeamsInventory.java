package fr.midey.buildrush.SetTeams;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.midey.buildrush.BuildRush;
import fr.midey.buildrush.tools.ItemsConstructor;

public class onSetTeamsInventory implements Listener {

	private BuildRush main;

	public onSetTeamsInventory(BuildRush main) {
		this.main = main;
	}
	
	
	//Modifie le nombre de personne par team
	//Exemple : 1v1, 2v2, 3v3, 4v4
	@EventHandler
	public void onClickSetTeamsInventory(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
		ItemStack GetClickIT = event.getCurrentItem();
		if(GetClickIT == null) return;
		if(inv.getName().equalsIgnoreCase("Teams")) {
			int nb = main.getNumberPerTeam();
			if (GetClickIT.getType() == Material.WOOL && GetClickIT.hasItemMeta() && GetClickIT.getItemMeta().getDisplayName().equalsIgnoreCase("§b" + nb +"v" + nb)) {
				event.setCancelled(true);
				InventoryAction action = event.getAction();
				if(action == InventoryAction.PICKUP_ALL && nb < 4) main.setNumberPerTeam(nb + 1);
				else if(action == InventoryAction.PICKUP_HALF && nb > 1) main.setNumberPerTeam(nb - 1);
				ItemsConstructor newIT = new ItemsConstructor(Material.WOOL);
				nb = main.getNumberPerTeam();
				newIT.applyName("§b" + nb +"v" + nb);
				inv.setItem(4, newIT.getItem());
				Player player = (Player) event.getWhoClicked();
				player.updateInventory();
			}
		}
	}
}
