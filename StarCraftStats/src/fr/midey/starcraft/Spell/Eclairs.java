package fr.midey.starcraft.Spell;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.midey.starcraft.Stats;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class Eclairs implements Listener {

	private Stats main;
	
	public Eclairs(Stats main) {
		this.main = main;
	}
	
	@EventHandler
	public void onEclairs(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if (it == null) return;
		if(it.getType() != Material.BARRIER) return;
		Action a = e.getAction();
		String itName = it.getItemMeta().getDisplayName();
		if(itName == null) return;
		if((itName.equalsIgnoreCase("§lÉclairs")) && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)) {
			
			Player p = e.getPlayer();
			if(main.getPlayerGrade().get(p.getUniqueId()).equalsIgnoreCase("Chevalier sith") || main.getPlayerGrade().get(p.getUniqueId()).equalsIgnoreCase("Seigneur sith")) {
				if(main.getCooldownEclairs().containsKey(p)) {
					p.sendMessage("Vous devez attendre §6" + main.getCooldownEclairs().get(p) + "§es§r avant de pouvoir utiliser cette compétence !");;
					return;
				}
				if (!p.getDisplayName().equalsIgnoreCase("Arzodiacc")) main.getCooldownEclairs().put(p, 60);
				Location pLoc = p.getLocation();
				int x = pLoc.getBlockX();
				int y = pLoc.getBlockY() + 1;
				int z = pLoc.getBlockZ();
				for (Player players : Bukkit.getOnlinePlayers()) {
					for (int xParticle = -4; xParticle <= 5; xParticle++) {
						for(int zParticle = -4; zParticle <= 5; zParticle++) {
							if (!(xParticle == 0 || xParticle == 1 || zParticle == 0 || zParticle == 1)) continue;
							PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.CRIT_MAGIC, true, (float) x + xParticle, (float) y, (float) z + zParticle, 0, 0, 0, 0, 1);
							((CraftPlayer)players).getHandle().playerConnection.sendPacket(packet);
							stackParticle((float) x + xParticle,(float) z + zParticle ,(float) y, EnumParticle.CRIT_MAGIC);
							if(players == p) continue;
							Location locs = players.getLocation();
							if ((locs.getBlockX() == x + xParticle) 
									&& (locs.getBlockZ() == z + zParticle) 
									&& (locs.getBlockY() <= y + 1 && locs.getBlockY() >= y - 1)) {
								players.setFireTicks(20*10);
								players.sendMessage("touché");
							}
						}
					}
				}
			}
		}
	}
	
	public void stackParticle(Float x, Float z, Float y, EnumParticle particle) {
		for(float t = -0.5f; t<0.5; t+=0.10) {
			for (Player players: Bukkit.getOnlinePlayers()) {
				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) x + t, (float) y, (float) z + t, 0, 0, 0, 0, 1);
				((CraftPlayer)players).getHandle().playerConnection.sendPacket(packet);
			}
		}
		for(float t = -0.5f; t<0.5; t+=0.10) {
			for (Player players: Bukkit.getOnlinePlayers()) {
				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) x, (float) y, (float) z + t, 0, 0, 0, 0, 1);
				((CraftPlayer)players).getHandle().playerConnection.sendPacket(packet);
			}
		}
		for(float t = -0.5f; t<0.5; t+=0.10) {
			for (Player players: Bukkit.getOnlinePlayers()) {
				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) x + t, (float) y, (float) z, 0, 0, 0, 0, 1);
				((CraftPlayer)players).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}
}
