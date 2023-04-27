package fr.midey.starcraft.statsManager;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.midey.starcraft.Stats;

public class StatsLoader implements Listener {

	private Stats main;
	
	public StatsLoader(Stats main) {
		this.main = main;
	}

	@EventHandler
	public void loadSpeed(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		if(main.getCooldownEtranglé().containsKey(p)) {
			e.setCancelled(true);
			return;
		}
		
		if(main.getCooldownStunt().containsKey(p)) {
			e.setCancelled(true);
			return;
		}

		if(main.getCooldownVitesseSurhumaine().containsKey(p)) {
			if(main.getCooldownVitesseSurhumaine().get(p) > 20) {
				return;
			}
		}
				
		HashMap<Player, Location> locP = main.getPlayerLoc();
		if (!locP.containsKey(p)) locP.put(p, p.getLocation());
		if((p.getLocation().getBlockX() == locP.get(p).getBlockX()) && (p.getLocation().getBlockZ() == locP.get(p).getBlockZ())) return;
		locP.replace(p, p.getLocation());
		HashMap<Player, Float> speedP = main.getStatsControler().getSpeedPlayer();
		if((speedP.get(p) -0.2)*1000/3 <= 20) {
			speedP.replace(p, speedP.get(p) + 0.0001f);
		}
		else if ((speedP.get(p) -0.2)*1000/3 <= 25) {
			speedP.replace(p, speedP.get(p) + 0.0000001f);
		}
		else if((speedP.get(p) -0.2)*1000/3 <= 30) {
			speedP.replace(p, speedP.get(p) + 0.00000001f);
		}
		else if((speedP.get(p) -0.2)*1000/3 <= 40) {
			speedP.replace(p, speedP.get(p) + 0.000000001f);
		}
		p.setWalkSpeed(speedP.get(p));
	}
	
	@EventHandler
	public void loadResistance(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		double damage = e.getDamage();
		Player p = (Player)e.getEntity();
		if(main.getCooldownStunt().containsKey(p)) e.setCancelled(true);
		HashMap<Player, Float> resistanceP = main.getStatsControler().getResistancePlayer();
		if((resistanceP.get(p) -0.2)*1000/3 <= 30) {
			resistanceP.replace(p, resistanceP.get(p) + 0.0001f);
		}
		else if ((resistanceP.get(p) -0.2)*1000/3 <= 60) {
			resistanceP.replace(p, resistanceP.get(p) + 0.00001f);
		}
		else if((resistanceP.get(p) -0.2)*1000/3 <= 90) {
			resistanceP.replace(p, resistanceP.get(p) + 0.000001f);
		}
		else if((resistanceP.get(p) -0.2)*1000/3 <= 100) {
			resistanceP.replace(p, resistanceP.get(p) + 0.0000001f);
		}
		double reducedDamage = damage - damage * (main.getStatsControler().getResistancePlayer().get(p)/2);
		e.setDamage(reducedDamage);
	}
	
	@EventHandler
	public void loadForce(EntityDamageByEntityEvent e) {
		if(!(e.getDamager() instanceof Player)) return;
		double damage = e.getDamage();
		Player p = (Player) e.getDamager();
		double dDamage = damage + damage * main.getStatsControler().getForcePlayer().get(p);
		HashMap<Player, Float> forceP = main.getStatsControler().getForcePlayer();
		if((forceP.get(p) -0.2)*1000/3 <= 30) {
			forceP.replace(p, forceP.get(p) + 0.0001f);
		}
		else if ((forceP.get(p) -0.2)*1000/3 <= 60) {
			forceP.replace(p, forceP.get(p) + 0.00001f);
		}
		else if((forceP.get(p) -0.2)*1000/3 <= 90) {
			forceP.replace(p, forceP.get(p) + 0.000001f);
		}
		else if((forceP.get(p) -0.2)*1000/3 <= 100) {
			forceP.replace(p, forceP.get(p) + 0.0000001f);
		}
		e.setDamage(dDamage);
	}
}
