package fr.midey.OnePieceCraftSkills.Skills.SkillLowRank;

import org.bukkit.event.Listener;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;

public class PasDeLune implements Listener {

//    private OnePieceCraftSkills plugin;

    public PasDeLune(OnePieceCraftSkills plugin) {
       // this.plugin = plugin;
    }
    /*
    @SuppressWarnings("deprecation")
	@EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

    	String technique = plugin.getPlayerData(player).getWeaponSkillLow();
    	if(player.getGameMode() == GameMode.SPECTATOR || player.getGameMode() == GameMode.CREATIVE) return;
        if ((player.isOnGround() && technique.equalsIgnoreCase("pas de lune")))
            player.setAllowFlight(true);
        else if (!technique.equalsIgnoreCase("pas de lune"))
            player.setAllowFlight(false);
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

		if ((player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR)) {
	        player.setFlying(false);
			event.setCancelled(true);
	        if (event.isFlying()) {
	        	if(plugin.getEnduranceManager().canUseSkill(player, 45) && !plugin.getPlayerData(player).isInCooldown()) {

		        	String technique = plugin.getPlayerData(player).getWeaponSkillLow();
	        		if (technique.equalsIgnoreCase("pas de lune")) {
	        			
	        			int size = 1;
	                    for (double x = -size; x <= size; x += 0.5) {
	                        for (double z = -size; z <= size; z += 0.1) {
	                            player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(x, 0, z), 1, 0, 0, 0, 0);
	                        }
	                    }
	        			
			            Vector jump = player.getLocation().getDirection().multiply(0.5).setY(1);
			            player.setVelocity(jump);
			
			            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 1);
						plugin.getEnduranceManager().useEndurance(player, 30); //Consomme l'endurance
	        		}
	        	} else if (!plugin.getPlayerData(player).isInCooldown()) {
	        		player.sendMessage(ChatColor.RED + "➤ " + ChatColor.GRAY + "Vos jambes ont surchauffé, vous aurez besoin de temps pour vous en remettre.");
		            plugin.getPlayerData(player).setInCooldown(true);
		            Bukkit.getScheduler().runTaskLater(plugin, () -> {
		        		player.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.GRAY + "Vos jambes s'en sont remis !");
			            plugin.getPlayerData(player).setInCooldown(false);
		            }, 20 * 15);
	        	}
	        }
	    }
    }*/
}
