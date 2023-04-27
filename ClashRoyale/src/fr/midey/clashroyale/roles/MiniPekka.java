package fr.midey.clashroyale.roles;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.midey.clashroyale.stuff.Armor;

public class MiniPekka {
	private Player p;
	private ItemStack weapon = Armor.sword(Material.DIAMOND_SWORD, "§4Hachoir", 1, 0, 0);


	private ItemStack[] set = {
			Armor.armor(Material.IRON_BOOTS, "§4Mini Pekka", 3, 0, 0, 0, 0, 0),
			Armor.armor(Material.CHAINMAIL_LEGGINGS, "§4Mini Pekka", 2, 0, 0, 0, 0, 0),
			Armor.armor(Material.CHAINMAIL_CHESTPLATE, "§4Mini Pekka", 2, 0, 0, 0, 0, 0),
			Armor.armor(Material.IRON_HELMET, "§4Mini Pekka", 3, 0, 0, 0, 0, 0)
	};
	
	public MiniPekka(Player p) {
		this.p = p;
	}
	
	public void stuffed() {
		Armor.clearALL(p);
		p.getInventory().setArmorContents(set);
		p.getInventory().addItem(weapon);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999999, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 999999999, 0));
		p.updateInventory();
	}
}
