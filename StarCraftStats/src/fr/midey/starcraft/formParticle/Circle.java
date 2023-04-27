package fr.midey.starcraft.formParticle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.midey.starcraft.Stats;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class Circle {

	private Location loc;
	private EnumParticle particle;
	private Player p;
	private Stats main;
	private double radius;
	private String spell;
	
	public Circle(Location loc, EnumParticle particle, Player p, Stats main, double radius, String spell) {
		this.loc = loc;
		this.particle = particle;
		this.p = p;
		this.main = main;
		this.radius = radius;
		this.spell = spell;
	}
	
	public void drawCircle() {
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		
		double coeff = 0;
		for(int k = 0; k < 150; k++) {
			double xC = radius* Math.sin(coeff);
			double zC = radius*Math.cos(coeff);
			coeff+=0.05;
			PacketParticuleSender packet = new PacketParticuleSender();
			packet.packetParticuleSender((float) xC + (float) x, (float) y, (float) zC + (float) z, particle);
			for (Player players : Bukkit.getOnlinePlayers()) {
				Location locs = players.getLocation();
				double xP = locs.getX();
				double zP = locs.getZ();
				double radiusReduct = radius - 0.5;
				if(spell.equalsIgnoreCase("Drain de vie")) {
					if((xP <= x + radiusReduct && zP <= radiusReduct + z) 
						&& (xP >= x - radiusReduct && zP >= z - radiusReduct)
						&& (xP >= x - radiusReduct && zP <= z + radiusReduct)
						&& (xP <= x + radiusReduct && zP >= z - radiusReduct)) {
					
						if(players == p) {
							if(!main.getCooldownStunt().containsKey(p)) main.getCooldownStunt().put(p, 6);
							continue;
						}
						if(!main.getCooldownStunt().containsKey(players)) {
							main.getCooldownStunt().put(players, 6);
							int amplifier = 0;
							for(PotionEffect potion : p.getActivePotionEffects()) {
								if(potion.getType().equals(PotionEffectType.HEALTH_BOOST)) {
									amplifier = potion.getAmplifier() + 1;
									p.removePotionEffect(potion.getType());
								}
							}
							double vie = players.getHealthScale() - 4;
							if(vie >= 1) players.setHealthScale(vie);
							main.getCooldownVie().put(players, 180);
							p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 20*60*3,amplifier), true);
						}
					}
				}
				if(spell.equalsIgnoreCase("Bouclier")) {
					if(main.getCooldownVie().containsKey(players)) continue;
					radiusReduct = radius + 1;
					if((xP <= x + radiusReduct && zP <= radiusReduct + z) 
						&& (xP >= x - radiusReduct && zP >= z - radiusReduct)
						&& (xP >= x - radiusReduct && zP <= z + radiusReduct)
						&& (xP <= x + radiusReduct && zP >= z - radiusReduct)
						&& locs.getBlockY() <= y + 2 && locs.getBlockY() >= y) {
						if(players == p) continue;
						if (!main.getCooldownVie().containsKey(players)) main.getCooldownVie().put(players, 0);
						double vie = players.getHealth();
						if (vie - 6 <= 0) players.setHealth(1);
						else players.setHealth(vie - 1);
						players.setVelocity(players.getLocation().getDirection().multiply(-3).setY(1));
					}
				}
			}
		}
	}
}
