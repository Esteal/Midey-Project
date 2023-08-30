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
import org.bukkit.util.Vector;

import fr.midey.MagicUHC.MagicUHC;
import fr.midey.MagicUHC.Nature;

public class DashAquatique implements Listener {
	
	private MagicUHC main;
	private Integer manaCost = 100;
	
	public DashAquatique(MagicUHC main) {
		this.main = main;
	}

	@EventHandler
	public void onDashAquatique(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if(it == null) return;
		if(!main.game) return;
		Player p = e.getPlayer();
		if(main.getPlayerNature().get(p).equals(Nature.Eau)) {
			if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() &&it.getItemMeta().getDisplayName().equalsIgnoreCase("§9Dash aquatique") && it.getType().equals(Material.NETHER_STAR)) {
				if(main.getPlayerMana().get(p) > manaCost) {
					main.getPlayerMana().replace(p, main.getPlayerMana().get(p) - manaCost);
					Location ploc = p.getLocation();
					Vector pvec = ploc.getDirection();
					WaterCooldown cd = new WaterCooldown();
					cd.cooldown = 5;
					cd.task = Bukkit.getScheduler().runTaskTimer(main, () -> {
						Double x = ploc.getX() + pvec.getX();
						Double y = ploc.getY() + pvec.getY();
						Double z = ploc.getZ() + pvec.getZ();
						Location plocFinal = new Location(ploc.getWorld(), x, y, z, ploc.getYaw(), ploc.getPitch());
						createWave(plocFinal, 4, Material.WATER, Material.AIR, p);
						Bukkit.getScheduler().runTaskLater(main, () -> {
							createWave(plocFinal, 6, Material.AIR, Material.WATER, p);
					}, 3);
					cd.cooldown--;
					pvec.add(pvec);
					if(cd.cooldown<= 0) cd.task.cancel();
					}, 0, 1);
				}
				else
					p.sendMessage("Il vous manque §e" + (manaCost - main.getPlayerMana().get(p)) + "§6 mana");
			}
		}
	}
	
	public void createWave(Location center, int radius, Material to, Material from, Player player) {
		World world = center.getWorld();
		WaterCooldown cd = new WaterCooldown();
		int locX;
		int locY;
		int locZ;
		for(int y = -2; y <= 2; y++) {
			for (int x = -2; x <= 2; x++) {
				for (int z = -2; z <= 2; z++) {
					locZ = center.getBlockZ() + z;
					locY = center.getBlockY() + y;
					locX = center.getBlockX() + x;
					cd.followLocation(locX, locY, locZ, center, player, 0, 1.5);
					if (center.distance(new Location(world, locX, locY, locZ)) <= radius) {
						if (world.getBlockAt(locX, locY, locZ).getType() == from) {
							world.getBlockAt(locX, locY, locZ).setType(to);
						}
					}
				}
			}
		}
	}	
}
