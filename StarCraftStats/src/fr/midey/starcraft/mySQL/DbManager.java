package fr.midey.starcraft.mySQL;

import java.sql.SQLException;

public class DbManager {
	
	private DbConnection StatsConnection;
	
	public DbManager() {
		this.StatsConnection = new DbConnection(new Dbcredentials("45.140.165.82", "u928_GTIXvG4vmh", "rMQX!bmO^cr8G7Panf=vgSO!", "s928_DbStats", 3306));
	}

	public DbConnection getStatsConnection() {
		return StatsConnection;
	}
	
	public void close() {
		try {
			this.StatsConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
