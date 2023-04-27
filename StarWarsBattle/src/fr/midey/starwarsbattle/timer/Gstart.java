package fr.midey.starwarsbattle.timer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.starwarsbattle.Main;
import fr.midey.starwarsbattle.boardmanager.BoardManager;
import fr.midey.starwarsbattle.listeners.onInteractListeners;
import fr.midey.starwarsbattle.state.Gstate;
import fr.midey.starwarsbattle.state.Pstate;
import fr.midey.starwarsbattle.stuff.Stuff;
import net.md_5.bungee.api.ChatColor;

public class Gstart extends BukkitRunnable{

	private Main main;
	public Gstart(Main main) {
		this.main = main;
	}
	
	private int i = 10;
	
	@Override
	public void run() {
	
		main.saveDefaultConfig();
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.setLevel(i);
			player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
			player.sendMessage("Lancement de la bataille dans " + ChatColor.GOLD + i + ChatColor.YELLOW + "s");
		}
		
		if (onInteractListeners.stop == 0) {
			cancel();
		}
		
		if (i <= 0) {
			
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (main.isPState(Pstate.EMPIRE, player)) {
					Location loc = new Location(Bukkit.getWorld("world"), main.getConfig().getDouble("empirespawn.x"), main.getConfig().getDouble("empirespawn.y"), main.getConfig().getDouble("empirespawn.z"), (float )main.getConfig().getDouble("empirespawn.pe"), (float )main.getConfig().getDouble("empirespawn.ya"));
					player.teleport(loc);
					player.getInventory().clear();
				}
				else if (main.isPState(Pstate.JEDI, player)) {
					Location loc = new Location(Bukkit.getWorld("world"), main.getConfig().getDouble("jedispawn.x"), main.getConfig().getDouble("jedispawn.y"), main.getConfig().getDouble("jedispawn.z"), (float )main.getConfig().getDouble("jedispawn.pe"), (float )main.getConfig().getDouble("jedispawn.ya"));
					player.teleport(loc);
					player.getInventory().clear();
				}
				else if(main.isPState(Pstate.NOTHING, player)) {
					Location loc = new Location(Bukkit.getWorld("world"), main.getConfig().getDouble("empirespawn.x"), main.getConfig().getDouble("empirespawn.y"), main.getConfig().getDouble("empirespawn.z"), (float )main.getConfig().getDouble("empirespawn.pe"), (float )main.getConfig().getDouble("empirespawn.ya"));
					player.teleport(loc);
					player.getInventory().clear();
					main.setPState(Pstate.EMPIRE, player);
				}
				BoardManager.boardManager(player);
				player.sendMessage(ChatColor.RED + "La bataille commence !");
				player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1f, 1f);
				Stuff.Stuffed(player);
				player.setHealth(20.0);
				player.setFoodLevel(20);
			}
			Gtime game = new Gtime();
			game.runTaskTimer(main, 0, 20);
			main.setState(Gstate.PLAYING);
			cancel();
		}
		i--;
	}

}
