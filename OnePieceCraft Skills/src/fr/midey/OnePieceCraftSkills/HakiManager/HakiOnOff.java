package fr.midey.OnePieceCraftSkills.HakiManager;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;

public class HakiOnOff implements CommandExecutor {

	private final OnePieceCraftSkills plugin;

    public HakiOnOff(OnePieceCraftSkills plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length == 2) {
            String hakiType = args[0].toLowerCase();
            String action = args[1].toLowerCase();

            if ("observation".equals(hakiType)) {
                handleObservationHaki(player, action);
            } else if ("armement".equals(hakiType)) {
                handleArmamentHaki(player, action);
            } else if ("roi".equals(hakiType)) {
                handleKingHaki(player, action);
            } else {
                player.sendMessage(ChatColor.RED + "Type de Haki inconnu.");
            }
        }

        return true;
    }

    private void handleObservationHaki(Player player, String action) {
    	UUID uuid = player.getUniqueId();
        PlayerData playerData = plugin.getPlayerDataMap().getOrDefault(uuid, new PlayerData());
        if ("on".equals(action)) {
        	playerData.setObservationHakiActive(true);
            
            player.sendMessage(ChatColor.GREEN + "Haki de l'Observation activé.");
        } else if ("off".equals(action)) {
        	playerData.setObservationHakiActive(false);
            player.sendMessage(ChatColor.RED + "Haki de l'Observation désactivé.");
        } else {
            player.sendMessage(ChatColor.RED + "ON / OFF ?");
        }
    }

    private void handleArmamentHaki(Player player, String action) {
        if ("on".equals(action)) {
            ItemStack hakiItem = new ItemStack(Material.OBSIDIAN);
            ItemMeta meta = hakiItem.getItemMeta();
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Haki de l'Armement");
            hakiItem.setItemMeta(meta);
            player.getInventory().addItem(hakiItem);
            player.sendMessage(ChatColor.GREEN + "Haki de l'Armement activé.");
        } else if ("off".equals(action)) {
            player.getInventory().remove(Material.OBSIDIAN); // Cela enlève tous les blocs d'obsidienne, ce qui pourrait être un problème.
            player.sendMessage(ChatColor.RED + "Haki de l'Armement désactivé.");
        } else {
            player.sendMessage(ChatColor.RED + "ON / OFF ?");
        }
    }
    
    private void handleKingHaki(Player player, String action) {
        if ("on".equals(action)) {
            ItemStack hakiItem = new ItemStack(Material.GOLD_BLOCK);
            ItemMeta meta = hakiItem.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "Haki des Rois");
            hakiItem.setItemMeta(meta);
            player.getInventory().addItem(hakiItem);
            player.sendMessage(ChatColor.GREEN + "Haki des Rois activé.");
        } else if ("off".equals(action)) {
        	player.getInventory().remove(Material.GOLD_BLOCK);
            player.sendMessage(ChatColor.RED + "Haki des Rois désactivé.");
        } else {
            player.sendMessage(ChatColor.RED + "ON / OFF ?");
        }
    }

}