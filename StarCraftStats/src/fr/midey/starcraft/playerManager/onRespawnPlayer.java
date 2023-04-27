package fr.midey.starcraft.playerManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import fr.midey.starcraft.Stats;
import fr.midey.starcraft.itemsPackage.ItemWand;

public class onRespawnPlayer implements Listener {

	private Stats main;
	
	public onRespawnPlayer(Stats main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		ItemStack barrier = new ItemWand(main).getWand();
		p.getInventory().addItem(barrier);
	}

}
