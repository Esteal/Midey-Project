package fr.midey.MagicUHC.Magie.Terre;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import fr.midey.MagicUHC.MagicUHC;
import fr.midey.MagicUHC.Nature;

public class Seisme implements Listener {

	private MagicUHC main;
	
	public Seisme(MagicUHC main) {
		this.main = main;
	}

	@EventHandler
	public void onPilier(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if(it == null) return;
		if(!main.game) return;
		Player p = e.getPlayer();
		if(main.getPlayerNature().get(p).equals(Nature.Terre)) {
			if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() &&it.getItemMeta().getDisplayName().equalsIgnoreCase("§6Séisme") && it.getType().equals(Material.NETHER_STAR)) {
				Location loc = p.getLocation();
				p.playSound(loc, Sound.EXPLODE, 1f, 1f);
				removeBlocksAroundPlayer(p);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void removeBlocksAroundPlayer(Player player) {
		int radius = 3;
		Location playerLocation = player.getLocation();
		int playerX = playerLocation.getBlockX();
		int playerY = playerLocation.getBlockY() + 2;
		int playerZ = playerLocation.getBlockZ();
		for (int x = playerX - radius; x <= playerX + radius; x++) {
			for (int y = playerY - radius; y <= playerY + radius - 2; y++) {
				for (int z = playerZ - radius; z <= playerZ + radius; z++) {
					Location blockLocation = new Location(player.getWorld(), x, y, z);
					Block block = blockLocation.getBlock();
					//Boucle pour test si un joueur est dans la zone
					for(Player ps : Bukkit.getOnlinePlayers()) {
						Location locs = ps.getLocation();
						if( ps == player) {
							player.setVelocity(player.getLocation().getDirection().multiply(1).setY(1));
							continue;
						}
						if((locs.getBlockX() == x)
								&& (locs.getBlockY() == y)
								&& (locs.getBlockZ() == z)) {
							ps.setVelocity(ps.getLocation().getDirection().multiply(2).setY(2));
							if(ps.getHealth() - 6 <= 0) ps.setHealth(0);
							else ps.setHealth(ps.getHealth() - 6);
						}
					}
					//Envoie les blocs en l'air
					if (block.getType() != Material.AIR) {
						FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(blockLocation.add(0.5, 0.5, 0.5), block.getType(), (byte) 0);
						Random rdm = new  Random();
						double xRdm = rdm.nextDouble() * 2;
						double zRdm = rdm.nextDouble() * 2;
						fallingBlock.setVelocity(new Vector(xRdm - 1, 1, zRdm - 1));
						block.breakNaturally();
					}
				}
			}
		}
	}
}
