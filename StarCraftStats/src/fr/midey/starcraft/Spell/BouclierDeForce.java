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
import net.minecraft.server.v1_8_R3.EnumParticle;

public class BouclierDeForce implements Listener {

	private Stats main;
	
	public BouclierDeForce(Stats main) {
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
		if((itName.equalsIgnoreCase("§lBouclier de force")) && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)) {
			Player p = e.getPlayer();
			if(main.getPlayerGrade().get(p.getUniqueId()).equalsIgnoreCase("Chevalier jedi") || main.getPlayerGrade().get(p.getUniqueId()).equalsIgnoreCase("Maître jedi")) {
				if(main.getCooldownBouclier().containsKey(p)) {
					p.sendMessage("Vous devez attendre §6" + main.getCooldownBouclier().get(p) + "§es§r avant de pouvoir utiliser cette compétence !");;
					return;
				}
				main.getCooldownBouclier().put(p, 60);
				
				BukkitTask task = Bukkit.getScheduler().runTaskTimer(main, new Runnable() {
					@Override
					public void run() {
						float radius = 1;
						for (float y = 0; y < 2.3; y+=0.1) {
							Location loc = p.getLocation();
							loc.setY(loc.getY() + y);
							if(y < 2) {
								Circle circle = new Circle(loc, EnumParticle.ENCHANTMENT_TABLE, p, main, radius, "Bouclier");
								circle.drawCircle();
							}
							else {
								radius-=0.1;
								Circle circle = new Circle(loc, EnumParticle.ENCHANTMENT_TABLE, p, main, radius, "Bouclier");
								circle.drawCircle();
							}
						}
						while(radius >= 0) {
							radius-=0.1;
							Location loc = p.getLocation();
							loc.setY(loc.getY() + 2.3);
							Circle circle = new Circle(loc, EnumParticle.ENCHANTMENT_TABLE, p, main, radius, "Bouclier");
							circle.drawCircle();
						}
					}
					
				}, 0, 5);
				
				Bukkit.getScheduler().runTaskLater(main, new Runnable() {

					@Override
					public void run() {
						task.cancel();
					}
					
				}, 20*10);
			}
		}
	}
}
