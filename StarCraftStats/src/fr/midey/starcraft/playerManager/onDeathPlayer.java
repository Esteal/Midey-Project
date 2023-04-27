package fr.midey.starcraft.playerManager;

import java.util.List;
import java.util.ListIterator;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class onDeathPlayer implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		List<ItemStack> drops = e.getDrops();
		ListIterator<ItemStack> iterator = drops.listIterator();
		while (iterator.hasNext()) {
			ItemStack item = iterator.next();
			if (item.getType() == Material.BARRIER) {
				iterator.remove();
			}
		}
	}
}
