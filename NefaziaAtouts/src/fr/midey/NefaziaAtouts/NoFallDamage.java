package fr.midey.NefaziaAtouts;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class NoFallDamage implements Listener {
	
	private Atouts plugin;
	
    public NoFallDamage(Atouts atouts) {
    	this.plugin = atouts;
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {
        // Obtenez l'action de l'événement
        Action action = event.getAction();

        // Vérifiez si c'est un clic droit
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            // Obtenez le joueur qui a fait l'action
            Player player = event.getPlayer();

            // Logique à exécuter lors d'un clic droit
            player.sendMessage("Tu as fait un clic droit !");
        }
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
