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
import fr.midey.buildrush.Player.CheckLocation;
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
	private PlayerManager plm;
	private int numberPerTeam; //Définit le nombre de joueur par équipe
	private HashMap<Player, States> playersStates; //Divers stats du joueur visible dans la classe States
	private Scoreboard scoreboard ; //Scoreboard
	private ScoreboardManager scoreboardManager; //Déclaration du scoreboard
	private ScheduledExecutorService executorMonoThread; //Lié au scoreboard
	private ScheduledExecutorService scheduledExecutorService; //Lié au scoreboard
	private String gameTime = "00:00:00"; //timer du scoreboard
    private Team redTeam; //red team
    private Team blueTeam; //blue team
    private Team loserTeam; //Team perdante
    private String redKill = "0";
    private String blueKill = "0";
    private int killObjective; //nombre de kill à atteindre pour gagner la partie
    private boolean gameEnding = false;

	@Override
	public void onEnable() {
		instance = this;
		setGameCycle(GameCycle.WAITING); //Met le mode de jeu dans sa phase d'attente
		saveDefaultConfig();
		
		//Recup des infos venant de la config
		killObjective = getConfig().getInt("killObjective");
		numberPerTeam = getConfig().getInt("playerParTeam");
		
		players = new ArrayList<>();
		playersStates = new HashMap<Player, States>();
		//Permet de lancer le SCOREBOARD et d'initialiser les teams bleues et rouges
		scheduledExecutorService = Executors.newScheduledThreadPool(16);
		executorMonoThread = Executors.newScheduledThreadPool(1);
		scoreboardManager = new ScoreboardManager();
		scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		blueTeam = doTeam("Bleue", "§9Bleue");
		redTeam = doTeam("Rouge", "§cRouge");
		
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
		
		CheckLocation checkLocation = new CheckLocation(this);
		checkLocation.runTaskTimer(this, 0, 10);
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

	public String getGameTime() { return gameTime; }
	
	public void setGameTime(String gameTime) { this.gameTime = gameTime; }

	public String getRedKill() { return redKill; }

	public void setRedKill(String redKill) { this.redKill = redKill; }

	public String getBlueKill() { return blueKill; }

	public void setBlueKill(String blueKill) { this.blueKill = blueKill; }

	public int getKillObjective() { return killObjective; }

	public void setKillObjective(int killObjective) { this.killObjective = killObjective; }

	public boolean isGameEnding() { return gameEnding; }

	public void setGameEnding(boolean gameEnding) { this.gameEnding = gameEnding; }

	public Team doTeam(String teamName, String prefix) {
		Team team = scoreboard.getTeam(teamName);
		if(team == null) 
			team = scoreboard.registerNewTeam(teamName);
		team.setPrefix(prefix);
		team.setAllowFriendlyFire(false);
		return team;
	}

	public Team getLoserTeam() {
		return loserTeam;
	}

	public void setLoserTeam(Team loserTeam) {
		this.loserTeam = loserTeam;
	}
}
