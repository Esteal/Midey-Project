package fr.midey.OnePieceCraftSkills.HakiManager;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;

public class HakiArmement implements Listener {

    private final OnePieceCraftSkills plugin;

    private static final long COOLDOWN_LEVEL_1 = 3_5 * 60 * 1_000;
    private static final long COOLDOWN_LEVEL_2 = 3 * 60 * 1_000;
    private static final long COOLDOWN_LEVEL_3 = 2 * 60 * 1_000;

    private static final long[] COOLDOWN_LEVELS = {COOLDOWN_LEVEL_1, COOLDOWN_LEVEL_2, COOLDOWN_LEVEL_3};

    public HakiArmement(OnePieceCraftSkills plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerUseHakiArmement(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (event.getItem() != null && event.getItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Haki de l'Armement")) {

            int hakiPoints = plugin.getHakiPoints(player, "armement");
            HashMap<UUID, PlayerData> playerDataMap = plugin.getPlayerDataMap();
            
            if (hakiPoints > 0) {
                long currentTime = System.currentTimeMillis();
                long lastUsage = playerDataMap.getOrDefault(playerId, new PlayerData()).getCooldownsHakiArmement();
                
                long cooldownDuration = COOLDOWN_LEVELS[hakiPoints - 1];

                if (currentTime - lastUsage < cooldownDuration) {
                    player.sendMessage(ChatColor.RED + "Haki de l'Armement en cooldown. Attendez quelques instants.");
                    return;
                }

                player.sendMessage(ChatColor.GREEN + "Haki de l'Armement activ� !");
                playerDataMap.getOrDefault(playerId, new PlayerData()).setCooldownsHakiArmement(currentTime);
                plugin.getActiveHakiArmement().add(playerId);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        plugin.getActiveHakiArmement().remove(playerId);
                        player.sendMessage(ChatColor.GREEN + "Le haki de l'Armement est maintenant disponible !");
                    }
                }.runTaskLater(plugin, cooldownDuration / 50);
            } else {
                player.sendMessage(ChatColor.RED + "Vous n'avez pas de points de comp�tence en Haki de l'Armement.");
            }
        }
    }
}