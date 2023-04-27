package fr.midey.clashroyale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.midey.clashroyale.board.ScoreBoard;
import fr.midey.clashroyale.gameManager.StateManager;
import fr.midey.clashroyale.listeners.menuGUI;
import fr.midey.clashroyale.listeners.onDamage;
import fr.midey.clashroyale.listeners.onGame;
import fr.midey.clashroyale.listeners.rolesGUI;
import fr.midey.clashroyale.state.GPlayerState;
import fr.midey.clashroyale.state.Grole;
import fr.midey.clashroyale.state.Gstate;
import fr.midey.clashroyale.state.Gteam;

public class Main extends JavaPlugin{
	
	private Gstate state;
	private List<Player> inGamePlayers = new ArrayList<>();
	private HashMap<Player, GPlayerState> playerState = new HashMap<Player, GPlayerState>();
	private HashMap<Player, Gteam> playerTeam = new HashMap<Player, Gteam>();
	private HashMap<Player, Grole> playerRole = new HashMap<Player, Grole>();
	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		
		setState(Gstate.WAITING);
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new onGame(this), this);
		pm.registerEvents(new menuGUI(this), this);
		pm.registerEvents(new onDamage(this), this);
		pm.registerEvents(new rolesGUI(this), this);
		
		new StateManager(this);
		new ScoreBoard(this);
	}
	
	public void setState(Gstate state) {
		this.state = state;
	}
	
	public boolean isState(Gstate state) {
		return this.state == state;
	}
	
	public HashMap<Player, GPlayerState> getPlayerState() {
		return playerState;
	}
	
	public List<Player> getPlayers() {
		return inGamePlayers;
	}
	
	public HashMap<Player, Gteam> getPlayerTeam() {
		return playerTeam;
	}
	
	public HashMap<Player, Grole> getPlayerRole() {
		return playerRole;
	}
}
