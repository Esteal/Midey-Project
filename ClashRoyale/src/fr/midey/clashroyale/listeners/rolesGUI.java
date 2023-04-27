package fr.midey.clashroyale.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.midey.clashroyale.Main;
import fr.midey.clashroyale.roles.Bomber;
import fr.midey.clashroyale.roles.HogRider;
import fr.midey.clashroyale.roles.MiniPekka;
import fr.midey.clashroyale.roles.Valkyrie;
import fr.midey.clashroyale.state.Grole;
import fr.midey.clashroyale.state.Gteam;
import fr.midey.clashroyale.stuff.Armor;
import net.md_5.bungee.api.ChatColor;

public class rolesGUI implements Listener {

	private Main main;
	public rolesGUI(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onRolesclick(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if (it == null) return;
		Player p = e.getPlayer();
		Action a = e.getAction();
		if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
			if (it.getItemMeta().getDisplayName().equalsIgnoreCase("§f§l» §3§lSéléction de la carte §f§l«") && it.getType() == Material.CHEST) {
				e.setCancelled(true);
				Inventory inv = Bukkit.createInventory(null, 9,"Séléctionner une carte");
				inv.setItem(0, Armor.EnchantedItem(Material.IRON_AXE, ChatColor.BOLD + "Valkyrie"));
				inv.setItem(1, Armor.EnchantedItem(Material.DIAMOND_AXE, ChatColor.BOLD + "Hog Rider"));
				inv.setItem(2, Armor.EnchantedItem(Material.DIAMOND_SWORD, ChatColor.BOLD + "Mini Pekka"));
				inv.setItem(3, Armor.EnchantedItem(Material.TNT, ChatColor.BOLD + "Bomber"));
				inv.setItem(4, Armor.EnchantedItem(Material.BOW, ChatColor.BOLD + "Princesse"));
				inv.setItem(5, Armor.EnchantedItem(Material.BONE, ChatColor.BOLD + "Squelleton Army"));
				inv.setItem(6, Armor.EnchantedItem(Material.DIAMOND_CHESTPLATE, ChatColor.BOLD + "Giant"));
				inv.setItem(7, Armor.EnchantedItem(Material.BLAZE_POWDER, ChatColor.BOLD + "Sapeur"));
				inv.setItem(8, Armor.EnchantedItem(Material.POTION, ChatColor.BOLD + "Healeuse"));
				p.openInventory(inv);
			}
		}
	}
	
	@EventHandler
	public void onSelectRoles(InventoryClickEvent e) {
		if(!e.getInventory().getName().equalsIgnoreCase("Séléctionner une carte")) return;
		Player p = (Player) e.getWhoClicked();
		ItemStack it = e.getCurrentItem();
		if (it == null) return;
		Location loc;
		if(main.getPlayerTeam().get(p) == Gteam.Red) {
			loc = new Location(p.getWorld(), -28.357, 149, -6.67, -90, 0);
		}
		else {
			loc = new Location(p.getWorld(), 13.372, 149, -6.37, 90, 0);
		}
		e.setCancelled(true);
		switch(it.getType()) {
			case IRON_AXE:
				Valkyrie valkyrie = new Valkyrie(p);
				valkyrie.stuffed();
				main.getPlayerRole().put(p, Grole.Valkyrie);
				break;
			case DIAMOND_AXE:
				HogRider rider = new HogRider(p);
				rider.stuffed();
				main.getPlayerRole().put(p, Grole.HogRider);
				break;
			case DIAMOND_SWORD:
				MiniPekka minipekka = new MiniPekka(p);
				minipekka.stuffed();
				main.getPlayerRole().put(p, Grole.MiniPeka);
				break;
			case TNT:
				Bomber bomber = new Bomber(p);
				bomber.stuffed();
				main.getPlayerRole().put(p, Grole.Bomber);
				break;
			case BOW:
				main.getPlayerRole().put(p, Grole.princesse);
				break;
			case BONE:
				main.getPlayerRole().put(p, Grole.SquelletonArmy);
				break;
			case DIAMOND_CHESTPLATE:
				main.getPlayerRole().put(p, Grole.Giant);
				break;
			case BLAZE_POWDER:
				main.getPlayerRole().put(p, Grole.Sapeur);
				break;
			case POTION:
				main.getPlayerRole().put(p, Grole.healer);
				break;
			default:
				break;
		}
		p.teleport(loc);
		p.closeInventory();
	}

}
