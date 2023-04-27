package fr.midey.spellmaker;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.midey.spellmaker.Listeners.interactSpell;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new interactSpell(this), this);
	}
}
