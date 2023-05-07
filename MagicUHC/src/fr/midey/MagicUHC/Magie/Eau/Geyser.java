package fr.midey.MagicUHC.Magie.Eau;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
			if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() &&it.getItemMeta().getDisplayName().equalsIgnoreCase("§9Geyser") && it.getType().equals(Material.NETHER_STAR)) {
				Location ploc = p.getLocation();
				WaterCooldown cd = new WaterCooldown();
				cd.cooldown = 10;
				cd.task = Bukkit.getScheduler().runTaskTimer(main, () -> {
					Double x = (double) ploc.getBlockX();
					Double y = (double) ploc.getBlockY();
					Double z = (double) ploc.getBlockZ();
					
					Location plocFinal = new Location(ploc.getWorld(), x, y, z, ploc.getYaw(), ploc.getPitch());
					createColonne(plocFinal, 4, Material.WATER, Material.AIR, p);
					Bukkit.getScheduler().runTaskLater(main, () -> {
						createColonne(plocFinal, 6, Material.AIR, Material.WATER, p);
				}, 3);
				ploc.setY(ploc.getY() + 1);
				cd.cooldown--;
				if(cd.cooldown<= 0) cd.task.cancel();
				}, 0, 0);
			}
		//}
	}
	
	public void createColonne(Location center, int radius, Material to, Material from, Player player) {
		World world = center.getWorld();
		WaterCooldown cd = new WaterCooldown();
		int locX;
		int y = center.getBlockY();
		int locZ;
		for (int x = -2; x <= 2; x++) {
			for (int z = -2; z <= 2; z++) {
				locZ = center.getBlockZ() + z;
				locX = center.getBlockX() + x;
				if(x == 0 && z ==0) continue;
				if (center.distance(new Location(world, locX, y, locZ)) <= radius) {
					if (world.getBlockAt(locX, y, locZ).getType() == from) {
						cd.propulsePlayer(locX, y, locZ, 1, 0.5, player);
						world.getBlockAt(locX, y, locZ).setType(to);
					}
				}
			}
		}
	}	
}
