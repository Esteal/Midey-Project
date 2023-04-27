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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import fr.midey.starcraft.Stats;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class Etranglement implements Listener {

	private Stats main;
	
	public Etranglement(Stats main) {
		this.main = main;
	}
	
	@EventHandler
	public void onEtranglement(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if (it == null) return;
		if(it.getType() != Material.BARRIER) return;
		Action a = e.getAction();
		String itName = it.getItemMeta().getDisplayName();
		if(itName == null) return;
		if((itName.equalsIgnoreCase("§lÉtranglement")) && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)) {
			
			Player p = e.getPlayer();
			if(main.getPlayerGrade().get(p.getUniqueId()).equalsIgnoreCase("Chevalier sith") || main.getPlayerGrade().get(p.getUniqueId()).equalsIgnoreCase("Seigneur sith")) {
				if(main.getCooldownEtranglement().containsKey(p)) {
					p.sendMessage("Vous devez attendre §6" + main.getCooldownEtranglement().get(p) + "§es§r avant de pouvoir utiliser cette compétence !");;
					return;
				}
				main.getCooldownEtranglement().put(p, 60);
				Location loc = p.getLocation();
				Vector v = p.getLocation().getDirection();
				int target = 0;
				double x = loc.getX();
				double y = loc.getY();
				double z = loc.getZ();
				for (int t = 0; t < 25; t++) {
					double xV = v.getX() + x;
					double yV = v.getY() + y;
					double zV = v.getZ() + z;
					for(Player player : Bukkit.getOnlinePlayers()) {
						PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.CRIT, true, (float) xV, (float) yV + 1.5f, (float) zV, 0, 0, 0, 0, 1);
						((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
						if (player == p) continue;
						Location locat = new Location(p.getWorld(), xV, yV, zV);
						if(player.getLocation().getBlockZ() == locat.getBlockZ()
								&& player.getLocation().getBlockX() == locat.getBlockX() 
								&& (player.getLocation().getBlockY() >= locat.getBlockY() 
								&& player.getLocation().getBlockY() - 2 <= locat.getBlockY())) {
							target = 1;
							main.getCooldownEtranglé().put(player, 5);
							main.getCooldownEtranglement().put(p, 60);
							player.setVelocity(player.getLocation().getDirection().multiply(-1).setY(1));
							player.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 30, 0));
						}
					}
					if (target == 1) break; 
					v.multiply(1.1);
				}
			}
		}
	}
}
