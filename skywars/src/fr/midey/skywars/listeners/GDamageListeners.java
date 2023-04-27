package fr.midey.skywars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import fr.midey.skywars.Gmain;
import fr.midey.skywars.Gstates;

public class GDamageListeners implements Listener {

	private Gmain main;
	
	public GDamageListeners(Gmain main) {
		this.main = main;
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		
		Entity victim = event.getEntity();
		
		if(!main.isState(Gstates.PLAYING)) {
			event.setCancelled(true);
		}
		
		if (victim instanceof Player) {
			
			Player player = (Player)victim;
			
			if(player.getHealth() <= event.getDamage()) {
				event.setDamage(0);
				main.eliminate(player);
			}
		}
		
	}
	
	@EventHandler
	public void onPvp(EntityDamageByEntityEvent event) {
		
		Entity victim = event.getEntity();
		
		
		if(!main.isState(Gstates.PLAYING)) {
			event.setCancelled(true);
		}
		if (victim instanceof Player) {
			
			Player player = (Player)victim;
			Entity damager = event.getDamager();
			Player killer = player;
			
			if(player.getHealth() <= event.getDamage()) {
				
				if(damager instanceof Player) {
					killer = (Player)damager;
				}
				
				if(damager instanceof Arrow) {
					
					Arrow arrow = (Arrow)damager;
					if(arrow.getShooter() instanceof Player) {
						killer = (Player) arrow.getShooter();
					}
				}
				event.setDamage(0);
				Bukkit.broadcastMessage(killer.getName() + " vient de massacrer " + victim.getName());
				main.eliminate(player);
			}
		}
	}
	
}
