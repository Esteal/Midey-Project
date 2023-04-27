package fr.midey.skywars.listeners;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.midey.skywars.GchestMaker;
import fr.midey.skywars.Gmain;
import fr.midey.skywars.tasks.GAutoRefile;
import io.netty.util.internal.ThreadLocalRandom;

public class GSetChestListeners implements Listener {
	
	private final static Set<Location> chestOppened = new HashSet<Location>();
	private List<GchestMaker> lootItems = new ArrayList<>();
	
	private Gmain main;
	public GSetChestListeners(Gmain main) {
		this.main = main;
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		
		Block block = event.getBlock();
		if(block.getType() == Material.CHEST) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		if(block.getType() == Material.CHEST) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onChestOpen(PlayerInteractEvent event) {
		
		Action action = event.getAction();
		
		if(action == Action.LEFT_CLICK_AIR || action == Action.RIGHT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) return;
		
		if(event.getClickedBlock().getType() == Material.CHEST && event.getPlayer() instanceof Player) {
			Chest chest = (Chest) event.getClickedBlock().getState();
			Location locChest = chest.getBlock().getLocation();
			if( !chestOppened.contains(locChest)) {
				if(action == Action.RIGHT_CLICK_BLOCK){
					chestOppened.add(locChest);
					createCommonChest(locChest);
				}
			}
		}
		return;
	}
	
	public void createCommonChest(Location spawnChest) {
		main.saveDefaultConfig();
		Chest chest = (Chest) spawnChest.getBlock().getState();
		Inventory chestMenu = chest.getInventory();
		ConfigurationSection itemSection = main.getConfig().getConfigurationSection("LootItems");
		if (itemSection == null) Bukkit.broadcastMessage("Erreur dans la configuration. section : " + itemSection);
		for (String key : itemSection.getKeys(false)) {
			ConfigurationSection section = itemSection.getConfigurationSection(key);
			lootItems.add(new GchestMaker(section));
		}
		ThreadLocalRandom random = ThreadLocalRandom.current();
		Set<GchestMaker> used = new HashSet<>();
		
		for (int slotIndex = 0; slotIndex < chestMenu.getSize(); slotIndex++) {
			GchestMaker randomItem = lootItems.get(random.nextInt(lootItems.size()));
		
			if (used.contains(randomItem)) continue;
			used.add(randomItem);
			if(randomItem.shouldFill(random)) {
				ItemStack itemStack = randomItem.make(random);
				chestMenu.setItem(slotIndex, itemStack);
				
			}
		}
		/*
		Random r = new Random();
		if (Math.random() * 100 < 50) {
			chestMenu.setItem(r.nextInt(chestMenu.getSize()), prout);
		}
		else if (Math.random() * 100 < 40 ) {
			chestMenu.setItem(r.nextInt(chestMenu.getSize()), prout);
		}*/
	}
	
	public void clearChestOppened() {
		
		main.saveDefaultConfig();
		
		GAutoRefile.timer = main.getConfig().getInt("skywars.timer.refileChest");
		GAutoRefile timerChest = new GAutoRefile(main);
		timerChest.runTaskTimer(main, 0, 20);
		chestOppened.clear();
	}
}
