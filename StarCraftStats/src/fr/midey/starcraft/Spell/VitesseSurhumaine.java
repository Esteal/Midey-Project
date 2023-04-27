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
import org.bukkit.scheduler.BukkitTask;

import fr.midey.starcraft.Stats;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class VitesseSurhumaine implements Listener {

	private Stats main;
	
	public VitesseSurhumaine(Stats main) {
		this.main = main;
	}
	
	@EventHandler
	public void onVitesseSurHumaine(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		if (it == null) return;
		if(it.getType() != Material.BARRIER) return;
		Action a = e.getAction();
		String itName = it.getItemMeta().getDisplayName();
		if(itName == null) return;
		if((itName.equalsIgnoreCase("§lVitesse Surhumaine")) && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)) {
			Player p = e.getPlayer();
			if(main.getPlayerGrade().get(p.getUniqueId()).equalsIgnoreCase("Chevalier jedi") || main.getPlayerGrade().get(p.getUniqueId()).equalsIgnoreCase("Maître jedi")) {
				if(main.getCooldownVitesseSurhumaine().containsKey(p)) {
					p.sendMessage("Vous devez attendre §6" + main.getCooldownVitesseSurhumaine().get(p) + "§es§r avant de pouvoir utiliser cette compétence !");;
					return;
				}
				main.getCooldownVitesseSurhumaine().put(p, 60);
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*10, 1), true);
				BukkitTask task = Bukkit.getScheduler().runTaskTimer(main, new Runnable() {
					@Override
					public void run() {
						Location loc = p.getLocation();
						double x = loc.getX();
						double y = loc.getY();
						double z = loc.getZ();
						for(Player player : Bukkit.getOnlinePlayers()) {
							PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.CLOUD, true, (float) x, (float) y + 1, (float) z, 0, 0, 0, 1, 10);
							((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
						}
					}
					
				}, 0, 0);
				
				Bukkit.getScheduler().runTaskLater(main, new Runnable() {
					@Override
					public void run() {
						task.cancel();
					}
				}, 20*10);
			}
		}
	}
}
