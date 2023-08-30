package fr.midey.onepiececraft.Region;


import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.midey.onepiececraft.OnePieceCraft;

public class GamePlayer {

    private Player player;

    private Location pos1;
    private Location pos2;
	private int countClaims;

    public static Map<String, GamePlayer> gamePlayers = new HashMap<>();

    public GamePlayer(String playerName) {
        this.player = Bukkit.getPlayer(playerName);
        
        for (String uuidStr : OnePieceCraft.MAIN.config.get().getKeys(false)) {
        	if(player.getUniqueId().toString().equals(uuidStr.substring(0, uuidStr.length() - 2))) { 
        		countClaims++;
        	}
        }
        
        gamePlayers.put(player.getName(), this);
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }
    
    public void setCountClaims(int countClaims) {
    	this.countClaims = countClaims;
    }
    
    public int getCountClaims() {
    	return countClaims;
    }
}
