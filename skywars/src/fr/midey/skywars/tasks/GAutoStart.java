package fr.midey.skywars.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.skywars.Gmain;
import fr.midey.skywars.Gstates;

public class GAutoStart extends BukkitRunnable{
	
	private  Gmain main;
	
	public GAutoStart(Gmain main) {
		this.main = main;
	}
	
	private int i = 1;
	private int timer;
	
	@Override
	public void run() {
		main.saveDefaultConfig();
		
		if (i == 1) {
			timer = main.getConfig().getInt("skywars.timer.beforestart");
			i--;
		}
		
		if(main.getPlayers().size() == main.getConfig().getInt("skywars.timer.nbjoueurmaxstart") && timer > 10) {
			timer = 10;
		}
		
		for(Player player : main.getPlayers()) {
			player.setLevel(timer);
		}
		
		if(timer == 10 || (timer <= 5 && timer > 0) || timer == 60) {
			Bukkit.broadcastMessage("Lancement de la partie dans �e" + timer + "�6s");
		}
	
		if(timer == 0) {
			Bukkit.broadcastMessage("Lancement de la partie !");
			main.setState(Gstates.PLAYING);
			
			for(int i = 0; i < main.getPlayers().size(); i++) {
				Player player = main.getPlayers().get(i);
				Location spawn = main.getSpawns().get(i);
				player.teleport(spawn);
				player.getInventory().clear();
				player.setGameMode(GameMode.SURVIVAL);
				player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
				player.updateInventory();
				
			}/*
			GGameCycle cycle = new GGameCycle(main);
			cycle.runTaskTimer(main, 0, 20);*/
			GAutoRefile timerChest = new GAutoRefile(main);
			timerChest.runTaskTimer(main, 0, 20);
			cancel();
		}
		
		timer--;
	}
}