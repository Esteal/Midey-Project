package fr.midey.starcraft.Spell;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.midey.starcraft.Stats;
import fr.midey.starcraft.itemsPackage.ItemSpell;
import fr.midey.starcraft.itemsPackage.ItemWand;

public class SelectClickSpell implements Listener {

	private Stats main;
	public SelectClickSpell(Stats main) {
		this.main = main;
	}
	
	@EventHandler
	public void onLeftClickWand(PlayerInteractEvent e) {
		
		ItemStack it = e.getItem();
		if(it == null) return;
		if(it.getType() == Material.BARRIER) {
			Action a = e.getAction();
			Player p = e.getPlayer();
			ItemWand itW = new ItemWand(main); 
			itW.spellAvailabe(p);
			List<String> spellAvailable = main.getPlayerSpellAvailable().get(p);
			String itName = it.getItemMeta().getDisplayName();
			if(itName == null) return;
			if(main.getCooldownChangeSpell().containsKey(p)) return;
			main.getCooldownChangeSpell().put(p, 0.0f);
			if((a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)) {
				if(itName.equalsIgnoreCase("§lForce")) {
					ItemSpell itS = new ItemSpell(spellAvailable.get(0), it.getItemMeta().getLore());
					p.setItemInHand(itS.spell());
				}
				if (spellAvailable.contains(itName)) {
					int index = indexer(spellAvailable, itName);
					ItemSpell itS;
					if(index + 1 == spellAvailable.size()) {
						itS = new ItemSpell("§lForce", it.getItemMeta().getLore());
					}
					else {
						itS = new ItemSpell(spellAvailable.get(index + 1), it.getItemMeta().getLore());
					}
					p.setItemInHand(itS.spell());
					}
				}
			}
		}
	
	public int indexer(List<String> strings, String string) {
		
		for (int i = 0; i < strings.size(); i++) {
			if(strings.get(i).equalsIgnoreCase(string)) return i;
		}
		return 0;
		
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(e.getBlock().getType() == Material.BARRIER) {
			Player p = e.getPlayer();
			ItemWand itW = new ItemWand(main); 
			itW.spellAvailabe(p);
			List<String> spellAvailable = main.getPlayerSpellAvailable().get(p);
			String itName = e.getItemInHand().getItemMeta().getDisplayName();
			if(spellAvailable.contains(itName) || itName.equalsIgnoreCase("§lForce")) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDestroy(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(p.getItemInHand().getType() == Material.BARRIER) {
			ItemWand itW = new ItemWand(main); 
			itW.spellAvailabe(p);
			List<String> spellAvailable = main.getPlayerSpellAvailable().get(p);
			String itName = p.getItemInHand().getItemMeta().getDisplayName();
			if(spellAvailable.contains(itName) || itName.equalsIgnoreCase("§lForce")) {
				e.setCancelled(true);
			}
		}
	}
}
