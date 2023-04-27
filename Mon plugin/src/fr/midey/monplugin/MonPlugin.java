package fr.midey.monplugin;

import org.bukkit.plugin.java.JavaPlugin;

import fr.midey.monplugin.commands.CommandKit;
import fr.midey.monplugin.commands.CommandTest;

public class MonPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("S'allume");
		getCommand("test").setExecutor(new CommandTest());
		getCommand("alert").setExecutor(new CommandTest());
		getCommand("kit").setExecutor(new CommandKit());
		getCommand("bidule").setExecutor(new CommandKit());
		getCommand("weekly").setExecutor(new CommandKit());
		getCommand("mlg").setExecutor(new CommandKit());
		getCommand("sword").setExecutor(new CommandKit());
		getServer().getPluginManager().registerEvents(new PatateCmd(), this);
	}
	
	@Override
	public void onDisable() {
		System.out.println("s'éteint");
	}
}