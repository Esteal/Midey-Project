package fr.midey.OnePieceCraftSkills.LevelManager;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;

public class LevelCommands {

    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, OnePieceCraftSkills plugin) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Cette commande ne peut être exécutée que par un joueur !");
            return true;
        }

        if (args.length < 4) {
            return false;
        }

        Player target = Bukkit.getPlayer(args[2]);
        
        if (target == null) {
            sender.sendMessage("Joueur introuvable !");
            return true;
        }
        
        UUID uuid = target.getUniqueId();
        HashMap<UUID, PlayerData> playerDataMap = plugin.getPlayerDataMap();

        switch (args[1].toLowerCase()) {
            case "exp":
                if (args[0].equalsIgnoreCase("give")) {
                    try {
                        int expAmount = Integer.parseInt(args[3]);
                        LevelSystem.addExperience(target, expAmount);
                        sender.sendMessage("Vous avez donné " + expAmount + " d'exp à " + target.getName() + ".");
                    } catch (NumberFormatException e) {
                        sender.sendMessage("Veuillez entrer un nombre valide d'exp !");
                    }
                }
                break;
            case "level":
                if (args[0].equalsIgnoreCase("give")) {
                    try {
                        int levelAmount = Integer.parseInt(args[3]);
                        // Mettez à jour la logique de niveau comme vous le souhaitez
                        playerDataMap.getOrDefault(uuid, new PlayerData()).setLevel(levelAmount);
                        playerDataMap.getOrDefault(uuid, new PlayerData()).setExperience(0);
                        LevelSystem.addExperience(target, 1);
                        sender.sendMessage("Vous avez donné " + levelAmount + " niveaux à " + target.getName() + ".");
                    } catch (NumberFormatException e) {
                        sender.sendMessage("Veuillez entrer un nombre valide de niveaux !");
                    }
                }
                break;
            case "sp":
                if (args[0].equalsIgnoreCase("give")) {
                    try {
                        int skillPoints = Integer.parseInt(args[3]);
                        int currentSkillPoints = playerDataMap.getOrDefault(uuid, new PlayerData()).getSkillPoints();
                        playerDataMap.getOrDefault(uuid, new PlayerData()).setSkillPoints(currentSkillPoints + skillPoints);
                        sender.sendMessage("Vous avez donné " + skillPoints + " points de compétence à " + target.getName() + ".");
                    } catch (NumberFormatException e) {
                        sender.sendMessage("Veuillez entrer un nombre valide de points de compétence !");
                    }
                }
            case "weapon":
            	if (args[0].equalsIgnoreCase("give")) {
                    try {
                        int weaponPoints = Integer.parseInt(args[3]);
                        int currentWeaponPoints = playerDataMap.getOrDefault(uuid, new PlayerData()).getWeaponPoints();
                        playerDataMap.getOrDefault(uuid, new PlayerData()).setWeaponPoints(Math.min(3, currentWeaponPoints + weaponPoints));
                        sender.sendMessage("Vous avez donné " + weaponPoints + " points de maniement du sabre à " + target.getName() + ".");
                    } catch (NumberFormatException e) {
                        sender.sendMessage("Veuillez entrer un nombre valide de points de maniement du sabre !");
                    }
            	}
                break;
            default:
                return false;
        }

        return true;
    }
}
