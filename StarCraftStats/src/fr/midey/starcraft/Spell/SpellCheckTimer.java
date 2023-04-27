package fr.midey.starcraft.Spell;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.starcraft.Stats;

public class SpellCheckTimer extends BukkitRunnable{

	private Stats main;
	
	
	public SpellCheckTimer(Stats main) {
		this.main = main;
	}

	@Override
	public void run() {
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			
			if(main.getCooldownAttraction().containsKey(p)) {
				Integer cd = main.getCooldownAttraction().get(p);
				main.getCooldownAttraction().replace(p, cd - 1);
				if(cd <= 0 ) main.getCooldownAttraction().remove(p);
			}
			
			if(main.getCooldownRepulsion().containsKey(p)) {
				Integer cd = main.getCooldownRepulsion().get(p);
				main.getCooldownRepulsion().replace(p, cd - 1);
				if(cd <= 0 ) main.getCooldownRepulsion().remove(p);
			}
			
			if(main.getCooldownVitesseSurhumaine().containsKey(p)) {
				Integer cd = main.getCooldownVitesseSurhumaine().get(p);
				main.getCooldownVitesseSurhumaine().replace(p, cd - 1);
				if(cd <= 0 ) main.getCooldownVitesseSurhumaine().remove(p);
			}
			
			if(main.getCooldownEtranglement().containsKey(p)) {
				Integer cd = main.getCooldownEtranglement().get(p);
				main.getCooldownEtranglement().replace(p, cd - 1);
				if(cd <= 0 ) main.getCooldownEtranglement().remove(p);
			}
			if(main.getCooldownEtranglé().containsKey(p)) {
				Integer cd = main.getCooldownEtranglé().get(p);
				main.getCooldownEtranglé().replace(p, cd - 1);
				if(cd <= 0 ) main.getCooldownEtranglé().remove(p);
			}
			
			if(main.getCooldownEclairs().containsKey(p)) {
				Integer cd = main.getCooldownEclairs().get(p);
				main.getCooldownEclairs().replace(p, cd - 1);
				if(cd <= 0 ) main.getCooldownEclairs().remove(p);
			}
			
			if(main.getCooldownDrainDeVie().containsKey(p)) {
				Integer cd = main.getCooldownDrainDeVie().get(p);
				main.getCooldownDrainDeVie().replace(p, cd - 1);
				if(cd <= 0 ) main.getCooldownDrainDeVie().remove(p);
			}
			
			if(main.getCooldownStunt().containsKey(p)) {
				Integer cd = main.getCooldownStunt().get(p);
				main.getCooldownStunt().replace(p, cd - 1);
				if(cd <= 0 ) {
					main.getCooldownStunt().remove(p);
					p.setWalkSpeed(main.getStatsControler().getSpeedPlayer().get(p));
				}
			}
			
			if(main.getCooldownVie().containsKey(p)) {
				Integer cd = main.getCooldownVie().get(p);
				main.getCooldownVie().replace(p, cd - 1);
				if(cd <= 0 ) {
					main.getCooldownVie().remove(p);
					p.setHealthScale(20);
				}
			}
			
			if(main.getCooldownBouclier().containsKey(p)) {
				Integer cd = main.getCooldownBouclier().get(p);
				main.getCooldownBouclier().replace(p, cd - 1);
				if(cd <= 0 ) main.getCooldownBouclier().remove(p);
			}
			
			if(main.getCooldownSabre().containsKey(p)) {
				Integer cd = main.getCooldownSabre().get(p);
				main.getCooldownSabre().replace(p, cd - 1);
				if(cd <= 0 ) main.getCooldownSabre().remove(p);
			}
		}
		
	}

}
