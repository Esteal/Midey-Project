package fr.midey.clashroyale.roles;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.midey.clashroyale.stuff.Armor;

public class Valkyrie {
	private Player p;
	private ItemStack weapon = Armor.sword(Material.IRON_AXE, "Valkyrie", 3, 0, 0);


	private ItemStack[] set = {
			Armor.armor(Material.IRON_BOOTS, "Valkyrie", 2, 0, 0, 0, 0, 0),
			Armor.armor(Material.IRON_LEGGINGS, "Valkyrie", 2, 0, 0, 0, 0, 0),
			Armor.armor(Material.IRON_CHESTPLATE, "Valkyrie", 2, 0, 0, 0, 0, 0),
			Armor.armor(Material.IRON_HELMET, "Valkyrie", 2, 0, 0, 0, 0, 0)
	};
	
	public Valkyrie(Player p) {
		this.p = p;
	}
	
	public void stuffed() {
		Armor.clearALL(p);
		p.getInventory().setArmorContents(set);
		p.getInventory().addItem(weapon);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 999999999, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999999, 0));
		p.updateInventory();
	}
}
