package fr.midey.skywars.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.skywars.Gmain;
import fr.midey.skywars.Gstates;
import fr.midey.skywars.listeners.GSetChestListeners;

public class GAutoRefile extends BukkitRunnable{

	private Gmain main;
	public GAutoRefile(Gmain main) {
		this.main = main;
	}
	
	private static int i = 1;
	public static int timer;
	
	@Override
	public void run() {
		
		main.saveDefaultConfig();
		
		if (i == 1) {
			timer = main.getConfig().getInt("skywars.timer.refileChest");
			i--;
		}
		
		if(main.isState(Gstates.FINISH)) cancel();
		
		if (timer == 10 || (timer <= 5 && timer > 0) || timer == 60 || timer == 90) {
			Bukkit.broadcastMessage("Remplissage automatique des coffres dans �e" + timer + "�6s");
		}
		
		if (timer <= 0)
		{
			Bukkit.broadcastMessage("�8Les coffres ont �t� reremplis !");
			GSetChestListeners gSetChestListeners = new GSetChestListeners(main);
			gSetChestListeners.clearChestOppened();
			cancel();
		}
		timer--;
	}
	
	
}