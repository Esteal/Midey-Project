package fr.midey.starcraft;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.midey.starcraft.Damage.onInvincible;
import fr.midey.starcraft.menuManager.clickMenu;
import fr.midey.starcraft.menuManager.clickMenuInventory;
import fr.midey.starcraft.playerManager.JoinPlayer;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		System.out.println("Successfully connected worldPlugin");
		
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new clickMenu(), this);
		pm.registerEvents(new clickMenuInventory(this), this);
		pm.registerEvents(new onInvincible(), this);
		pm.registerEvents(new JoinPlayer(), this);

	}
}
