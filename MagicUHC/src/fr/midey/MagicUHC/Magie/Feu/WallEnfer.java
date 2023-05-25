package fr.midey.MagicUHC.Magie.Feu;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import fr.midey.MagicUHC.MagicUHC;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class WallEnfer implements Listener {
	
	private MagicUHC main;

	public WallEnfer(MagicUHC main) {
		this.main = main;
	}

	@EventHandler
	public void onWallEnfer(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if(it == null) return;
		//if(!main.game) return;
		Player p = e.getPlayer();
		//if(main.getPlayerNature().get(p).equals(Nature.Feu)) {
			if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() &&it.getItemMeta().getDisplayName().equalsIgnoreCase("§4Mur des enfers") && it.getType().equals(Material.NETHER_STAR)) {
				FireCooldown cd = new FireCooldown();
				cd.timer = 20;
				Location loc = p.getLocation();
				cd.task = Bukkit.getScheduler().runTaskTimer(main, () -> {
					Vector v = loc.getDirection();
					Block bloc = loc.getBlock();
					while(bloc.getType() == Material.AIR) {
						loc.setY(loc.getY() - 1);
						bloc = loc.getBlock();
					}
					double blocFeet = loc.getY();
					v.multiply(1);
					loc.setY(loc.getY() + 5);
					for(double i = loc.getY(); i> blocFeet; i-=0.1) {
						cd.drawWall(loc, v, EnumParticle.FLAME, 2f, main, 0.5, 0);
						loc.setY(loc.getY() - 0.1);
					}
					cd.timer--;
					if(cd.timer <=0) cd.task.cancel();
				}, 0, 5);

			}
		//}
	}

}
