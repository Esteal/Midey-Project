package fr.midey.MagicUHC.Magie.Feu;

import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import fr.midey.MagicUHC.MagicUHC;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class CercleDesEnfers implements Listener {
	private MagicUHC main;

	public CercleDesEnfers(MagicUHC main) {
		this.main = main;
	}
	
	@EventHandler
	public void onCercle(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if(it == null) return;
		//if(!main.game) return;
		Player p = e.getPlayer();
		//if(main.getPlayerNature().get(p).equals(Nature.Terre)) {
			if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() &&it.getItemMeta().getDisplayName().equalsIgnoreCase("§4Cercle des enfers") && it.getType().equals(Material.NETHER_STAR)) {
				Location loc = p.getLocation();
				FireCooldown cd = new FireCooldown(); 
				cd.timer = 10;
				double x = loc.getX();
				double y = loc.getY();
				double z = loc.getZ();
				double radius = 5;
				isInCircle(loc, radius, cd.timer);
				cd.task = Bukkit.getScheduler().runTaskTimer(main, () -> {
					FireCooldown cd2 = new FireCooldown();
					cd2.multiple = 0;
					cd2.timer2 = -2;
					cd2.task2 = Bukkit.getScheduler().runTaskTimer(main, () -> {
						for(int k = 0; k < 100; k++) {
							double xC = radius*Math.sin(cd2.multiple);
							double zC = radius*Math.cos(cd2.multiple);
							cd2.multiple+=0.1;
							cd2.packetParticuleSender(x + xC, y + cd2.timer2, z + zC, EnumParticle.FLAME, loc);
						}
						cd2.timer2+=0.3;
						if(cd2.timer2 > 8) cd2.task2.cancel();
					}, 0, 0);
					cd.timer--;
					if(cd.timer <= 0) cd.task.cancel();
				}, 0, 30);
			}
	}
	
	public void isInCircle(Location loc, double radius, double timer) {
		FireCooldown cd = new FireCooldown();
		cd.timer = timer * 30;
		cd.task = Bukkit.getScheduler().runTaskTimer(main, () -> {
			cd.multiple = 0;
			for(double yC = -4; yC < 8; yC+=0.3) {
				for(int k = 0; k < 100; k++) {
					double xC = radius*Math.sin(cd.multiple);
					double zC = radius*Math.cos(cd.multiple);
					cd.multiple+=0.1;
					Location circleLoc = new Location(loc.getWorld(), loc.getX() + xC, loc.getY() + yC, loc.getZ() + zC);
					Collection<Entity> nearbyEntities = circleLoc.getWorld().getNearbyEntities(circleLoc, xC, yC, zC);
					for(Player players : Bukkit.getOnlinePlayers()) {
						if(!(players.getLocation().distance(circleLoc) > 0.5)) {
							double repulsionStrength = -1.5;
							Vector targetDirection = circleLoc.toVector().subtract(players.getLocation().toVector()).normalize();
							Vector repulsionVector = targetDirection.multiply(repulsionStrength);
							players.setVelocity(repulsionVector);
							players.setFireTicks(20*10);
						}
					}
				}
			}
			cd.timer--;
			if(cd.timer <= 0) cd.task.cancel();
		}, 0, 1);
	}
}
