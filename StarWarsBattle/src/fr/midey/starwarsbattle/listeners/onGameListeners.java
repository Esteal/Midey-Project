package fr.midey.starwarsbattle.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import fr.midey.starwarsbattle.Main;
import fr.midey.starwarsbattle.boardmanager.BoardManager;
import fr.midey.starwarsbattle.rolemanager.RoleManager;
import fr.midey.starwarsbattle.state.Gstate;
import fr.midey.starwarsbattle.state.Kits;
import fr.midey.starwarsbattle.state.Pstate;
import fr.midey.starwarsbattle.stuff.Stuff;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class onGameListeners implements Listener{

	private Main main;
	public static int Task = 0;
	
	public onGameListeners(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		main.saveDefaultConfig();
		Player p = e.getPlayer();
		Stuff.clearALL(p);
		p.setGameMode(GameMode.SURVIVAL);
		Location loc = new Location(Bukkit.getWorld("world"), main.getConfig().getDouble("spawnlobby.x"), main.getConfig().getDouble("spawnlobby.y"), main.getConfig().getDouble("spawnlobby.z"));
		if(main.isState(Gstate.PLAYING) || main.isState(Gstate.FINISH)) {
			e.setJoinMessage(null);
			RoleManager.addPlayer(p);
			p.setGameMode(GameMode.SPECTATOR);
			p.sendMessage("La partie a déjà commencé");
			main.setVie(0, p);
			BoardManager.boardManager(p);
		}
		if(main.isState(Gstate.WAITING) || main.isState(Gstate.STARTING)) {
			RoleManager.addPlayer(p);
			p.teleport(loc);
			p.getInventory().setItem(4, Stuff.EnchantedItem(Material.COMPASS, "§4Menu principal"));
			e.setJoinMessage("§4[§eStarWarsBattle§4] §rle joueur §4" + p.getName() + " §r a rejoint la partie ! §a(" + main.getPlayers().size() + "/" + Bukkit.getMaxPlayers() + ")");
			BoardManager.boardManager(p);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		if(main.isKits(Kits.DARTH_VADER, p)) main.Vader = 0;
		if(main.isKits(Kits.MANDO, p)) main.Mando = 0;
		RoleManager.removePlayer(p);
		e.setQuitMessage("§4[§eStarWarsBattle§4] §rle joueur §4" + p.getName() + " §r a quitté la partie ! §a(" + main.getPlayers().size() + "/" + Bukkit.getMaxPlayers() + ")");
		Bukkit.broadcastMessage("" + main.Vader + " " + main.Mando);
	}

	@EventHandler
	public void onPlayerDoubleJump(PlayerToggleFlightEvent e){
		main.saveDefaultConfig();
		Player p = e.getPlayer();
		if((main.isPState(Pstate.JEDI, p) || main.isKits(Kits.DARTH_VADER, p)) && !Main.cooldownJedi.containsKey(p)) {
			e.setCancelled(true);
			int cost = main.getConfig().getInt("cost.jump");
			if(p.getGameMode() != GameMode.CREATIVE && main.getStaminaOfPlayer(p) - cost >= 0){
					Vector v = p.getLocation().getDirection().multiply(1).setY(1);
					p.setVelocity(v);
					p.setAllowFlight(false);
					Main.cooldownJedi.put(p, main.getConfig().getInt("cooldown.jump"));
					main.setStamina(main.getStaminaOfPlayer(p) - cost, p);
			}
		}
	}
	
	@EventHandler
	public void onForce(PlayerInteractEvent event) {
		main.saveDefaultConfig();
		Player p = event.getPlayer();
		Action action = event.getAction();
		ItemStack it = p.getItemInHand();
		if (it.getType() == Material.AIR) return;
		Location loc = p.getLocation();
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		if(it.getType() == Material.STICK && it.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY + "POUSSÉ DE FORCE " + ChatColor.DARK_GRAY + "(" +  main.getConfig().getInt("cost.pousse") + ")")) {
			Integer cost = main.getConfig().getInt("cost.pousse");
			int cours = 0;
			if (!Main.cooldownBump.containsKey(p) && main.getStaminaOfPlayer(p) - cost >= 0) {
				if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
					for (Player players : Bukkit.getOnlinePlayers()) {
						if(players.getName() == p.getName()) continue;
						if((main.isPState(Pstate.EMPIRE, players) && main.isPState(Pstate.EMPIRE, p)) || (main.isPState(Pstate.JEDI, players) && main.isPState(Pstate.JEDI, p))) continue;
						double xP = players.getLocation().getX();
						double yP = players.getLocation().getY();
						double zP = players.getLocation().getZ();
						if ((xP <= x + 5 && yP <= y + 5 && zP <= z + 5) && (xP >= x - 5 && yP >= y - 5 && zP >= z - 5) && (xP >= x - 5 && yP <= y + 5 && zP <= z + 5) && (xP <= x + 5 && yP >= y - 5 && zP >= z - 5)) {
							Vector v = players.getLocation().getDirection().multiply(-3).setY(1);
							players.setVelocity(v);
							players.sendMessage("Vous avez été propulsé par la force de " + ChatColor.RED + p.getName());
							Main.cooldownBump.put(p, main.getConfig().getInt("cooldown.pousse"));
							cours = 1;							
						}
					}
					if (cours > 0) main.setStamina(main.getStaminaOfPlayer(p) - cost, p);
				}
			}
		}
		if(it.getType() == Material.FLINT_AND_STEEL) {
			Integer cost = main.getConfig().getInt("cost.eclairs");
			int course = 0;
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				event.setCancelled(true);
				if (!Main.cooldownFire.containsKey(p) && main.getStaminaOfPlayer(p) - cost >= 0) {
					for (Player players : Bukkit.getOnlinePlayers()) {
						if(players.getName() == p.getName()) continue;
						if((main.isPState(Pstate.EMPIRE, players) && main.isPState(Pstate.EMPIRE, p)) || (main.isPState(Pstate.JEDI, players) && main.isPState(Pstate.JEDI, p))) continue;
						double xP = players.getLocation().getX();
						double yP = players.getLocation().getY();
						double zP = players.getLocation().getZ();
						if ((xP <= x + 5 && yP <= y + 5 && zP <= z + 5) && (xP >= x - 5 && yP >= y - 5 && zP >= z - 5) && (xP >= x - 5 && yP <= y + 5 && zP <= z + 5) && (xP <= x + 5 && yP >= y - 5 && zP >= z - 5)) {
							players.setFireTicks(20*10);
							players.sendMessage("Vous avez été éléctrocuté par les éclairs de " + ChatColor.RED + p.getName());
							Main.cooldownFire.put(p, main.getConfig().getInt("cooldown.eclairs"));
							course = 1;
						}
					}
					if (course > 0) main.setStamina(main.getStaminaOfPlayer(p) - cost, p);
				}
			}
		}
		if(it.getType() == Material.FIREBALL) {
			Integer cost = main.getConfig().getInt("cost.bombe");
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				event.setCancelled(true);
				if (!Main.cooldownBomb.containsKey(p) && main.getStaminaOfPlayer(p) - cost >= 0){
					int fireBallSpeed = 3;
					Fireball fireball =  p.getWorld().spawn(p.getLocation().add(0, 2.5, 0), Fireball.class);
					fireball.setVelocity(p.getLocation().getDirection().multiply(fireBallSpeed));
					Main.cooldownBomb.put(p, main.getConfig().getInt("cooldown.bombe"));
					main.setStamina(main.getStaminaOfPlayer(p) - cost, p);
				}
			}
		}
		if(it.getType() == Material.GLASS) {
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				Integer cost = main.getConfig().getInt("cost.jetpack");
				event.setCancelled(true);
				if(Main.useJetPack.containsKey(p) && Main.useJetPack.get(p) == 1) {
					Main.useJetPack.replace(p, 0);
					p.setAllowFlight(false);
					Bukkit.getScheduler().cancelTask(Task);
					p.sendMessage("JetPack désactivé");
				}
				else {
					p.sendMessage("JetPack activé");
					p.setAllowFlight(true);
					Main.useJetPack.put(p, 1);
					Task = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {

						@Override
						public void run() {
							if (main.isKits(Kits.MANDO, p)) {
								main.setStamina(main.getStaminaOfPlayer(p) - cost, p);
							}
						}
						
					}, 0L, 20L);
				}
			}
		}
		if(it.getType() == Material.STICK && it.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY + "ATTRACTION DE FORCE " + ChatColor.DARK_GRAY + "(" +  main.getConfig().getInt("cost.attraction") + ")")) {
				int cost = main.getConfig().getInt("cost.attraction");
				if((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && (main.getStaminaOfPlayer(p) - cost >= 0 && !Main.cooldownAttract.containsKey(p))) {
					Vector v = p.getLocation().getDirection();
					int target = 0;
					for (int t = 0; t < 100; t++) {
						double xV = v.getX() + x;
						double yV = v.getY() + y;
						double zV = v.getZ() + z;
						for(Player player :Bukkit.getOnlinePlayers()) {
							PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.CRIT, true, (float) xV, (float) yV + 1.5f, (float) zV, 0, 0, 0, 0, 1);
							((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
							if (player == p) continue;
							Location locat = new Location(Bukkit.getWorld("world"), xV, yV, zV);
							if(player.getLocation().getBlockZ() == locat.getBlockZ() && player.getLocation().getBlockX() == locat.getBlockX() && (player.getLocation().getBlockY() >= locat.getBlockY() && player.getLocation().getBlockY() - 2 <= locat.getBlockY())) {
								target = 1;
								Vector vec = p.getLocation().getDirection().multiply(-2).setY(1);
								player.setVelocity(vec);
								player.sendMessage("Vous avez été attiré par la force de " + ChatColor.RED + p.getName());
								Main.cooldownAttract.put(p, main.getConfig().getInt("cooldown.attraction"));
								main.setStamina(main.getStaminaOfPlayer(p) - cost, p);
							}
						}
						if (target == 1) break; 
						v.multiply(1.1);
						
				}
			}
		}
	}
}
