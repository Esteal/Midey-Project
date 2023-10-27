package fr.midey.NefaziaAtouts;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class NoRodDamage implements Listener {

	@EventHandler
    public void onPlayerUseRod(PlayerFishEvent event) {
        if (event.getCaught() instanceof Player) {
            Player caughtPlayer = (Player) event.getCaught();
            if (caughtPlayer.hasPermission("atouts.norod.enable")) {
                event.setCancelled(true);
                event.getHook().remove();
            }
        }
    }
}
