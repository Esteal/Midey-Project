package fr.midey.pvpbox.timer;

import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {

	public static int time;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(time <= 0) {
			cancel();
		}
		time--;
	}

}
