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
import org.bukkit.util.Vector;

import fr.midey.MagicUHC.MagicUHC;
import fr.midey.MagicUHC.Nature;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class EnvoléCéleste implements Listener {

	private MagicUHC main;
	private Integer manaCost = 100;

	public EnvoléCéleste(MagicUHC main) {
		this.main = main;
	}

	@EventHandler
	public void onEnvoléCéleste(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if(it == null) return;
		if(!main.game) return;
		Player p = e.getPlayer();
		if(main.getPlayerNature().get(p).equals(Nature.Air)) {
			if(it.hasItemMeta() 
					&& it.getItemMeta().hasDisplayName() 
					&& it.getItemMeta().getDisplayName().equalsIgnoreCase("§bEnvolé céleste") 
					&& it.getType().equals(Material.NETHER_STAR)) {
				if(main.getPlayerMana().get(p) > manaCost) {
					main.getPlayerMana().replace(p, main.getPlayerMana().get(p) - manaCost);
					for(int boucle = 0; boucle < 3; boucle++) {
						 Bukkit.getScheduler().runTaskLater(main, () -> {
							 Location loc = p.getLocation();
							 p.playSound(loc, Sound.ENDERDRAGON_WINGS, 1f, 1f);
							 displayCloudParticuleOnFeet(loc, p);
							 p.setVelocity(loc.getDirection().multiply(3).setY(1.5));
						 }, boucle * 20);
					 }
				}
				else
					p.sendMessage("Il vous manque §e" + (manaCost - main.getPlayerMana().get(p)) + "§6 mana");
			}
		}
	}
	
	public void displayCloudParticuleOnFeet(Location loc, Player p) {
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		AirCooldown airCooldown = new AirCooldown();
		for (Player players : Bukkit.getOnlinePlayers()) {
			for (int xParticle = -4; xParticle <= 5; xParticle++) {
				for(int zParticle = -4; zParticle <= 5; zParticle++) {
					airCooldown.packetParticuleSender(x + xParticle, y, z + zParticle,EnumParticle.CLOUD, loc);
					if(players == p || airCooldown.playersHit.contains(players)) continue;
					Location locs = players.getLocation();
					Location locParticule = new Location(p.getWorld(), x + xParticle, y, z + zParticle);
					if (locs.distance(locParticule) < 2) {
						Vector v = players.getLocation().getDirection().multiply(-3).setY(1);
						players.setVelocity(v);
						double playersHealth = players.getHealth();
						double damage = 4;
						if(playersHealth - damage <= 0)
							players.setHealth(0);
						else
							players.setHealth(playersHealth - damage);
						airCooldown.playersHit.add(players);
						players.playEffect(EntityEffect.HURT);
					}
				}
			}
		}
	}
}
