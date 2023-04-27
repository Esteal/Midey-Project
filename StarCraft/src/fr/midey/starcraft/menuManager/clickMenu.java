package fr.midey.starcraft.menuManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.midey.starcraft.items.itemConstructor;

public class clickMenu implements Listener {

	@EventHandler
	public void onClickCompass(PlayerInteractEvent e) {
		
		if(e.getItem() == null) return;
		Player p = e.getPlayer();
		ItemStack it = e.getItem();
		if(it.getType() == Material.COMPASS && it.getItemMeta().getDisplayName().equalsIgnoreCase("§4MENU")) {
			Action a = e.getAction();
			if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
				
				Inventory inv = Bukkit.createInventory(null, 45, "§4Menu principal");
				
				itemConstructor coru = new itemConstructor(Material.GLASS);
				coru.applyName("§aCorucentre");
				coru.applyLore("§7Planète neutre dans le conflit");
				coru.applyLore("§7Vous y trouverez un spawn, une warzone");
				coru.applyLore("§7Ainsi que pleins de mystères utiles à votre aventure");
				inv.setItem(15, coru.getItem());
				
				itemConstructor kori = new itemConstructor(Material.GLASS);
				kori.applyName("§4Korriban");
				kori.applyLore("§7Planète des siths");
				kori.applyLore("§7Les jedis ne seront pas les bienvenus");

				inv.setItem(16, kori.getItem());
				
				itemConstructor  yavin = new itemConstructor(Material.GLASS);
				yavin.applyName("§9Yavin");
				yavin.applyLore("§7Planète des jedis");
				yavin.applyLore("§7Les siths ne seront pas les bienvenus");
				inv.setItem(17, yavin.getItem());
				p.openInventory(inv);
			}
		}
	}
}
