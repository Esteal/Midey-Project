package fr.midey.clashroyale.gameManager;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.midey.clashroyale.Main;
import fr.midey.clashroyale.board.ScoreBoard;
import fr.midey.clashroyale.state.GPlayerState;
import fr.midey.clashroyale.stuff.Armor;
import net.md_5.bungee.api.ChatColor;

public class StateManager {

	private static Main main;
	
	public StateManager(Main main) {
		StateManager.main = main;
	}
	
	public static void onPlayerLoad(Player p) {
		main.saveDefaultConfig();
		
		ScoreBoard.createScoreBoard(p);
		ScoreBoard.updateScoreboard();
		
		if(!main.getPlayers().contains(p)) main.getPlayers().add(p);
		
		if (main.getPlayers().contains(p)) main.getPlayerState().put(p, GPlayerState.Nothing);
		
		Location loc = new Location(Bukkit.getWorld("world"), main.getConfig().getDouble("spawnlobby.x"), main.getConfig().getDouble("spawnlobby.y"), main.getConfig().getDouble("spawnlobby.z"), (float ) main.getConfig().getDouble("spawnlobby.ya"), (float) main.getConfig().getDouble("spawnlobby.pe"));
		p.setBedSpawnLocation(loc);
		p.teleport(loc);
		Armor.clearALL(p);
		p.setGameMode(GameMode.SURVIVAL);
		p.getInventory().setItem(4, Armor.EnchantedItem(Material.COMPASS, ChatColor.BLUE + "MENU"));
	}
}
