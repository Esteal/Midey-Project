package fr.midey.NefaziaAtouts;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Atouts extends JavaPlugin {

	@Override
	public void onEnable() {
		
		getCommand("atouts").setExecutor(new SimpleAtouts());
        getCommand("atouts").setTabCompleter(new TheTabCompleter());

		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new NoFallDamage(), this);
		pm.registerEvents(new NoRodDamage(), this);
	    pm.registerEvents(new AntiCleanUp(), this);
	}
}
