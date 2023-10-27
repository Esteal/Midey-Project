package fr.midey.NefaziaAtouts;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class AntiCleanUp implements Listener {

	@EventHandler
    public void onPlayerKill(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player && event.getEntity().getKiller() instanceof Player) {
            Player killed = (Player) event.getEntity();
            Player killer = killed.getKiller();

            if (killer.hasPermission("atouts.anticleanup.enable")) {
                killer.setHealth(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            }
        }
    }
}
