package fr.midey.nefaziatool;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor {

	private static Main main;

	public Commands(Main main) {
		Commands.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		main.saveDefaultConfig();
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(msg.equalsIgnoreCase("transformatrice")) {
				ItemStack it = EnchantedItem(Material.GOLD_HOE, ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("item.transformatrice.name")), "transformatrice");
				p.getInventory().addItem(it);
			}
			if(msg.equalsIgnoreCase("dynamite")) {
				ItemStack it = EnchantedItem(Material.TNT, ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("item.dynamite.name")), "dynamite");
				p.getInventory().addItem(it);
			}
			if(msg.equalsIgnoreCase("chestviewer")) {
				ItemStack it = EnchantedItem(Material.STICK, ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("item.chestViewer.name")), "chestViewer");
				p.getInventory().addItem(it);
			}
			if(msg.equalsIgnoreCase("batisseuse")) {
				ItemStack it = EnchantedItem(Material.GOLD_AXE, ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("item.batisseuse obsi.name")), "batisseuse obsi");
				ItemStack it2 = EnchantedItem(Material.GOLD_AXE, ChatColor.translateAlternateColorCodes('&',main.getConfig().getString("item.batisseuse sable.name")), "batisseuse sable");
				p.getInventory().addItem(it);
				p.getInventory().addItem(it2);
				
			}
		}
		return false;
	}
	
	public static ItemStack EnchantedItem(Material material, String Name, String s) 
	{
		ItemStack it = new ItemStack(material);
		ItemMeta customF = it.getItemMeta();
		customF.setDisplayName(Name);
		customF.setLore(makeLores(s));
		customF.addEnchant(Enchantment.ARROW_DAMAGE, 0, true);
		customF.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		it.setItemMeta(customF);
		return it;
	}
	
	public static List<String> makeLores(String item) {
		main.saveDefaultConfig();
		ConfigurationSection itemSection = main.getConfig().getConfigurationSection("item");
		List<String> lores = new ArrayList<>();
		for(String key : itemSection.getKeys(false)) {
			if(key.equalsIgnoreCase(item)) {
				ConfigurationSection optionItem = itemSection.getConfigurationSection(key);
				for(String keyOption : optionItem.getKeys(false)) {
					if(keyOption.equalsIgnoreCase("lores")) {
						ConfigurationSection optionLores = optionItem.getConfigurationSection(keyOption);
						for(String lore : optionLores.getKeys(false)) {
							lores.add(ChatColor.translateAlternateColorCodes('&', lore));
						}
					}
				}
			}
		}
		return lores;
	}
}
