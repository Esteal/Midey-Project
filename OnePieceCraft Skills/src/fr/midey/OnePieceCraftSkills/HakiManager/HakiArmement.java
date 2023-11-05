package fr.midey.OnePieceCraftSkills.HakiManager;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;

public class HakiArmement {

    private final OnePieceCraftSkills plugin;

    private static final long COOLDOWN_LEVEL_1 = 3_5 * 60 * 1_000;
    private static final long COOLDOWN_LEVEL_2 = 3 * 60 * 1_000;
    private static final long COOLDOWN_LEVEL_3 = 2 * 60 * 1_000;

    private static final long[] COOLDOWN_LEVELS = {COOLDOWN_LEVEL_1, COOLDOWN_LEVEL_2, COOLDOWN_LEVEL_3};

    public HakiArmement(OnePieceCraftSkills plugin) {
        this.plugin = plugin;
    }

    public void onPlayerUseHakiArmement(Player player) {
    	
        UUID playerId = player.getUniqueId();
        PlayerData playerData = plugin.getPlayerData(player);


            int hakiPoints = playerData.getHakiArmementLevel();;
            
            if (hakiPoints > 0) {
            	
                long currentTime = System.currentTimeMillis();
                long lastUsage = playerData.getCooldownsHakiArmement();
                
                long cooldownDuration = COOLDOWN_LEVELS[hakiPoints - 1];

                if (currentTime - lastUsage < cooldownDuration) {
                    long tempsRestant = (cooldownDuration - (currentTime - lastUsage))/1000;
                    player.sendMessage(ChatColor.RED + "Haki de l'Armement en cooldown pour encore : " + (tempsRestant) + "s.");
                    return;
                }

                player.sendMessage(ChatColor.GREEN + "Haki de l'Armement activé !");
                playerData.setCooldownsHakiArmement(currentTime);
                plugin.getActiveHakiArmement().add(playerId);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        plugin.getActiveHakiArmement().remove(playerId);
                        player.sendMessage(ChatColor.GREEN + "Le haki de l'Armement est maintenant disponible !");
                    }
                }.runTaskLater(plugin, cooldownDuration / 50);
            } else {
                player.sendMessage(ChatColor.RED + "Vous n'avez pas de points de compétence en Haki de l'Armement.");
            }
    }
}