package fr.midey.starcraft.mySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

import org.bukkit.entity.Player;

import fr.midey.starcraft.Stats;

public class DbUpdate {
	
	private static Stats main;
	public DbUpdate(Stats main) {
		DbUpdate.main = main;
	}
	
	public void updateBDD(Player p) {

		UUID uuid = p.getUniqueId();
		DbConnection statsConnection = main.getDbManager().getStatsConnection();
		try {
			Float s = main.getStatsControler().getSpeedPlayer().get(p);
			Float f = main.getStatsControler().getForcePlayer().get(p);
			Float r = main.getStatsControler().getResistancePlayer().get(p);
			String g = main.getPlayerGrade().get(p.getUniqueId());
			Timestamp date = new Timestamp(System.currentTimeMillis());
			Connection connection = statsConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player_stats SET speed = "+ s +", strenght = "+f+", resistance = "+r+" ,Grade = '"+g+"', updated_at = ? WHERE uuid = ?");
			preparedStatement.setTimestamp(1, date);
			preparedStatement.setString(2, uuid.toString());
			preparedStatement.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
