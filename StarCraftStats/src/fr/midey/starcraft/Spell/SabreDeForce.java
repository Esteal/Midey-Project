package fr.midey.starcraft.Spell;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import fr.midey.starcraft.Stats;
import fr.midey.starcraft.formParticle.Cross;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class SabreDeForce implements Listener {

	private Stats main;
	
	public SabreDeForce(Stats main) {
		this.main = main;
	}
	
	@EventHandler
	public void onShield(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if (it == null) return;
		if(it.getType() != Material.BARRIER) return;
		Action a = e.getAction();
		String itName = it.getItemMeta().getDisplayName();
		if(itName == null) return;
		if((itName.equalsIgnoreCase("§lSabre de force")) && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)) {
			Player p = e.getPlayer();
			if(main.getPlayerGrade().get(p.getUniqueId()).equalsIgnoreCase("Maître jedi")) {
				if(main.getCooldownSabre().containsKey(p)) {
					p.sendMessage("Vous devez attendre §6" + main.getCooldownSabre().get(p) + "§es§r avant de pouvoir utiliser cette compétence !");;
					return;
				}
				if(!p.getDisplayName().equalsIgnoreCase("Midey1901")) main.getCooldownSabre().put(p, 60 * 5);
				
				Location loc = p.getLocation();
				Vector v = loc.getDirection();
				BukkitTask task = Bukkit.getScheduler().runTaskTimer(main, new Runnable() {

					@Override
					public void run() {
						Cross croix = new Cross(loc, v, EnumParticle.CLOUD, 2f, main);
						croix.drawCross();
						v.multiply(1.1);
					}
					
				}, 0, 1);
				
				Bukkit.getScheduler().runTaskLater(main, new Runnable() {
					@Override
					public void run() {
						task.cancel();
					}
				}, 20);
			}
		}
	}
}
