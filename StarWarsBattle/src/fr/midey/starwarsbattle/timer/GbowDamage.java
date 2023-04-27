package fr.midey.starwarsbattle.timer;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GbowDamage extends BukkitRunnable{

	private int timer = 1;
	private Player player;
	
	public GbowDamage(Player player) {
		this.player = player;
	}
	@Override
	public void run() {

		if (timer == 0) {
			player.spigot().setCollidesWithEntities(true);
			cancel();
		}
		timer--;
	}

}
