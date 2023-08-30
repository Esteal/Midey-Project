package fr.midey.onepiececraft.Faction;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.midey.onepiececraft.OnePieceCraft;

public class FactionCommands implements CommandExecutor {
	
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Cette commande ne peut être exécutée que par un joueur !");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("f")) {
            if (args.length == 0) {
                // Afficher l'aide de la commande ici si besoin
                player.sendMessage("§a/f create <Nom de la faction>");
                player.sendMessage("§a/f invite <Nom du joueur>");
                return true;
            } else if (args[0].equalsIgnoreCase("create")) {
                if (args.length < 2) {
                    player.sendMessage("§aUtilisation : /f create <Nom de la faction>");
                    return true;
                }

                String factionName = args[1];
                // Appel à la méthode de création de faction avec factionName en argument
                createFaction(player, factionName);
                return true;
            } else if (args[0].equalsIgnoreCase("invite")) {
                if (args.length < 2) {
                    player.sendMessage("§aUtilisation : /f invite <Nom du joueur>");
                    return true;
                }

                String playerName = args[1];
                // Appel à la méthode d'invitation d'un joueur avec playerName en argument
                invitePlayer(player, playerName);
                return true;
            }  else if (args[0].equalsIgnoreCase("join")) {
                if (args.length < 2) {
                    player.sendMessage("§aUtilisation : /f join <Nom de la faction>");
                    return true;
                }

                String factionName = args[1];
                // Appel à la méthode pour que le joueur rejoigne la faction avec factionName en argument
                joinFaction(player, factionName);
                return true;
            } else if (args[0].equalsIgnoreCase("lead")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Cette commande ne peut être exécutée que par un joueur !");
                    return true;
                }

                if (args.length < 2) {
                    player.sendMessage("§aUtilisation : /f lead <Nom du joueur>");
                    return true;
                }

                String playerName = args[1];
                // Appel à la méthode pour passer le chef de la faction avec playerName en argument
                passFactionLeader(player, playerName);
                return true;
            } else if (args[0].equalsIgnoreCase("leave")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Cette commande ne peut être exécutée que par un joueur !");
                    return true;
                }

                // Appel à la méthode pour permettre aux membres de quitter la faction
                leaveFaction(player);
                return true;
            }
            // Ajoute d'autres sous-commandes ici si nécessaire
        }

        return false;
    }

    private void createFaction(Player player, String factionName) {
        // Vérifions d'abord si le joueur n'appartient pas déjà à une faction
        if (isPlayerInFaction(player)) {
            player.sendMessage("§cVous êtes déjà dans une faction. Vous devez quitter votre faction actuelle pour en créer une nouvelle.");
            return;
        }

        // Vérifions si une faction portant ce nom existe déjà
        if (isFactionExists(factionName)) {
            player.sendMessage("§cUne faction portant ce nom existe déjà.");
            return;
        }

        // Créons une nouvelle faction
        Faction faction = new Faction(factionName, player.getUniqueId());

        // Ajoutons cette faction à la liste des factions
        // Assure-toi d'avoir une liste de factions dans ta classe principale
        // par exemple : private List<Faction> factions = new ArrayList<>();
        OnePieceCraft.getMAIN().factions.add(faction);

        // Sauvegardons les informations de la faction dans le fichier config.yml
        saveFactions();

        player.sendMessage("§aFélicitations ! Vous avez créé la faction : " + factionName);
    }
    
    private boolean isPlayerInFaction(Player player) {
        // Vérifie si le joueur appartient déjà à une faction en parcourant toutes les factions
        for (Faction faction : OnePieceCraft.getMAIN().factions) {
            if (faction.getMembers().contains(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    private boolean isFactionExists(String factionName) {
        // Vérifie si une faction portant le même nom existe déjà en parcourant toutes les factions
        for (Faction faction : OnePieceCraft.getMAIN().factions) {
            if (faction.getName().equalsIgnoreCase(factionName)) {
                return true;
            }
        }
        return false;
    }

    private void saveFactions() {
        // Efface toutes les anciennes factions du fichier config.yml

        // Enregistre toutes les factions actuelles dans le fichier config.yml
        for (Faction faction : OnePieceCraft.getMAIN().factions) {
            String factionName = faction.getName();
            UUID factionLeader = faction.getLeader();
            List<UUID> factionMembers = faction.getMembers();

            OnePieceCraft.getMAIN().config.get().set("factions." + factionName + ".leader", factionLeader.toString());
            OnePieceCraft.getMAIN().config.get().set("factions." + factionName + ".members", factionMembers.toString());
        }

        // Sauvegarde le fichier config.yml
        OnePieceCraft.getMAIN().config.save();
    }

    private void invitePlayer(Player player, String playerName) {
        // Vérifier si le joueur qui exécute la commande est le chef d'une faction
        Faction faction = getFactionByLeader(player.getUniqueId());
        if (faction == null) {
            player.sendMessage("§cVous devez être le chef d'une faction pour inviter des joueurs.");
            return;
        }

        // Vérifier si le joueur a déjà une faction
        if (isPlayerInFaction(playerName)) {
            player.sendMessage("§cLe joueur §b" + playerName + "§c appartient déjà à une faction.");
            return;
        }

        // Vérifier si le nombre de joueurs dans la faction est inférieur à 15
        if (faction.getMembers().size() >= 15) {
            player.sendMessage("§cVotre faction a déjà atteint le nombre maximum de membres (15).");
            return;
        }

        // Vérifier si le joueur a déjà une invitation en cours
        if (OnePieceCraft.MAIN.pendingInvitations.containsKey(playerName)) {
            long expirationTime = OnePieceCraft.MAIN.pendingInvitations.get(playerName);
            if (System.currentTimeMillis() < expirationTime) {
                player.sendMessage("§aVous avez déjà invité §b" + playerName + "§a. Veuillez attendre la fin de l'invitation en cours.");
                return;
            } else {
                // Si l'invitation a expiré, supprimer l'invitation en cours
            	OnePieceCraft.MAIN.pendingInvitations.remove(playerName);
                player.sendMessage("§cL'invitation envoyée à §b" + playerName + "§c a expiré.");
            }
        }

        // Ajouter le joueur invité à la faction
        UUID invitedPlayerUUID = getPlayerUUID(playerName);
        if (invitedPlayerUUID != null) {
            saveFactions();
            player.sendMessage("§aVous avez invité §b" + playerName + "§a à rejoindre votre faction.");
            Player invitedPlayer = Bukkit.getPlayer(invitedPlayerUUID);
            if (invitedPlayer != null) {
                invitedPlayer.sendMessage("§aVous avez été invité à rejoindre la faction §b" + faction.getName() + "§a. Utilisez /f join pour rejoindre.");
            }

            // Ajouter l'invitation à la HashMap avec une expiration de 30 secondes
            long expirationTime = System.currentTimeMillis() + 30 * 1000; // 30 secondes en millisecondes
            OnePieceCraft.MAIN.pendingInvitations.put(playerName, expirationTime);
            OnePieceCraft.MAIN.pendingInvitations.put(player.getName(), expirationTime);
        } else {
            player.sendMessage("§cLe joueur §b" + playerName + "§c n'existe pas ou n'est pas en ligne.");
        }
    }

    private UUID getPlayerUUID(String playerName) {
        Player invitedPlayer = Bukkit.getPlayerExact(playerName);
        if (invitedPlayer != null) {
            return invitedPlayer.getUniqueId();
        } else {
            // Si le joueur n'est pas en ligne, essayons de récupérer son UUID à partir de l'historique des noms
            @SuppressWarnings("deprecation")
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
            if (offlinePlayer != null) {
                return offlinePlayer.getUniqueId();
            }
        }
        return null;
    }

    private Faction getFactionByLeader(UUID leaderUUID) {
        for (Faction faction : OnePieceCraft.getMAIN().factions) {
            if (faction.getLeader().equals(leaderUUID)) {
                return faction;
            }
        }
        return null;
    }
    
    private boolean isPlayerInFaction(String playerName) {
        UUID playerUUID = getPlayerUUID(playerName);
        if (playerUUID != null) {
            for (Faction faction : OnePieceCraft.getMAIN().factions) {
                if (faction.getMembers().contains(playerUUID)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void joinFaction(Player player, String factionName) {
        // Vérifier si le joueur a déjà une faction
        if (isPlayerInFaction(player)) {
            player.sendMessage("§cVous êtes déjà dans une faction. Vous devez quitter votre faction actuelle pour rejoindre une autre faction.");
            return;
        }

        // Vérifier si le joueur a une invitation en cours pour rejoindre la faction spécifiée
        if (!OnePieceCraft.MAIN.pendingInvitations.containsKey(player.getName())) {
            player.sendMessage("§cVous n'avez pas d'invitation en cours pour rejoindre cette faction.");
            return;
        }

        // Vérifier si l'invitation n'a pas expiré
        long expirationTime = OnePieceCraft.MAIN.pendingInvitations.get(player.getName());
        if (System.currentTimeMillis() >= expirationTime) {
            OnePieceCraft.MAIN.pendingInvitations.remove(player.getName());
            player.sendMessage("§cL'invitation pour rejoindre la faction a expiré.");
            return;
        }

        // Trouver la faction par son nom ou par le pseudo d'un de ses membres
        Faction faction = getFactionByNameOrMember(player, factionName);
        if (faction == null) {
            player.sendMessage("§cAucune faction portant ce nom ou dont l'un des membres a ce pseudo n'existe.");
            return;
        }

        // Ajouter le joueur à la faction
        faction.addMember(player.getUniqueId());

        // Supprimer l'invitation en cours
        OnePieceCraft.MAIN.pendingInvitations.remove(player.getName());

        // Sauvegarder les informations de la faction dans le fichier config.yml
        saveFactions();

        player.sendMessage("§aVous avez rejoint la faction §b" + faction.getName() + "§a avec succès !");
    }

    private Faction getFactionByNameOrMember(Player player, String factionName) {
        Faction factionByName = getFactionByName(factionName);
        if (factionByName != null) {
            return factionByName;
        }

        UUID playerUUID = getPlayerUUID(factionName);
        if (playerUUID == null) {
            return null;
        }

        for (Faction faction : OnePieceCraft.getMAIN().factions) {
            if (faction.getMembers().contains(playerUUID)) {
                return faction;
            }
        }
        return null;
    }

    private Faction getFactionByName(String factionName) {
        for (Faction faction : OnePieceCraft.getMAIN().factions) {
            if (faction.getName().equalsIgnoreCase(factionName)) {
                return faction;
            }
        }
        return null;
    }

    private void passFactionLeader(Player player, String newLeaderName) {
        Faction faction = getFactionByLeader(player.getUniqueId());
        if (faction == null) {
            player.sendMessage("§cVous devez être le chef d'une faction pour passer le rôle de chef à un membre.");
            return;
        }
        
        UUID newLeaderUUID = getPlayerUUID(newLeaderName);
        if (newLeaderUUID == null || !faction.isMember(newLeaderUUID)) {
            player.sendMessage("§cLe joueur §b" + newLeaderName + "§c n'appartient pas à votre faction ou n'existe pas.");
            return;
        }

        faction.setLeader(newLeaderUUID);
        saveFactions();

        player.sendMessage("§aVous avez passé le rôle de chef à §b" + newLeaderName + "§a. Vous êtes maintenant membre de la faction.");
        Player newLeaderPlayer = Bukkit.getPlayer(newLeaderUUID);
        if (newLeaderPlayer != null) {
            newLeaderPlayer.sendMessage("§aVous êtes maintenant le chef de la faction §b" + faction.getName() + "§a.");
        }
    }
    
    private void leaveFaction(Player player) {
        Faction faction = Faction.getPlayerFaction(player.getUniqueId());
        if (faction == null) {
            player.sendMessage("§cVous ne pouvez pas quitter de faction car vous n'en avez pas.");
            return;
        }

        UUID playerUUID = player.getUniqueId();
        if (faction.getLeader().equals(playerUUID)) {
            player.sendMessage("§cVous êtes le chef de la faction. Vous devez d'abord passer le rôle de chef à un autre membre pour quitter la faction.");
            return;
        }

        // Retirer le joueur de la faction
        faction.removeMember(playerUUID);

        // Sauvegarder les informations de la faction dans le fichier config.yml
        saveFactions();

        player.sendMessage("§aVous avez quitté la faction §b" + faction.getName() + "§a.");

        // Informer le chef de la faction du départ du membre (ancien chef)
        Player leaderPlayer = Bukkit.getPlayer(faction.getLeader());
        if (leaderPlayer != null) {
            leaderPlayer.sendMessage("§b" + player.getName() + "§a a quitté la faction.");
        }
    }


}
