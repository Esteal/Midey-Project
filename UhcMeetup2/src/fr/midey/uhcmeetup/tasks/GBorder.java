package fr.midey.uhcmeetup.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.uhcmeetup.GState;
import fr.midey.uhcmeetup.Gmain;

public class GBorder extends BukkitRunnable{

	
	private Gmain main;
	public GBorder(Gmain main) {
		this.main = main;
	}
	
	private int timer;
	private int pi = 1;
	
	@Override
	public void run() {
		
		if (pi == 1) {
			timer = main.getConfig().getInt("timerbeforeborder");
			pi--;
		}
		
		if(!Gmain.isState(GState.PLAYING)) cancel();
		
		if(timer == 10 || (timer <= 5 && timer > 0) || timer == 60 || timer == 15 || timer == 30 || timer == 45 || timer == 90 || timer == 120 || timer == 150 || timer == 180 || timer == 600) {
			Bukkit.broadcastMessage("Rétrécissement de la bordure dans §e" + timer + "§6s");
		}
		
		if(timer == 0) {
			main.getBorder().setSize(50, 120);
			cancel();
		}
		
		timer--;
	}

}
