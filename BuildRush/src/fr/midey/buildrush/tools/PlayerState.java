package fr.midey.buildrush.tools;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PlayerState {

	public static void clearALL(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		clearEffect(player);
	}
	
	public static void clearEffect(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			effect.getType();
			player.removePotionEffect(effect.getType());
		}
	}
}
