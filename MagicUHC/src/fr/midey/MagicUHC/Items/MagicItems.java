package fr.midey.MagicUHC.Items;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MagicItems {

	
	
	private String name1;
	private String name2;
	private String name3;

	public MagicItems(String name1, String name2, String name3) {
		this.name1 = name1;
		this.name2 = name2;
		this.name3 = name3;
	}
	
	public void Item(Player p) {
		ItemsConstructor it = new ItemsConstructor(Material.NETHER_STAR);
		it.applyName(name1);
		p.getInventory().addItem(it.getItem());
		it.applyName(name2);
		p.getInventory().addItem(it.getItem());
		it.applyName(name3);
		p.getInventory().addItem(it.getItem());
	}
}
