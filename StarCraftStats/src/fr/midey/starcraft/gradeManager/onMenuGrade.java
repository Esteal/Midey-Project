package fr.midey.starcraft.gradeManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import fr.midey.starcraft.Stats;

public class onMenuGrade implements Listener {

	private Stats main;
	
	public onMenuGrade(Stats main) {
		this.main = main;
	}
	
	@EventHandler
	public void clickMenuGrade(InventoryClickEvent e) {
		if(e.getInventory().getName().equalsIgnoreCase("Force")) {
			ItemStack it = e.getCurrentItem();
			if(it.getType() == Material.AIR) return;
			Player p = (Player) e.getWhoClicked();
			String n = it.getItemMeta().getDisplayName();
			e.setCancelled(true);
			switch(n) {
				case "§4Côté obscure":
					main.getPlayerGrade().replace(p.getUniqueId(), "Apprenti sith");
					GradeLoader.updateGrade(p, "Apprenti sith");
					p.closeInventory();
					Bukkit.broadcastMessage("Le joueur §e" + p.getDisplayName() + "§r vient de passer §4Apprenti sith");
					break;
				case "§bCôté lumineux":
					main.getPlayerGrade().replace(p.getUniqueId(), "Apprenti jedi");
					GradeLoader.updateGrade(p, "Apprenti jedi");
					p.closeInventory();
					Bukkit.broadcastMessage("Le joueur §e" + p.getDisplayName() + "§r vient de passer §bApprenti jedi");
					break;
				default: break;
			}
		}
		if(e.getInventory().getName().equalsIgnoreCase("Stats de " + e.getWhoClicked().getName())) {
			e.setCancelled(true);
		}
	}
}
