package fr.midey.buildrush.ENDING;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.buildrush.BuildRush;
import fr.midey.buildrush.tools.DisplayHotBarMessage;

public class TimerEnding extends BukkitRunnable{

	private BuildRush main;
	private DisplayHotBarMessage displayHotBarMessage;
	private int timer;
	
	@SuppressWarnings("deprecation")
	public TimerEnding(BuildRush main) {
		this.main = main;
		this.displayHotBarMessage = new DisplayHotBarMessage(this.main);
		this.timer = 30;
		
		for(OfflinePlayer players : this.main.getLoserTeam().getPlayers()) {
			Player p = (Player) players;
			p.setGameMode(GameMode.SPECTATOR);
			
		}
	}
	
	@Override
	public void run() {
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			displayHotBarMessage.displayHotbarMessage(players, "§6Le serveur fermera dans " + timer + "s", 20);
		}
		
		if(timer == 0) {
			Bukkit.broadcastMessage("RESTARTTTTTTTTTTTT");
			Bukkit.getServer().spigot().restart();
			cancel();
		}
		timer--;
	}

}
