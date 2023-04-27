package fr.midey.clashroyale.timer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.clashroyale.Main;
import fr.midey.clashroyale.listeners.menuGUI;
import fr.midey.clashroyale.state.Gstate;
import fr.midey.clashroyale.state.Gteam;
import fr.midey.clashroyale.stuff.Armor;

public class Start extends BukkitRunnable{

	private Main main;
	public Start (Main main) {
		this.main = main;
	}
	
	int timer = 10;
	@Override
	public void run() {
		
		main.saveDefaultConfig();
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.setLevel(timer);
			player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
			player.sendMessage("Lancement de la bataille dans " + ChatColor.GOLD + timer + ChatColor.YELLOW + "s");
		}
		
		if(timer <= 0) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1f, 1f);
				if(main.getPlayerTeam().containsKey(player)) {
					if(main.getPlayerTeam().get(player) == Gteam.Blue) {
						Location loc = new Location(player.getWorld(), main.getConfig().getDouble("bleuspawn.x"), main.getConfig().getDouble("bleuspawn.y"), main.getConfig().getDouble("bleuspawn.z"), (float) main.getConfig().getDouble("bleuspawn.ya"),(float) main.getConfig().getDouble("bleuspawn.pe"));
						player.teleport(loc);
						Armor.clearALL(player);
					}
					else if (main.getPlayerTeam().get(player) == Gteam.Red){
						Location loc = new Location(player.getWorld(), main.getConfig().getDouble("rougespawn.x"), main.getConfig().getDouble("rougespawn.y"), main.getConfig().getDouble("rougespawn.z"), (float) main.getConfig().getDouble("rougespawn.ya"),(float) main.getConfig().getDouble("rougespawn.pe"));
						player.teleport(loc);
						Armor.clearALL(player);
					}
				}
				else {
					player.sendMessage("Vous n'avez pas séléctionné de team");
					player.setGameMode(GameMode.SPECTATOR);
				}
				player.getInventory().setItem(4, Armor.EnchantedItem(Material.CHEST, "§f§l» §3§lSéléction de la carte §f§l«"));
				player.playSound(player.getLocation(), Sound.WITHER_SPAWN, 1f, 1f);
			}
			cancel();
			main.setState(Gstate.PLAYING);
		}
		
		if (menuGUI.getSTOP() == 0) {
			cancel();
		}
		timer--;
	}

}
