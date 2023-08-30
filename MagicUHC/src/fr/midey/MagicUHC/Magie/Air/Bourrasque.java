package fr.midey.MagicUHC.Magie.Air;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import fr.midey.MagicUHC.MagicUHC;
import fr.midey.MagicUHC.Nature;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class Bourrasque implements Listener {
	
	private MagicUHC main;
	private Integer manaCost = 100;
	public Bourrasque(MagicUHC main) {
		this.main = main;
	}

	@EventHandler
	public void onBourrasque(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if(it == null) return;
		if(!main.game) return;
		Player p = e.getPlayer();
		if(main.getPlayerNature().get(p).equals(Nature.Air)) {
			if(it.hasItemMeta() 
					&& it.getItemMeta().hasDisplayName() 
					&& it.getItemMeta().getDisplayName().equalsIgnoreCase("§bBourrasque") 
					&& it.getType().equals(Material.NETHER_STAR)) {
				if(main.getPlayerMana().get(p) > manaCost) {
					main.getPlayerMana().replace(p, main.getPlayerMana().get(p) - manaCost);
					AirCooldown ac = new AirCooldown();
					ac.timer = 200;
					ac.task = Bukkit.getScheduler().runTaskTimer(main, () -> {
						Location eyesLoc = p.getEyeLocation();
						boolean hitten = createCircle(eyesLoc, 1, EnumParticle.CLOUD);
						if(hitten || ac.timer < 0) ac.task.cancel();
						ac.timer--;
					}, 0, 0);
				}
				else 
					p.sendMessage("Il vous manque §e" + (manaCost - main.getPlayerMana().get(p)) + "§6 mana");
			}
		}
	}

    public static boolean createCircle(Location loc, int radius, EnumParticle particle) {
        Location playerLocation = loc;
        boolean hit = false;
        Vector direction = playerLocation.getDirection().normalize();
        AirCooldown ac = new AirCooldown();
        // Nous allons placer le cercle à une distance devant le joueur (par exemple 2 blocs)
        double distanceFromPlayer = 4;
        Location centerLocation = playerLocation.clone().add(direction.multiply(distanceFromPlayer));
        
        for(Player players : Bukkit.getOnlinePlayers()) {
        	if (players.getLocation().distance(centerLocation) < 3) {
        			hit = true;
        			players.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*3, 1));
        			players.playEffect(EntityEffect.HURT);
        			double playersHealth = players.getHealth();
					double damage = 8;
					if(playersHealth - damage <= 0)
						players.setHealth(0);
					else
						players.setHealth(playersHealth - damage);
        	}
        }
        
        for (double y = -radius; y <= radius; y+=0.3) {
            double yRatio = (double) y / radius;
            int xzRadius = (int) Math.round(radius * Math.sqrt(1 - yRatio * yRatio));

            for (double x = -xzRadius; x <= xzRadius; x+=0.3) {
                for (double z = -xzRadius; z <= xzRadius; z+=0.3) {
                    if (x * x + y * y + z * z <= radius * radius) {
                        Location blockLocation = centerLocation.clone().add(x, y, z);
                        if(blockLocation.getBlock().getType() == Material.AIR && hit == false)
                        	ac.packetParticuleSender(particle, blockLocation);
                        else {
                        	hit = true;
                        	ac.packetParticuleSender(EnumParticle.EXPLOSION_LARGE, blockLocation);
                        	loc.getWorld().playSound(blockLocation, Sound.EXPLODE, 1f, 1f);
                        }
                    }
                }
            }
        }
        return hit;
    }
}
