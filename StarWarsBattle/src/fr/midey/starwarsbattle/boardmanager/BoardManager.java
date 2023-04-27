package fr.midey.starwarsbattle.boardmanager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import fr.midey.starwarsbattle.Main;
import fr.midey.starwarsbattle.state.Kits;
import fr.midey.starwarsbattle.state.Pstate;
import fr.midey.starwarsbattle.timer.Gtime;
import net.md_5.bungee.api.ChatColor;

public class BoardManager {

	private static Main main;
	public BoardManager(Main main) {
		BoardManager.main = main;
	}
	
	public static void boardManager(Player player) {
		
		final ScoreboardManager scoreboardManager  = Bukkit.getScoreboardManager();
		final Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
		final Objective objective = scoreboard.registerNewObjective("general", "dummy");
		
		objective.setDisplayName(ChatColor.GOLD + "---StarWarsBattle---");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
				
		String teamS = "";
		String role = "";
		String vie = "" + main.getVie(player);
		String nbEmpire = "" + main.nbTeam(Pstate.EMPIRE);
		String nbJedi = "" + main.nbTeam(Pstate.JEDI);
		String nbKIll = "" + main.getSpecifiKill(player);
		String duration = "00:00";
		String StaminaDisplay = "" + main.getStaminaOfPlayer(player);
		
		if(main.isPState(Pstate.EMPIRE, player)) teamS = "§4EMPIRE";
		else if(main.isPState(Pstate.JEDI, player)) teamS = "§bJEDI";
		
		if (Gtime.iheure == 0) {
			if (Gtime.iminute < 10 && Gtime.iseconde < 10) duration = "0" + Gtime.iminute + " : 0" + Gtime.iseconde;
			if (Gtime.iseconde >= 10 && Gtime.iminute < 10) duration = "0" + Gtime.iminute + " : " + Gtime.iseconde;
			if (Gtime.iminute >= 10 && Gtime.iseconde >= 10) duration = "" + Gtime.iminute + " : " + Gtime.iseconde;
		}
		else {
			if (Gtime.iminute < 10 && Gtime.iseconde < 10) duration = "01 : 0" + Gtime.iminute + " : 0" + Gtime.iseconde;
			if (Gtime.iseconde >= 10 && Gtime.iminute < 10) duration = "01 : " + Gtime.iminute + " : " + Gtime.iseconde;
			if (Gtime.iminute >= 10 && Gtime.iseconde >= 10) duration = "01 : " + Gtime.iminute + " : " + Gtime.iseconde;
		}
		if(main.isKits(Kits.DARTH_VADER, player)) role = "§4Darth Vader";
		else if(main.isKits(Kits.TROOPER, player)) role = "§4StormTrooper";
		else if(main.isKits(Kits.MANDO, player)) role = "§4Boba Fett";
		else if(main.isKits(Kits.FORCE, player)) role = ChatColor.AQUA + "Jedi " + ChatColor.DARK_PURPLE + "(bonus de force)";
		else if(main.isKits(Kits.RESISTANCE, player)) role = ChatColor.AQUA + "Jedi " + ChatColor.DARK_BLUE + "(bonus de résistance)";
		else if(main.isKits(Kits.SPEED, player)) role = ChatColor.AQUA + "Jedi " + ChatColor.DARK_GREEN+ "(bonus de rapidité)";
		
		final Score team = objective.getScore(ChatColor.GRAY + "Team : " + teamS);
		final Score kit = objective.getScore(ChatColor.GRAY + "Kit : " + role);
		final Score life = objective.getScore(ChatColor.GRAY + "Vies restantes : " + ChatColor.LIGHT_PURPLE + vie);
		final Score teamJedi = objective.getScore(ChatColor.GRAY + "Jedi : " + ChatColor.AQUA + nbJedi);
		final Score teamEmpire = objective.getScore(ChatColor.GRAY + "Impériaux : " + ChatColor.DARK_RED + nbEmpire);
		final Score kill = objective.getScore(ChatColor.GRAY + "Kill : " + ChatColor.GREEN + nbKIll);
		final Score gameDuration = objective.getScore(ChatColor.GRAY + "Timer : " + ChatColor.YELLOW + duration);
		final Score StaminaDisplayer = objective.getScore(ChatColor.GRAY + "Stamina : " + ChatColor.WHITE + StaminaDisplay);
		
		team.setScore(7);
		kit.setScore(6);
		life.setScore(5);
		StaminaDisplayer.setScore(4);
		kill.setScore(3);
		teamJedi.setScore(2);
		teamEmpire.setScore(1);
		gameDuration.setScore(0);
		
		player.setScoreboard(scoreboard);
	}
}
