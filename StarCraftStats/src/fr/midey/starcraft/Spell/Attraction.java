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

public class Attraction implements Listener {

	private Stats main;
	
	public Attraction(Stats main) {
		this.main = main;
	}

	@EventHandler
	public void onAttraction(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if (it == null) return;
		if(it.getType() != Material.BARRIER) return;
		Action a = e.getAction();
		String itName = it.getItemMeta().getDisplayName();
		if(itName == null) return;
		if((itName.equalsIgnoreCase("§lAttraction")) && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)) {
			Player p = e.getPlayer();
			Location loc = p.getLocation();
			Vector v = p.getLocation().getDirection();
			int target = 0;
			double x = loc.getX();
			double y = loc.getY();
			double z = loc.getZ();
			if(main.getCooldownAttraction().containsKey(p)) {
				p.sendMessage("Vous devez attendre §6" + main.getCooldownAttraction().get(p) + "§es§r avant de pouvoir utiliser cette compétence !");;
				return;
			}
			if (!p.getDisplayName().equalsIgnoreCase("Midey1901")) main.getCooldownAttraction().put(p, 10);
			for (int t = 0; t < 25; t++) {
				double xV = v.getX() + x;
				double yV = v.getY() + y;
				double zV = v.getZ() + z;
				for(Player player : Bukkit.getOnlinePlayers()) {
					PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.ENCHANTMENT_TABLE, true, (float) xV, (float) yV + 1.5f, (float) zV, 0, 0, 0, 0, 5);
					((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
					if (player == p) continue;
					Location locat = new Location(p.getWorld(), xV, yV, zV);
					if(player.getLocation().getBlockZ() == locat.getBlockZ()
							&& player.getLocation().getBlockX() == locat.getBlockX() 
							&& (player.getLocation().getBlockY() >= locat.getBlockY() 
							&& player.getLocation().getBlockY() - 2 <= locat.getBlockY())) {
						target = 1;
						Vector vec = p.getLocation().getDirection().multiply(-3).setY(1);
						player.setVelocity(vec);
					}
				}
				if (target == 1) break; 
				v.multiply(1.1);
			}
		}
	}
}
