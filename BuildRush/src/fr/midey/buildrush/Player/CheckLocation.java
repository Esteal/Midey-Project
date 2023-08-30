package fr.midey.buildrush.Player;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.buildrush.BuildRush;
import fr.midey.buildrush.GameCycle;

public class CheckLocation extends BukkitRunnable{

	private BuildRush main;
	private double yVoid;
	private List<GameCycle> gcs;
	
	public CheckLocation(BuildRush main) {
		this.main = main;
		this.main.saveDefaultConfig();
		yVoid = main.getConfig().getDouble("killCouch");
		gcs = Arrays.asList(GameCycle.LAUNCHING, GameCycle.WAITING);
		Bukkit.broadcastMessage("aaaaaaaa");
	}
	
	@Override
	public void run() {
		main.saveDefaultConfig();
		GameCycle gc = main.getGameCycle();
		for(Player players : main.getPlayers()) {
			double y = players.getLocation().getY();
			if(y <= yVoid) {
				if(gcs.contains(gc)) {
					Location spawnLocation = new Location(players.getWorld(), main.getConfig().getDouble("spawn.hub.x"), main.getConfig().getDouble("spawn.hub.y"), main.getConfig().getDouble("spawn.hub.z"), (float) main.getConfig().getDouble("spawn.hub.pitch"), (float) main.getConfig().getDouble("spawn.hub.yaw"));
					players.teleport(spawnLocation);
				} else {
					players.damage(players.getMaxHealth() * 10);
				}
			}
		}
	}

}
