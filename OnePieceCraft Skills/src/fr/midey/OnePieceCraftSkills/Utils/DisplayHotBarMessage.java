package fr.midey.OnePieceCraftSkills.Utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DisplayHotBarMessage {

    private static final int BAR_LENGTH = 0; // nombre de caractères de la barre de progression
    private static final String FULL_CHAR = "";
    private static final String EMPTY_CHAR = "";

    public static void displayExperienceBar(Player player, int currentExp, int requiredExp) {
        double percentage = (double) currentExp / requiredExp;
        int fullChars = (int) (percentage * BAR_LENGTH);

        StringBuilder bar = new StringBuilder();
        bar.append(ChatColor.GREEN);
        for (int i = 0; i < fullChars; i++) {
            bar.append(FULL_CHAR);
        }
        bar.append(ChatColor.GRAY);
        for (int i = 0; i < BAR_LENGTH - fullChars; i++) {
            bar.append(EMPTY_CHAR);
        }
        bar.append(ChatColor.WHITE).append("§a ").append(currentExp).append("/").append(requiredExp);

        player.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                net.md_5.bungee.api.chat.TextComponent.fromLegacyText(bar.toString()));
    }
}
