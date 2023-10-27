package fr.midey.OnePieceCraftSkills.HakiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;

public class HakiRoi implements Listener {

    private final OnePieceCraftSkills plugin;

    private static final long COOLDOWN_ROI = 5 * 60 * 1_000; //5 minutes

    public HakiRoi(OnePieceCraftSkills plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerUseHakiRoi(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (isHakiRoiItem(event)) {
            int hakiPoints = plugin.getHakiPoints(player, "roi");

            if (hakiPoints > 0 && isCooldownOver(player)) {
                activateHakiRoi(player, hakiPoints);
            } else {
                informPlayer(player, hakiPoints);
            }
        }
    }
    
    private boolean isHakiRoiItem(PlayerInteractEvent event) {
        return event.getItem() != null && event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Haki des Rois");
    }
    
    private boolean isCooldownOver(Player player) {
        long currentTime = System.currentTimeMillis();
        long lastUsage = plugin.getPlayerDataMap().getOrDefault(player.getUniqueId(), new PlayerData()).getCooldownsHakiRoi();
        return currentTime - lastUsage >= COOLDOWN_ROI;
    }
    
    private void informPlayer(Player player, int hakiPoints) {
        String message = hakiPoints > 0
                ? ChatColor.RED + "Haki des Rois en cooldown. Attendez quelques instants."
                : ChatColor.RED + "Vous n'avez pas de points de compétence en Haki des Rois.";
        player.sendMessage(message);
    }
    
    private void activateHakiRoi(Player player, int hakiPoints) {
        player.sendMessage(ChatColor.GREEN + "Haki des Rois activé !");
        plugin.getActiveHakiRoi().add(player.getUniqueId());

        // Business Logic to apply Haki effects
        applyKingHakiEffects(player, hakiPoints);

        // Business logic for cooldowns
        setCooldownAndScheduleReset(player);
    }
    
    private void setCooldownAndScheduleReset(Player player) {
        long currentTime = System.currentTimeMillis();
        plugin.getPlayerDataMap().getOrDefault(player.getUniqueId(), new PlayerData()).setCooldownsHakiRoi(currentTime);

        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.getActiveHakiRoi().remove(player.getUniqueId());
                player.sendMessage(ChatColor.GREEN + "Le Haki des Rois est maintenant disponible !");
            }
        }.runTaskLater(plugin, COOLDOWN_ROI / 50);
    }
    private static class HakiStats {
        int radius;
        int damage;
        List<PotionEffect> effects;

        HakiStats(int radius, int damage, List<PotionEffect> effects) {
            this.radius = radius;
            this.damage = damage;
            this.effects = effects;
        }
    }

    private HakiStats getHakiStats(int hakiPoints) {
        switch (hakiPoints) {
            case 1:
                return new HakiStats(10, 3, List.of(new PotionEffect(PotionEffectType.SLOW, 30 * 20, 0)));
            case 2:
                return new HakiStats(20, 6, List.of(new PotionEffect(PotionEffectType.SLOW, 30 * 20, 1), new PotionEffect(PotionEffectType.SLOW_DIGGING, 30 * 20, 0)));
            case 3:
                return new HakiStats(30, 9, List.of(
                        new PotionEffect(PotionEffectType.SLOW, 30 * 20, 1),
                        new PotionEffect(PotionEffectType.SLOW_DIGGING, 30 * 20, 1),
                        new PotionEffect(PotionEffectType.POISON, 30 * 20, 0),
                        new PotionEffect(PotionEffectType.WEAKNESS, 30 * 20, 0),
                        new PotionEffect(PotionEffectType.CONFUSION, 10 * 20, 0)
                ));
            default:
                return null;
        }
    }

    private void applyKingHakiEffects(Player player, int hakiPoints) {
        HakiStats stats = getHakiStats(hakiPoints);
        if (stats == null) return;

        List<String> affectedEntities = new ArrayList<>();
        Location center = player.getLocation();
        
        new BukkitRunnable() {
            int currentRadius = 5;
            @Override
            public void run() {
                DustOptions redDustOptions = new DustOptions(Color.RED, 1);
                DustOptions blackDustOptions = new DustOptions(Color.BLACK, 1);
                
                createSphere(center, currentRadius, redDustOptions, blackDustOptions, player, affectedEntities, stats.damage, stats.effects);
                
                if (currentRadius++ > stats.radius) {
                    notifyPlayerOfAffectedEntities(player, affectedEntities);
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 0L);
    }
    
    private void notifyPlayerOfAffectedEntities(Player player, List<String> affectedEntities) {
        if (!affectedEntities.isEmpty()) {
            player.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[Haki des Rois] " + ChatColor.GREEN + "Vous avez touché les entités suivantes :");
            for (String entityName : affectedEntities) {
                player.sendMessage(ChatColor.YELLOW + "- " + ChatColor.WHITE + entityName);
            }
        } else {
            player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Haki des Rois] " + ChatColor.RED + "Vous n'avez touché aucune entité.");
        }
    }
    
    
    private void createSphere(Location center, int radius, DustOptions redDustOptions, DustOptions blackDustOptions, Player player, List<String> affectedEntities, int damage, List<PotionEffect> effects) {
        World world = center.getWorld();
        int radiusSquared = radius * radius;

        // Obtenez les entités à proximité une seule fois
        List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);

        // Centre de la sphère
        int centerX = center.getBlockX();
        int centerY = center.getBlockY();
        int centerZ = center.getBlockZ();

        // Parcourir un cube autour du joueur
        for (int x = centerX - radius; x <= centerX + radius; x++) {
            int dx = centerX - x;
            int dx2 = dx * dx;

            for (int y = centerY - radius; y <= centerY + radius; y++) {
                int dy = centerY - y;
                int dy2 = dy * dy;

                for (int z = centerZ - radius; z <= centerZ + radius; z++) {
                    int dz = centerZ - z;
                    int dz2 = dz * dz;

                    int distanceSquared = dx2 + dy2 + dz2;

                    // Bloc sur la surface de la sphère
                    if (distanceSquared <= radiusSquared && distanceSquared >= (radius-1)*(radius-1)) {
                        // Choix aléatoire pour la couleur de la particule
                        DustOptions chosenDust = (Math.random() < 0.5) ? redDustOptions : blackDustOptions;
                        world.spawnParticle(Particle.REDSTONE, x, y, z, 1, 0, 0, 0, chosenDust);

                        for (Entity entity : nearbyEntities) {
                            if (!(entity instanceof Player) || affectedEntities.contains(entity.getName())) continue;
                            
                             Player targetPlayer = (Player) entity;
                             HashMap<UUID, PlayerData> playerDataMap = plugin.getPlayerDataMap();

                             int levelPlayer = playerDataMap.getOrDefault(player.getUniqueId(), new PlayerData()).getLevel();
                             int levelTarget = playerDataMap.getOrDefault(targetPlayer.getUniqueId(), new PlayerData()).getLevel();

                             // Remplacez "getLevel" par votre propre méthode pour obtenir le niveau du joueur si nécessaire.
                             if (levelTarget <= levelPlayer) {
                                 for (PotionEffect effect : effects) {
                                     targetPlayer.addPotionEffect(effect);
                                 }
                                 // Appliquer les dégâts
                                 targetPlayer.damage(damage);
                                 targetPlayer.playEffect(EntityEffect.HURT);
                                 targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_PLAYER_HURT, 1f, 1f);
                                 // Envoyer le message au joueur touché
                                 targetPlayer.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[Haki des Rois] " + ChatColor.RED + "Vous avez été affecté par le haki des rois de " + ChatColor.GOLD + player.getName());
                                 // Ajoute le pseudo du joueur à la liste
                                 affectedEntities.add(targetPlayer.getName());
                             }
                         }
                     }
                 }
             }
         }
    }
}
