package fr.midey.buildrush.WAITING;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import fr.midey.buildrush.BuildRush;
import fr.midey.buildrush.GameCycle;

public class PlayerDoNotAction implements Listener {

	private BuildRush main;
	private List<GameCycle> gc = Arrays.asList(GameCycle.LAUNCHING, GameCycle.WAITING, GameCycle.ENDING);
	public PlayerDoNotAction(BuildRush main) {
		this.main = main;
	}
	//Evite que le jouer prenne/mette des damages
	@EventHandler
	public void dontAttack(EntityDamageEvent event) {
		if(gc.contains(main.getGameCycle())) {
			//event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if(gc.contains(main.getGameCycle()) && event.getBlock().getType() != Material.AIR) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if(gc.contains(main.getGameCycle()) && event.getBlock().getType() != Material.AIR) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlew(BlockExplodeEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		
		if (event.getItemDrop() != null) {
			event.setCancelled(true);
			event.getPlayer().updateInventory();
		}
	}
}
