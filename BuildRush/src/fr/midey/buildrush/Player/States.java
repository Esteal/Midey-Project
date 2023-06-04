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
	private Player player;
	private ItemStack[] armorContent;
	private ItemsConstructor helmet;
	private ItemsConstructor chestplate;
	private ItemsConstructor leggings;
	private ItemsConstructor boots;
	private ItemsConstructor sword;
	private ItemsConstructor pickaxe;
	
	public States(Player player) {
		this.player = player;
		this.helmet = new ItemsConstructor(Material.LEATHER_HELMET);
		this.chestplate = new ItemsConstructor(Material.LEATHER_CHESTPLATE);
		this.leggings = new ItemsConstructor(Material.LEATHER_LEGGINGS);
		this.boots = new ItemsConstructor(Material.LEATHER_BOOTS);
		this.sword = new ItemsConstructor(Material.IRON_SWORD);
		this.pickaxe = new ItemsConstructor(Material.IRON_PICKAXE);
		
		
		enchantArmor(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		sword.applyEnchant(Enchantment.DAMAGE_ALL, 2);
		pickaxe.applyEnchant(Enchantment.DIG_SPEED, 1);
		
		this.helmet.applyUmbreakable(true);
		this.chestplate.applyUmbreakable(true);
		this.leggings.applyUmbreakable(true);
		this.boots.applyUmbreakable(true);
		this.sword.applyUmbreakable(true);
		
		armorFile();
	}
	
	public void stuffLoad() {
		player.getInventory().setArmorContents(armorContent);
		player.getInventory().setItem(0, sword.getItem());
		player.getInventory().setItem(1, pickaxe.getItem());
		player.getInventory().setItem(2, new ItemStack(Material.GOLDEN_APPLE, 16));
		for(int i = 3; i<9;i++) {
			player.getInventory().setItem(i, new ItemStack(Material.SANDSTONE, 64));
		}
		player.updateInventory();
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

	public ItemsConstructor getSword() { return sword; }

	public void setSword(ItemsConstructor sword) { this.sword = sword; }

	public ItemsConstructor getPickaxe() { return pickaxe; }

	public void setPickaxe(ItemsConstructor pickaxe) { this.pickaxe = pickaxe; }
}
