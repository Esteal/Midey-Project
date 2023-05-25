package fr.midey.buildrush;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.midey.buildrush.Player.States;
import fr.midey.buildrush.ScoreBoardManager.ScoreboardManager;
import fr.midey.buildrush.SetTeams.onSetTeamsInventory;
import fr.midey.buildrush.SetTeams.setTeams;
import fr.midey.buildrush.WAITING.PlayerDoNotAction;
import fr.midey.buildrush.WAITING.PlayerManager;
import fr.midey.buildrush.WAITING.PlayerTeamSelect;

public class BuildRush extends JavaPlugin{
	
	private static BuildRush instance;
	private GameCycle gameCycle; //Permet de gérer les différentes phases du jeu
	private List<Player> players = new ArrayList<>(); //Contient les joueurs qui jouent
	private int numberPerTeam = 2; //Définit le nombre de joueur par équipe
	private HashMap<Player, States> playersStates = new HashMap<Player, States>();
	private ScoreboardManager scoreboardManager;
	private ScheduledExecutorService executorMonoThread; //Lié au scoreboard
	private ScheduledExecutorService scheduledExecutorService; //Lié au scoreboard
	
	@Override
	public void onEnable() {
		instance = this;
		setGameCycle(GameCycle.WAITING); //Met le mode de jeu dans sa phase d'attente
		
		//Permet de lancer le SCOREBOARD
		scheduledExecutorService = Executors.newScheduledThreadPool(16);
		executorMonoThread = Executors.newScheduledThreadPool(1);
		scoreboardManager = new ScoreboardManager();
		
		PluginManager pm = getServer().getPluginManager();
		//WAITING
		pm.registerEvents(new PlayerManager(this), this);
		pm.registerEvents(new PlayerTeamSelect(this), this);
		pm.registerEvents(new PlayerDoNotAction(this), this);
		//SetTeams
		pm.registerEvents(new onSetTeamsInventory(this), this);
		getCommand("teams").setExecutor(new setTeams(this));
		
		PlayerManager plm = new PlayerManager(this);
		for(Player player : Bukkit.getOnlinePlayers()) plm.doGestion(player);
	}
	
	@Override
	public void onDisable() {
		getScoreboardManager().onDisable();
	}
	
	public static BuildRush getInstance() { return instance; }
	
	public GameCycle getGameCycle() { return gameCycle; }

	public void setGameCycle(GameCycle gameCycle) { this.gameCycle = gameCycle; }

	public List<Player> getPlayers() { return players; }

	public int getNumberPerTeam() { return numberPerTeam; }

	public void setNumberPerTeam(int numberPerTeam) { this.numberPerTeam = numberPerTeam; }

	public HashMap<Player, States> getPlayersStates() { return playersStates; }

	public ScoreboardManager getScoreboardManager() { return scoreboardManager; }

	public ScheduledExecutorService getExecutorMonoThread() { return executorMonoThread; }

	public ScheduledExecutorService getScheduledExecutorService() { return scheduledExecutorService; }
}
