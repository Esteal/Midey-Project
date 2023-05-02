package fr.midey.MagicUHC.Magie.Eau;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
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
		//if(main.getPlayerNature().get(p).equals(Nature.Terre)) {
			if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() &&it.getItemMeta().getDisplayName().equalsIgnoreCase("�9Tsunami") && it.getType().equals(Material.NETHER_STAR)) {
				Location ploc = p.getLocation();
				Vector pvec = ploc.getDirection();
				WaterCooldown cd = new WaterCooldown();
				cd.cooldownTsunami = 10;
				cd.multiplicateur = 3;
				cd.tsunami = Bukkit.getScheduler().runTaskTimer(main, () -> {
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
				cd.cooldownTsunami--;
				if(cd.cooldownTsunami <= 0) cd.tsunami.cancel();
				}, 0, 4);
				
			}
		//}
	}
	
	public void createSphere(Location center, int radius, Material to, Material from, Player player) {
		World world = center.getWorld(); // Obtenir le monde dans lequel le joueur se trouve

		// Parcourir tous les blocs dans la zone d�finie par le rayon autour de la position du joueur
		for (int x = center.getBlockX() - radius; x <= center.getBlockX() + radius; x++) {
			for (int y = center.getBlockY() - radius; y <= center.getBlockY() + radius; y++) {
				for (int z = center.getBlockZ() - radius; z <= center.getBlockZ() + radius; z++) {
					// V�rifier si le bloc est � l'int�rieur du rayon sph�rique
					if (center.distance(new Location(world, x, y, z)) <= radius) {
						// V�rifier si le bloc est de type AIR, pour �viter de remplacer les blocs existants
						if (world.getBlockAt(x, y, z).getType() == from) {
							// D�finir le bloc � la position actuelle comme �tant de la terre
							for(Player p : Bukkit.getOnlinePlayers()) {
								Location locs = p.getLocation();
								//if(p == player) continue;
								if(locs.getBlockX() == x
									&& locs.getBlockY() == y
									&& locs.getBlockZ() == z) {
									p.playEffect(EntityEffect.HURT);
									if(p.getHealth() - 0.5 <= 0) p.setHealth(0);
									else p.setHealth(p.getHealth() - 0.5);
									p.setVelocity(locs.getDirection().multiply(-1));
								}
							}
							world.getBlockAt(x, y, z).setType(to);
						}
					}
				}
			}
		}
	}
}
