package fr.midey.StarCraft.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.midey.StarCraft.Main;
import fr.midey.StarCraft.items.constructorItem;

public class playerManager implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		constructorItem it = new constructorItem(Material.COMPASS);
		it.applyName("§4Menu");
		p.getInventory().setItem(3, it.getItem());
		/*Charger ses stats depuis la BDD*/
	}
}
