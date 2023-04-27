package fr.midey.clashroyale.board;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import fr.midey.clashroyale.Main;
import fr.midey.clashroyale.state.Gteam;
import net.md_5.bungee.api.ChatColor;

public class ScoreBoard {

	private static Main main;
	public ScoreBoard(Main main) {
		ScoreBoard.main = main;
	}
	
	@SuppressWarnings("deprecation")
	public static void createScoreBoard(Player p) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective obj = board.registerNewObjective("States", "dummy");
		obj.setDisplayName(ChatColor.YELLOW + "---Craft Royale---");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		Team red = board.registerNewTeam("Rouge");
		Team blue = board.registerNewTeam("Blue");
		
		red.setPrefix(ChatColor.RED + "");
		blue.setPrefix(ChatColor.BLUE + "");
		
		red.setAllowFriendlyFire(true);
		blue.setAllowFriendlyFire(true);
		
		if (main.getPlayerTeam().get(p) == Gteam.Blue) blue.addPlayer(p);
		if (main.getPlayerTeam().get(p) == Gteam.Red) red.addPlayer(p);
		
		Score nbPlayer = obj.getScore("Players : (" +  Bukkit.getMaxPlayers() + "/" + Bukkit.getMaxPlayers() + ")");
		
		nbPlayer.setScore(0);
		p.setScoreboard(board);
	}
	
	public static void updateScoreboard() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Score nbPlayer = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore("Players : (" +  Bukkit.getMaxPlayers() + "/" + Bukkit.getMaxPlayers() + ")");
			nbPlayer.setScore(0);
		}
	}
}
