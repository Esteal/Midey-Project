package fr.midey.OnePieceCraftSkills.HakiManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;

public class HakiCommands {
    
    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args, OnePieceCraftSkills plugin) {
        if (args.length < 4) {
            sender.sendMessage(ChatColor.RED + "Utilisation incorrecte de la commande.");
            return false;
        }

        String playerName = args[3];
        Player target = Bukkit.getPlayerExact(playerName);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Joueur introuvable.");
            return false;
        }

        String hakiType = args[2];
        String operation = args[0];
        int points;

        try {
            points = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Nombre de points invalide.");
            return false;
        }

        if (operation.equalsIgnoreCase("give")) {
            plugin.addHakiPoints(target.getUniqueId(), hakiType, points);
            sender.sendMessage(ChatColor.GREEN + "Ajout de " + points + " points de §c" + hakiType + "§a à §c" + target.getName());
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "Opération non supportée.");
            return false;
        }
    }
}
