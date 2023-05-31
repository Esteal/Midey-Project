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
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import fr.midey.buildrush.PLAYING.PlayerKillAnotherPlayer;
import fr.midey.buildrush.Player.FoodListener;
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
	private List<Player> players; //Contient les joueurs qui jouent
	PlayerManager plm;
	private int numberPerTeam = 1; //Définit le nombre de joueur par équipe
	private HashMap<Player, States> playersStates; //Divers stats du joueur visible dans la classe States
	private String gameTime = "00:00:00"; //timer du scoreboard
	private ScoreboardManager scoreboardManager; //Déclaration du scoreboard
	private ScheduledExecutorService executorMonoThread; //Lié au scoreboard
	private ScheduledExecutorService scheduledExecutorService; //Lié au scoreboard
    private Team redTeam; //red team
    private Team blueTeam; //blue team
    Scoreboard scoreboard ; //Scoreboard

	@Override
	public void onEnable() {
		instance = this;
		setGameCycle(GameCycle.WAITING); //Met le mode de jeu dans sa phase d'attente

		players = new ArrayList<>();
		playersStates = new HashMap<Player, States>();
		//Permet de lancer le SCOREBOARD et d'initialiser les teams bleues et rouges
		scheduledExecutorService = Executors.newScheduledThreadPool(16);
		executorMonoThread = Executors.newScheduledThreadPool(1);
		scoreboardManager = new ScoreboardManager();
		scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		blueTeam = doTeam("Bleue", "§9");
		redTeam = doTeam("Rouge", "§c");

		PluginManager pm = getServer().getPluginManager();
		//Package Player
		pm.registerEvents(new FoodListener(), this);
		//Package WAITING
		pm.registerEvents(new PlayerManager(this), this);
		pm.registerEvents(new PlayerTeamSelect(this), this);
		pm.registerEvents(new PlayerDoNotAction(this), this);
		//Package PLAYING
		pm.registerEvents(new PlayerKillAnotherPlayer(this), this);
		//Package SetTeams
		pm.registerEvents(new onSetTeamsInventory(this), this);
		getCommand("teams").setExecutor(new setTeams(this));
		
		plm = new PlayerManager(this);
		for(Player player : Bukkit.getOnlinePlayers())
			plm.doGestionEnable(player);
	}
	
	@Override
	public void onDisable() {
		scoreboardManager.onDisable();
		for(Player player : Bukkit.getOnlinePlayers()) 
			plm.doGestionDisable(player);
		blueTeam.unregister();
		redTeam.unregister();
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

	public Team getRedTeam() { return redTeam; }

	public Team getBlueTeam() { return blueTeam; }
	
	public Team doTeam(String teamName, String prefix) {
		Team team = scoreboard.getTeam(teamName);
		if(team == null) 
			team = scoreboard.registerNewTeam(teamName);
		team.setPrefix(prefix);
		team.setAllowFriendlyFire(false);
		return team;
	}

	public String getGameTime() { return gameTime; }
	
	public void setGameTime(String gameTime) { this.gameTime = gameTime; }
}
