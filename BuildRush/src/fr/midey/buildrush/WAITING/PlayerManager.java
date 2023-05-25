package fr.midey.buildrush.WAITING;

import org.bukkit.GameMode;
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
	
	public PlayerManager(BuildRush main) {
		this.main = main;
	}

	//Gestion des connections lors de la phase d'attente du jeu
	@EventHandler
	public void playerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(main.getGameCycle() == GameCycle.WAITING && (main.getNumberPerTeam() <= (main.getPlayers().size() * 2) || main.getPlayers().size() == 0)) {
			doGestion(player);
		}
		else if (!main.getPlayers().contains(player)){
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage("�7La partie a d�j� commenc� donc vous avez �t� mis en mode spectateur");
		}
	}
	
	@EventHandler
	public void PlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(main.getGameCycle() == GameCycle.WAITING || main.getGameCycle() == GameCycle.LAUNCHING) {
			main.getPlayers().remove(player);
			main.getScoreboardManager().onLogout(player);
		}
	}
	
	public void doGestion(Player player) {
		PlayerState.clearALL(player);
		main.getPlayers().add(player);
		player.setGameMode(GameMode.SURVIVAL);
		ItemsConstructor banner = new ItemsConstructor(Material.BANNER);
		ItemsConstructor bed = new ItemsConstructor(Material.BED);
		bed.applyName("�b�lHUB");
		banner.applyName("�b�lS�lection de l'�quipe");
		player.getInventory().setItem(0, banner.getItem());
		player.getInventory().setItem(8, bed.getItem());
		main.getPlayersStates().put(player, new States());
		main.getScoreboardManager().onLogin(player);
	}
}
