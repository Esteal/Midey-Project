package fr.midey.uhcmeetup.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.midey.uhcmeetup.GState;
import fr.midey.uhcmeetup.Gmain;

public class GDamageListerners implements Listener {

	private Gmain main;
	
	
	public GDamageListerners(Gmain main) {
		this.main = main;
	}


	/*
	@EventHandler
	public void onPvp(EntityDamageByEntityEvent event) {
		
		if (event.getEntity() instanceof Player) {
			Player victim = (Player) event.getEntity();
			Player killer = victim;
			if (victim.getHealth() <= event.getDamage(DamageModifier.ARMOR)) {
				
				if (event.getDamager() instanceof Player) {
					killer = (Player) event.getDamager();
				}
				if (event.getDamager() instanceof Arrow) {
					Arrow arrow = (Arrow) event.getDamager();
					if(arrow.getShooter() instanceof Player) {
						killer = (Player) arrow.getShooter();
					}
				}
				event.setDamage(0);
				Bukkit.broadcastMessage(killer.getName() + " vient de massacrer " + victim.getName());
				Gmain.eliminate(victim);
			}
		}
	}*/
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		
		if (main.isState(GState.PLAYING)) {
			if (event.getEntity() instanceof Player) {
			
				Player player = event.getEntity();
				player.setGameMode(GameMode.SPECTATOR);
				player.sendMessage("Vous avez perdu !");
				if(main.getPlayers().contains(player)) {
					main.getPlayers().remove(player);
				}
				main.checkwin();
			}
		}
	}
}
