package fr.midey.uhcmeetup.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class GStop extends BukkitRunnable{

	private int timer = 10;

	@Override
	public void run() {
		
		if(timer == 10 || (timer <= 5 && timer > 0) || timer == 60 || timer == 15 || timer == 30 || timer == 45 || timer == 90 || timer == 120 || timer == 150 || timer == 180) {
			Bukkit.broadcastMessage("Fermeture du serveur dans §e" + timer + "§6s");
		}
		
		if(timer == 0) {
			Bukkit.broadcastMessage("Fermeture du serveur");
			cancel();
		}
		timer --;
	}

}
