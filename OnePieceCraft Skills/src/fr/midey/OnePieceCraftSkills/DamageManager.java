package fr.midey.OnePieceCraftSkills;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Manages damage-related logic, such as buffs and debuffs based on Haki level.
 */
public class DamageManager implements Listener {
    
    // Reference to the main plugin class
    private final OnePieceCraftSkills plugin;

    // Constants for damage boost levels
    private static final double DAMAGE_BOOST_LEVEL_1 = 0.1;  // +10%
    private static final double DAMAGE_BOOST_LEVEL_2 = 0.15; // +15%
    private static final double DAMAGE_BOOST_LEVEL_3 = 0.2;  // +20%

    // Constants for damage reduction levels
    private static final double DAMAGE_REDUCTION_LEVEL_1 = 0.9;  // -10%
    private static final double DAMAGE_REDUCTION_LEVEL_2 = 0.85; // -15%
    private static final double DAMAGE_REDUCTION_LEVEL_3 = 0.8;  // -20%

    // Arrays for damage boosts and reductions for quick reference
    private static final double[] DAMAGE_BOOST_LEVELS = {DAMAGE_BOOST_LEVEL_1, DAMAGE_BOOST_LEVEL_2, DAMAGE_BOOST_LEVEL_3};
    private static final double[] DAMAGE_REDUCTION_LEVELS = {DAMAGE_REDUCTION_LEVEL_1, DAMAGE_REDUCTION_LEVEL_2, DAMAGE_REDUCTION_LEVEL_3};

    /**
     * Constructor for DamageManager.
     * @param plugin Reference to the main plugin class
     */
    public DamageManager(OnePieceCraftSkills plugin) {
        this.plugin = plugin;
    }

    /**
     * Event handler for when an entity is damaged by another entity.
     * @param event The EntityDamageByEntityEvent object
     */
	@EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            UUID playerId = player.getUniqueId();
             HashSet<UUID> activeHakiArmement = plugin.getActiveHakiArmement();

            if (activeHakiArmement.contains(playerId)) {
                int hakiPoints = plugin.getHakiPoints(player, "armement");

                double originalDamage = event.getDamage();
                double additionalDamage = originalDamage * DAMAGE_BOOST_LEVELS[hakiPoints - 1];

                event.setDamage(originalDamage + additionalDamage);
            }
        }
    }

    /**
     * Event handler for when an entity is damaged.
     * @param event The EntityDamageEvent object
     */
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            UUID playerId = player.getUniqueId();
            var activeHakiArmement = plugin.getActiveHakiArmement();

            if (activeHakiArmement.contains(playerId)) {
                int hakiPoints = plugin.getHakiPoints(player, "armement");

                double originalDamage = event.getDamage();
                double reducedDamage = originalDamage * DAMAGE_REDUCTION_LEVELS[hakiPoints - 1];

                event.setDamage(reducedDamage);
            }
        }
    }
}
