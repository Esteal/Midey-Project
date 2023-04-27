package fr.midey.clashroyale.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.midey.clashroyale.Main;
import fr.midey.clashroyale.board.ScoreBoard;
import fr.midey.clashroyale.gameManager.StateManager;
import fr.midey.clashroyale.state.GPlayerState;
import fr.midey.clashroyale.state.Gstate;

public class onGame implements Listener {

	private Main main;
	
	public onGame(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		main.saveDefaultConfig();
		Player p = e.getPlayer();
		
		if((main.isState(Gstate.PLAYING) || main.isState(Gstate.FINISH)) && !main.getPlayers().contains(p)) {
			main.getPlayerState().put(p, GPlayerState.Spectate);
			p.setGameMode(GameMode.SPECTATOR);
		}
		
		else if((main.isState(Gstate.PLAYING) || main.isState(Gstate.FINISH)) && main.getPlayers().contains(p)) {
			ScoreBoard.createScoreBoard(p);
			ScoreBoard.updateScoreboard();
		}
		else if(main.isState(Gstate.WAITING) || main.isState(Gstate.STARTING)) {
			StateManager.onPlayerLoad(p);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		main.saveDefaultConfig();
		Player p = e.getPlayer();
		
		if(main.isState(Gstate.PLAYING) && !main.getPlayers().contains(p)) {
			if(main.getPlayerTeam().containsKey(p)) main.getPlayerTeam().remove(p);
			e.setQuitMessage(null);
			return;
		}
		else if (main.isState(Gstate.PLAYING) && main.getPlayers().contains(p)) {
			
		}
		
	}
}
