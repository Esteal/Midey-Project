package fr.midey.OnePieceCraftSkills.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.HakiManager.HakiCommands;
import fr.midey.OnePieceCraftSkills.LevelManager.LevelCommands;
import fr.midey.OnePieceCraftSkills.Weapons.WeaponCommands;

public class MasterCommand implements CommandExecutor {

    private final OnePieceCraftSkills plugin;

    public MasterCommand(OnePieceCraftSkills plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sendUsageMessage(sender);
            return false;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "help":
                sendHelpMessage(sender);
                break;
            case "give":
                handleGiveCommand(sender, command, label, args);
                break;
            case "view":
                ViewCommands.onCommand(sender, command, label, args, plugin);
                break;
            case "weaponskill":
                WeaponCommands.onCommand(sender, command, label, args, plugin);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Sous-commande inconnue: " + ChatColor.YELLOW + subCommand);
                return false;
        }

        return true;
    }

    private void sendUsageMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "Utilisation : " + ChatColor.WHITE + "/opc help");
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "━━━━━━━━━━━━━━━ " + ChatColor.AQUA + "OnePieceCraft" + ChatColor.GOLD + " ━━━━━━━━━━━━━━━");
        sender.sendMessage(ChatColor.YELLOW + "/opc give <exp|level|sp> <Pseudo> <quantités>" + ChatColor.GRAY + " - Donne exp, niveau, ou points de compétence.");
        sender.sendMessage(ChatColor.YELLOW + "/opc give haki <armement|roi|observation> <Pseudo> <quantités>" + ChatColor.GRAY + " - Donne des points de haki.");
        sender.sendMessage(ChatColor.YELLOW + "/opc view points <Pseudo>" + ChatColor.GRAY + " - Visualise les points d’un joueur.");
        sender.sendMessage(ChatColor.YELLOW + "/opc weaponskill <low|high> <Pseudo> <technique>" + ChatColor.GRAY + " - Attribue des points de maîtrise d'arme.");
    }

    private void handleGiveCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sendHelpMessage(sender);
            return;
        }

        String subCommandTwo = args[1].toLowerCase();

        if (subCommandTwo.equalsIgnoreCase("exp")
                || subCommandTwo.equalsIgnoreCase("level")
                || subCommandTwo.equalsIgnoreCase("sp")
                || subCommandTwo.equalsIgnoreCase("weapon")) {
            LevelCommands.onCommand(sender, command, label, args, plugin);
        } else if (subCommandTwo.equalsIgnoreCase("haki")) {
            HakiCommands.onCommand(sender, command, label, args, plugin);
        } else {
            sendHelpMessage(sender);
        }
    }
}
