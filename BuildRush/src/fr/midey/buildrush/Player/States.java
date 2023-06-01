package fr.midey.buildrush.Player;

import org.bukkit.Color;
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
	private ItemsConstructor helmet;
	private ItemsConstructor chestplate;
	private ItemsConstructor leggings;
	private ItemsConstructor boots;
	private ItemStack sword;
	private ItemStack pickaxe;
	
	public States() {
		this.helmet = new ItemsConstructor(Material.LEATHER_HELMET);
		this.chestplate = new ItemsConstructor(Material.LEATHER_CHESTPLATE);
		this.leggings = new ItemsConstructor(Material.LEATHER_LEGGINGS);
		this.boots = new ItemsConstructor(Material.LEATHER_BOOTS);
		
		enchantArmor(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
	
		this.helmet.applyUmbreakable(true);
		this.chestplate.applyUmbreakable(true);
		this.leggings.applyUmbreakable(true);
		this.boots.applyUmbreakable(true);
		
		armorFile();
	}
	
	public void armorFile() {
		this.armorContent = new ItemStack[4];
		this.armorContent[3] = helmet.getItem();
		this.armorContent[2] = chestplate.getItem();
		this.armorContent[1] = leggings.getItem();
		this.armorContent[0] = boots.getItem();
	}
	
	public void enchantArmor(Enchantment enchant, int level) {
		this.helmet.applyEnchant(enchant, level);
		this.chestplate.applyEnchant(enchant, level);
		this.leggings.applyEnchant(enchant, level);
		this.boots.applyEnchant(enchant, level);
		armorFile();
	}
	
	public void colorArmor(Color color) {
		this.helmet.colorArmor(color);
		this.chestplate.colorArmor(color);
		this.leggings.colorArmor(color);
		this.boots.colorArmor(color);
		armorFile();
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
