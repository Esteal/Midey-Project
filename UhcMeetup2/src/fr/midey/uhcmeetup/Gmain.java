package fr.midey.uhcmeetup;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.midey.uhcmeetup.listeners.GDamageListerners;
import fr.midey.uhcmeetup.listeners.GPlayerListeners;
import fr.midey.uhcmeetup.tasks.GStop;

public class Gmain extends JavaPlugin {
	
	private static GState state;
	private static List<Player> players = new ArrayList<>();
	private WorldBorder border;
	
	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		
		setState(GState.WAITING);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new GPlayerListeners(this), this);
		pm.registerEvents(new GDamageListerners(this), this);
		border = Bukkit.getWorld("world").getWorldBorder();
		border.setCenter(0, 0);
		border.setWarningDistance(5);
		border.setSize(200);
		
	}

	public void setState(GState states) {
		state = states;
	}
	
	public static boolean isState(GState states) {
		return state == states;
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public WorldBorder getBorder() {
		return border;
	}
	
	public static void eliminate(Player player) {
		if(players.contains(player)) {
			players.remove(player);
		}
		Location loc = player.getLocation();
		player.setGameMode(GameMode.SPECTATOR);
		for(ItemStack it : player.getInventory()) {
			if (it == null) continue;
			player.getWorld().dropItem(loc, it);
		}
		if (!(player.getEquipment().getBoots() == null)) {
		player.getWorld().dropItem(loc, player.getEquipment().getBoots());
		}
		if (!(player.getEquipment().getLeggings() == null)) {
		player.getWorld().dropItem(loc, player.getEquipment().getLeggings());
		}
		if (!(player.getEquipment().getChestplate() == null)) {
		player.getWorld().dropItem(loc, player.getEquipment().getChestplate());
		}
		if (!(player.getEquipment().getHelmet() == null)) {
		player.getWorld().dropItem(loc, player.getEquipment().getHelmet());
		}
		Bukkit.broadcastMessage("Le joueur §5" + player + " s'est déconnecté et est mort");
		player.sendMessage("Vous avez perdu !");
		clearALL(player);
	}
	
	public static void clearALL(Player player) {
		player.getInventory().clear();
		player.getEquipment().setBoots(null);
		player.getEquipment().setLeggings(null);
		player.getEquipment().setChestplate(null);
		player.getEquipment().setHelmet(null);;
	}

	public void checkwin() {
		if (players.size() == 1) {
			Player winner = players.get(0);
			Bukkit.broadcastMessage("Le joueur §4" + winner.getName() + "§ra remporté la partie");
			setState(GState.FINISH);
			GStop stoping = new GStop(this);
			stoping.runTaskTimer(this, 0, 20);
		}
	}
}
