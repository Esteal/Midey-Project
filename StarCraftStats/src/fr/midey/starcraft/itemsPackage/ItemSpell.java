package fr.midey.starcraft.itemsPackage;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemSpell {

	private ItemsConstructor itC = new ItemsConstructor(Material.BARRIER);
	private String name;
	private List<String> lore;
	
	public ItemSpell(String name, List<String> lore) {
		this.name = name;
		this.lore = lore;
	}
	
	public ItemStack spell() {
		
		itC.applyName(name);
		for(int i = 0; i < lore.size(); i++) {
			itC.applyLore(lore.get(i));
		}
		return itC.getItem();
	}
}
