package fr.midey.starwarsbattle.timer;

import org.bukkit.scheduler.BukkitRunnable;

public class Gtime extends BukkitRunnable{
	
	public static int iminute = 0;
	public static int iseconde = 0;
	public static int iheure = 0;
	
	@Override
	public void run() {
		
		if(iseconde == 60) {
			iseconde = 0;
			iminute++;
		}
		if(iminute == 60) {
			iminute = 0;
			iheure++;
		}
		iseconde++;
	}

}
