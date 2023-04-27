package fr.midey.nefaziatool;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements Listener{
	
	private HashMap<Player, Integer> cooldownHoe  = new HashMap<Player, Integer>();
	private HashMap<Player, ItemStack> helmetSave = new HashMap<Player, ItemStack>();
	private HashMap<Player, Integer> cooldownHelmet = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> chestViewer = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> dynamite = new HashMap<Player, Integer>();
	private HashMap<Player, Entity> dynamiteLoc = new HashMap<Player, Entity>();
	private HashMap<Player, Integer> cooldownBattisseuse = new HashMap<Player, Integer>();
	@Override
	public void onEnable() {
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this,this);
		
		getCommand("transformatrice").setExecutor(new Commands(this));
		getCommand("dynamite").setExecutor(new Commands(this));
		getCommand("chestviewer").setExecutor(new Commands(this));
		getCommand("batisseuse").setExecutor(new Commands(this));

		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				
				for(Player players : Bukkit.getOnlinePlayers()) {
					if (cooldownHoe.containsKey(players)){
						cooldownHoe.replace(players, cooldownHoe.get(players) - 1);
						if (cooldownHoe.get(players) <= 0) cooldownHoe.remove(players);
					}
					if (cooldownHelmet.containsKey(players)){
						cooldownHelmet.replace(players, cooldownHelmet.get(players) - 1);
						if (cooldownHelmet.get(players) <= 0) {
							cooldownHelmet.remove(players);
							if (helmetSave.containsKey(players)) {
								players.getInventory().setHelmet(helmetSave.get(players));
								helmetSave.remove(players);
							}
						}
					}
					if (cooldownBattisseuse.containsKey(players)){
						cooldownBattisseuse.replace(players, cooldownBattisseuse.get(players) - 1);
						if (cooldownBattisseuse.get(players) <= 0) cooldownBattisseuse.remove(players);
					}
				}
			}
			
		},0L,20L);
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {

			@Override
			public void run() {
				for(Player players : Bukkit.getOnlinePlayers()) {
					if(dynamite.containsKey(players)) {
						Block b = (Block) dynamiteLoc.get(players).getLocation().getBlock();
						if(b.getType() != Material.AIR) b.breakNaturally();
						b.getDrops().clear();
						Bukkit.getWorld("world").createExplosion(b.getLocation(), 3);
						Bukkit.getScheduler().cancelTask(dynamite.get(players));
						dynamite.remove(players);
						dynamiteLoc.remove(players);
					}
				}
			}
		}, 40L, 40L);
	}
	
	
	/*HAMMER
	@EventHandler
	public void onHammerDestroy(BlockBreakEvent event) {
		
		Block b = event.getBlock();
		Player player = event.getPlayer();
		ItemStack it = player.getItemInHand();
		if (it == null) return;
		if(it.getType() == Material.DIAMOND_PICKAXE) {
			for(int y = -1; y <= 1; y++) {
				for(int x = -1, z = -1; x <= 1 && z <=1; x++, z++) {
					b.getLocation().add(x, y, z).getBlock().breakNaturally();
				}
				for(int x = 0, z = -1; x <= 1 && z <=1; x++, z++) {
					b.getLocation().add(x, y, z).getBlock().breakNaturally();
				}
				for(int x = 1, z = -1; x <= 1 && z <=1; x++, z++) {
					b.getLocation().add(x, y, z).getBlock().breakNaturally();
				}
				for(int x = -1, z = 0; x <= 1 && z <=1; x++, z++) {
					b.getLocation().add(x, y, z).getBlock().breakNaturally();
				}
				for(int x = -1, z = 1; x <= 1 && z <=1; x++, z++) {
					b.getLocation().add(x, y, z).getBlock().breakNaturally();
				}
			}
		}
	}
	HAMMER*/
	
	/*HOE REPLACER*/
	@EventHandler
	public void HelmetReplacer(EntityDamageByEntityEvent e) {
		
		saveDefaultConfig();
		
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player victim = (Player) e.getEntity();
			Player damager = (Player) e.getDamager();
			ItemStack it = damager.getItemInHand();
			if (it == null) return;
			if (it.getType() == Material.GOLD_HOE && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(getConfig().getString(ChatColor.translateAlternateColorCodes('&',"item.transformatrice.name")))) {
				if(!cooldownHoe.containsKey(damager) && !helmetSave.containsKey(victim)) {
					if (it.getType() != Material.AIR) helmetSave.put(victim, victim.getInventory().getHelmet());
					victim.getInventory().setHelmet(new ItemStack(Material.OBSIDIAN));
					cooldownHoe.put(damager, 60);
					cooldownHelmet.put(victim, 5);
				}
				else if (cooldownHoe.containsKey(damager)){
					damager.sendMessage("Vous pourrez réutiliser cet item dans " + cooldownHoe.get(damager) + "s");
				}
			}
		}
	}
	/*HOE REPLACER*/
	
	/*CHESTVIEWER*/
	@EventHandler
	public void onChestViewer(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if (it == null) return;
		saveDefaultConfig();
		if(it.getType() == Material.STICK && e.getAction() == Action.RIGHT_CLICK_BLOCK && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(getConfig().getString(ChatColor.translateAlternateColorCodes('&',"item.chestViewer.name")))) {
			if (e.getClickedBlock().getType() == Material.CHEST) {
				e.setCancelled(true);
				Chest chest = (Chest) e.getClickedBlock().getState();
				Inventory invChest = chest.getInventory();
				chestViewer.put(e.getPlayer(), 1);
				e.getPlayer().openInventory(invChest);
			}
		}
	}
	
	@EventHandler
	public void onInventoryViewer(InventoryClickEvent e) {
		if(chestViewer.containsKey(e.getWhoClicked())) {
			if (chestViewer.get(e.getWhoClicked()) == 1) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onInventoryCloseViewer(InventoryCloseEvent e) {
		if (chestViewer.containsKey(e.getPlayer())) {
			chestViewer.remove(e.getPlayer());
		}
	}
	/*CHESTVIEWER*/
	
	/*Dynamite*/
	@EventHandler
	public void onDynamite(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if (it == null) return;
		
		Action action = e.getAction();
		saveDefaultConfig();
		if(it.getType() == Material.TNT && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(getConfig().getString(ChatColor.translateAlternateColorCodes('&',"item.dynamite.name")))) {
			if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && !dynamite.containsKey(e.getPlayer())) {
				e.setCancelled(true);
				Player p = e.getPlayer();
				Location loc = p.getLocation();
				Vector v = loc.getDirection().multiply(1);
				@SuppressWarnings("deprecation")
				Entity tnt = (Entity) Bukkit.getWorld("world").spawnFallingBlock(loc, Material.TNT, (byte) 2);
				tnt.setVelocity(v);
				dynamite.put(p, Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
					@Override
					public void run() {
						dynamiteLoc.put(p, tnt);
					}
				},1L, 1L));
				it.setAmount(it.getAmount() - 1);
				if(it.getAmount() < 1) p.getInventory().setItemInHand(new ItemStack(Material.AIR));
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if(event.getBlock().getType() == Material.AIR) return;
		if(event.getBlock().getType() == Material.TNT && event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(getConfig().getString(ChatColor.translateAlternateColorCodes('&',"item.dynamite.name")))) {
			event.setCancelled(true);
		}
	}
	
	/*BATISSEUSE*/
	@EventHandler
	public void onBatisseuse(PlayerInteractEvent e) {
		
		saveDefaultConfig();
		
		ItemStack it = e.getItem();
		if(it == null) return;
		if(it.getType() != Material.GOLD_AXE || e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		Block b = e.getClickedBlock();
		Location loc = b.getLocation();
		Action action = e.getAction();
		if (it.getType() == Material.GOLD_AXE && action == Action.RIGHT_CLICK_BLOCK) {
			if(loc.add(0, 1, 0).getBlock().getType() != Material.AIR) return;
			if (!cooldownBattisseuse.containsKey(e.getPlayer()) && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(getConfig().getString(ChatColor.translateAlternateColorCodes('&',"item.batisseuse obsi.name")))) {
			for (double i = e.getClickedBlock().getY() + 1; i <= 255; i++) {
				loc.setY(i);
				Block block = loc.getBlock();
				if(block.getType() != Material.AIR) break;
				block.setType(Material.OBSIDIAN);
			}
			it.setDurability((short) (it.getDurability() + (short) 1));
			if(it.getDurability() > (short) 32) e.getPlayer().getInventory().setItemInHand(new ItemStack(Material.AIR));
			cooldownBattisseuse.put(e.getPlayer(), 3);
			return;
			}
			else if (!cooldownBattisseuse.containsKey(e.getPlayer()) && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(getConfig().getString(ChatColor.translateAlternateColorCodes('&',"item.batisseuse sable.name")))) {
			for (double i = e.getClickedBlock().getY() + 1; i <= 255; i++) {
				loc.setY(i);
				Block block = loc.getBlock();
				if(block.getType() != Material.AIR) break;
				block.setType(Material.SAND);
			}
			it.setDurability((short) (it.getDurability() + (short) 1));
			if(it.getDurability() > (short) 32) e.getPlayer().getInventory().setItemInHand(new ItemStack(Material.AIR));
			cooldownBattisseuse.put(e.getPlayer(), 3);
			return;
			}
			else if (cooldownBattisseuse.containsKey(e.getPlayer())) e.getPlayer().sendMessage("§4Veuillez attendre " + cooldownBattisseuse.get(e.getPlayer()) + "s avant de pouvoir reposer");
		}
	}
}
