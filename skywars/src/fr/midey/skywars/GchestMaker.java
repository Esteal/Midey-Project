package fr.midey.skywars;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class GchestMaker {

	private final Material item;
	private final String Name;
	private final int max;
	private final int min;
	private final double chance;
	private final Map<Enchantment, Integer> enchantments = new HashMap<>();
	
	public GchestMaker(ConfigurationSection section) {
		Material material;
		try {
			material = Material.valueOf(section.getString("material"));
		} catch(Exception e) {
			material = Material.AIR;
		}
		this.item = material;
		this.Name = section.getString("name");
		ConfigurationSection enchantmentSection = section.getConfigurationSection("enchantments");
		if(enchantmentSection != null) {
			for (String enchantmentKey : enchantmentSection.getKeys(false)) {
				Enchantment enchant = Enchantment.getByName(enchantmentKey);
				if (enchant != null) {
					int level = enchantmentSection.getInt(enchantmentKey);
					enchantments.put(enchant, level);
				}
			}
		}
		this.max = section.getInt("maxAmount");
		this.min = section.getInt("minAmount");
		this.chance = section.getDouble("chance");
	}
	
	public boolean shouldFill(Random random) {
		return random.nextDouble() < chance;
	}
	
	public ItemStack make(io.netty.util.internal.ThreadLocalRandom random) {
		int amount = random.nextInt(min, max + 1);
		ItemStack it = new ItemStack(item, amount);
		ItemMeta customIt = it.getItemMeta();
		
		if (Name != null) {
			customIt.setDisplayName(ChatColor.translateAlternateColorCodes('&', Name));
		}
		
		if (!enchantments.isEmpty()) {
			for (Map.Entry<Enchantment, Integer> enchantEntry : enchantments.entrySet()) {
				customIt.addEnchant(enchantEntry.getKey(), enchantEntry.getValue(), true);
			}
		}
		
		it.setItemMeta(customIt);
		return it;
	}
}
