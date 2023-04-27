package fr.midey.starwarsbattle.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.midey.starwarsbattle.Main;
import fr.midey.starwarsbattle.boardmanager.BoardManager;
import fr.midey.starwarsbattle.state.Kits;
import fr.midey.starwarsbattle.state.OPState;
import fr.midey.starwarsbattle.state.Pstate;
import fr.midey.starwarsbattle.stuff.Stuff;
import fr.midey.starwarsbattle.timer.Gstart;

public class onInteractListeners implements Listener {

	private Main main;
	public onInteractListeners(Main main) {
		this.main = main;
	}
	
	public static int stop = 0;
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		main.saveDefaultConfig();
		
		Player p = e.getPlayer();
		Action action = e.getAction();
		
		if(p.getItemInHand().getType() == Material.COMPASS && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§4Menu principal")) {
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				Inventory selec = Bukkit.createInventory(null, 45, "§4Menu principal");
				selec.setItem(21, Stuff.Banner(new ItemStack(Material.BANNER, 1, (short) 1), "§4Empire"));
				selec.setItem(22, Stuff.Banner(new ItemStack(Material.BANNER, 1, (short) 12), "§bJedi"));
				if (stop == 0) {
					selec.setItem(23, Stuff.Banner(new ItemStack(Material.WOOL, 1, (short) 5), ChatColor.GREEN + "Start"));
				}
				else {
					selec.setItem(23, Stuff.Banner(new ItemStack(Material.WOOL, 1, (short) 14), ChatColor.DARK_RED + "Stop"));
				}
				p.openInventory(selec);
			}
		}
		if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			if(p.getItemInHand().getType() == Material.MILK_BUCKET) {
				e.setCancelled(true);
				Integer cost = main.getConfig().getInt("cost.brevage");
				if(!Main.cooldownHealth.containsKey(p) && main.getStaminaOfPlayer(p) - cost >= 0) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, 1));
					main.setStamina(main.getStaminaOfPlayer(p) - cost, p);
					Main.cooldownHealth.put(p, main.getConfig().getInt("cooldown.brevage"));
				}
			}
		}
	}
	
	
	@EventHandler
	public void onMenuInventory(InventoryClickEvent e) {
		Inventory inv = e.getInventory();
		ItemStack it = e.getCurrentItem();
		Player player = (Player) e.getWhoClicked();
		
		if (it == null)	return;
		
		if (inv.getName().equalsIgnoreCase("§4Menu principal")) {
			e.setCancelled(true);

			switch(it.getType()) {
				
				case BANNER:
					if (it.getItemMeta().getDisplayName().equalsIgnoreCase("§4Empire")) {
						//if (BoardManager.jedi.hasPlayer(player)) BoardManager.jedi.removePlayer(player);
						//TeamManager.EMPIRE.addPlayer(player);
						//TeamManager.teamManager(player);
						Inventory kitSelection = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Séléction du kit (EMPIRE)");
						kitSelection.setItem(12, Stuff.EnchantedItem(Material.IRON_HELMET, ChatColor.DARK_RED + "StormTrooper"));
						kitSelection.setItem(13, Stuff.EnchantedItem(Material.DIAMOND_HELMET, ChatColor.DARK_RED + "Darth Vader " + ChatColor.DARK_GRAY + "(" + main.Vader + "/1)"));
						kitSelection.setItem(14, Stuff.EnchantedItem(Material.PUMPKIN, ChatColor.DARK_RED + "Boba Fett " + ChatColor.DARK_GRAY + "(" + main.Mando+ "/1)"));
						player.openInventory(kitSelection);
					}
					else {
						//if (BoardManager.EMPIRE.hasPlayer(player)) BoardManager.EMPIRE.removePlayer(player);
						//TeamManager.jedi.addPlayer(player);
						//TeamManager.teamManager(player);
						Inventory kitSelection = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Séléction du kit (JEDI)");
						kitSelection.setItem(12, Stuff.EnchantedItem(Material.WOOD_SWORD, ChatColor.DARK_GREEN + "Jedi (Bonus de rapidité)"));
						kitSelection.setItem(13, Stuff.EnchantedItem(Material.STONE_SWORD, ChatColor.DARK_PURPLE + "Jedi (Bonus de force)"));
						kitSelection.setItem(14, Stuff.EnchantedItem(Material.IRON_SWORD, ChatColor.DARK_AQUA + "Jedi (Bonus de résistance)"));
						player.openInventory(kitSelection);
					}
					break;
				case WOOL:
					if (main.isOPstate(OPState.OP, player)) {
						if(it.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Start") && stop == 0) {
							
							for(Player players : Bukkit.getOnlinePlayers()) {
								if(main.isPState(Pstate.NOTHING, players)) players.sendMessage("§4AUCUNE ÉQUIPE SÉLÉCTIONNÉE !");
							}
							Gstart start = new Gstart(main);
							start.runTaskTimer(main, 0, 20);
							inv.setItem(23, Stuff.Banner(new ItemStack(Material.WOOL, 1, (short) 14), ChatColor.DARK_RED + "Stop"));
							stop++;
						}
						else if(it.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_RED + "Stop")) {
							stop--;
							inv.setItem(23, Stuff.Banner(new ItemStack(Material.WOOL, 1, (short) 5), ChatColor.GREEN + "Start"));
						}
						break;
					}
					default:
						break;
			}
		}
	}
	
	@EventHandler
	public void onKitInventory(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
		ItemStack it = event.getCurrentItem();
		Player player = (Player) event.getWhoClicked();
		
		if (it == null) return;
		
		if (inv.getName().equalsIgnoreCase(ChatColor.GOLD + "Séléction du kit (EMPIRE)")) {
			
			event.setCancelled(true);
			player.closeInventory();
			
			switch(it.getType()) {
				
				case DIAMOND_HELMET:
					if(main.isKits(Kits.DARTH_VADER, player)) main.Vader = 0;
					if(main.isKits(Kits.MANDO, player)) main.Mando = 0;
					if (main.Vader == 0) {
						if(main.isKits(Kits.MANDO, player)) main.Mando = 0;
						main.setPState(Pstate.EMPIRE, player);
						main.setKits(Kits.DARTH_VADER, player);
						player.sendMessage("Vous avez rejoint l'§4Empire§r  en tant que §4Darth Vader");
						main.Vader = 1;
						main.setVie(3, player);
						BoardManager.boardManager(player);
					}
					else {
						if (main.isKits(Kits.DARTH_VADER, player)) break;
						player.sendMessage("Un autre joueur a déjà séléctionné ce kit, veuillez en choisir un autre.");
					}
					break;
				case PUMPKIN:
					if(main.isKits(Kits.DARTH_VADER, player)) main.Vader = 0;
					if(main.isKits(Kits.MANDO, player)) main.Mando = 0;
					if(main.Mando == 0) {
						onEmpire(player);
						main.Mando = 1;
						main.setKits(Kits.MANDO, player);
						main.setVie(3, player);
						BoardManager.boardManager(player);
					}
					else {
						if (main.isKits(Kits.MANDO, player)) break;
						player.sendMessage("Un autre joueur a déjà séléctionné ce kit, veuillez en choisir un autre.");
					}
					break;
				case IRON_HELMET:
					if(main.isKits(Kits.DARTH_VADER, player)) main.Vader = 0;
					if(main.isKits(Kits.MANDO, player)) main.Mando = 0;
					onEmpire(player);
					main.setKits(Kits.TROOPER, player);
					main.setVie(3, player);
					BoardManager.boardManager(player);
					break;
			default: break;
			}
		}
		else if (inv.getName().equalsIgnoreCase(ChatColor.GOLD + "Séléction du kit (JEDI)")) {
		
			event.setCancelled(true);
			player.closeInventory();

			switch(it.getType()) {
				
				case WOOD_SWORD:
					if(main.isKits(Kits.DARTH_VADER, player)) main.Vader = 0;
					if(main.isKits(Kits.MANDO, player)) main.Mando = 0;
					main.setPState(Pstate.JEDI, player);
					main.setKits(Kits.SPEED, player);
					player.sendMessage("Vous avez rejoint les §bjedi");
					main.setVie(3, player);
					BoardManager.boardManager(player);
					break;
				case STONE_SWORD:
					if(main.isKits(Kits.DARTH_VADER, player)) main.Vader = 0;
					if(main.isKits(Kits.MANDO, player)) main.Mando = 0;
					main.setPState(Pstate.JEDI, player);
					
					main.setKits(Kits.FORCE, player);
					main.setVie(3, player);
					player.sendMessage("Vous avez rejoint les §bjedi");
					BoardManager.boardManager(player);
					break;
				case IRON_SWORD:
					if(main.isKits(Kits.DARTH_VADER, player)) main.Vader = 0;
					if(main.isKits(Kits.MANDO, player)) main.Mando = 0;
					main.setPState(Pstate.JEDI, player);
					main.setKits(Kits.RESISTANCE, player);
					main.setVie(3, player);
					player.sendMessage("Vous avez rejoint les §bjedi");
					BoardManager.boardManager(player);
					break;
				default: break;
			}
			
		}
	}
	
	@EventHandler
	public void onChestOpen(PlayerInteractEvent event) {
		
		Action action = event.getAction();
		
		if(action == Action.LEFT_CLICK_AIR || action == Action.RIGHT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) return;
		
		if(event.getClickedBlock().getType() == Material.CHEST && event.getPlayer() instanceof Player) {
			event.setCancelled(true);
		}
	}
	
	public void onEmpire(Player player) {
		if(main.isKits(Kits.DARTH_VADER, player)) main.Vader = 0;
		main.setPState(Pstate.EMPIRE, player);
		player.sendMessage("Vous avez rejoint l'§4Empire");
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if(event.getBlock().getType() != Material.AIR) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if(event.getBlock().getType() != Material.AIR) {
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
