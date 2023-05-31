package fr.midey.buildrush.Player;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.midey.buildrush.tools.ItemsConstructor;

public class States {
	
	private String team;
	private int point;
	private int kills;
	private String teams;
	private Player killer;
	private ItemStack[] armorContent;
	private ItemStack sword;
	private ItemStack pickaxe;
	
	public States() {
		ItemsConstructor helmet = new ItemsConstructor(Material.LEATHER_HELMET);
		ItemsConstructor chestplate = new ItemsConstructor(Material.LEATHER_CHESTPLATE);
		ItemsConstructor leggings = new ItemsConstructor(Material.LEATHER_LEGGINGS);
		ItemsConstructor boots = new ItemsConstructor(Material.LEATHER_BOOTS);
		
		helmet.applyEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		chestplate.applyEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		leggings.applyEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		boots.applyEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
	
		helmet.applyUmbreakable(true);
		chestplate.applyUmbreakable(true);
		leggings.applyUmbreakable(true);
		boots.applyUmbreakable(true);
		
		armorContent = new ItemStack[4];
		
		armorContent[3] = helmet.getItem();
		armorContent[2] = chestplate.getItem();
		armorContent[1] = leggings.getItem();
		armorContent[0] = boots.getItem();
	}
	
	public String getTeam() { return team; }
	public void setTeam(String team) { this.team = team; }
	
	public int getPoint() { return point; }
	public void setPoint(int point) { this.point = point; }
	
	public int getKills() { return kills; }
	public void setKills(int kills) { this.kills = kills; }
	
	public String getTeams() { return teams; }
	public void setTeams(String teams) { this.teams = teams; }
	
	public Player getKiller() { return killer; }
	public void setKiller(Player killer) { this.killer = killer; }

	public ItemStack[] getArmorContent() { return armorContent; }
	public void setArmorContent(ItemStack[] armorContent) { this.armorContent = armorContent; }

	public ItemStack getSword() { return sword; }

	public void setSword(ItemStack sword) { this.sword = sword; }

	public ItemStack getPickaxe() { return pickaxe; }

	public void setPickaxe(ItemStack pickaxe) { this.pickaxe = pickaxe; }
}
