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

public class Tsunami implements Listener {

	private MagicUHC main;
	
	
	public Tsunami(MagicUHC main) {
		this.main = main;
	}
	
	@EventHandler
	public void onTsunami(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if(it == null) return;
		//if(!main.game) return;
		Player p = e.getPlayer();
		//if(main.getPlayerNature().get(p).equals(Nature.Eau)) {
			if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() &&it.getItemMeta().getDisplayName().equalsIgnoreCase("§9Tsunami") && it.getType().equals(Material.NETHER_STAR)) {
				Location ploc = p.getLocation();
				Vector pvec = ploc.getDirection();
				WaterCooldown cd = new WaterCooldown();
				cd.cooldown = 10;
				cd.multiplicateur = 3;
				cd.task = Bukkit.getScheduler().runTaskTimer(main, () -> {
					pvec.multiply(cd.multiplicateur);
					cd.multiplicateur = cd.multiplicateur/1.25;
					Double x = ploc.getX() + pvec.getX();
					Double y = ploc.getY() + pvec.getY();
					Double z = ploc.getZ() + pvec.getZ();
					Location plocFinal = new Location(ploc.getWorld(), x, y, z, ploc.getYaw(), ploc.getPitch());
					createSphere(plocFinal, 4, Material.WATER, Material.AIR, p);
					Bukkit.getScheduler().runTaskLater(main, () -> {
						createSphere(plocFinal, 6, Material.AIR, Material.WATER, p);
				}, 5);
				cd.cooldown--;
				if(cd.cooldown<= 0) cd.task.cancel();
				}, 0, 4);
				
			}
		//}
	}
	
	public void createSphere(Location center, int radius, Material to, Material from, Player player) {
		World world = center.getWorld(); // Obtenir le monde dans lequel le joueur se trouve
		WaterCooldown cd = new WaterCooldown();
		// Parcourir tous les blocs dans la zone définie par le rayon autour de la position du joueur
		for (int x = center.getBlockX() - radius; x <= center.getBlockX() + radius; x++) {
			for (int y = center.getBlockY() - radius; y <= center.getBlockY() + radius; y++) {
				for (int z = center.getBlockZ() - radius; z <= center.getBlockZ() + radius; z++) {
					// Vérifier si le bloc est à l'intérieur du rayon sphérique
					if (center.distance(new Location(world, x, y, z)) <= radius) {
						// Vérifier si le bloc est de type AIR, pour éviter de remplacer les blocs existants
						if (world.getBlockAt(x, y, z).getType() == from) {
							// Définir le bloc à la position actuelle comme étant de la terre
							cd.followLocation(x, y, z, center, player, 1, 1.25);
							world.getBlockAt(x, y, z).setType(to);
						}
					}
				}
			}
		}
	}
}

