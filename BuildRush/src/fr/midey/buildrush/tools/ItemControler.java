package fr.midey.buildrush.tools;

import org.bukkit.inventory.ItemStack;

public class ItemControler {

	public static boolean itemControler(ItemStack it, String displayName) {
		if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase(displayName)) return true;
		return false;
	}
}
