package fr.midey.LgUHC;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoin implements Listener {

	private LgUHC plugin;

	public PlayerJoin(LgUHC plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		loadGame(event.getPlayer());
	}
	
	public void loadGame(Player player) {
		State gameState = plugin.getGameState();
		switch(gameState) {
			case WAITING:
				waitingFunction(player);
				break;
			case STARTING:
				break;
			case PLAYING:
				break;
			case ENDING:
				break;
			default:
				break;
		}
	}
	
	private void waitingFunction(Player player) {

		if(Bukkit.getOnlinePlayers().size() >= plugin.getPlayerBeforeAutoStart() && plugin.getGameState() == State.WAITING) {
			plugin.setGameState(State.STARTING);
			startingCountdown();
		}
	}

	private void startingCountdown() {

		new BukkitRunnable() {
			int timer = plugin.getConfig().getInt("timerBeforeStarting");

			@Override
			public void run() {
				Bukkit.broadcastMessage("" + timer);
				if(Bukkit.getOnlinePlayers().size() == Bukkit.getMaxPlayers() && timer > 6)
					timer = 6;
				
				timer--;
				if(timer <= 0) {
					startGame();
					this.cancel();
				}
			}
		}.runTaskTimer(plugin, 0, 20);
	}

	private void startGame() {
		plugin.setGameState(State.PLAYING);
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			
			plugin.getPlayerData(players);
			plugin.teleportPlayerRandomly(players);
			
		}
	}
}
