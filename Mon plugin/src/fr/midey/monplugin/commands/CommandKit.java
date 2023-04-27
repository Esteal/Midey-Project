package fr.midey.monplugin.commands;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandKit implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			//carrote de speed
			ItemStack carrote = new ItemStack(Material.CARROT_ITEM, 16);
			ItemMeta customM = carrote.getItemMeta();
			customM.setDisplayName("§bCarrote de speed");
			customM.setLore(Arrays.asList("§fCeci est une carrote pouvant", "§fvous conférer l'effet speed"));
			customM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			customM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			carrote.setItemMeta(customM);
			//carrote de speed
			
			
			//patate de force
			ItemStack patate = new ItemStack(Material.BAKED_POTATO, 16);
			ItemMeta customP = carrote.getItemMeta();
			customP.setDisplayName("§4Patate de force");
			customP.setLore(Arrays.asList("§fCeci est une patate pouvant", "§fvous conférer l'effet force"));
			customP.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			customP.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			patate.setItemMeta(customP);
			//patate de force
			
			//Baton du mage
		
			//Baton du mage
			
			
			//épée de test
			ItemStack epee = new ItemStack(Material.DIAMOND_SWORD);
			ItemMeta customEpee = epee.getItemMeta();
			customEpee.setDisplayName("epee de test");
			customEpee.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
			customEpee.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
			customEpee.addEnchant(Enchantment.DURABILITY, 3, true);
			epee.setItemMeta(customEpee);
			//épée de test
			
			//armure de test
			ItemStack casque = new ItemStack(Material.DIAMOND_HELMET);
			ItemMeta customCasque = casque.getItemMeta();
			customCasque.setDisplayName("casque de test");
			casque.setItemMeta(customCasque);
			//armure de test
			
			//seau d'eau
			ItemStack seau = new ItemStack(Material.WATER_BUCKET);
			//
			
			if(cmd.getName().equalsIgnoreCase("kit")) {
							
				player.getInventory().addItem(carrote);
				player.getInventory().addItem(patate);
				
				player.sendMessage("vous avez reçu vos §4Carrote de speed §f!");
			
				return true;				
			}
			if(cmd.getName().equalsIgnoreCase("bidule")) {
				player.getInventory().addItem();
			}
			
			if(cmd.getName().equalsIgnoreCase("mlg")) {
				player.getInventory().addItem(seau);
			}
			
			if(cmd.getName().equalsIgnoreCase("sword")) {
				player.getInventory().addItem(epee);
				player.getInventory().addItem(casque);
			}
		}
		
		
		return false;
	}

	

	
	
}

