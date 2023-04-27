package fr.midey.skywars;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.midey.skywars.listeners.GDamageListeners;
import fr.midey.skywars.listeners.GPlayerListeners;
import fr.midey.skywars.listeners.GSetChestListeners;

public class Gmain extends JavaPlugin{

	private Gstates state;
	private List<Player> players = new ArrayList<>();
	private List<Location> spawns = new ArrayList<>();
	
	@Override
	public void onEnable() {

		
		saveDefaultConfig();
		setState(Gstates.WAITING);
		
		//Load spawn
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new GPlayerListeners(this), this);
		pm.registerEvents(new GDamageListeners(this), this);
		pm.registerEvents(new GSetChestListeners(this), this);
		addSpawn();
	}
	
	@Override
	public void onDisable() {
		WorldManager.replaceWorld(true);
	}
	
	public void setState(Gstates state) {
		this.state = state;
	}
	
	public boolean isState(Gstates state) {
		return this.state == state;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public List<Location> getSpawns() {
		return spawns;
	}

	public void eliminate(Player player) {
		if(players.contains(player)) {
			players.remove(player);
		}
		player.setGameMode(GameMode.SPECTATOR);
		for(ItemStack it : player.getInventory()) {
			if (it == null) continue;
			Location loc = player.getLocation();
			player.getWorld().dropItem(loc, it);
			player.sendMessage("lalala");
		}
		player.sendMessage("Vous avez perdu !");
		player.getInventory().clear();
		player.teleport(spawns.get(0));
		checkWin();
	}

	public void checkWin() {
		if (players.size() == 1) {
			Player winner = players.get(0);
			Bukkit.broadcastMessage("Le joueur §4" + winner.getName() + "§ra remporté la partie");
			setState(Gstates.FINISH);
			
		}
		if (players.size() == 0) {
			Bukkit.shutdown();
		}
	}
	
	public void addSpawn() {
		
		saveDefaultConfig();
		
		World world = Bukkit.getWorld("world");
		spawns.add(new Location(world, d("skywars.location.player1.x"), d("skywars.location.player1.y"), d("skywars.location.player1.z")));
		spawns.add(new Location(world, d("skywars.location.player2.x"), d("skywars.location.player2.y"), d("skywars.location.player2.z")));
	}
	
	public Double d(String path) {
		saveDefaultConfig();
		return this.getConfig().getDouble(path);
	}
}
