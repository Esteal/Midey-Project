package fr.midey.MagicUHC.Magie.Terre;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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

public class Faille implements Listener {

	private MagicUHC main;

	public Faille(MagicUHC main) {
		this.main = main;
	}
	
	@EventHandler
	public void onFaille(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if(it == null) return;
		if(!main.game) return;
		Player p = e.getPlayer();
		if(main.getPlayerNature().get(p).equals(Nature.Terre)) {
			if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() &&it.getItemMeta().getDisplayName().equalsIgnoreCase("§6Choc sismique") && it.getType().equals(Material.NETHER_STAR)) {
				breakBlocksAroundPlayer(p);
			}
		}
	}

	public void breakBlocksAroundPlayer(Player player) {
		Location loc = player.getLocation();
		Vector v = loc.getDirection();
		v.setY(loc.getBlockY());
		v.multiply(2.5);
		for(int i = 0; i<10;i++) {
			for(int x1 = -1; x1 <= 1; x1++) {
				for(int z1 = -1; z1 <= 1; z1++) {
					int x = v.getBlockX() + loc.getBlockX() + x1;
					int y = loc.getBlockY() - 1;
					int z = v.getBlockZ() + loc.getBlockZ() + z1;
					Location BlockLocation = new Location(loc.getWorld(), x, y, z);
					Location BlockLocationUp = new Location(loc.getWorld(), x, y + 1, z);
					Location BlockLocationDown = new Location(loc.getWorld(), x, y - 1, z);
					Block blockUp = BlockLocationUp.getBlock();
					Block blockDown = BlockLocationDown.getBlock();
					Block block = BlockLocation.getBlock();
					while(blockUp.getType() != Material.AIR) {
						BlockLocation = BlockLocationUp; 
						block = blockUp;
						y++;
						BlockLocationUp = new Location(loc.getWorld(), x, y + 1, z);
						blockUp = BlockLocationUp.getBlock();
						if (y >= 240) break;
					}
					while(block.getType() == Material.AIR) {
						BlockLocation = BlockLocationDown; 
						block = blockDown;
						y--;
						BlockLocationDown = new Location(loc.getWorld(), x, y - 1, z);
						blockDown = BlockLocationDown.getBlock();
						if (y <= 4) break;
					}
					if(block.getType() != Material.AIR || block.getType() != Material.GRASS) {
						@SuppressWarnings("deprecation")
						FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(BlockLocation, block.getType(), (byte) 0);
						fallingBlock.setVelocity(new Vector(0, 0.25, 0));
						block.setType(Material.AIR);
						for(Player ps : Bukkit.getOnlinePlayers()) {
							if(ps == player) continue;
							Location locs = ps.getLocation();
							if((locs.getBlockX() == x) 
									&& (locs.getBlockY() == y + 1) 
									&& (locs.getBlockZ() == z)) {
								Random rdm = new  Random();
								double xRdm = rdm.nextDouble() * 2;
								double zRdm = rdm.nextDouble() * 2;
								ps.setVelocity(new Vector(xRdm - 1, 1.5, zRdm - 1));
								if(ps.getHealth() - 8 <= 0) ps.setHealth(0);
								else ps.setHealth(ps.getHealth() - 8);
							}
						}
					}
				}
			}
			v.multiply(1.25);
		}
	}



}
