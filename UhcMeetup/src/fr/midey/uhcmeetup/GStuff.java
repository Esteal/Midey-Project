package fr.midey.uhcmeetup;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GStuff {
	
	public static ItemStack armor(Material c1, Material c2) {
		Double rdmIt = Math.random() * 100 + 1;
		ItemStack it = null;
		if (rdmIt < 70) {
			it = new ItemStack(c1);
		}
		else if (rdmIt >= 30) {
			it = new ItemStack(c2);
		}
		ItemMeta customB = it.getItemMeta();
		Double rdmEn = Math.random() * 100 + 1;
		int rdmLv = (int) (Math.random() * 3 + 1);
		
		if (c1 == Material.IRON_SWORD) {
			customB.addEnchant(Enchantment.DAMAGE_ALL, rdmLv, true);
		}
		else if (c1 == Material.BOW) {
			customB.addEnchant(Enchantment.ARROW_DAMAGE, rdmLv, true);
		}
		else {
			if (rdmEn <= 85) {
				customB.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, rdmLv, true);
			}
			else if (rdmEn > 15) {
			customB.addEnchant(Enchantment.PROTECTION_PROJECTILE, rdmLv, true);
			}
		}
		it.setItemMeta(customB);
		return it;
	}
	
	public static ItemStack bonus() {
		
		int rdmIt = (int) (Math.random() * 4 +1);
		ItemStack it = null;
		if (rdmIt == 1) {
			it = new ItemStack(Material.FISHING_ROD);
		}
		else if (rdmIt == 2) {
			it = new ItemStack(Material.WEB, 16);
		}
		else if (rdmIt == 3) {
			it = new ItemStack(Material.LAVA_BUCKET);
		}
		else if (rdmIt == 4) {
			it = new ItemStack(Material.FLINT_AND_STEEL);
		}
		return it;
		
	}
	
	public static ItemStack itAlea(Material material, int max, int min) {
		
		int rdmS = min + (int)(Math.random() * ((max - min) + 1));
		ItemStack it = new ItemStack(material, rdmS);
		return it;
	}
}
