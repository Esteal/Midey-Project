package fr.midey.MagicUHC.Items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsConstructor {

	private Material material;
	private ItemStack it;
	private Enchantment enchant;
	private List<String> lore = new ArrayList<>();
	private String name;
	
	public ItemsConstructor (Material material) {
		this.material = material;
		it = new ItemStack(this.material);
	}
	
	public void applyEnchant(Enchantment enchantment, int level){
		ItemMeta M = it.getItemMeta();
		enchant = enchantment;
		M.addEnchant(enchant, level, true);
		M.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		it.setItemMeta(M);
	}
	
	public void applyUmbreakable(boolean umbreak) {
			ItemMeta M = it.getItemMeta();
			M.spigot().setUnbreakable(umbreak);
			M.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			it.setItemMeta(M);
	}
	
	public void applyName(String named) {
		ItemMeta M = it.getItemMeta();
		name = named;
		M.setDisplayName(name);
		it.setItemMeta(M);
	}
	public void applyLore(String lores) {
		ItemMeta M = it.getItemMeta();
		lore.add(lores);
		M.setLore(lore);
		it.setItemMeta(M);
	}
	
	public ItemStack getItem() {
		return it;
	}
	
	public void colorGlass(short nb) {
		it.setDurability(nb);
	}
}
