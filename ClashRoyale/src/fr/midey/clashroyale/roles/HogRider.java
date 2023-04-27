package fr.midey.clashroyale.roles;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.midey.clashroyale.stuff.Armor;

public class HogRider {
	
	private Player p;
	private ItemStack weapon = Armor.sword(Material.DIAMOND_AXE, "Hog Rider", 3, 0, 0);


	private ItemStack[] set = {
			Armor.armor(Material.LEATHER_BOOTS, "Hog Rider", 3, 0, 0, 0, 0, 0),
			Armor.armor(Material.LEATHER_LEGGINGS, "Hog Rider", 3, 0, 0, 0, 0, 0),
			Armor.armor(Material.LEATHER_CHESTPLATE, "Hog Rider", 3, 0, 0, 0, 0, 0),
			Armor.armor(Material.LEATHER_HELMET, "Hog Rider", 3, 0, 0, 0, 0, 0)
	};
	
	public HogRider(Player p) {
		this.p = p;
	}
	
	public void stuffed() {
		Armor.clearALL(p);
		p.getInventory().setArmorContents(set);
		p.getInventory().addItem(weapon);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 999999999, 0));
		p.updateInventory();
	}
}

