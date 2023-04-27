package fr.midey.StarCraft;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.midey.StarCraft.listeners.playerManager;


public class Main extends JavaPlugin{
	
	private HashMap<Player, Float> forcePlayer = new HashMap<Player, Float>();
	private HashMap<Player, Float> speedPlayer = new HashMap<Player, Float>();
	private HashMap<Player, Float> resistancePlayer = new HashMap<Player, Float>();

	@Override
	public void  onEnable() {
		
		saveDefaultConfig();
		Bukkit.broadcastMessage("Enable");
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new playerManager(), this);		
	}
	
	public HashMap<Player, Float> getForcePlayer() {
		return forcePlayer;
	}

	public void setForcePlayer(HashMap<Player, Float> forcePlayer) {
		this.forcePlayer = forcePlayer;
	}
	
	public void setSpecificForcePlayer(Player p, Float f) {
		if (!forcePlayer.containsKey(p)) forcePlayer.put(p, f);
		else forcePlayer.replace(p, f);
	}

	public HashMap<Player, Float> getSpeedPlayer() {
		return speedPlayer;
	}

	public void setSpeedPlayer(HashMap<Player, Float> speedPlayer) {
		this.speedPlayer = speedPlayer;
	}
	
	public void setSpecificSpeedPlayer(Player p, Float f) {
		if (!speedPlayer.containsKey(p)) speedPlayer.put(p, f);
		else speedPlayer.replace(p, f);
	}

	public HashMap<Player, Float> getResistancePlayer() {
		return resistancePlayer;
	}

	public void setResistancePlayer(HashMap<Player, Float> resistancePlayer) {
		this.resistancePlayer = resistancePlayer;
	}
	
	public void setSpecificResistancePlayer(Player p, Float f) {
		if (!resistancePlayer.containsKey(p)) resistancePlayer.put(p, f);
		else resistancePlayer.replace(p, f);
	}
}
