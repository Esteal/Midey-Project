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

	//Ouvre le menu de s�lection de team lors du clique sur la banner
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
	//Permet de s�lectionner une �quipe
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickBannerInInventory(InventoryClickEvent event) {
		ItemStack it = event.getCurrentItem();
		if(it == null) return;
		Inventory inv = event.getClickedInventory();
		if(inv.getName().equalsIgnoreCase("Choisi ton �quipe")) {
			Player player = (Player) event.getWhoClicked();
			if(ItemControler.itemControler(it, "�c�quipe rouge") && main.getRedTeam().getSize() < main.getNumberPerTeam()) {
				main.getRedTeam().addPlayer(player.getPlayer());
				main.getPlayersStates().get(player).setTeam("red");
				player.sendMessage("�6[BuildRush] �7Vous avez rejoint l'�quipe �cRouge�7.");
			}
			else if(ItemControler.itemControler(it, "�9�quipe bleue") && main.getBlueTeam().getSize() < main.getNumberPerTeam()) {
				main.getBlueTeam().addPlayer(player.getPlayer());
				main.getPlayersStates().get(player).setTeam("blue");
				player.sendMessage("�6[BuildRush] �7Vous avez rejoint l'�quipe �9Bleu�7.");
			}
			else if(ItemControler.itemControler(it, "�e�quipe al�atoire")) {
				
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
		Inventory inv  = Bukkit.createInventory(player, 45, "Choisi ton �quipe");
		ItemsConstructor red = new ItemsConstructor(Material.BANNER);
		ItemsConstructor blue = new ItemsConstructor(Material.BANNER);
		ItemsConstructor random = new ItemsConstructor(Material.BANNER);
		red.Color((short) 1);
		blue.Color((short) 4);
		random.Color((short) 15);
		red.applyName("�c�quipe rouge");
		blue.applyName("�9�quipe bleue");
		random.applyName("�e�quipe al�atoire");
		red.applyLore("�7Joueurs:");
		blue.applyLore("�7Joueurs:");
		red.applyLore(" ");
		blue.applyLore(" ");
		for(Player players : main.getPlayers()) {
			States states = main.getPlayersStates().get(players);
			if(states.getTeam() == "blue") blue.applyLore("�8� �9" + players.getName());
			else if (states.getTeam() == "red") red.applyLore("�8� �c" + players.getName());
			if(players.getOpenInventory().getTitle().equalsIgnoreCase("Choisi ton �quipe")) {
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
