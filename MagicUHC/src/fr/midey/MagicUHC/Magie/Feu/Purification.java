package fr.midey.MagicUHC.Magie.Feu;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import fr.midey.MagicUHC.MagicUHC;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class Purification implements Listener {

	private MagicUHC main;

	public Purification(MagicUHC main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPurification(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if(it == null) return;
		//if(!main.game) return;
		Player p = e.getPlayer();
		//if(main.getPlayerNature().get(p).equals(Nature.Terre)) {
			if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() &&it.getItemMeta().getDisplayName().equalsIgnoreCase("§4Purification") && it.getType().equals(Material.NETHER_STAR)) {
				Location loc = p.getEyeLocation();
				Vector v = loc.getDirection();
				v.multiply(0.7);
				FireCooldown cd = new FireCooldown();
				cd.drawCross(loc, v, EnumParticle.FLAME, 2f, main, 0.3, 0.15);
			}
		//}
	}
	

}
