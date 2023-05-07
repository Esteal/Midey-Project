package fr.midey.MagicUHC;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import fr.midey.MagicUHC.Magie.MagicSelection;
import fr.midey.MagicUHC.Magie.Eau.DashAquatique;
import fr.midey.MagicUHC.Magie.Eau.Geyser;
import fr.midey.MagicUHC.Magie.Eau.Tsunami;
import fr.midey.MagicUHC.Magie.Terre.Faille;
import fr.midey.MagicUHC.Magie.Terre.Piliers;
import fr.midey.MagicUHC.Magie.Terre.Seisme;
import fr.midey.uhcmeetup.GState;
import fr.midey.uhcmeetup.Gmain;

public class MagicUHC extends JavaPlugin {
	
	private BukkitTask taskID;
	private HashMap<Player, Nature> playerNature = new HashMap<Player, Nature>();
	public boolean game = false;
	MagicSelection selec = new  MagicSelection(this);
	
	@Override
	public void onEnable() {
		
		PluginManager pm = getServer().getPluginManager();
		//Sorts de terre
		pm.registerEvents(new Seisme(this), this);
		pm.registerEvents(new Piliers(this), this);
		pm.registerEvents(new Faille(this), this);
		
		//Sorts d'eau
		pm.registerEvents(new Tsunami(this), this);
		pm.registerEvents(new Geyser(this), this);
		pm.registerEvents(new DashAquatique(this), this);
		
		taskID = Bukkit.getScheduler().runTaskTimer(this, () -> {
			if(Gmain.isState(GState.PLAYING)) {
				game = true;
				selec.magieSelection();
				taskID.cancel();
			}
		}, 10, 0);
	}
	public HashMap<Player, Nature> getPlayerNature() {
		return playerNature;
	}
}
