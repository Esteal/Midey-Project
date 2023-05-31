package fr.midey.buildrush.PLAYING;

import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.buildrush.BuildRush;

public class TimerPlaying extends BukkitRunnable{

	private BuildRush main;
	private Integer seconde = 0;
	private Integer minute = 0;
	private Integer heure = 0;
	
	public TimerPlaying(BuildRush main) {
		this.main = main;
	}

	@Override
	public void run() {
		doTimer();
	}
	
	public void doTimer() {
		seconde++;
		
		if(seconde == 60) {
			seconde = 0;
			minute++;
			if(minute == 60) {
				minute = 0;
				heure++;
			}
		}
		
		String formattedTime = String.format("%02d:%02d:%02d", heure, minute, seconde);
		main.setGameTime(formattedTime);
	}
}
