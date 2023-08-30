package fr.midey.onepiececraft.Region;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.midey.onepiececraft.OnePieceCraft;

public class ClaimManager implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) { new GamePlayer(e.getPlayer().getName()); }
	
	@EventHandler
	public void onBLockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		for (int x = 0; x < OnePieceCraft.getMAIN().regions.size(); x++) {
			
			if (OnePieceCraft.getMAIN().regions.get(x).isInArea(e.getBlock().getLocation())) {
				if (OnePieceCraft.getMAIN().regions.get(x).getName().equals(p.getName())) continue;
				e.setCancelled(true);
				p.sendMessage("§cZone protégée.");
				return;
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		
		for (int x = 0; x < OnePieceCraft.getMAIN().regions.size(); x++) {
			if(OnePieceCraft.getMAIN().regions.get(x).isInArea(e.getBlock().getLocation())) {
				if (OnePieceCraft.getMAIN().regions.get(x).getName().equals(p.getName())) continue;
				e.setCancelled(true);
				p.sendMessage("§cZone protégée.");
				return;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void OnInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			for (int x = 0; x < OnePieceCraft.getMAIN().regions.size(); x++) {
				if(OnePieceCraft.getMAIN().regions.get(x).isInArea(e.getClickedBlock().getLocation())) {
					if (OnePieceCraft.getMAIN().regions.get(x).getName().equals(p.getName())) continue;
						e.setCancelled(true);
						p.sendMessage("§cZone protégée.");
				}	
			}
		}
		
		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			
			if(p.getItemInHand().getType() == Material.DIAMOND_HOE) {
				e.setCancelled(true);
				GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());
				
				//Défini le premier point du claim
				if(gp.getPos1() == null) {
					gp.setPos1(e.getClickedBlock().getLocation());
					gp.getPos1().setY(-65);					
					p.sendMessage("§aPosition 1 de votre claim définie.");
					
					//Supprime le premier point si il n'y a pas eu de second point séléctionné au bout de 5mn
					Bukkit.getScheduler().runTaskLater(OnePieceCraft.getMAIN(), new Runnable() {
						@Override
						public void run() {
							if (gp.getPos1() != null && gp.getPos2() == null) {
								gp.setPos1(null);
								gp.setPos2(null);
								p.sendMessage("§aSéléction des claims annulé.");
							}
						}
					}, 20*60);
					return;
				}
				
				//Défini le second point du claim
				if(gp.getPos1() != null && gp.getPos2() == null) {
					
					gp.setPos2(e.getClickedBlock().getLocation());
					gp.getPos2().setY(321);

					//Empêche de claim si les 2 points ne sont pas dans le même monde
					if (!gp.getPos1().getWorld().equals(gp.getPos2().getWorld())) {
						p.sendMessage("§cVous devez être dans le même monde que le premier point du claim.");
						gp.setPos1(null);
						gp.setPos2(null);
						return;
					}
					
					//Créer une région
					RegionManager region = new RegionManager(gp.getPos1(), gp.getPos2(), p.getName());
					
					for (int i = 0; i < OnePieceCraft.MAIN.regions.size(); i++) {
						RegionManager regionget = OnePieceCraft.getMAIN().regions.get(i);
						
						if(regionget.isInArea(region.maxLoc) || regionget.isInArea(region.minLoc) || regionget.isInArea(region.getMiddle())) {
							p.sendMessage("§cVous ne pouvez pas claim une zone appartenant déjà à un pirate");
							gp.setPos1(null);
							gp.setPos2(null);
							return;
						}
					}
					
					gp.setCountClaims(gp.getCountClaims() + 1);	
					
					gp.getPos1().setY(-65);
					gp.getPos2().setY(321);
					
					String[] loc = new String[] {"" + gp.getPos1().getX(), "" + gp.getPos1().getY(), "" + gp.getPos1().getZ(), 
												 "" + gp.getPos2().getX(), "" + gp.getPos2().getY(), "" + gp.getPos2().getZ(),
												 p.getLocation().getWorld().getName(), p.getName()};
					
					OnePieceCraft.getMAIN().config.get().set(p.getUniqueId().toString() + "_" + gp.getCountClaims(), loc);
					OnePieceCraft.getMAIN().config.save();
					
					OnePieceCraft.getMAIN().regions.add(region);
					
					p.sendMessage("§aPosition 2 de votre claim définie.");
					p.sendMessage("§aVous venez de claim une zone.");

					gp.setPos1(null);
					gp.setPos2(null);
				}
			}
		}
		
	}
}
