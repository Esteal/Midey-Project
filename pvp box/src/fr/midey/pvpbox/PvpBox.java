package fr.midey.pvpbox;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.midey.pvpbox.commands.Commands;
import fr.midey.pvpbox.interaction.Interaction;

public class PvpBox extends JavaPlugin {

	public static HashMap<UUID, Integer> cooldown = new HashMap<UUID, Integer>();
	
	@Override
	public void onEnable() {
		System.out.println("Plugin load !");
		getCommand("kit").setExecutor(new Commands());
		getCommand("set").setExecutor(new Commands());
		getCommand("feed").setExecutor(new Commands());
		getCommand("Health").setExecutor(new Commands());
		getServer().getPluginManager().registerEvents(new Interaction(), this);
		
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				for (Map.Entry<UUID, Integer> entry : PvpBox.cooldown.entrySet()) {
					entry.setValue(Integer.valueOf(entry.getValue().intValue()- 1));
					if(entry.getValue().intValue() == 0) {
						PvpBox.cooldown.remove(entry.getKey());
					}
				}
			}
		}, 0L, 20L);
	}
	@Override
	public void onDisable() {
		System.out.println("Plugin reload or server shut down !");
	}
}
