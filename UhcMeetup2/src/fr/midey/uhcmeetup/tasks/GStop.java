package fr.midey.uhcmeetup.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.uhcmeetup.Gmain;

public class GStop extends BukkitRunnable{

	private Gmain main;
	public GStop(Gmain main) {
		this.main = main;
	}
	private int timer;
	private int pi = 1;
	@Override
	public void run() {
		main.saveDefaultConfig();
		if (pi == 1) {
			timer = main.getConfig().getInt("timerbeforecloseserver");
			pi--;
		}
		
		if(timer == 10 || (timer <= 5 && timer > 0) || timer == 60 || timer == 15 || timer == 30 || timer == 45 || timer == 90 || timer == 120 || timer == 150 || timer == 180) {
			Bukkit.broadcastMessage("Fermeture du serveur dans §e" + timer + "§6s");
		}
		
		if(timer == 0) {
			Bukkit.broadcastMessage("Fermeture du serveur");
			Bukkit.getServer().spigot().restart();
			cancel();
		}
		timer --;
	}

}
