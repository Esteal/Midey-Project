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
import org.bukkit.util.Vector;

import fr.midey.starcraft.Stats;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class Repulsion implements Listener {

	private Stats main;
	
	public Repulsion(Stats main) {
		this.main = main;
	}

	@EventHandler
	public void onRepulsion(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if (it == null) return;
		if(it.getType() != Material.BARRIER) return;
		Action a = e.getAction();
		String itName = it.getItemMeta().getDisplayName();
		if(itName == null) return;
		if((itName.equalsIgnoreCase("§lRépulsion")) && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)) {
			Player p = e.getPlayer();
			Location pLoc = p.getLocation();
			int x = pLoc.getBlockX();
			int y = pLoc.getBlockY();
			int z = pLoc.getBlockZ();
			if(main.getCooldownRepulsion().containsKey(p)) {
				p.sendMessage("Vous devez attendre §6" + main.getCooldownRepulsion().get(p) + "§es§r avant de pouvoir utiliser cette compétence !");;
				return;
			}
			if (!p.getDisplayName().equalsIgnoreCase("Midey1901")) main.getCooldownRepulsion().put(p, 10);
			for (Player players : Bukkit.getOnlinePlayers()) {
				for (int xParticle = -4; xParticle <= 5; xParticle++) {
					for(int zParticle = -4; zParticle <= 5; zParticle++) {
						PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.CLOUD, true, (float) x + xParticle, (float) y, (float) z + zParticle, 0, 0, 0, 0, 1);
						((CraftPlayer)players).getHandle().playerConnection.sendPacket(packet);
						if(players == p) continue;
						Location locs = players.getLocation();
						if ((locs.getBlockX() == x + xParticle) 
								&& (locs.getBlockZ() == z + zParticle) 
								&& (locs.getBlockY() <= y + 2 && locs.getBlockY() >= y)) {
							Vector v = players.getLocation().getDirection().multiply(-3).setY(1);
							players.setVelocity(v);
						}
					}
				}
			}
		}
	}
}
