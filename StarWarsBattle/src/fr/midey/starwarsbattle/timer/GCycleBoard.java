package fr.midey.starwarsbattle.timer;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.starwarsbattle.Main;
import fr.midey.starwarsbattle.boardmanager.BoardManager;
import fr.midey.starwarsbattle.commands.Commands;
import fr.midey.starwarsbattle.listeners.onGameListeners;
import fr.midey.starwarsbattle.rolemanager.RoleManager;
import fr.midey.starwarsbattle.state.Gstate;
import fr.midey.starwarsbattle.state.Kits;
import fr.midey.starwarsbattle.state.Pstate;

public class GCycleBoard extends BukkitRunnable{

	int timer = 999999999;
	int game = 0;
	private Main main;
	public GCycleBoard(Main main){
		this.main = main;
	}
	
	@Override
	public void run() {
		
		if (timer == 999999999) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule keepInventory true");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doFireTick false");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule naturalRegeneration false");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule mobGriefing false");
			for (Player p : Bukkit.getOnlinePlayers()) {
				RoleManager.addPlayer(p);
			}
			Commands.onOp();
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.getGameMode() == GameMode.SPECTATOR) continue;
			BoardManager.boardManager(p);
			if(main.isState(Gstate.PLAYING)) {
				if (main.isKits(Kits.SPEED, p) && main.isState(Gstate.PLAYING)) { 
					p.removePotionEffect(PotionEffectType.SPEED);
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 11, 1));
				}
				if (main.isKits(Kits.RESISTANCE, p) && main.isState(Gstate.PLAYING)) {
					p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 11, 0));
				}
				if (main.isKits(Kits.DARTH_VADER, p) && main.isState(Gstate.PLAYING)) {
					p.removePotionEffect(PotionEffectType.SPEED);
					p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 11, 0));
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 11, 0));
				}
				
				if(main.getStaminaOfPlayer(p) <= 10 && main.isKits(Kits.MANDO, p) && onGameListeners.Task != 0) {
					Bukkit.getScheduler().cancelTask(onGameListeners.Task);
					Main.useJetPack.replace(p, 0);
					p.sendMessage("JetPack désactivé");
					p.setAllowFlight(false);
				}
				
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999999, 250));
				if(main.getStaminaOfPlayer(p) < 100) main.setStamina(main.getStaminaOfPlayer(p) + 3, p);
				if(main.getStaminaOfPlayer(p) >= 100 && main.getStaminaOfPlayer(p) < 200 && main.isKits(Kits.DARTH_VADER, p)) main.setStamina(main.getStaminaOfPlayer(p) + 3, p);
				if(main.getStaminaOfPlayer(p) >= 100 && main.getStaminaOfPlayer(p) < 125 && main.isPState(Pstate.JEDI, p)) main.setStamina(main.getStaminaOfPlayer(p) + 3, p);
				
				if(main.getStaminaOfPlayer(p) > 125 && (main.isPState(Pstate.JEDI, p) || main.isKits(Kits.MANDO, p))) main.setStamina(125, p);
				if(main.getStaminaOfPlayer(p) > 200 && main.isKits(Kits.DARTH_VADER, p)) main.setStamina(200, p);
				if(main.getStaminaOfPlayer(p) > 100 && !main.isKits(Kits.DARTH_VADER, p) && main.isPState(Pstate.EMPIRE, p)) main.setStamina(100, p);
				
				if(main.getStaminaOfPlayer(p) <= 10 && game > 4) {
					p.removePotionEffect(PotionEffectType.SLOW);
					p.removePotionEffect(PotionEffectType.WEAKNESS);
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*6, 1));
					p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20*6, 1));
					p.sendMessage("Vous entrez dans un état de §4fatigue");
				}
			}
		}
		if (main.isState(Gstate.PLAYING)) game++;
		if (timer == -999999998) cancel();
		timer--;
	}

}
