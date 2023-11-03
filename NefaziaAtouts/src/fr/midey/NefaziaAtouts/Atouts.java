package fr.midey.NefaziaAtouts;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Atouts extends JavaPlugin {

	private final HashMap<UUID, Boolean> playerNoFallEnable = new HashMap<>();
	private final HashMap<UUID, Boolean> playerAntiCleanUpEnable = new HashMap<>();
	private final HashMap<UUID, Boolean> playerNoRodEnable = new HashMap<>();
    private File customConfigFile;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {
        createCustomConfig(); // S'assure que le fichier de configuration personnalisé existe
        
        // Les autres appels pour enregistrer les commandes et les événements
        getCommand("atouts").setExecutor(new SimpleAtouts(this));
        getCommand("atouts").setTabCompleter(new TheTabCompleter());
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new NoFallDamage(this), this);
        pm.registerEvents(new NoRodDamage(this), this);
        pm.registerEvents(new AntiCleanUp(this), this);

        // Charger les données ici
        loadAtoutData();
    }

    @Override
    public void onDisable() {
        // Sauvegarder les données ici
        saveAtoutData();
    }


	public FileConfiguration getCustomConfig() {
	    return this.customConfig;
	}
	
	private void createCustomConfig() {
	    customConfigFile = new File(getDataFolder(), "atouts_data.yml");
	    if (!customConfigFile.exists()) {
	        customConfigFile.getParentFile().mkdirs();
	        saveResource("atouts_data.yml", false);
	    }
	
	    customConfig = new YamlConfiguration();
	    try {
	        customConfig.load(customConfigFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void saveAtoutData() {
	    for (UUID uuid : playerNoFallEnable.keySet()) {
	        customConfig.set(uuid + ".NoFall", playerNoFallEnable.get(uuid));
	    }
	    for (UUID uuid : playerAntiCleanUpEnable.keySet()) {
	        customConfig.set(uuid + ".AntiCleanUp", playerAntiCleanUpEnable.get(uuid));
	    }
	    for (UUID uuid : playerNoRodEnable.keySet()) {
	        customConfig.set(uuid + ".NoRod", playerNoRodEnable.get(uuid));
	    }
	    try {
	        customConfig.save(customConfigFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void loadAtoutData() {
	    for (String key : customConfig.getKeys(false)) {
	        UUID uuid = UUID.fromString(key);
	        playerNoFallEnable.put(uuid, customConfig.getBoolean(key + ".NoFall"));
	        playerAntiCleanUpEnable.put(uuid, customConfig.getBoolean(key + ".AntiCleanUp"));
	        playerNoRodEnable.put(uuid, customConfig.getBoolean(key + ".NoRod"));
	    }
	}

	public HashMap<UUID, Boolean> getPlayerNoFallEnable() { return playerNoFallEnable; }

	public HashMap<UUID, Boolean> getPlayerAntiCleanUpEnable() { return playerAntiCleanUpEnable; }

	public HashMap<UUID, Boolean> getPlayerNoRodEnable() { return playerNoRodEnable; }
}
