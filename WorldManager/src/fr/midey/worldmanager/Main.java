package fr.midey.worldmanager;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	@Override
	public void onEnable() {
		System.out.println("Plugin de Reset de map ON");
	}
	
	@Override
	public void onDisable() {
		System.out.println("Reset de la map");
		WorldManager.replaceWorld(true);
	}
}
