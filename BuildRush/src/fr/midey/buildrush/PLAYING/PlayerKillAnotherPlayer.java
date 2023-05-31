package fr.midey.buildrush.PLAYING;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.midey.buildrush.BuildRush;
import fr.midey.buildrush.GameCycle;

public class PlayerKillAnotherPlayer implements Listener {

	private BuildRush main;
	
	public PlayerKillAnotherPlayer(BuildRush main) {
		this.main = main;
	}

	@EventHandler
	public void playerKillAnotherPlayer(PlayerDeathEvent event) {
		if(main.getGameCycle() == GameCycle.PLAYING) {
			Player victim = event.getEntity();
			Player killer = victim.getKiller();
			
			if(killer !=null) {
				main.getPlayersStates().get(killer).setKills(main.getPlayersStates().get(killer).getKills() + 1);
				Bukkit.broadcastMessage(killer.getName() + " a " + main.getPlayersStates().get(killer).getKills() + " kills");
			}
		}
	}
}
