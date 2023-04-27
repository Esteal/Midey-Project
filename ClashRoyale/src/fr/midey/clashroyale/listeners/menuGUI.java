package fr.midey.clashroyale.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.midey.clashroyale.Main;
import fr.midey.clashroyale.board.ScoreBoard;
import fr.midey.clashroyale.state.Gteam;
import fr.midey.clashroyale.stuff.Armor;
import fr.midey.clashroyale.timer.Start;

public class menuGUI implements Listener {
	
	private static int STOP = 0;
	
	public static int getSTOP() {
		return STOP;
	}
	
	private Main main;
	public menuGUI(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onGUI(PlayerInteractEvent e) {
		
		ItemStack it = e.getItem();
		
		if (it == null) return;
		
		if(it.getType() == Material.COMPASS && it.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.BLUE + "MENU")) {
			Player p = e.getPlayer();
			Inventory GUI = Bukkit.createInventory(null, 45, "Menu principal");
			GUI.setItem(21, Armor.Banner(new ItemStack(Material.BANNER, 1, (short) 1), "§4Rouge"));
			GUI.setItem(22, Armor.Banner(new ItemStack(Material.BANNER, 1, (short) 12), "§bBleu"));
			
			switch(STOP) {
				case 0:
					GUI.setItem(23, Armor.Banner(new ItemStack(Material.WOOL, 1, (short) 5), ChatColor.GREEN + "Start"));
					break;
				case 1:
					GUI.setItem(23, Armor.Banner(new ItemStack(Material.WOOL, 1, (short) 14), ChatColor.DARK_RED + "Stop"));
					break;
				default: p.sendMessage("§4ERROR");
			}
			
			p.openInventory(GUI);
		}
	}
	
	@EventHandler
	public void onClickGUI(InventoryClickEvent e) {
		
		main.saveDefaultConfig();
		
		Inventory inv = e.getInventory();
		ItemStack it = e.getCurrentItem();
		Player player = (Player) e.getWhoClicked();
	
		if(it == null) return;
		else if (inv.getName().equalsIgnoreCase("Menu principal")) {
			e.setCancelled(true);
			switch(it.getType()) {
				
				case BANNER:
					if (it.getItemMeta().getDisplayName().equalsIgnoreCase("§4Rouge")) {
						main.getPlayerTeam().put(player, Gteam.Red);
						ScoreBoard.createScoreBoard(player);
						ScoreBoard.updateScoreboard();
					}
					else if (it.getItemMeta().getDisplayName().equalsIgnoreCase("§bBleu")) {
						main.getPlayerTeam().put(player, Gteam.Blue);
						ScoreBoard.createScoreBoard(player);
						ScoreBoard.updateScoreboard();
					}
				case WOOL:
							if (it.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Start") && STOP == 0) {
								Start start = new Start(main);
								start.runTaskTimer(main, 0, 20);
								inv.setItem(23, Armor.Banner(new ItemStack(Material.WOOL, 1, (short) 14), ChatColor.DARK_RED + "Stop"));
								STOP = 1;
							}
							if(it.getItemMeta().getDisplayName().equalsIgnoreCase( ChatColor.DARK_RED + "Stop")) {
								STOP = 0;
								inv.setItem(23, Armor.Banner(new ItemStack(Material.WOOL, 1, (short) 5), ChatColor.GREEN + "Start"));
							}
							break;
				default:
					break;
			}
		}
	}
}
