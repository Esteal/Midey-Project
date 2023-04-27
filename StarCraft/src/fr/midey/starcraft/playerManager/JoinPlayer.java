package fr.midey.starcraft.playerManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.midey.starcraft.items.itemConstructor;

public class JoinPlayer implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		itemConstructor it = new itemConstructor(Material.COMPASS);
		it.applyName("§4Menu");
		p.getInventory().setItem(4, it.getItem());
	}
}
