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

import fr.midey.starcraft.Stats;
import fr.midey.starcraft.formParticle.Circle;
import fr.midey.starcraft.formParticle.Stars;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class DrainDeVie implements Listener {

	private Stats main;
	
	public DrainDeVie(Stats main) {
		this.main = main;
	}
	
	@EventHandler
	public void onDrainDeVie(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if (it == null) return;
		if(it.getType() != Material.BARRIER) return;
		Action a = e.getAction();
		String itName = it.getItemMeta().getDisplayName();
		if(itName == null) return;
		if((itName.equalsIgnoreCase("§lDrain de vie")) && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)) {
			
			Player p = e.getPlayer();
			if(main.getPlayerGrade().get(p.getUniqueId()).equalsIgnoreCase("Seigneur sith")) {
				if(main.getCooldownDrainDeVie().containsKey(p)) {
					p.sendMessage("Vous devez attendre §6" + main.getCooldownDrainDeVie().get(p) + "§es§r avant de pouvoir utiliser cette compétence !");;
					return;
				}
				if (!p.getDisplayName().equalsIgnoreCase("Arzodiacc")) main.getCooldownDrainDeVie().put(p, 60*5);
				
				Location loc = p.getLocation();
				BukkitTask task = Bukkit.getScheduler().runTaskTimer(main, new Runnable() {
					@Override
					public void run() {
						Stars stars = new Stars(loc, EnumParticle.FOOTSTEP);
						Circle circle = new Circle(loc, EnumParticle.FOOTSTEP, p, main, 7, "Drain de vie");
						stars.drawStars();
						circle.drawCircle();
					}		
				}, 0, 5);
				
				Bukkit.getScheduler().runTaskLater(main, new Runnable() {
					@Override
					public void run() {
						task.cancel();
					}
				}, 100);
			}
		}
		
	}
}
