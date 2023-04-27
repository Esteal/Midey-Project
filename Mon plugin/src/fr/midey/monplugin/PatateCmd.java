package fr.midey.monplugin;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PatateCmd implements Listener {
	
	
	public void batonn() {
		int dura = 10;
		ItemStack baton = new ItemStack(Material.STICK);
		ItemMeta customB = baton.getItemMeta();
		customB.setDisplayName("§4Baton du magicien");
		customB.setLore(Arrays.asList("§fBaguette d'un ancien sorcier", "§fseul les plus forts pourront s'en servir", dura + "/50"));
		customB.addEnchant(Enchantment.KNOCKBACK, 1, true);
		baton.setItemMeta(customB);
		
	}
	
	@EventHandler 
	public void onInteract(PlayerInteractEvent event){
		
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack it = event.getItem();
		
		if(it == null) return;
		
		if(it.getType() == Material.CARROT_ITEM && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§bCarrote de speed")) {
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK ) {
				player.setFoodLevel(19);
				for (PotionEffect effect : player.getActivePotionEffects()) {
					effect.getType();
					player.removePotionEffect(PotionEffectType.SPEED);
				}
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 480, 1));
				it.setAmount(it.getAmount() -1);
				player.getInventory().setItemInHand(it);
				
			}
		}	
		if(it.getType() == Material.BAKED_POTATO && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§4Patate de force")) {
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK ) {
				player.setFoodLevel(19);
				for (PotionEffect effect : player.getActivePotionEffects()) {
					effect.getType();
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				}
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 480, 0));
				it.setAmount(it.getAmount() -1);
				player.getInventory().setItemInHand(it);
								
			}
		}
		if(it.getType() == Material.STICK && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("§4Baton du magicien")) {
			if(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK ) {
				
				Location ploc = player.getLocation();
				Location lancement = new Location(player.getWorld(), ploc.getX(), ploc.getY() + 3, ploc.getZ(), ploc.getPitch(), ploc.getPitch());
				player.getWorld().spawnEntity(lancement, EntityType.ARROW);
			}
		}

		if(it.getType()== Material.DIAMOND_SWORD && it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().equalsIgnoreCase("epee de test")) {
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				
				player.setWalkSpeed((float) 5.1);
				}
			}
		}
	}
}
