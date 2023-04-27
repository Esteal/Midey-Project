package fr.midey.clashroyale.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import fr.midey.clashroyale.Main;
import fr.midey.clashroyale.state.Gteam;
import fr.midey.clashroyale.stuff.Armor;

public class onDamage implements Listener {

	private Main main;
	public onDamage(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		if (main.getPlayerTeam().containsKey(p)) {
			if (main.getPlayerTeam().get(p) == Gteam.Blue) {
				Location loc = new Location(p.getWorld(), main.getConfig().getDouble("bleuspawn.x"), main.getConfig().getDouble("bleuspawn.y"), main.getConfig().getDouble("bleuspawn.z"), (float) main.getConfig().getDouble("bleuspawn.ya"),(float) main.getConfig().getDouble("bleuspawn.pe"));
				e.setRespawnLocation(loc);
				Armor.clearALL(p);
			}
			else if (main.getPlayerTeam().get(p) == Gteam.Red) {
				Location loc = new Location(p.getWorld(), main.getConfig().getDouble("rougespawn.x"), main.getConfig().getDouble("rougespawn.y"), main.getConfig().getDouble("rougespawn.z"), (float) main.getConfig().getDouble("rougespawn.ya"),(float) main.getConfig().getDouble("rougespawn.pe"));
				e.setRespawnLocation(loc);
				Armor.clearALL(p);
			}
		}
	}

}
