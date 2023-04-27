package fr.midey.skywars.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.skywars.Gmain;
import fr.midey.skywars.Gstates;

public class GGameCycle extends BukkitRunnable{

	private Gmain main;
	
	public GGameCycle(Gmain main) {
		this.main = main;
	}

	private int i = 1;
	private int timer;
	@Override
	public void run() {
		
		main.saveDefaultConfig();
		
		if(i == 1) {
			timer = main.getConfig().getInt("skywars.timer.deathmatch");
			i--;
		}
		
		if(main.isState(Gstates.FINISH)) cancel();
		
		if (timer == 300)
			Bukkit.broadcastMessage("Il reste §e5§6mn §ravant le §4DeathMatch");
		
		if (timer == 60)
			Bukkit.broadcastMessage("Il reste §e1§6mn §ravant le §4DeathMatch");
		
		if(timer == 30 || timer == 15 || timer == 10 || (timer <= 5 && timer > 0))
			Bukkit.broadcastMessage("Il reste §e" + timer + "§6s §ravant le §4DeathMatch");
		
		if(timer == 0) {
			for(Player pl : main.getPlayers()) {
				pl.teleport(new Location(Bukkit.getWorld("world"), -850, 22, 707, -169.5f, 31.6f));
			}
			cancel();
		}
		timer--;
	}

}
