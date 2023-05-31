package fr.midey.zilconis.Spell;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import fr.midey.zilconis.Zilconis;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class Spell1 implements Listener {

	
	private Zilconis main;

	public Spell1(Zilconis main) {
		this.main = main;
	}

	@EventHandler
	public void onSpell1(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if(it == null) return;
		
		if(it.getType() == Material.STICK) {
			Player p = e.getPlayer();
			EnumParticle particle = EnumParticle.REDSTONE;
			Location targetLocation = spawnBlock(p, 15);
			Cooldown cd = new Cooldown();
			cd.loc = targetLocation;
			cd.timer = 4;
			cd.task = Bukkit.getScheduler().runTaskTimer(main, () -> {
				spawnParticleCircle(cd.loc, 2, EnumParticle.CLOUD);
				cd.timer--;
				if(cd.timer <= 0) cd.task.cancel();
			}, 0, 10);
			Location circleLoc = targetLocation.clone();
			circleLoc.setY(circleLoc.getY() + 20);
			int radius = 3;
			cd.timer2 = 20;
			cd.task2 = Bukkit.getScheduler().runTaskTimer(main, () -> {
					spawnParticleHalfCircle1(circleLoc, radius, particle);
					spawnParticleHalfCircle2(circleLoc, radius, particle);
					spawnParticleHalfCircle3(circleLoc, radius, particle);
					spawnParticleHalfCircle4(circleLoc, radius, particle);
					cd.timer2--;
					if(cd.timer2<=0) cd.task2.cancel();
			}, 20, 2);
			Location line = circleLoc.clone();
			line.setY(line.getY() + radius);
			lineDown(line, EnumParticle.SPELL_MOB);
		}
	}
	
	public Location spawnBlock(Player player, int distance) {
	    Location playerLocation = player.getLocation();
	    Vector direction = playerLocation.getDirection().normalize();
	    Location spawnLocation = playerLocation.add(direction.multiply(distance));
	    while(spawnLocation.getBlock().getType() == Material.AIR) {
	    	spawnLocation.setY(spawnLocation.getBlockY() - 1);
	    }
	    while(spawnLocation.getBlock().getType() != Material.AIR) {
    		spawnLocation.setY(spawnLocation.getBlockY() + 1);
	    }
		spawnLocation.setY(spawnLocation.getBlockY() + 0.1);
	    return spawnLocation;
	}
	
	public void spawnParticleCircle(Location center, double radius, EnumParticle particle) {
	    World world = center.getWorld();
	    for (double theta = 0; theta < 2 * Math.PI; theta += Math.PI / 16) {
	        double x = center.getX() + radius * Math.cos(theta);
	        double z = center.getZ() + radius * Math.sin(theta);
	        Location particleLocation = new Location(world, x, center.getY(), z);
	        packetParticuleSender(particle, particleLocation);
	    }
	}
	
	public void spawnCircleInSquare(Location center, double radiusX, double radiusZ, int height, EnumParticle particle) {
	    World world = center.getWorld();
	    double increment = 2 * Math.PI / 100; // Ajustez le nombre pour changer la densité des particules

	    for (int i = 0; i < height; i++) {
	        double y = center.getY() + i;
	        for (double theta = 0; theta < 2 * Math.PI; theta += increment) {
	            double x = center.getX() + radiusX * Math.cos(theta);
	            double z = center.getZ() + radiusZ * Math.sin(theta);
	            Location particleLocation = new Location(world, x, y, z);
	            packetParticuleSender(particle, particleLocation);
	        }
	    }

	    double y = center.getY() + height / 2;
	    for (double theta = 0; theta < 2 * Math.PI; theta += increment) {
	        double x = center.getX() + radiusX * Math.cos(theta);
	        double z = center.getZ() + radiusZ * Math.sin(theta);
	        Location particleLocation = new Location(world, x, y, z);
            packetParticuleSender(particle, particleLocation);
	    }

	    double x1 = center.getX() - radiusX;
	    double x2 = center.getX() + radiusX;
	    for (double z = center.getZ() - radiusZ; z <= center.getZ() + radiusZ; z += increment) {
	        Location particleLocation1 = new Location(world, x1, y, z);
	        Location particleLocation2 = new Location(world, x2, y, z);
            packetParticuleSender(particle, particleLocation1);
            packetParticuleSender(particle, particleLocation2);
	    }

	    double z1 = center.getZ() - radiusZ;
	    double z2 = center.getZ() + radiusZ;
	    for (double x = center.getX() - radiusX; x <= center.getX() + radiusX; x += increment) {
	        Location particleLocation1 = new Location(world, x, y, z1);
	        Location particleLocation2 = new Location(world, x, y, z2);
	        packetParticuleSender(particle, particleLocation2);
	        packetParticuleSender(particle, particleLocation1);
	    }
	}
	
	public void spawnParticleHalfCircle1(Location center, double radius, EnumParticle particle) {
	    World world = center.getWorld();
	    for (double theta = 0; theta <= Math.PI; theta += Math.PI / 32) {
	        double x = center.getX();
	        double y = center.getY() + radius * Math.cos(theta);
	        double z = center.getZ() + radius * Math.sin(theta);
	        Location particleLocation = new Location(world, x, y, z);
	        packetParticuleSender(particle, particleLocation);
	    }
	}
	
	public void spawnParticleHalfCircle2(Location center, double radius, EnumParticle particle) {
	    World world = center.getWorld();

	    for (double theta = 0; theta <= Math.PI; theta += Math.PI / 32) {
	        double x = center.getX() + radius * Math.sin(theta);
	        double y = center.getY() + radius * Math.cos(theta);
	        double z = center.getZ();
	        Location particleLocation = new Location(world, x, y, z);
	        packetParticuleSender(particle, particleLocation);
	    }
	}
	
	public void spawnParticleHalfCircle3(Location center, double radius, EnumParticle particle) {
	    World world = center.getWorld();

	    for (double theta = 0; theta >= -Math.PI; theta -= Math.PI / 32) {
	        double x = center.getX();
	        double y = center.getY() + radius * Math.cos(theta);
	        double z = center.getZ() + radius * Math.sin(theta);
	        Location particleLocation = new Location(world, x, y, z);
	        packetParticuleSender(particle, particleLocation);
	    }
	}
	
	public void spawnParticleHalfCircle4(Location center, double radius, EnumParticle particle) {
	    World world = center.getWorld();

	    for (double theta = 0; theta >= -Math.PI; theta -= Math.PI / 32) {
	        double x = center.getX() + radius * Math.sin(theta);
	        double y = center.getY() + radius * Math.cos(theta);
	        double z = center.getZ();
	        Location particleLocation = new Location(world, x, y, z);
	        packetParticuleSender(particle, particleLocation, 0.1f, 1f, 0f, 1f, 0);
	    }
	}
	
	public void lineDown(Location center, EnumParticle particle) {
		Cooldown cd = new Cooldown();
		cd.timer = 50;
		center.setY(center.getY());
		cd.task = Bukkit.getScheduler().runTaskTimer(main, () -> {
			for(int i = 10; i>=0; i--) {
				center.setY(center.getY()-0.2);
				packetParticuleSender(particle, center, 1, -1, 1, 10, 0);	
				if(center.getBlock().getType() != Material.AIR)
				{
					packetParticuleSender(EnumParticle.EXPLOSION_HUGE, center);
					cd.task.cancel();
				}
			}
			cd.timer--;
			if(cd.timer <= 0) {
				packetParticuleSender(EnumParticle.EXPLOSION_HUGE, center);
				cd.task.cancel();
			}
		}, 25, 0);
	}
	
	public void packetParticuleSender(EnumParticle particle, Location loc) {
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.getLocation().distance(loc) > 30) continue;
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) x, (float) y, (float) z, 0, 0, 0, 0, 1);
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	public void packetParticuleSender(EnumParticle particle, Location loc, float r, float g, float b, float speed, int amount) {
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.getLocation().distance(loc) > 30) continue;
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) x, (float) y, (float) z, r, g, b, speed, amount);
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
		}
	}
}
