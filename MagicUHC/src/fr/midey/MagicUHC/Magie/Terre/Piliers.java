package fr.midey.MagicUHC.Magie.Terre;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import fr.midey.MagicUHC.MagicUHC;
import fr.midey.MagicUHC.Nature;

public class Piliers implements Listener {

	private MagicUHC main;
	
	public Piliers(MagicUHC main) {
		this.main = main;
	}

	@EventHandler
	public void onPilier(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if(it == null) return;
		if(!main.game) return;
		Player p = e.getPlayer();
		if(main.getPlayerNature().get(p).equals(Nature.Terre)) {
			if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() &&it.getItemMeta().getDisplayName().equalsIgnoreCase("§6Pilier de pierre") && it.getType().equals(Material.NETHER_STAR)) {
				Location loc = p.getLocation();
				Vector v = loc.getDirection();
				v.multiply(8);
				loc.add(v);
				while(loc.getBlock().getType() == Material.AIR) {
					loc.setY(loc.getBlockY()-1);
					if(loc.getBlockY() <= 0) break;
				}
				loc.add(0, -1, 0);
				Material[] mat = { Material.STONE, Material.COBBLESTONE, Material.COBBLE_WALL, Material.GRAVEL };
				Vector newVec = p.getLocation().getDirection().multiply(2.5).setY(2);
				for(Player ps : Bukkit.getOnlinePlayers()) {
					if(isPlayerNearPillar(ps, loc, 3)) {
						ps.setVelocity(newVec);
					}
				}
				generatePillar(loc, 9, mat);
			}
		}
		return;
	}
	
	public void generatePillar(Location location, int height, Material[] materials) {
		Bukkit.getScheduler().runTaskLater(main, () -> {
			Random random = new Random();
			int radius = 3;
			int[] tab = {1, 3, 5};
			for (int y = 0; y < height; y++) {
				for (int x = -radius; x <= radius; x++) {
					for (int z = -radius; z <= radius; z++) {
						if (Math.sqrt(x*x + z*z) <= radius + random.nextDouble()) {
							int index = random.nextInt(materials.length);
							Material material = materials[index];
							location.clone().add(x, y, z).getBlock().setType(material);
						}
					}
				}
				for(int i = 0; i < tab.length; i++) {
					if(tab[i] == y) radius--;
				}
			}
		}, 2);
	}

	public boolean isPlayerNearPillar(Player player, Location pillarLocation, int radius) {
		Location playerLocation = player.getLocation();
		int pillarX = pillarLocation.getBlockX();
		int pillarY = pillarLocation.getBlockY();
		int pillarZ = pillarLocation.getBlockZ();
		int playerX = playerLocation.getBlockX();
		int playerY = playerLocation.getBlockY();
		int playerZ = playerLocation.getBlockZ();
		return (Math.abs(pillarX - playerX) <= radius) &&
				(Math.abs(pillarY - playerY) <= radius + 5) &&
				(Math.abs(pillarZ - playerZ) <= radius);
	}

}
