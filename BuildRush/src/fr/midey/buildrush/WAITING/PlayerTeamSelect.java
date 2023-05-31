package fr.midey.buildrush.WAITING;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.midey.buildrush.BuildRush;
import fr.midey.buildrush.Player.States;
import fr.midey.buildrush.tools.ItemControler;
import fr.midey.buildrush.tools.ItemsConstructor;

public class PlayerTeamSelect implements Listener {

	private BuildRush main;
	
	public PlayerTeamSelect(BuildRush main) {
		this.main = main;
	}

	//Ouvre le menu de sélection de team lors du clique sur la banner
	@EventHandler
	public void onClickBanner(PlayerInteractEvent event) {
		Action action = event.getAction();
		ItemStack it = event.getItem();
		if(it == null) return;
		if(it.getType() == Material.BANNER && it.hasItemMeta() && it.getItemMeta().hasDisplayName()) {
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				doInventoryOfSelectionTeam(event.getPlayer());
			}
		}
	}
	//Permet de sélectionner une équipe
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickBannerInInventory(InventoryClickEvent event) {
		ItemStack it = event.getCurrentItem();
		if(it == null) return;
		Inventory inv = event.getClickedInventory();
		if(inv.getName().equalsIgnoreCase("Choisi ton équipe")) {
			Player player = (Player) event.getWhoClicked();
			if(ItemControler.itemControler(it, "§cÉquipe rouge") && main.getRedTeam().getSize() < main.getNumberPerTeam()) {
				main.getRedTeam().addPlayer(player.getPlayer());
				main.getPlayersStates().get(player).setTeam("red");
				player.sendMessage("§6[BuildRush] §7Vous avez rejoint l'équipe §cRouge§7.");
			}
			else if(ItemControler.itemControler(it, "§9Équipe bleue") && main.getBlueTeam().getSize() < main.getNumberPerTeam()) {
				main.getBlueTeam().addPlayer(player.getPlayer());
				main.getPlayersStates().get(player).setTeam("blue");
				player.sendMessage("§6[BuildRush] §7Vous avez rejoint l'équipe §9Bleu§7.");
			}
			else if(ItemControler.itemControler(it, "§eÉquipe aléatoire")) {
				
				if(main.getBlueTeam().hasPlayer(player)) 
					main.getBlueTeam().removePlayer(player);
				if(main.getRedTeam().hasPlayer(player)) 
					main.getRedTeam().removePlayer(player);
				
				main.getPlayersStates().get(player).setTeam(null);
			}
			doInventoryOfSelectionTeam(player);
			event.setCancelled(true);
		}
	}
	
	public void doInventoryOfSelectionTeam(Player player) {
		Inventory inv  = Bukkit.createInventory(player, 45, "Choisi ton équipe");
		ItemsConstructor red = new ItemsConstructor(Material.BANNER);
		ItemsConstructor blue = new ItemsConstructor(Material.BANNER);
		ItemsConstructor random = new ItemsConstructor(Material.BANNER);
		red.Color((short) 1);
		blue.Color((short) 4);
		random.Color((short) 15);
		red.applyName("§cÉquipe rouge");
		blue.applyName("§9Équipe bleue");
		random.applyName("§eÉquipe aléatoire");
		red.applyLore("§7Joueurs:");
		blue.applyLore("§7Joueurs:");
		red.applyLore(" ");
		blue.applyLore(" ");
		for(Player players : main.getPlayers()) {
			States states = main.getPlayersStates().get(players);
			if(states.getTeam() == "blue") blue.applyLore("§8» §9" + players.getName());
			else if (states.getTeam() == "red") red.applyLore("§8» §c" + players.getName());
			if(players.getOpenInventory().getTitle().equalsIgnoreCase("Choisi ton équipe")) {
				inv.setItem(21, red.getItem());
				inv.setItem(22, random.getItem());
				inv.setItem(23, blue.getItem());
				players.openInventory(inv);
			}
		}
		inv.setItem(21, red.getItem());
		inv.setItem(22, random.getItem());
		inv.setItem(23, blue.getItem());
		player.openInventory(inv);
	}
}
