package fr.midey.skywars.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.midey.skywars.Gmain;
import fr.midey.skywars.Gstates;
import fr.midey.skywars.tasks.GAutoStart;

public class GPlayerListeners implements Listener {

	private Gmain main;
	public GPlayerListeners(Gmain main) {
		this.main = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {

		main.saveDefaultConfig();
		
		Player player = event.getPlayer();
		Location spawn = new Location(Bukkit.getWorld("world"), -825, 17, 685, 88.5f, 25.4f);
		player.teleport(spawn);
		player.setHealth(20);
		player.setFoodLevel(20);
		player.getInventory().clear();
		player.setLevel(main.getConfig().getInt("skywars.timer.beforestart"));
		
		if(!main.isState(Gstates.WAITING) && !main.isState(Gstates.STARTING)) {
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage("La partie a déjà commencé !");
			event.setJoinMessage(null);
			return;
		}
		
		if(!main.getPlayers().contains(player)) {
			main.getPlayers().add(player);
		}
		player.setGameMode(GameMode.ADVENTURE);
		event.setJoinMessage("§4[§eSkyWars§4] §rle joueur §4" + player.getName() + " §r a rejoint la partie ! §a(" + main.getPlayers().size() + "/" + Bukkit.getMaxPlayers() + ")");
		
		if(main.isState(Gstates.WAITING) && main.getPlayers().size() >= main.getConfig().getInt("skywars.timer.nbjoueurminstart")) {
			GAutoStart start = new GAutoStart(main);
			start.runTaskTimer(main, 0, 20);
			main.setState(Gstates.STARTING);
		}
	}
	
	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(main.getPlayers().contains(player)) {
			main.getPlayers().remove(player);
		}
		event.setQuitMessage(null);
		main.checkWin();
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if(!main.isState(Gstates.PLAYING)) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if(!main.isState(Gstates.PLAYING)) {
			event.setCancelled(true);
			return;
		}
	}
	
	
}
