package fr.midey.starcraft.statsManager;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class StatsControler {

	private HashMap<Player, Float> forcePlayer = new HashMap<Player, Float>();
	private HashMap<Player, Float> speedPlayer = new HashMap<Player, Float>();
	private HashMap<Player, Float> resistancePlayer = new HashMap<Player, Float>();
	
	public HashMap<Player, Float> getForcePlayer() {
		return forcePlayer;
	}

	public void setForcePlayer(HashMap<Player, Float> forcePlayer) {
		this.forcePlayer = forcePlayer;
	}

	public HashMap<Player, Float> getSpeedPlayer() {
		return speedPlayer;
	}

	public HashMap<Player, Float> getResistancePlayer() {
		return resistancePlayer;
	}
}
