package fr.midey.starcraft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.midey.starcraft.Spell.Attraction;
import fr.midey.starcraft.Spell.BouclierDeForce;
import fr.midey.starcraft.Spell.DrainDeVie;
import fr.midey.starcraft.Spell.Eclairs;
import fr.midey.starcraft.Spell.Etranglement;
import fr.midey.starcraft.Spell.Repulsion;
import fr.midey.starcraft.Spell.SabreDeForce;
import fr.midey.starcraft.Spell.SelectClickSpell;
import fr.midey.starcraft.Spell.SpellCheckTimer;
import fr.midey.starcraft.Spell.VitesseSurhumaine;
import fr.midey.starcraft.commands.CommandsStats;
import fr.midey.starcraft.gradeManager.GradeChat;
import fr.midey.starcraft.gradeManager.GradeLoader;
import fr.midey.starcraft.gradeManager.onMenuGrade;
import fr.midey.starcraft.mySQL.DbConnection;
import fr.midey.starcraft.mySQL.DbManager;
import fr.midey.starcraft.mySQL.DbUpdate;
import fr.midey.starcraft.playerManager.onDeathPlayer;
import fr.midey.starcraft.playerManager.onJoinPlayer;
import fr.midey.starcraft.playerManager.onQuitPlayer;
import fr.midey.starcraft.playerManager.onRespawnPlayer;
import fr.midey.starcraft.statsManager.StatsControler;
import fr.midey.starcraft.statsManager.StatsLoader;

public class Stats extends JavaPlugin{

	private DbManager dbManager;
	private StatsControler statsControler;
	private GradeLoader gradeLoader;
	private DbUpdate dbUpdate;
	private SpellCheckTimer spellCheckTimer;
	
	private HashMap<UUID, String> playerGrade = new HashMap<UUID, String>();
	private HashMap<Player, List<String>> playerSpellAvailable = new HashMap<Player, List<String>>();
	private HashMap<Player, Location> playerLoc = new HashMap<Player, Location>();
	private HashMap<Player, Float> cooldownChangeSpell = new HashMap<Player, Float>();
	private HashMap<Player, Integer> cooldownAttraction = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> cooldownRepulsion = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> cooldownVitesseSurhumaine = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> cooldownEtranglement = new HashMap<Player, Integer>();	
	private HashMap<Player, Integer> cooldownEtranglé = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> cooldownEclairs = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> cooldownDrainDeVie = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> cooldownStunt = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> cooldownVie = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> cooldownBouclier = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> cooldownSabre = new HashMap<Player, Integer>();
	
	@Override
	public void onEnable() {
		
		dbManager = new DbManager();
		statsControler = new StatsControler();
		gradeLoader = new GradeLoader(this);
		dbUpdate = new DbUpdate(this);
		spellCheckTimer = new SpellCheckTimer(this);
		
		spellCheckTimer.runTaskTimer(this, 0, 20);
		gradeLoader.runTaskTimer(this, 0, 20);
		
		PluginManager pm = getServer().getPluginManager();
		
		//PlayerManager
		pm.registerEvents(new onJoinPlayer(this), this);
		pm.registerEvents(new onQuitPlayer(this), this);
		pm.registerEvents(new onDeathPlayer(), this);
		pm.registerEvents(new onRespawnPlayer(this), this);
		//GradeManager
		pm.registerEvents(new StatsLoader(this), this);
		pm.registerEvents(new onMenuGrade(this), this);
		pm.registerEvents(new GradeChat(this), this);
		//Spell choice
		pm.registerEvents(new SelectClickSpell(this), this);
		//Spell basique
		pm.registerEvents(new Attraction(this), this);
		pm.registerEvents(new Repulsion(this), this);
		//Spell sith
		pm.registerEvents(new Etranglement(this), this);
		pm.registerEvents(new Eclairs(this), this);
		pm.registerEvents(new DrainDeVie(this), this);
		//Spell jedi
		pm.registerEvents(new BouclierDeForce(this), this);
		pm.registerEvents(new VitesseSurhumaine(this), this);
		pm.registerEvents(new SabreDeForce(this), this);

		getCommand("set").setExecutor(new CommandsStats(this));
		getCommand("stats").setExecutor(new CommandsStats(this));
		getCommand("wand").setExecutor(new CommandsStats(this));

		for (Player p : Bukkit.getOnlinePlayers()) {
			UUID uuid = p.getUniqueId();
			DbConnection statsConnection = getDbManager().getStatsConnection();
			Connection connection;
			try {
				connection = statsConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid, strenght, speed, Grade, resistance FROM player_stats WHERE uuid = ?");

				preparedStatement.setString(1, uuid.toString());
				ResultSet resultSet = preparedStatement.executeQuery();
				
				if(resultSet.next()) {
					String grade = resultSet.getString("Grade");
					Float speed = resultSet.getFloat("speed");
					Float force = resultSet.getFloat("strenght");
					Float resistance = resultSet.getFloat("resistance");
					onJoinPlayer.loadUserStats(uuid, speed, force, resistance);
					onJoinPlayer.loadGrade(uuid, grade);
				}
				else {
					onJoinPlayer.createUserStats(connection, uuid);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {

			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if(getCooldownChangeSpell().containsKey(p)) {
						getCooldownChangeSpell().remove(p);
					}
				}
			}			
		}, 0, 5);
	}
	
	@Override
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			dbUpdate.updateBDD(p);
		}
		this.dbManager.close();
	}

	public DbManager getDbManager() {
		return dbManager;
	}

	public StatsControler getStatsControler() {
		return statsControler;
	}

	public HashMap<UUID, String> getPlayerGrade() {
		return playerGrade;
	}

	public GradeLoader getGradeLoader() {
		return gradeLoader;
	}

	public DbUpdate getDbUpdate() {
		return dbUpdate;
	}

	public HashMap<Player, List<String>> getPlayerSpellAvailable() {
		return playerSpellAvailable;
	}

	public HashMap<Player, Integer> getCooldownAttraction() {
		return cooldownAttraction;
	}

	public HashMap<Player, Integer> getCooldownRepulsion() {
		return cooldownRepulsion;
	}

	public HashMap<Player, Integer> getCooldownVitesseSurhumaine() {
		return cooldownVitesseSurhumaine;
	}

	public HashMap<Player, Integer> getCooldownEtranglement() {
		return cooldownEtranglement;
	}

	public HashMap<Player, Integer> getCooldownEtranglé() {
		return cooldownEtranglé;
	}

	public HashMap<Player, Integer> getCooldownEclairs() {
		return cooldownEclairs;
	}

	public HashMap<Player, Float> getCooldownChangeSpell() {
		return cooldownChangeSpell;
	}

	public HashMap<Player, Integer> getCooldownDrainDeVie() {
		return cooldownDrainDeVie;
	}

	public HashMap<Player, Location> getPlayerLoc() {
		return playerLoc;
	}

	public HashMap<Player, Integer> getCooldownStunt() {
		return cooldownStunt;
	}

	public HashMap<Player, Integer> getCooldownVie() {
		return cooldownVie;
	}

	public HashMap<Player, Integer> getCooldownBouclier() {
		return cooldownBouclier;
	}

	public HashMap<Player, Integer> getCooldownSabre() {
		return cooldownSabre;
	}
}
