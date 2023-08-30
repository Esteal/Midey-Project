package fr.midey.onepiececraft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.midey.onepiececraft.Faction.Faction;
import fr.midey.onepiececraft.Faction.FactionCommands;
import fr.midey.onepiececraft.Region.ClaimManager;
import fr.midey.onepiececraft.Region.GamePlayer;
import fr.midey.onepiececraft.Region.RegionManager;

public class OnePieceCraft extends JavaPlugin{
	
	public static OnePieceCraft MAIN;
	public ConfigFile config;
	public List <RegionManager> regions;
    public List<Faction> factions;
	public HashMap<String, Long> pendingInvitations;

	@Override
	public void onEnable() {
		MAIN = this;
		
		reloadConfig();
		
		
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new ClaimManager(), this);
		getCommand("f").setExecutor(new FactionCommands());
		
		regions = new ArrayList<>();
		
		this.config = new ConfigFile(this, "config.yml");
		this.factions = new ArrayList<>();
		this.pendingInvitations = new HashMap<String, Long>();
		
		for (String uuids : config.get().getKeys(false)) {
			List<String> coo = config.get().getStringList(uuids);
			if(coo.size() < 7) continue;
			Location pos1 = new Location(Bukkit.getWorld(coo.get(6)), Double.parseDouble(coo.get(0)), Double.parseDouble(coo.get(1)), Double.parseDouble(coo.get(2)));
			Location pos2 = new Location(Bukkit.getWorld(coo.get(6)), Double.parseDouble(coo.get(3)), Double.parseDouble(coo.get(4)), Double.parseDouble(coo.get(5)));

			RegionManager region = new RegionManager(pos1, pos2, coo.get(7));
			this.regions.add(region);
		}
		
		loadFactions();
		
	    Bukkit.getScheduler().runTaskTimer(this, this::cleanExpiredInvitations, 0L, 20L);
		
		for (Player ps : Bukkit.getOnlinePlayers()) { new GamePlayer(ps.getName()); }
	}
	
	@Override
	public void onDisable() {}
	
	public static OnePieceCraft getMAIN() { return MAIN; }

	private void loadFactions() {
		//LOAD FACTION PAS CORRECTE && PEUT ETRE LA FONCTION F LEAD QUI POSE PROBLEME
	    ConfigurationSection factionsSection = OnePieceCraft.getMAIN().config.get().getConfigurationSection("factions");
	    if (factionsSection != null) {
	        for (String factionName : factionsSection.getKeys(false)) {
	            UUID factionLeader = UUID.fromString(factionsSection.getString(factionName + ".leader"));
	            List<String> factionMembersNames = factionsSection.getStringList(factionName + ".members");

	            Faction faction = new Faction(factionName, factionLeader);
	            for (String memberName : factionMembersNames) {
	                // Convertir les noms de joueurs en UUIDs
	                Player player = Bukkit.getPlayerExact(memberName);
	                if (player != null) {
	                    faction.addMember(player.getUniqueId());
	                }
	            }

	            // Cherche si la faction existe déjà dans la liste des factions
	            // Si oui, nous la mettons à jour plutôt que de l'ajouter à nouveau
	            int existingIndex = findFactionIndexByName(factionName);
	            if (existingIndex >= 0) {
	                factions.set(existingIndex, faction);
	            } else {
	                factions.add(faction);
	            }
	        }
	    }
	}


	private int findFactionIndexByName(String factionName) {
	    for (int i = 0; i < factions.size(); i++) {
	        Faction faction = factions.get(i);
	        if (faction.getName().equals(factionName)) {
	            return i;
	        }
	    }
	    return -1;
	}
	
	private void cleanExpiredInvitations() {
	    long currentTime = System.currentTimeMillis();
	    Iterator<Map.Entry<String, Long>> iterator = pendingInvitations.entrySet().iterator();
	    while (iterator.hasNext()) {
	        Map.Entry<String, Long> entry = iterator.next();
	        if (currentTime >= entry.getValue()) {
	            iterator.remove();
	            Player player = Bukkit.getPlayerExact(entry.getKey());
	            if (player != null) {
	                player.sendMessage("§cL'invitation envoyée a expiré.");
	            }
	        }
	    }
	}
	
	public class ConfigFile {
		private File file;
		private YamlConfiguration conf;
 
		public ConfigFile(JavaPlugin plugin, String fileName) {
			this.file = new File(plugin.getDataFolder(), fileName);
			if (!file.exists())
				try {
					if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
					InputStream in = plugin.getResource(fileName);
					if (in != null) {
						OutputStream out = new FileOutputStream(file);
 
						byte[] buf = new byte[1024 * 4];
						int len = in.read(buf);
						while (len != -1) {
							out.write(buf, 0, len);
							len = in.read(buf);
						}
						out.close();
						in.close();
					}
					else file.createNewFile();
				} catch(Exception e) {e.printStackTrace();}
			reload();
		}
		
		public void reload() {
			try {
				conf = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(file)));
			}
			catch (FileNotFoundException e) { e.printStackTrace(); }
		}
		
		public YamlConfiguration get() {
			return conf;
		}
		
		public void save() {
			try {
				conf.save(file);
			} catch (IOException e) { e.printStackTrace(); }
		}
	}
}
