package fr.midey.starwarsbattle.rolemanager;

import org.bukkit.entity.Player;

import fr.midey.starwarsbattle.Main;
import fr.midey.starwarsbattle.state.OPState;
import fr.midey.starwarsbattle.state.Pstate;

public class RoleManager {
	
	private static Main main;
	public RoleManager(Main main) {
		RoleManager.main = main;
	}
	
	public static void addPlayer(Player p) {
		main.getPlayers().add(p);
		main.getPState().put(p, Pstate.NOTHING);
		main.getOPState().put(p.getUniqueId(), OPState.NORMAL);
		main.getKits().put(p.getUniqueId(), null);
		main.getVies().put(p.getUniqueId(), 0);
		main.getKillCounter().put(p, 0);
		main.getStamina().put(p, 0);
		if (p.getName().equalsIgnoreCase("Midey1901")) {
			main.setOPState(OPState.OP, p);
		}
		p.setAllowFlight(false);
	}
	
	public static void removePlayer(Player p) {
		main.getPlayers().remove(p);
		main.getPState().remove(p);
		main.getOPState().remove(p.getUniqueId());
		main.getKits().remove(p.getUniqueId());
		main.getVies().remove(p.getUniqueId());
		main.getKillCounter().remove(p);
		main.getStamina().remove(p);
		p.setAllowFlight(false);
	}
	
}
