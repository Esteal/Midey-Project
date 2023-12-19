package fr.midey.LgUHC;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LgUHC extends JavaPlugin{

	private HashMap<UUID, PlayerData> playerDataMap = new HashMap<UUID, PlayerData>();
	private State gameState;
	private int playerBeforeAutoStart;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		registerVar();
		registerEvent();
		
		for(World world : Bukkit.getWorlds()) {
			WorldBorder wb = world.getWorldBorder();
			wb.setCenter(0, 0);
			wb.setSize(2000);
		}
		
		Bukkit.getOnlinePlayers().forEach(p -> new PlayerJoin(this).loadGame(p));
		
	}

	public void registerVar() {
		setGameState(State.WAITING);
		setPlayerBeforeAutoStart(this.getConfig().getInt("playerBeforeAutoStart"));
	}
	
	public void registerEvent() {
        PluginManager pm = getServer().getPluginManager();
        
        pm.registerEvents(new PlayerJoin(this), this);
	}
	
	public void teleportPlayerRandomly(Player player) {
	    World world = player.getWorld();
	    WorldBorder worldBorder = world.getWorldBorder();
	    Random random = new Random();
	    
	    int minX = worldBorder.getCenter().getBlockX();
	    int minZ = worldBorder.getCenter().getBlockZ();
	    
	    int x = minX + random.nextInt((int) (worldBorder.getSize()/2 - minX + 1));
	    int z = minZ + random.nextInt((int) (worldBorder.getSize()/2 - minZ + 1));

	    Location randomLocation = new Location(world, x + 0.5, world.getMaxHeight(), z + 0.5);
	    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*30, 255, false, false));
	    player.teleport(randomLocation);
	}

	
	public HashMap<UUID, PlayerData> getPlayerDataMap() { return playerDataMap; }
	public PlayerData getPlayerData(Player player) { return getPlayerDataMap().getOrDefault(player.getUniqueId(), new PlayerData()); }

	public State getGameState() { return gameState; }
	public void setGameState(State gameState) { this.gameState = gameState; }

	public int getPlayerBeforeAutoStart() { return playerBeforeAutoStart; }
	public void setPlayerBeforeAutoStart(int playerBeforeAutoStart) { this.playerBeforeAutoStart = playerBeforeAutoStart; }
}
