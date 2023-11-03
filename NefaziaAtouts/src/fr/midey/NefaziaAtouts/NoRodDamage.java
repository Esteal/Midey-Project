package fr.midey.NefaziaAtouts;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class NoRodDamage implements Listener {

	private Atouts plugin;

	public NoRodDamage(Atouts atouts) {
    	this.plugin = atouts;
	}

	@EventHandler
    public void onPlayerUseRod(PlayerFishEvent event) {
        if (event.getCaught() instanceof Player) {
            Player caughtPlayer = (Player) event.getCaught();
            if (plugin.getPlayerAntiCleanUpEnable().getOrDefault(caughtPlayer.getUniqueId(), false)) {
                event.setCancelled(true);
                event.getHook().remove();
            }
        }
    }
}
