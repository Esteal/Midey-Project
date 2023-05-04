package fr.midey.MagicUHC.Magie.Eau;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import fr.midey.MagicUHC.MagicUHC;

public class Geyser implements Listener {

	private MagicUHC main;
	
	public Geyser(MagicUHC main) {
		this.main = main;
	}
	
	@EventHandler
	public void onGeyser(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if(it == null) return;
		//if(!main.game) return;
		Player p = e.getPlayer();
		//if(main.getPlayerNature().get(p).equals(Nature.Eau)) {
			if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() &&it.getItemMeta().getDisplayName().equalsIgnoreCase("�9Geyser") && it.getType().equals(Material.NETHER_STAR)) {
				Location ploc = p.getLocation();
				World world = ploc.getWorld();
				Vector v = ploc.getDirection();
				v.multiply(8);
				Location newLoc = new Location(world, v.getX() + ploc.getBlockX(), v.getY() + ploc.getBlockY(), v.getZ() + ploc.getBlockZ());
				WaterCooldown cd = new WaterCooldown();
				cd.cooldown = 10;
				while(newLoc.getBlock().getType() == Material.AIR) {
					newLoc.setY(newLoc.getBlockY() - 1);
				}
				cd.task = Bukkit.getScheduler().runTaskTimer(main, () -> {
					if(newLoc.getBlock().getType() == Material.AIR) {
						world.getBlockAt(newLoc).setType(Material.WATER);
					}
					p.sendMessage("y : " + newLoc.getBlockY());
					newLoc.setY(newLoc.getBlockY() + 1);
					cd.cooldown--;
					Bukkit.getScheduler().runTaskLater(main, () -> {
						Block block = world.getBlockAt(newLoc);
						int x = block.getLocation().getBlockX();
						int z = block.getLocation().getBlockZ();
						int y = block.getLocation().getBlockY();
						Location locBlock = new Location(world, x, y - 3, z);
						Block blockFinal = world.getBlockAt(locBlock);
						if(blockFinal.getType() == Material.WATER || blockFinal.getType() == Material.STATIONARY_WATER) {
							blockFinal.setType(Material.AIR);
						}
					}, 3);
					if(cd.cooldown <= 3) {
						Bukkit.getScheduler().runTaskLater(main, () -> {
							Block block = world.getBlockAt(newLoc);
							int x = block.getLocation().getBlockX();
							int z = block.getLocation().getBlockZ();
							int y = block.getLocation().getBlockY();
							Location locBlock = new Location(world, x, y - cd.cooldown - 1, z);
							Block blockFinal = world.getBlockAt(locBlock);
							if(blockFinal.getType() == Material.WATER || blockFinal.getType() == Material.STATIONARY_WATER) {
								blockFinal.setType(Material.AIR);
								p.sendMessage("caca");
							}
						}, 3);
					}
					if(cd.cooldown <= 0) cd.task.cancel();
				}, 0, 0);
				Bukkit.getScheduler().runTaskLater(main, () -> {
					if(newLoc.getBlock().getType() == Material.WATER) {
						world.getBlockAt(newLoc).setType(Material.AIR);
					}
				}, 15);
			//}
		}
	}
}
