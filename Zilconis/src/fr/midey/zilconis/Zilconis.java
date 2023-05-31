package fr.midey.zilconis;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.midey.zilconis.Spell.Spell1;

public class Zilconis extends JavaPlugin{

	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new Spell1(this), this);
	}
}
