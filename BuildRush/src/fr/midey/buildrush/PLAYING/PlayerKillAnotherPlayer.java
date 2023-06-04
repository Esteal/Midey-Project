package fr.midey.buildrush.PLAYING;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.midey.buildrush.BuildRush;
import fr.midey.buildrush.GameCycle;
import fr.midey.buildrush.Player.States;

public class PlayerKillAnotherPlayer implements Listener {

	private BuildRush main;
	
	public PlayerKillAnotherPlayer(BuildRush main) {
		this.main = main;
	}

	//Permet de détecter qui est le dernier joueur à lui avoir mis un damage
	@EventHandler
	public void PlayerDamageByPlayer(EntityDamageByEntityEvent event) {
		if(main.getGameCycle() == GameCycle.PLAYING) {
			if(event.getDamager() instanceof Player) {
				Player victim = (Player) event.getEntity();
				Player killer = (Player) event.getDamager();
				main.getPlayersStates().get(victim).setKiller(killer);
			}
		}
	}
	
	//Permet de savoir qui tue qui et de gérer les kills/points du scoreboard + faire respawn le joueur + conditions de victoire
	@EventHandler
	public void playerKillAnotherPlayer(PlayerDeathEvent event) {
		if(main.getGameCycle() == GameCycle.PLAYING) {
			Player victim = event.getEntity();
			Player killer = victim.getKiller();
			
			if(killer !=null) {
				main.getPlayersStates().get(killer).setKills(main.getPlayersStates().get(killer).getKills() + 1);
				main.getPlayersStates().get(killer).setPoint(main.getPlayersStates().get(killer).getPoint() + 10);
				main.getPlayersStates().get(victim).setKiller(null);
				if(main.getPlayersStates().get(killer).getTeam().equalsIgnoreCase("Blue")) {
					main.setBlueKill(killScoreboard("Blue"));
					if(main.getBlueKill().equalsIgnoreCase("" + main.getKillObjective()))
						main.setGameEnding(true);
				}
				else {
					main.setRedKill(killScoreboard("Red"));
					if(main.getRedKill().equalsIgnoreCase("" + main.getKillObjective()))
						main.setGameEnding(true);
				}
			}
			Bukkit.getScheduler().runTaskLater(main, () -> {
				victim.spigot().respawn();
				Bukkit.getScheduler().runTaskLater(main, () -> {
					main.getPlayersStates().get(victim).stuffLoad();
				}, 1);
			}, 1);
			event.getDrops().clear();;
		}
	}
	
	public String killScoreboard(String team) {
		int totalKill = 0;
		for(Player players : main.getPlayers()) {
			States player = main.getPlayersStates().get(players);
			if(player.getTeam().equalsIgnoreCase(team))
				totalKill+=player.getKills();
		}
		if(totalKill != 0)
			return "" + totalKill;
		return "0";
	}
}
