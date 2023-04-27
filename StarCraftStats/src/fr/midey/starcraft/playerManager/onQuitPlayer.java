package fr.midey.starcraft.playerManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.midey.starcraft.Stats;
import fr.midey.starcraft.mySQL.DbConnection;

public class onQuitPlayer implements Listener {

	private Stats main;
	
	public onQuitPlayer(Stats main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		DbConnection statsConnection = main.getDbManager().getStatsConnection();
		try {
			Float s = main.getStatsControler().getSpeedPlayer().get(p);
			Float f = main.getStatsControler().getForcePlayer().get(p);
			Float r = main.getStatsControler().getResistancePlayer().get(p);
			String g = main.getPlayerGrade().get(p.getUniqueId());
			Connection connection = statsConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player_stats SET speed = "+ s +", strenght = "+f+", resistance = "+r+" ,Grade = '"+g+"' WHERE uuid = ?");
			preparedStatement.setString(1, uuid.toString());
			preparedStatement.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
