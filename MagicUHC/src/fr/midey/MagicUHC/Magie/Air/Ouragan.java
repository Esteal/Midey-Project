package fr.midey.MagicUHC.Magie.Air;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import fr.midey.MagicUHC.MagicUHC;
import fr.midey.MagicUHC.Nature;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class Ouragan implements Listener {
	
	private MagicUHC main;
	private Integer manaCost = 100;

	public Ouragan(MagicUHC main) {
		this.main = main;
	}

	@EventHandler
	public void onOuragan(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if(it == null) return;
		if(!main.game) return;
		Player p = e.getPlayer();
		if(main.getPlayerNature().get(p).equals(Nature.Air)) {
			if(it.hasItemMeta() 
					&& it.getItemMeta().hasDisplayName() 
					&& it.getItemMeta().getDisplayName().equalsIgnoreCase("§bOuragan") 
					&& it.getType().equals(Material.NETHER_STAR)) {
				if(main.getPlayerMana().get(p) > manaCost) {
					main.getPlayerMana().replace(p, main.getPlayerMana().get(p) - manaCost);
					Location playerLocation = p.getEyeLocation();
					Vector direction = playerLocation.getDirection();
					double distanceFromPlayer = 15;
					Location centerLocation = playerLocation.clone().add(direction.multiply(distanceFromPlayer));
					doOuragan(centerLocation);
				}
				else 
					p.sendMessage("Il vous manque §e" + (manaCost - main.getPlayerMana().get(p)) + "§6 mana");
			}
		}
	}
	
	public void doOuragan(Location loc) {
		AirCooldown cd = new AirCooldown();
		cd.timer = 10;
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		cd.radius = 2;
		cd.radPlus = true;
		cd.task = Bukkit.getScheduler().runTaskTimer(main, () -> {
			AirCooldown cd2 = new AirCooldown();
			cd2.multiple = 0;
			cd2.timer2 = -2;
			cd2.task2 = Bukkit.getScheduler().runTaskTimer(main, () -> {
				isInOuragan(loc);
				for(int k = 0; k < 100; k++) {
					double xC = cd.radius*Math.sin(cd2.multiple);
					double zC = cd.radius*Math.cos(cd2.multiple);
					cd2.multiple+=0.1;
					cd2.packetParticuleSender(x + xC, y + cd2.timer2, z + zC, EnumParticle.CLOUD, loc);
				}
				cd2.timer2+=0.3;
				
				if(cd.radius <= 5 && cd.radPlus) {
					if (cd.radius >= 5)
						cd.radPlus = false;
					cd.radius+=0.5;
				}
				else {
					if (cd.radius <= 2)
						cd.radPlus = true;
					else
						cd.radius-=0.5;
				}
				if(cd2.timer2 > 8) 
					cd2.task2.cancel();
			}, 0, 0);
			cd.timer--;
			if(cd.timer <= 0) cd.task.cancel();
		}, 0, 30);
	}
	
	public void isInOuragan(Location ouraganLocation) {
		for(Player players : Bukkit.getOnlinePlayers()) {
			Location playersLocation = players.getLocation();
			if (playersLocation.distance(ouraganLocation) <= 10) {
				players.setVelocity(playersLocation.getDirection().setY(0.8));
				if (playersLocation.distance(ouraganLocation) <= 12)
					players.setVelocity(playersLocation.getDirection().setY(2));
				players.playEffect(EntityEffect.HURT);
				double playersHealth = players.getHealth();
				double damage = 0.5;
				if(playersHealth - damage <= 0)
					players.setHealth(0);
				else
					players.setHealth(playersHealth - damage);
			}
		}
	}
}
