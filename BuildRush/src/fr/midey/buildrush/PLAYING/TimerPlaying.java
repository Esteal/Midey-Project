package fr.midey.buildrush.PLAYING;

import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.buildrush.BuildRush;
import fr.midey.buildrush.GameCycle;
import fr.midey.buildrush.ENDING.TimerEnding;

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
		if(main.isGameEnding()) {
			TimerEnding timerEnding = new TimerEnding(main);
			timerEnding.runTaskTimer(main, 0, 20);
			main.setGameCycle(GameCycle.ENDING);
			cancel();
		}
		String formattedTime = String.format("%02d:%02d:%02d", heure, minute, seconde);
		main.setGameTime(formattedTime);
	}
}
