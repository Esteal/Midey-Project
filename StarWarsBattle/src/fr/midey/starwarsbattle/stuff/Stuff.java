package fr.midey.starwarsbattle.stuff;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import fr.midey.starwarsbattle.Main;
import fr.midey.starwarsbattle.state.Kits;
import net.md_5.bungee.api.ChatColor;

public class Stuff {

	private static Main main;
	
	public Stuff(Main main) {
		Stuff.main = main;
	}

	public static ItemStack EnchantedItem(Material material, String Name) 
	{
		ItemStack it = new ItemStack(material);
		ItemMeta customF = it.getItemMeta();
		customF.setDisplayName(Name);
		customF.addEnchant(Enchantment.ARROW_DAMAGE, 0, true);
		customF.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		it.setItemMeta(customF);
		return it;
	}
	
	public static ItemStack Banner(ItemStack it, String Name) 
	{
		ItemMeta customF = it.getItemMeta();
		customF.setDisplayName(Name);
		customF.addEnchant(Enchantment.ARROW_DAMAGE, 0, true);
		customF.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		it.setItemMeta(customF);
		return it;
	}
	
	public static void Stuffed(Player player) {
		
		if (main.isKits(Kits.TROOPER, player)) {
			Trooper(player);
		}
		else if (main.isKits(Kits.DARTH_VADER, player)) {
			Vader(player);
		}
		else if (main.isKits(Kits.MANDO, player)) {
			mando(player);
		}
		else if (main.isKits(Kits.RESISTANCE, player)) {
			player.getInventory().addItem(sword(Material.IRON_SWORD, ChatColor.DARK_BLUE + "Sabre laser", 4, 0, 0));
			jedi(player);
		}
		else if (main.isKits(Kits.SPEED, player)) {
			player.getInventory().addItem(sword(Material.WOOD_SWORD, ChatColor.DARK_GREEN + "Sabre laser", 5, 0, 0));
			jedi(player);
		}
		else if (main.isKits(Kits.FORCE, player)) {
			player.getInventory().addItem(sword(Material.STONE_SWORD, ChatColor.DARK_PURPLE + "Sabre laser", 7, 0, 0));
			jedi(player);			
		}
	}
	
	public static void Trooper(Player player) {
		main.saveDefaultConfig();
		player.getInventory().addItem(EnchantedItem(Material.BOW, "§4Blaster"));
		player.getInventory().addItem(EnchantedItem(Material.FIREBALL, "§4Bombe " + ChatColor.DARK_GRAY + "(" + main.getConfig().getInt("cost.bombe") + ")"));
		player.getInventory().addItem(sword(Material.GOLD_SWORD, ChatColor.GOLD + "Glaive Laser", 0, 1, 0));
		player.getInventory().setBoots(armor(Material.IRON_BOOTS, "§4Bottes de Trooper",3, 0, 0 ,0, 0, 0));
		player.getInventory().setLeggings(armor(Material.IRON_LEGGINGS, "§4Jambière de Trooper",3, 0, 0 ,0, 0, 0));
		player.getInventory().setChestplate(armor(Material.IRON_CHESTPLATE, "§4Plastron de Trooper",3, 0, 0 ,0, 0, 0));
		player.getInventory().setHelmet(armor(Material.IRON_HELMET, "§4Casque de Trooper",3, 0, 0 ,0, 0, 0));
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
		player.getInventory().addItem(EnchantedItem(Material.MILK_BUCKET, ChatColor.LIGHT_PURPLE + "Brevage revigorant " + ChatColor.DARK_GRAY + "(" +  main.getConfig().getInt("cost.brevage") + ")"));
		player.updateInventory();
	}
	public static void mando(Player player) {
		main.saveDefaultConfig();
		player.getInventory().addItem(EnchantedItem(Material.BOW, "§4Blaster"));
		player.getInventory().addItem(sword(Material.GOLD_SWORD, ChatColor.GOLD + "Glaive Laser", 2, 2, 0));
		player.getInventory().setBoots(armor(Material.IRON_BOOTS, "§4Bottes de Mandalorien",3, 0, 0 ,0, 0, 0));
		player.getInventory().setLeggings(armor(Material.IRON_LEGGINGS, "§4Jambière de Mandalorien",3, 0, 0 ,0, 0, 0));
		player.getInventory().setChestplate(armor(Material.IRON_CHESTPLATE, "§4Plastron de Mandalorien",3, 0, 0 ,0, 0, 0));
		player.getInventory().setHelmet(armor(Material.PUMPKIN, "§4Casque de Mandalorien",5, 0, 0 ,0, 0, 0));
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
		player.getInventory().addItem(EnchantedItem(Material.MILK_BUCKET, ChatColor.LIGHT_PURPLE + "Brevage revigorant " + ChatColor.DARK_GRAY + "(" +  main.getConfig().getInt("cost.brevage") + ")"));
		player.getInventory().addItem(EnchantedItem(Material.GLASS, ChatColor.AQUA + "JetPack " + ChatColor.DARK_GRAY + "(" +  main.getConfig().getInt("cost.jetpack") + "/s)"));
		player.updateInventory();
	}
	public static void Vader(Player player) {
		main.saveDefaultConfig();
		player.setAllowFlight(true);
		player.getInventory().addItem(sword(Material.DIAMOND_SWORD, "§4Sabre Laser", 5, 0, 0));
		player.getInventory().setBoots(armor(Material.DIAMOND_BOOTS, "§4Armure robotique",1, 10, 0 ,0, 0, 0));
		player.getInventory().setLeggings(armor(Material.DIAMOND_LEGGINGS, "§4§4Armure robotique",1, 0, 0 ,0, 0, 0));
		player.getInventory().setChestplate(armor(Material.DIAMOND_CHESTPLATE, "§4§4Armure robotique",1, 0, 0 ,0, 0, 0));
		player.getInventory().setHelmet(armor(Material.DIAMOND_HELMET, "§4§4Armure robotique",1, 0, 0 ,0, 0, 0));
		player.getInventory().addItem(EnchantedItem(Material.STICK, ChatColor.GRAY + "POUSSÉ DE FORCE " + ChatColor.DARK_GRAY + "(" +  main.getConfig().getInt("cost.pousse") + ")"));
		player.getInventory().addItem(EnchantedItem(Material.STICK, ChatColor.GRAY + "ATTRACTION DE FORCE " + ChatColor.DARK_GRAY + "(" +  main.getConfig().getInt("cost.attraction") + ")"));
		player.getInventory().addItem(EnchantedItem(Material.FLINT_AND_STEEL, ChatColor.AQUA + "ÉCLAIRS DE FORCE " + ChatColor.DARK_GRAY + "(" +  main.getConfig().getInt("cost.eclairs") + ")"));
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
		player.getInventory().addItem(EnchantedItem(Material.MILK_BUCKET, ChatColor.LIGHT_PURPLE + "Brevage revigorant " + ChatColor.DARK_GRAY + "(" +  main.getConfig().getInt("cost.brevage") + ")"));
		player.updateInventory();
	}
	
	public static void jedi(Player player) {
		player.setAllowFlight(true);
		player.getInventory().setBoots(armor(Material.LEATHER_BOOTS, "§bTunique du jedi",5, 10, 0 ,0, 0, 0));
		player.getInventory().setLeggings(armor(Material.LEATHER_LEGGINGS, "§bTunique du jedi",5, 0, 0 ,0, 0, 0));
		player.getInventory().setChestplate(armor(Material.LEATHER_CHESTPLATE, "§bTunique du jedi",5, 0, 0 ,0, 0, 0));
		player.getInventory().setHelmet(armor(Material.LEATHER_HELMET, "§bTunique du jedi",5, 0, 0 ,0, 0, 0));
		player.getInventory().addItem(EnchantedItem(Material.STICK, ChatColor.GRAY + "POUSSÉ DE FORCE " + ChatColor.DARK_GRAY + "(" +  main.getConfig().getInt("cost.pousse") + ")"));
		player.getInventory().addItem(EnchantedItem(Material.STICK, ChatColor.GRAY + "ATTRACTION DE FORCE " + ChatColor.DARK_GRAY + "(" +  main.getConfig().getInt("cost.attraction") + ")"));
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
		player.getInventory().addItem(EnchantedItem(Material.MILK_BUCKET, ChatColor.LIGHT_PURPLE + "Brevage revigorant " + ChatColor.DARK_GRAY + "(" +  main.getConfig().getInt("cost.brevage") + ")"));
		player.updateInventory();
	}
	
	
	public static ItemStack armor(Material armor, String Name, int levelP, int levelF, int levelPP, int levelA, int levelU, int levelT) {
		// Armor => pièce d'armur selectionne
		// Name => nom donne à la piece
		// levelP => protection
		// levelS => feather felling
		// levelPP =>protection projectile
		// levelA => agilité aquatique
		// levelU => unbreaking
		// levelT => thorns
		
		ItemStack armure = new ItemStack(armor);
		ItemMeta customA = armure.getItemMeta();
		customA.setDisplayName(Name);
		if(levelP > 0)
			customA.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, levelP, true);
		if(levelF > 0)
			customA.addEnchant(Enchantment.PROTECTION_FALL, levelF, true);
		if(levelPP > 0)	
			customA.addEnchant(Enchantment.PROTECTION_PROJECTILE, levelPP, true);
		if(levelA > 0)
			customA.addEnchant(Enchantment.DEPTH_STRIDER, levelA, true);
		if(levelU > 0)
			customA.addEnchant(Enchantment.DURABILITY, levelU, true);
		if(levelT > 0)
			customA.addEnchant(Enchantment.THORNS, levelT, true);
		customA.spigot().setUnbreakable(true);
		customA.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		armure.setItemMeta(customA);
		
		return armure;
	}
	public static ItemStack sword(Material sworde, String Name, int levelS, int levelK, int levelF) {
		//levelS -> sharpness
		//levelK -> knockback
		//levelF -> fire aspect
		ItemStack sword = new ItemStack(sworde);
		ItemMeta customS = sword.getItemMeta();
		customS.setDisplayName(Name);
		if(levelS > 0)
			customS.addEnchant(Enchantment.DAMAGE_ALL, levelS, true);
		if(levelK > 0)
			customS.addEnchant(Enchantment.KNOCKBACK, levelK, true);
		if(levelF > 0)	
			customS.addEnchant(Enchantment.FIRE_ASPECT, levelF, true);
		customS.spigot().setUnbreakable(true);
		sword.setItemMeta(customS);
		return sword;
	}
	
	public static void clearALL(Player player) {
		player.getInventory().clear();
		player.getEquipment().setBoots(null);
		player.getEquipment().setLeggings(null);
		player.getEquipment().setChestplate(null);
		player.getEquipment().setHelmet(null);;
		clearEffect(player);
	}
	
	public static void clearEffect(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			effect.getType();
			player.removePotionEffect(effect.getType());
		}
	}
}
