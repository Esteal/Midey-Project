package fr.midey.NefaziaAtouts;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoFallDamage implements Listener {
	
	private Atouts plugin;
	
    public NoFallDamage(Atouts atouts) {
    	this.plugin = atouts;
    }

	@EventHandler
    public void onFallDamage(EntityDamageEvent event) {
    	
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL && plugin.getPlayerNoFallEnable().getOrDefault(player.getUniqueId(), false))
            event.setCancelled(true);
    }
}
