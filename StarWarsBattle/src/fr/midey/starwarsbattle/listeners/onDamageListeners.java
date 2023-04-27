package fr.midey.starwarsbattle.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import fr.midey.starwarsbattle.Main;
import fr.midey.starwarsbattle.boardmanager.BoardManager;
import fr.midey.starwarsbattle.state.Gstate;
import fr.midey.starwarsbattle.state.Pstate;
import fr.midey.starwarsbattle.stuff.Stuff;
import fr.midey.starwarsbattle.timer.GbowDamage;

public class onDamageListeners implements Listener {

	private static Main main;
	
	public onDamageListeners(Main main) {
		onDamageListeners.main = main;
	}

	@EventHandler
	public void onBow(PlayerInteractEvent event) {
		
		main.saveDefaultConfig();
		
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack it = event.getItem();
		
		if (it == null) return;

		if(it.getType() == Material.BOW) {
			if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				if(!Main.cooldown.containsKey(player)) {
					int arrowSpeed = 3;
					player.spigot().setCollidesWithEntities(false);
					Arrow arrow = (Arrow) player.getWorld().spawn(player.getLocation().add(0,1.7,0), Arrow.class);
					arrow.spigot().setDamage(main.getConfig().getInt("damage.blaster"));
					arrow.setVelocity(player.getLocation().getDirection().multiply(arrowSpeed));
					player.playSound(player.getLocation(), Sound.SHOOT_ARROW, 1f, 1f);
					GbowDamage bw = new GbowDamage(player);
					bw.runTaskTimer(main, 0, 2);
					Main.cooldown.put(player, main.getConfig().getInt("cooldown.blaster"));
				}
			}
		}
	}
	
	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent event) {
		if(!main.isState(Gstate.PLAYING)) event.setCancelled(true);
		Entity damager = event.getDamager();
		Entity victim = event.getEntity();
		if (victim instanceof Player) {
			Player player = (Player) victim;
			if (damager instanceof Arrow) {
				if (main.isPState(Pstate.EMPIRE, player)) {
					event.setCancelled(true);;
					return;
				}
			}
			//if (damager instanceof Player) {
				//Player p = (Player)damager;
				//if(p.getItemInHand().getType() == Material.DIAMOND_SWORD || p.getItemInHand().getType() == Material.GOLD_SWORD || p.getItemInHand().getType() == Material.WOOD_SWORD || p.getItemInHand().getType() == Material.STONE_SWORD || p.getItemInHand().getType() == Material.IRON_SWORD) {
					//if(Main.cooldownSaber.containsKey(p)) event.setCancelled(true);
				//}
			//}
			if (damager instanceof Fireball) {
				event.setDamage(event.getDamage() * 4);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {	
		if(!main.isState(Gstate.PLAYING)) event.setCancelled(true);
		DamageCause fa = event.getCause();
		if(fa == DamageCause.FALL) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = event.getEntity();
			main.setVie(main.getVie(player) - 1, player);
			BoardManager.boardManager(player);
			player.setGameMode(GameMode.SURVIVAL);
			if (event.getEntity().getKiller() instanceof Player) {
			BoardManager.boardManager(event.getEntity().getKiller());
			main.addKillCounter(event.getEntity().getKiller());
			}
			
			if (main.isVie(0, player)) {
				player.sendMessage("Vous avez été éliminé");
				player.setGameMode(GameMode.SPECTATOR);
				main.getPlayers().remove(player);
				main.getKits().remove(player.getUniqueId());
				main.getPState().remove(player);
				if(main.nbTeam(Pstate.EMPIRE) == 0) {
					Bukkit.broadcastMessage(ChatColor.UNDERLINE + "§bVictoire des Jedi !");
					main.setState(Gstate.FINISH);
					reloadGame();
				}
				if(main.nbTeam(Pstate.JEDI)== 0) {
					Bukkit.broadcastMessage(ChatColor.UNDERLINE + "§4Victoire de l'Empire !");
					main.setState(Gstate.FINISH);
					reloadGame();
				}
			}
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		main.saveDefaultConfig();
		Player player = event.getPlayer();
		if(main.isState(Gstate.WAITING)) {
			Location loc = new Location(Bukkit.getWorld("world"), main.getConfig().getDouble("spawnlobby.x"), main.getConfig().getDouble("spawnlobby.y"), main.getConfig().getDouble("spawnlobby.z"));
			event.setRespawnLocation(loc);
		}
		if (main.isPState(Pstate.EMPIRE, player)) {
			Location loc = new Location(Bukkit.getWorld("world"), main.getConfig().getDouble("empirespawn.x"), main.getConfig().getDouble("empirespawn.y"), main.getConfig().getDouble("empirespawn.z"), (float )main.getConfig().getDouble("empirespawn.pe"), (float )main.getConfig().getDouble("empirespawn.ya"));
			event.setRespawnLocation(loc);
			main.setStamina(11, player);
		}
		else if (main.isPState(Pstate.JEDI, player)) {
			Location loc = new Location(Bukkit.getWorld("world"), main.getConfig().getDouble("jedispawn.x"), main.getConfig().getDouble("jedispawn.y"), main.getConfig().getDouble("jedispawn.z"), (float )main.getConfig().getDouble("jedispawn.pe"), (float )main.getConfig().getDouble("jedispawn.ya"));
			event.setRespawnLocation(loc);
			main.setStamina(11, player);
		}
	}
	
	public static void reloadGame() {
		main.saveDefaultConfig();
		for (Player p : Bukkit.getOnlinePlayers()) {
			Stuff.clearALL(p);
			Location loc = new Location(Bukkit.getWorld("world"), main.getConfig().getDouble("spawnlobby.x"), main.getConfig().getDouble("spawnlobby.y"), main.getConfig().getDouble("spawnlobby.z"));
			p.setGameMode(GameMode.SURVIVAL);
			p.teleport(loc);
			p.getInventory().setItem(4, Stuff.EnchantedItem(Material.COMPASS, "§4Menu principal"));
		}
		Bukkit.reload();
	}
	public static void reloadGameQuit() {
		main.saveDefaultConfig();
		for (Player p : Bukkit.getOnlinePlayers()) {
			Stuff.clearALL(p);
			Location loc = new Location(Bukkit.getWorld("world"), main.getConfig().getDouble("spawnlobby.x"), main.getConfig().getDouble("spawnlobby.y"), main.getConfig().getDouble("spawnlobby.z"));
			p.setGameMode(GameMode.SURVIVAL);
			p.teleport(loc);
			p.getInventory().setItem(4, Stuff.EnchantedItem(Material.COMPASS, "§4Menu principal"));
		}
		Bukkit.reload();
	}
}
