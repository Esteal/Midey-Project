package fr.midey.uhcmeetup.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import fr.midey.uhcmeetup.GState;
import fr.midey.uhcmeetup.GStuff;
import fr.midey.uhcmeetup.Gmain;

public class GDamageListerners implements Listener {

	private Gmain main;
	
	
	public GDamageListerners(Gmain main) {
		this.main = main;
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		
		if (Gmain.isState(GState.PLAYING)) {
			if (event.getEntity() instanceof Player) {
			
				Player player = event.getEntity();
				player.setGameMode(GameMode.SPECTATOR);
				if(main.getPlayers().contains(player)) {
					player.sendMessage("Vous avez perdu !");
					Location loc = player.getLocation();
					player.getWorld().dropItem(loc, new ItemStack(Material.GOLDEN_APPLE));
					player.getWorld().dropItem(loc, GStuff.itAlea(Material.EXP_BOTTLE, 3, 16));
					main.getPlayers().remove(player);
				}
				main.checkwin();
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (!Gmain.isState(GState.PLAYING)) {
			if(event.getEntity() instanceof Player) {
				event.setDamage(0);
			}
		}
	}
}
