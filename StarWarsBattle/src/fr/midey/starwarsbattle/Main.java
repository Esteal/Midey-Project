package fr.midey.starwarsbattle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.midey.starwarsbattle.boardmanager.BoardManager;
import fr.midey.starwarsbattle.commands.Commands;
import fr.midey.starwarsbattle.listeners.onDamageListeners;
import fr.midey.starwarsbattle.listeners.onGameListeners;
import fr.midey.starwarsbattle.listeners.onInteractListeners;
import fr.midey.starwarsbattle.rolemanager.RoleManager;
import fr.midey.starwarsbattle.state.Gstate;
import fr.midey.starwarsbattle.state.Kits;
import fr.midey.starwarsbattle.state.OPState;
import fr.midey.starwarsbattle.state.Pstate;
import fr.midey.starwarsbattle.stuff.Stuff;
import fr.midey.starwarsbattle.timer.GCycleBoard;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{

	private Gstate state;
	private static List<Player> players = new ArrayList<>();
	private static HashMap<Player, Pstate> playersState = new HashMap<Player, Pstate>();
	private static HashMap<UUID, OPState> playersOPState = new HashMap<UUID, OPState>();
	private static HashMap<UUID, Kits> playersKits = new HashMap<UUID, Kits>();
	private static HashMap<UUID, Integer> vie = new HashMap<UUID, Integer>();
	private static HashMap<Player, Integer> killCounter = new HashMap<Player, Integer>();
	private static HashMap<Player, Integer> stamina = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> cooldown = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> cooldownJedi = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> cooldownHealth = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> cooldownFire = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> cooldownBomb = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> cooldownBump = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> useJetPack = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> cooldownAttract = new HashMap<Player, Integer>();

	public int Vader = 0;
	public int Mando = 0;
	
	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		setState(Gstate.WAITING);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new onGameListeners(this), this);
		pm.registerEvents(new onInteractListeners(this), this);
		pm.registerEvents(new onDamageListeners(this), this);
		getCommand("opp").setExecutor(new Commands(this));
		new BoardManager(this);
		new Stuff(this);
		new RoleManager(this);
		
		
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				
				for (Player p : Bukkit.getOnlinePlayers()) {
					if(!cooldown.containsKey(p)) continue;
					cooldown.replace(p, cooldown.get(p) - 1);
					if(cooldown.get(p) <= 0) {
						Main.cooldown.remove(p);
					}
				}
			}
		}, 0L, 20L);
		
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				
				for (Player p : Bukkit.getOnlinePlayers()) {
					if(!cooldownJedi.containsKey(p)) continue;
					cooldownJedi.replace(p, cooldownJedi.get(p) - 1);
					p.setLevel(cooldownJedi.get(p));
					if(Main.cooldownJedi.get(p) == 0) {
						p.setAllowFlight(true);
						Main.cooldownJedi.remove(p);
					}
				}
				for (Player p : Bukkit.getOnlinePlayers()) {
					if(!Main.cooldownHealth.containsKey(p)) continue;
					Main.cooldownHealth.replace(p, Main.cooldownHealth.get(p) - 1);
					for(int i = 0; i < p.getInventory().getSize() - 1; i++) {
						ItemStack it = p.getInventory().getItem(i);
						if (it == null) continue;
						if(it.getType() == Material.MILK_BUCKET) {
							it.setAmount(Integer.valueOf(Main.cooldownHealth.get(p) + 1));
							break;
						}
					}
					if(Main.cooldownHealth.get(p) <= 0) {
						Main.cooldownHealth.remove(p);
					}
				}
				for (Player p : Bukkit.getOnlinePlayers()) {
					if(!Main.cooldownFire.containsKey(p)) continue;
					Main.cooldownFire.replace(p, Main.cooldownFire.get(p) - 1);
					for(int i = 0; i < p.getInventory().getSize() - 1; i++) {
						ItemStack it = p.getInventory().getItem(i);
						if (it == null) continue;
						if(it.getType() == Material.FLINT_AND_STEEL) {
							it.setAmount(Integer.valueOf(Main.cooldownFire.get(p) + 1));
							break;
						}
					}
					if(Main.cooldownFire.get(p) <= 0) {
						Main.cooldownFire.remove(p);
					}
				}
				for (Player p : Bukkit.getOnlinePlayers()) {
					if(!Main.cooldownBomb.containsKey(p)) continue;
					Main.cooldownBomb.replace(p, Main.cooldownBomb.get(p) - 1);
					for(int i = 0; i < p.getInventory().getSize() - 1; i++) {
						ItemStack it = p.getInventory().getItem(i);
						if (it == null) continue;
						if(it.getType() == Material.FIREBALL) {
							it.setAmount(Integer.valueOf(Main.cooldownBomb.get(p) + 1));
						}
					}
					if(Main.cooldownBomb.get(p) <= 0) {
						Main.cooldownBomb.remove(p);
					}
				}
				for (Player p : Bukkit.getOnlinePlayers()) {
					if(!Main.cooldownBump.containsKey(p)) continue;
					Main.cooldownBump.replace(p, Main.cooldownBump.get(p) - 1);
					for(int i = 0; i < p.getInventory().getSize(); i++) {
						ItemStack it = p.getInventory().getItem(i);
						if (it == null) continue;
						if(it.getType() == Material.STICK && it.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY + "POUSSÉ DE FORCE " + ChatColor.DARK_GRAY + "(" +  getConfig().getInt("cost.pousse") + ")")) {
							it.setAmount(Main.cooldownBump.get(p) + 1);
						}
					}
					if(Main.cooldownBump.get(p) <= 0) {
						Main.cooldownBump.remove(p);
					}
				}
				for (Player p : Bukkit.getOnlinePlayers()) {
					if(!Main.cooldownAttract.containsKey(p)) continue;
					Main.cooldownAttract.replace(p, Main.cooldownAttract.get(p) - 1);
					for(int i = 0; i < p.getInventory().getSize(); i++) {
						ItemStack it = p.getInventory().getItem(i);
						if (it == null) continue;
						if(it.getType() == Material.STICK && it.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY + "ATTRACTION DE FORCE " + ChatColor.DARK_GRAY + "(" +  getConfig().getInt("cost.attraction") + ")")) {
							it.setAmount(Main.cooldownAttract.get(p) + 1);
						}
					}
					if(Main.cooldownAttract.get(p) <= 0) {
						Main.cooldownAttract.remove(p);
					}
				}
			}
		}, 0L, 20L);
		
		GCycleBoard cycleBoard = new GCycleBoard(this);
		cycleBoard.runTaskTimer(this, 0, 20);
	}
	
	@Override
	public void onDisable() {
		onDamageListeners.reloadGameQuit();
	}
	
	/*Game State GETER & SETER*/
	public void setState(Gstate state) {
		this.state = state;
	}
	
	public boolean isState(Gstate state) {
		return this.state == state;
	}
	/*Game State GETER & SETER*/
	
	/*Player State GETER & SETER*/
	public void setPState(Pstate state, Player player) {
		playersState.put(player, state);
	}
	
	public HashMap<Player, Pstate> getPState(){
		return playersState;
	}
	

	
	public boolean isPState(Pstate state, Player player) {
		
		for (Entry<Player, Pstate> entry : Main.playersState.entrySet()) {
			if (entry.getKey() == player && entry.getValue() == state) {
				return true;
			}
		}
		return false;
	}
	/*Player State GETER & SETER*/
	
	/*OP GETER & SETER*/
	public void setOPState(OPState state, Player player) {
		playersOPState.put(player.getUniqueId(), state);
	}
	
	public boolean isOPstate(OPState state, Player player) {
		
		for (Entry<UUID, OPState> entry : Main.playersOPState.entrySet()) {
			if (entry.getKey() == player.getUniqueId() && entry.getValue() == state) {
				return true;
			}
		}
		return false;
	}
	
	public HashMap<UUID, OPState> getOPState(){
		return playersOPState;
	}
	
	/*OP GETER & SETER*/
	
	/*KITS GETER & SETER*/
	
	public void setKits(Kits kit, Player player) {
		playersKits.put(player.getUniqueId(), kit);
	}
	
	public boolean isKits(Kits kit, Player player) {
		
		for (Entry<UUID, Kits> entry : Main.playersKits.entrySet()) {
			if (entry.getKey() == player.getUniqueId() && entry.getValue() == kit) {
				return true;
			}
		}
		return false;
	}
	
	public HashMap<UUID, Kits> getKits(){
		return playersKits;
	}
	/*KITS GETER & SETER*/
	
	/*VIE GETER & SETER*/
	
	public void setVie(Integer vies, Player player) {
		vie.put(player.getUniqueId(), vies);
	}
	
	public boolean isVie(Integer vies, Player player) {
		
		for (Entry<UUID, Integer> entry : Main.vie.entrySet()) {
			if (entry.getKey() == player.getUniqueId() && entry.getValue() == vies) {
				return true;
			}
		}
		return false;
	}
	
	public HashMap<UUID, Integer> getVies(){
		return vie;
	}
	
	public int getVie(Player player) {
		return vie.get(player.getUniqueId());
	}
	
	/*VIE GETER & SETER*/
	
	/*PLAYER GETER*/
	public List<Player> getPlayers() {
		return players;
	}
	/*PLAYER GETER*/
	
	/*NOMBRE de TEAM GETER*/
	public int nbTeam(Pstate state) {
		int nb = 0;
		for(Entry<Player, Pstate> entry : Main.playersState.entrySet()) {
			if(isPState(state, entry.getKey())) nb++;
		}
		return nb;
	}
	/*NOMBRE de TEAM GETER*/
	
	/*Kill GETER & SETER*/
	public HashMap<Player, Integer> getKillCounter() {
		return killCounter;
	}
	
	public Integer getSpecifiKill(Player p) {
		if (!killCounter.containsKey(p)) {
			killCounter.put(p, 0);
		}
		return killCounter.get(p);
	}
	
	public void addKillCounter(Player p) {
		if(killCounter.containsKey(p)) {
			killCounter.replace(p, killCounter.get(p) + 1);
		}
		else {
			killCounter.put(p, 1);
		}
	}
	/*Kill GETER & SETER*/
	
	/*Stamina GETER & SETER*/
	
	public void setStamina(Integer valueStamina, Player player) {
		if(stamina.containsKey(player)) stamina.replace(player, valueStamina);
		else stamina.put(player, valueStamina);
	}
	
	public boolean isStamina(Integer valueStamina, Player player) {
		
		for (Entry<Player, Integer> entry : Main.stamina.entrySet()) {
			if (entry.getKey() == player && entry.getValue() == valueStamina) {
				return true;
			}
		}
		return false;
	}
	
	public Integer getStaminaOfPlayer(Player player){	
		return stamina.get(player);
	}
	
	public HashMap<Player, Integer> getStamina(){
		return stamina;
	}
	/*Stamina GETER & SETER*/
}