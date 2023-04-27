package fr.midey.starcraft.menuManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.midey.starcraft.Main;

public class clickMenuInventory implements Listener {
	
	private Main main;
	
	public clickMenuInventory(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onClickMenuInventory(InventoryClickEvent e) {
		if(!e.getInventory().getName().equalsIgnoreCase("§4Menu principal")) return;
		Player p = (Player) e.getWhoClicked();
		ItemStack it = e.getCurrentItem();
		if(it.getType() == Material.AIR) return;
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		e.setCancelled(true);
		switch(it.getItemMeta().getDisplayName()) {
			case "§aCorucentre":
				out.writeUTF("Connect");
				out.writeUTF("Corucentre");
				p.sendPluginMessage(main, "BungeeCord", out.toByteArray());
				break;
			case "§4Korriban":
				out.writeUTF("Connect");
				out.writeUTF("Korriban");
				p.sendPluginMessage(main, "BungeeCord", out.toByteArray());
				break;
			case "§9Yavin":
				out.writeUTF("Connect");
				out.writeUTF("Yavin");
				p.sendPluginMessage(main, "BungeeCord", out.toByteArray());
				break;
			default: break;
		}
	}
}
