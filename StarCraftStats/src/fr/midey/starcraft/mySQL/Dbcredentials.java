package fr.midey.starcraft.mySQL;

public class Dbcredentials {
	
	private String host;
	private String user;
	private String pass;
	private String dbName;
	private int port;

	public Dbcredentials(String host, String user, String pass, String dbName, int port) {
		this.host = host;
		this.user = user;
		this.pass = pass;
		this.dbName = dbName;
		this.port = port;
	}
	
	public String toURI() {
		StringBuilder sb = new StringBuilder();
		sb.append("jdbc:mysql://")
		.append(host)
		.append(":")
		.append(port)
		.append("/")
		.append(dbName);
		return sb.toString();
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPass() {
		return pass;
	}
}
