package fr.midey.uhcmeetup.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.uhcmeetup.GState;
import fr.midey.uhcmeetup.GStuff;
import fr.midey.uhcmeetup.Gmain;

public class GAutoStart extends BukkitRunnable{

	private Gmain main;
	public GAutoStart(Gmain main) {
		this.main = main;
	}

	private int timer = 10;
	
	@Override
	public void run() {
		
		for(Player player : main.getPlayers()) {
			player.setLevel(timer);
		}
		
		if(timer == 10 || (timer <= 5 && timer > 0) || timer == 60 || timer == 15 || timer == 30 || timer == 45 || timer == 90 || timer == 120 || timer == 150 || timer == 180) {
			Bukkit.broadcastMessage("Lancement de la partie dans §e" + timer + "§6s");
		}
		if (timer == 0) {
			Bukkit.broadcastMessage("Lancement de la partie !");
			main.setState(GState.PLAYING);
			
			for (int i = 0; i < main.getPlayers().size(); i++) {
				Player player = main.getPlayers().get(i);
				World world = Bukkit.getWorld("world");
				Location loc = new Location(world, Math.random()*201-100, 100, Math.random()*201 - 100);
				player.teleport(loc);
				player.setGameMode(GameMode.SURVIVAL);
				player.setFoodLevel(20);
				player.setHealth(20.0);
				player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*5, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*5, 254));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 20*99999999, 254));
				Gmain.clearALL(player);
				player.getInventory().setBoots(GStuff.armor(Material.IRON_BOOTS, Material.DIAMOND_BOOTS));
				player.getInventory().setLeggings(GStuff.armor(Material.IRON_LEGGINGS, Material.DIAMOND_LEGGINGS));
				player.getInventory().setChestplate(GStuff.armor(Material.IRON_CHESTPLATE, Material.DIAMOND_CHESTPLATE));
				player.getInventory().setHelmet(GStuff.armor(Material.IRON_HELMET, Material.DIAMOND_HELMET));
				player.getInventory().addItem(GStuff.armor(Material.IRON_SWORD, Material.DIAMOND_SWORD));
				player.getInventory().addItem(GStuff.armor(Material.BOW, Material.BOW));
				player.getInventory().addItem(GStuff.bonus());
				player.getInventory().addItem(new ItemStack(Material.LOG, 128));
				player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
				player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
				player.getInventory().addItem(GStuff.itAlea(Material.ARROW, 48, 12));
				player.getInventory().addItem(GStuff.itAlea(Material.GOLDEN_APPLE, 14, 4));
				player.setLevel(10);
				player.updateInventory();
			}
			GBorder timeBorder = new GBorder(main);
			timeBorder.runTaskTimer(main, 0, 20);
			cancel();
		}
		timer--;
	}
}
