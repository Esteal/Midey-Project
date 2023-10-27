package fr.midey.OnePieceCraftSkills.HakiManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;

public class HakiObservation {

    private final OnePieceCraftSkills plugin;

    public HakiObservation(OnePieceCraftSkills plugin) {
        this.plugin = plugin;
    }
	
    public void HakiObservations() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (plugin.getPlayerDataMap().getOrDefault(player.getUniqueId(), new PlayerData()).isObservationHakiActive()) {
                        int hakiPoints = plugin.getHakiPoints(player, "observation");
                        int radius = 0;
                        
                        switch (hakiPoints) {
                            case 1: radius = 10; break;
                            case 2: radius = 30; break;
                            case 3: radius = 70; break;
                        }

                        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                            if (otherPlayer == player) continue;
                            int dist = (int) player.getLocation().distance(otherPlayer.getLocation());
                            if (dist <= radius) {
                                player.sendMessage(ChatColor.RED + otherPlayer.getName() + " est à "+ dist +"m de vous");
                                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                            }
                        }
                    }
                }
            }
        }, 0L, 100L);
    }
}
