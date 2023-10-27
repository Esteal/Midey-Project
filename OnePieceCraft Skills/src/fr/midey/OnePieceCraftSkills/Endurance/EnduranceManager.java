package fr.midey.OnePieceCraftSkills.Endurance;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class EnduranceManager {
	
    private final OnePieceCraftSkills plugin;

    public EnduranceManager(OnePieceCraftSkills plugin) {
        this.plugin = plugin;
        initEnduranceRegeneration();
    }

    private void initEnduranceRegeneration() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                	PlayerData playerData = plugin.getPlayerData(player);
                    int endurance = playerData.getEndurance();
                    if (endurance < 100) {
                        endurance += 100;
                        if (endurance > 100) {
                            endurance = 100;
                        }
                        playerData.setEndurance(endurance);
                    }
                    updateEnduranceBar(player, playerData);
                }
            }
        }.runTaskTimer(plugin, 0, 5);
    }

    public void useEndurance(Player player, double amount) {
    	PlayerData playerData = plugin.getPlayerData(player);
        int endurance = playerData.getEndurance();
        endurance -= amount;
        if (endurance < 0) {
            endurance = 0;
        }
        playerData.setEndurance(endurance);
        updateEnduranceBar(player, playerData);
    }

    public boolean canUseSkill(Player player, int amount) {
        return plugin.getPlayerData(player).getEndurance() > amount;
    }

    private void updateEnduranceBar(Player player, PlayerData playerData) {
        int endurance = playerData.getEndurance();
        int barLength = 50;
        int fill = (int) ((endurance / 100.0) * barLength);
        StringBuilder bar = new StringBuilder();
        bar.append(ChatColor.GREEN);
        for (int i = 0; i < fill; i++) {
            bar.append('|');
        }
        bar.append(ChatColor.RED);
        for (int i = fill; i < barLength; i++) {
            bar.append('|');
        }
        
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(bar.toString()));
    }
}
