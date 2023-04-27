package fr.midey.uhcmeetup.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.uhcmeetup.GState;
import fr.midey.uhcmeetup.Gmain;

public class GBorder extends BukkitRunnable{

	private int timer = 20;
	
	private Gmain main;
	public GBorder(Gmain main) {
		this.main = main;
	}
	
	@Override
	public void run() {
		
		if (!main.isState(GState.PLAYING)) cancel();
		
		if(timer == 10 || (timer <= 5 && timer > 0) || timer == 60 || timer == 15 || timer == 30 || timer == 45 || timer == 90 || timer == 120 || timer == 150 || timer == 180) {
			Bukkit.broadcastMessage("Rétrécissement de la bordure dans §e" + timer + "§6s");
		}
		
		if(timer == 0) {
			main.getBorder().setSize(50, 120);
			cancel();
		}
		
		timer--;
	}

}
