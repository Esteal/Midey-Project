package fr.midey.MagicUHC;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import fr.midey.MagicUHC.Magie.MagicSelection;
import fr.midey.MagicUHC.Magie.Air.Bourrasque;
import fr.midey.MagicUHC.Magie.Air.EnvoléCéleste;
import fr.midey.MagicUHC.Magie.Air.Ouragan;
import fr.midey.MagicUHC.Magie.Eau.DashAquatique;
import fr.midey.MagicUHC.Magie.Eau.Geyser;
import fr.midey.MagicUHC.Magie.Eau.Tsunami;
import fr.midey.MagicUHC.Magie.Feu.CercleDesEnfers;
import fr.midey.MagicUHC.Magie.Feu.Purification;
import fr.midey.MagicUHC.Magie.Feu.WallEnfer;
import fr.midey.MagicUHC.Magie.Terre.Faille;
import fr.midey.MagicUHC.Magie.Terre.Piliers;
import fr.midey.MagicUHC.Magie.Terre.Seisme;
import fr.midey.uhcmeetup.GState;
import fr.midey.uhcmeetup.Gmain;

public class MagicUHC extends JavaPlugin {
	
	private BukkitTask taskID;
	private HashMap<Player, Nature> playerNature = new HashMap<Player, Nature>();
	private HashMap<Player, Integer> playerMana = new HashMap<Player, Integer>();
	private DisplayHotBarMessage displayHotBarMessage = new DisplayHotBarMessage();
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
		
		//Sorts de feu
		pm.registerEvents(new WallEnfer(this), this);
		pm.registerEvents(new Purification(this), this);
		pm.registerEvents(new CercleDesEnfers(this), this);
		
		//Sorts de l'air
		pm.registerEvents(new EnvoléCéleste(this), this);
		pm.registerEvents(new Bourrasque(this), this);
		pm.registerEvents(new Ouragan(this), this);
		
		//Give de l'afinité magique + du mana + rechargement du mana
		taskID = Bukkit.getScheduler().runTaskTimer(this, () -> {
			if(Gmain.isState(GState.PLAYING)) {
				game = true;
				selec.magieSelection();
				for(Player players : Bukkit.getOnlinePlayers()) 
					playerMana.put(players, 1000);
				Bukkit.getScheduler().runTaskTimer(this, () -> {
					for(Player players : Bukkit.getOnlinePlayers()) {
						Integer mana = playerMana.get(players);
						if(mana < 1000)
							playerMana.replace(players, mana + 1);
						displayHotBarMessage.displayHotbarMessage(players, ("§l§3✧" + mana+ "✧"), 20);
					}
				}, 0, 20);
				taskID.cancel();
			}
		}, 10, 0);
	}
	
	public HashMap<Player, Nature> getPlayerNature() {
		return playerNature;
	}

	public HashMap<Player, Integer> getPlayerMana() {
		return playerMana;
	}
}
