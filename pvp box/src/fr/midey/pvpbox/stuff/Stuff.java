package fr.midey.pvpbox.stuff;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Stuff implements Listener {

	
	public static void archer(Player player) {
		player.getInventory().clear();
		clearALL(player);
		clearEffect(player);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 255));
		player.getInventory().setBoots(armor(new ItemStack(Material.LEATHER_BOOTS), "�9Bottes du clampin", 2, 0, 0, 0, 0, 0));
		player.getInventory().setLeggings(armor(new ItemStack(Material.LEATHER_LEGGINGS), "�9Jambi�re du clampin", 2, 0, 0, 0, 0, 0));
		player.getInventory().setChestplate(armor(new ItemStack(Material.LEATHER_CHESTPLATE), "�9Plastron du clampin", 2, 0, 0, 0, 0, 0));
		player.getInventory().setHelmet(armor(new ItemStack(Material.LEATHER_HELMET), "�9Casque du clampin", 2, 0, 0, 0, 0, 0));
		player.getInventory().setItem(0,sword(new ItemStack(Material.WOOD_SWORD), "�9�p�e du clampin",0, 1, 0));
		player.getInventory().setItem(1, Bow(new ItemStack(Material.BOW), "�9Arc du clampin",3, 1, 0, 0));
		player.getInventory().setItem(2, EnchantedFeed(new ItemStack(Material.SUGAR), "�9Sniffff moi �a mon rheuf"));
		player.getInventory().setItem(35, new ItemStack(Material.ARROW, 1));
		player.updateInventory();
		player.setHealthScale(12.0);
	}
	
	public static void berserker(Player player) {
		player.getInventory().clear();
		clearALL(player);
		clearEffect(player);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 255));
		player.getInventory().setBoots(armor(new ItemStack(Material.CHAINMAIL_BOOTS), "�2Bottes du bourrin", 3, 0, 0, 0, 0, 0));
		player.getInventory().setLeggings(armor(new ItemStack(Material.LEATHER_LEGGINGS), "�2Jambi�re du bourrin", 3, 0, 0, 0, 0, 0));
		player.getInventory().setChestplate(armor(new ItemStack(Material.LEATHER_CHESTPLATE), "�2Plastron du bourrin", 3, 0, 0, 0, 0, 0));
		player.getInventory().setHelmet(armor(new ItemStack(Material.CHAINMAIL_HELMET), "�2Casque du bourrin", 3, 0, 0, 0, 0, 0));
		player.getInventory().setItem(0, sword(new ItemStack(Material.STONE_AXE), "�2Hache du bourrin",3, 0, 0));
		player.getInventory().setItem(1,EnchantedFeed(new ItemStack(Material.BLAZE_POWDER), "�2C'est d'la bonne mec !"));
		player.setHealthScale(24);
		player.setHealth(20.0);
		player.updateInventory();
	}

	public static void berserker2(Player player) {
		player.getInventory().clear();
		clearALL(player);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 255));
		player.getInventory().setBoots(armor(new ItemStack(Material.LEATHER_BOOTS), "�2Bottes du �4GROS �2bourrin", 2, 0, 0, 0, 0, 0));
		player.getInventory().setLeggings(armor(new ItemStack(Material.LEATHER_LEGGINGS), "�2Jambi�re du �4GROS �2bourrin", 1, 0, 0, 0, 0, 0));
		player.getInventory().setChestplate(armor(new ItemStack(Material.LEATHER_CHESTPLATE), "�2Plastron du �4GROS �2bourrin", 1, 0, 0, 0, 0, 0));
		player.getInventory().setHelmet(armor(new ItemStack(Material.LEATHER_HELMET), "�2Casque du �4GROS �2bourrin", 2, 0, 0, 0, 0, 0));
		player.getInventory().setItem(0, sword(new ItemStack(Material.DIAMOND_AXE), "�2Hache du �4GROS �2bourrin",2, 0, 0));
		player.getInventory().setItem(1, new ItemStack(Material.GOLDEN_APPLE, 2));
		player.setHealthScale(8.0);
		player.setHealth(20.0);
		player.updateInventory();
	}
	
	public static void monk(Player player) {
		player.getInventory().clear();
		clearALL(player);
		clearEffect(player);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 255));
		player.getInventory().setBoots(armor(new ItemStack(Material.CHAINMAIL_BOOTS), "�1Bottes du crois�", 3, 0, 0, 0, 2, 0));
		player.getInventory().setLeggings(armor(new ItemStack(Material.LEATHER_LEGGINGS), "�1Jambi�re du crois�", 3, 0, 0, 0, 2, 0));
		player.getInventory().setChestplate(armor(new ItemStack(Material.LEATHER_CHESTPLATE), "�1Plastron du crois�", 3, 0, 0, 0, 2, 0));
		player.getInventory().setHelmet(armor(new ItemStack(Material.CHAINMAIL_HELMET), "�1Casque du crois�", 3, 0, 0, 0, 2, 0));
		player.getInventory().setItem(0, sword(new ItemStack(Material.STONE_SWORD),"�1�p�e du crois�", 2, 0, 0));
		player.getInventory().setItem(1, EnchantedFeed(new ItemStack(Material.IRON_INGOT), "�1Deviens un racailloux !!!!"));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
		player.setHealthScale(18.0);
		player.setHealth(20.0);
		player.updateInventory();
	}
	
	public static void assassin(Player player) {
		player.getInventory().clear();
		clearALL(player);
		clearEffect(player);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 255));
		player.getInventory().setBoots(armor(new ItemStack(Material.CHAINMAIL_BOOTS), "�7Bottes du fdp", 2, 0, 0, 0, 2, 0));
		player.getInventory().setLeggings(armor(new ItemStack(Material.CHAINMAIL_LEGGINGS), "�7Jambi�re du fdp", 1, 0, 0, 0, 2, 0));
		player.getInventory().setChestplate(armor(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "�7Plastron du fdp", 1, 0, 0, 0, 2, 0));
		player.getInventory().setHelmet(armor(new ItemStack(Material.CHAINMAIL_HELMET), "�7Casque du fdp", 2, 0, 0, 0, 2, 0));
		player.getInventory().setItem(0, sword(new ItemStack(Material.GOLD_SWORD),"�7�p�e du fdp", 3, 0, 0));
		player.getInventory().setItem(1, EnchantedFeed(new ItemStack(Material.EYE_OF_ENDER), "�7Furtivit�"));
		player.setHealthScale(20.0);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
		player.updateInventory();
	}
	public static void assassin2(Player player) {
		clearALL(player);
		player.getInventory().clear();
		player.getInventory().setItem(0, sword(new ItemStack(Material.DIAMOND_SWORD), "�7Coup en douce", 4, 0, 0));
		player.getInventory().setItem(2, EnchantedFeed(new ItemStack(Material.CHEST), "�7Ton stuff est l� dedans !"));
		player.updateInventory();
	}
	
	public static void paladin(Player player) {
		player.getInventory().clear();
		clearALL(player);
		clearEffect(player);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 255));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0));
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
		player.getInventory().setItem(1, EnchantedFeed(new ItemStack(Material.IRON_BLOCK), "R�siste plus !"));
		player.setHealthScale(29.0);
		player.setHealth(20.0);
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
	public static ItemStack EnchantedFeed(ItemStack feed, String Name) 
	{
		ItemMeta customF = feed.getItemMeta();
		customF.setDisplayName(Name);
		customF.addEnchant(Enchantment.ARROW_DAMAGE, 0, true);
		customF.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		feed.setItemMeta(customF);
		return feed;
	}
	
	public static ItemStack sword(ItemStack sword, String Name, int levelS, int levelK, int levelF) {
		//levelS -> sharpness
		//levelK -> knockback
		//levelF -> fire aspect
		
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
	
	public static ItemStack armor(ItemStack armor, String Name, int levelP, int levelF, int levelPP, int levelA, int levelU, int levelT) {
		// Armor => pi�ce d'armur selectionne
		// Name => nom donne � la piece
		// levelP => protection
		// levelS => feather felling
		// levelPP =>protection projectile
		// levelA => agilit� aquatique
		// levelU => unbreaking
		// levelT => thorns
		ItemStack armure = armor;
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
		armure.setItemMeta(customA);
		
		return armure;
		}
	
	public static ItemStack Bow(ItemStack bow, String Name, int levelP, int levelI, int levelF, int levelPU) {
		//Bow -> arc
		//Name -> nom
		//levelP -> puissance
		//levelI -> infinity
		//levelF -> flame
		//levelPU -> punch
		ItemMeta customB = bow.getItemMeta();
		customB.setDisplayName(Name);
		if(levelP > 0)
			customB.addEnchant(Enchantment.ARROW_DAMAGE, levelP, true);
		if(levelI > 0)
			customB.addEnchant(Enchantment.ARROW_INFINITE, levelI, true);
		if(levelF > 0)	
			customB.addEnchant(Enchantment.ARROW_FIRE, levelF, true);
		if(levelPU > 0)
			customB.addEnchant(Enchantment.ARROW_KNOCKBACK, levelPU, true);
		customB.spigot().setUnbreakable(true);
		bow.setItemMeta(customB);
		return bow;
	}
}
