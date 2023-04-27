package fr.midey.starcraft.playerManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.midey.starcraft.Stats;
import fr.midey.starcraft.mySQL.DbConnection;

public class onJoinPlayer implements Listener {

	private static Stats main;
	
	public onJoinPlayer(Stats main) {
		onJoinPlayer.main = main;
	}



	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		UUID uuid = e.getPlayer().getUniqueId();
		DbConnection statsConnection = main.getDbManager().getStatsConnection();
		
		try {
			
			Connection connection = statsConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid, strenght, speed, Grade, resistance FROM player_stats WHERE uuid = ?");
			
			preparedStatement.setString(1, uuid.toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				String grade = resultSet.getString("Grade");
				Float speed = resultSet.getFloat("speed");
				Float force = resultSet.getFloat("strenght");
				Float resistance = resultSet.getFloat("resistance");
				loadUserStats(uuid, speed, force, resistance);
				loadGrade(uuid, grade);
			}
			else {
				createUserStats(connection, uuid);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}	
	}

	public static void createUserStats(Connection connection, UUID uuid) {
		try {
			
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO player_stats VALUES (?, ?, ?, ?, ?, ?, ?)");
			long time = System.currentTimeMillis();
			preparedStatement.setString(1, uuid.toString());
			preparedStatement.setString(2, "Vagabond");
			preparedStatement.setFloat(3, 0.2f);
			preparedStatement.setFloat(4, 0.0f);
			preparedStatement.setFloat(5, 0.0f);
			preparedStatement.setTimestamp(6, new Timestamp(time));
			preparedStatement.setTimestamp(7, new Timestamp(time));
			preparedStatement.executeUpdate();
			
			loadUserStats(uuid, 0.2f, 0.0f, 0.0f);
			loadGrade(uuid, "Vagabond");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void loadUserStats(UUID uuid, Float speed, Float force, Float resistance) {
		
		
		Player p = Bukkit.getPlayer(uuid);
		main.getStatsControler().getForcePlayer().put(p, force);
		main.getStatsControler().getSpeedPlayer().put(p, speed);
		main.getStatsControler().getResistancePlayer().put(p, resistance);
	}

	public static void loadGrade(UUID uuid, String grade) {
		main.getPlayerGrade().put(uuid, grade);	
	}

}
