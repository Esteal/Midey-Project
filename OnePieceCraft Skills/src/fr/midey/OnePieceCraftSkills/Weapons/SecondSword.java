package fr.midey.OnePieceCraftSkills.Weapons;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;

public class SecondSword implements Listener{

	private OnePieceCraftSkills plugin;

	public SecondSword(OnePieceCraftSkills plugin) {
		this.plugin = plugin;
	}
	
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCursor();
        
        if (event.getSlot() == 40) {
        	if (plugin.getPlayerData(player).getWeaponPoints() <= 1) {
        		event.setCancelled(true);
        	}  else if (clickedItem.getType() == Material.AIR)
    			event.setCancelled(false);
        	else if (!(plugin.getTools().contains(clickedItem.getType()))) {
    			event.setCancelled(true);
    		}
        }
    }
}
