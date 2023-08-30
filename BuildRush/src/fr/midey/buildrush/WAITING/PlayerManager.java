package fr.midey.buildrush.WAITING;

import java.util.Arrays;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.midey.buildrush.BuildRush;
import fr.midey.buildrush.GameCycle;
import fr.midey.buildrush.Player.States;
import fr.midey.buildrush.tools.ItemsConstructor;
import fr.midey.buildrush.tools.PlayerState;

public class PlayerManager implements Listener {

	private BuildRush main;
	List<GameCycle> gc;
	public PlayerManager(BuildRush main) {
		gc = Arrays.asList(GameCycle.LAUNCHING, GameCycle.WAITING);	
		this.main = main;
	}

	//Gestion des connections lors de la phase d'attente du jeu
	@EventHandler
	public void playerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(gc.contains(main.getGameCycle())) {
			doGestionEnable(player);
		}
		else if (!main.getPlayers().contains(player)){
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage("La partie a déjà commencé");
		}
	}
	
	@EventHandler
	public void PlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		doGestionDisable(player);
	}
	
	@SuppressWarnings("deprecation")
	public void doGestionDisable(Player player) {
		if(gc.contains(main.getGameCycle()) || main.getGameCycle() == GameCycle.PLAYING) {
			main.getPlayers().remove(player);
			if(main.getBlueTeam().hasPlayer(player)) 
				main.getBlueTeam().removePlayer(player);
			else if(main.getRedTeam().hasPlayer(player)) 
				main.getRedTeam().removePlayer(player);
			main.getScoreboardManager().onLogout(player);
		}
	}
	
	public void doGestionEnable(Player player) {
		Location spawnLocation = new Location(player.getWorld(), main.getConfig().getDouble("spawn.hub.x"), main.getConfig().getDouble("spawn.hub.y"), main.getConfig().getDouble("spawn.hub.z"), (float) main.getConfig().getDouble("spawn.hub.pitch"), (float) main.getConfig().getDouble("spawn.hub.yaw"));
		player.teleport(spawnLocation);
		PlayerState.clearALL(player);
		main.getPlayers().add(player);
		player.setLevel(0);
		player.setGameMode(GameMode.SURVIVAL);
		ItemsConstructor banner = new ItemsConstructor(Material.BANNER);
		ItemsConstructor bed = new ItemsConstructor(Material.BED);
		bed.applyName("§b§lHUB");
		banner.applyName("§b§lSélection de l'équipe");
		player.getInventory().setItem(0, banner.getItem());
		player.getInventory().setItem(8, bed.getItem());
		main.getPlayersStates().put(player, new States(player));
		main.getScoreboardManager().onLogin(player);
		if((main.getNumberPerTeam() * 2)== (main.getPlayers().size() - 1)) player.kickPlayer("Partie Full");
		if(main.getPlayers().size() >= main.getNumberPerTeam() * 2 && !(main.getGameCycle() == GameCycle.LAUNCHING)) {
			TimerLaunching timeStart = new TimerLaunching(main);
			timeStart.runTaskTimer(main, 0, 20);
			main.setGameCycle(GameCycle.LAUNCHING);
		}
	}
}
