package fr.midey.clashroyale.stuff;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class Armor {
	
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
}
