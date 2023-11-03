package fr.midey.NefaziaAtouts;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class AntiCleanUp implements Listener {

	private Atouts plugin;

	public AntiCleanUp(Atouts atouts) {
    	this.plugin = atouts;
	}

	@EventHandler
    public void onPlayerKill(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player && event.getEntity().getKiller() instanceof Player) {
            Player killed = (Player) event.getEntity();
            Player killer = killed.getKiller();

            if (plugin.getPlayerAntiCleanUpEnable().getOrDefault(killer.getUniqueId(), false)) {
                killer.setHealth(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            }
        }
    }
}
