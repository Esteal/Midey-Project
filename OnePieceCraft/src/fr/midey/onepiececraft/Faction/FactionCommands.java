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
            sender.sendMessage("Cette commande ne peut �tre ex�cut�e que par un joueur !");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("f")) {
            if (args.length == 0) {
                // Afficher l'aide de la commande ici si besoin
                player.sendMessage("�a/f create <Nom de la faction>");
                player.sendMessage("�a/f invite <Nom du joueur>");
                return true;
            } else if (args[0].equalsIgnoreCase("create")) {
                if (args.length < 2) {
                    player.sendMessage("�aUtilisation : /f create <Nom de la faction>");
                    return true;
                }

                String factionName = args[1];
                // Appel � la m�thode de cr�ation de faction avec factionName en argument
                createFaction(player, factionName);
                return true;
            } else if (args[0].equalsIgnoreCase("invite")) {
                if (args.length < 2) {
                    player.sendMessage("�aUtilisation : /f invite <Nom du joueur>");
                    return true;
                }

                String playerName = args[1];
                // Appel � la m�thode d'invitation d'un joueur avec playerName en argument
                invitePlayer(player, playerName);
                return true;
            }  else if (args[0].equalsIgnoreCase("join")) {
                if (args.length < 2) {
                    player.sendMessage("�aUtilisation : /f join <Nom de la faction>");
                    return true;
                }

                String factionName = args[1];
                // Appel � la m�thode pour que le joueur rejoigne la faction avec factionName en argument
                joinFaction(player, factionName);
                return true;
            } else if (args[0].equalsIgnoreCase("lead")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Cette commande ne peut �tre ex�cut�e que par un joueur !");
                    return true;
                }

                if (args.length < 2) {
                    player.sendMessage("�aUtilisation : /f lead <Nom du joueur>");
                    return true;
                }

                String playerName = args[1];
                // Appel � la m�thode pour passer le chef de la faction avec playerName en argument
                passFactionLeader(player, playerName);
                return true;
            } else if (args[0].equalsIgnoreCase("leave")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Cette commande ne peut �tre ex�cut�e que par un joueur !");
                    return true;
                }

                // Appel � la m�thode pour permettre aux membres de quitter la faction
                leaveFaction(player);
                return true;
            }
            // Ajoute d'autres sous-commandes ici si n�cessaire
        }

        return false;
    }

    private void createFaction(Player player, String factionName) {
        // V�rifions d'abord si le joueur n'appartient pas d�j� � une faction
        if (isPlayerInFaction(player)) {
            player.sendMessage("�cVous �tes d�j� dans une faction. Vous devez quitter votre faction actuelle pour en cr�er une nouvelle.");
            return;
        }

        // V�rifions si une faction portant ce nom existe d�j�
        if (isFactionExists(factionName)) {
            player.sendMessage("�cUne faction portant ce nom existe d�j�.");
            return;
        }

        // Cr�ons une nouvelle faction
        Faction faction = new Faction(factionName, player.getUniqueId());

        // Ajoutons cette faction � la liste des factions
        // Assure-toi d'avoir une liste de factions dans ta classe principale
        // par exemple : private List<Faction> factions = new ArrayList<>();
        OnePieceCraft.getMAIN().factions.add(faction);

        // Sauvegardons les informations de la faction dans le fichier config.yml
        saveFactions();

        player.sendMessage("�aF�licitations ! Vous avez cr�� la faction : " + factionName);
    }
    
    private boolean isPlayerInFaction(Player player) {
        // V�rifie si le joueur appartient d�j� � une faction en parcourant toutes les factions
        for (Faction faction : OnePieceCraft.getMAIN().factions) {
            if (faction.getMembers().contains(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    private boolean isFactionExists(String factionName) {
        // V�rifie si une faction portant le m�me nom existe d�j� en parcourant toutes les factions
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
        // V�rifier si le joueur qui ex�cute la commande est le chef d'une faction
        Faction faction = getFactionByLeader(player.getUniqueId());
        if (faction == null) {
            player.sendMessage("�cVous devez �tre le chef d'une faction pour inviter des joueurs.");
            return;
        }

        // V�rifier si le joueur a d�j� une faction
        if (isPlayerInFaction(playerName)) {
            player.sendMessage("�cLe joueur �b" + playerName + "�c appartient d�j� � une faction.");
            return;
        }

        // V�rifier si le nombre de joueurs dans la faction est inf�rieur � 15
        if (faction.getMembers().size() >= 15) {
            player.sendMessage("�cVotre faction a d�j� atteint le nombre maximum de membres (15).");
            return;
        }

        // V�rifier si le joueur a d�j� une invitation en cours
        if (OnePieceCraft.MAIN.pendingInvitations.containsKey(playerName)) {
            long expirationTime = OnePieceCraft.MAIN.pendingInvitations.get(playerName);
            if (System.currentTimeMillis() < expirationTime) {
                player.sendMessage("�aVous avez d�j� invit� �b" + playerName + "�a. Veuillez attendre la fin de l'invitation en cours.");
                return;
            } else {
                // Si l'invitation a expir�, supprimer l'invitation en cours
            	OnePieceCraft.MAIN.pendingInvitations.remove(playerName);
                player.sendMessage("�cL'invitation envoy�e � �b" + playerName + "�c a expir�.");
            }
        }

        // Ajouter le joueur invit� � la faction
        UUID invitedPlayerUUID = getPlayerUUID(playerName);
        if (invitedPlayerUUID != null) {
            saveFactions();
            player.sendMessage("�aVous avez invit� �b" + playerName + "�a � rejoindre votre faction.");
            Player invitedPlayer = Bukkit.getPlayer(invitedPlayerUUID);
            if (invitedPlayer != null) {
                invitedPlayer.sendMessage("�aVous avez �t� invit� � rejoindre la faction �b" + faction.getName() + "�a. Utilisez /f join pour rejoindre.");
            }

            // Ajouter l'invitation � la HashMap avec une expiration de 30 secondes
            long expirationTime = System.currentTimeMillis() + 30 * 1000; // 30 secondes en millisecondes
            OnePieceCraft.MAIN.pendingInvitations.put(playerName, expirationTime);
            OnePieceCraft.MAIN.pendingInvitations.put(player.getName(), expirationTime);
        } else {
            player.sendMessage("�cLe joueur �b" + playerName + "�c n'existe pas ou n'est pas en ligne.");
        }
    }

    private UUID getPlayerUUID(String playerName) {
        Player invitedPlayer = Bukkit.getPlayerExact(playerName);
        if (invitedPlayer != null) {
            return invitedPlayer.getUniqueId();
        } else {
            // Si le joueur n'est pas en ligne, essayons de r�cup�rer son UUID � partir de l'historique des noms
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
        // V�rifier si le joueur a d�j� une faction
        if (isPlayerInFaction(player)) {
            player.sendMessage("�cVous �tes d�j� dans une faction. Vous devez quitter votre faction actuelle pour rejoindre une autre faction.");
            return;
        }

        // V�rifier si le joueur a une invitation en cours pour rejoindre la faction sp�cifi�e
        if (!OnePieceCraft.MAIN.pendingInvitations.containsKey(player.getName())) {
            player.sendMessage("�cVous n'avez pas d'invitation en cours pour rejoindre cette faction.");
            return;
        }

        // V�rifier si l'invitation n'a pas expir�
        long expirationTime = OnePieceCraft.MAIN.pendingInvitations.get(player.getName());
        if (System.currentTimeMillis() >= expirationTime) {
            OnePieceCraft.MAIN.pendingInvitations.remove(player.getName());
            player.sendMessage("�cL'invitation pour rejoindre la faction a expir�.");
            return;
        }

        // Trouver la faction par son nom ou par le pseudo d'un de ses membres
        Faction faction = getFactionByNameOrMember(player, factionName);
        if (faction == null) {
            player.sendMessage("�cAucune faction portant ce nom ou dont l'un des membres a ce pseudo n'existe.");
            return;
        }

        // Ajouter le joueur � la faction
        faction.addMember(player.getUniqueId());

        // Supprimer l'invitation en cours
        OnePieceCraft.MAIN.pendingInvitations.remove(player.getName());

        // Sauvegarder les informations de la faction dans le fichier config.yml
        saveFactions();

        player.sendMessage("�aVous avez rejoint la faction �b" + faction.getName() + "�a avec succ�s !");
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
            player.sendMessage("�cVous devez �tre le chef d'une faction pour passer le r�le de chef � un membre.");
            return;
        }
        
        UUID newLeaderUUID = getPlayerUUID(newLeaderName);
        if (newLeaderUUID == null || !faction.isMember(newLeaderUUID)) {
            player.sendMessage("�cLe joueur �b" + newLeaderName + "�c n'appartient pas � votre faction ou n'existe pas.");
            return;
        }

        faction.setLeader(newLeaderUUID);
        saveFactions();

        player.sendMessage("�aVous avez pass� le r�le de chef � �b" + newLeaderName + "�a. Vous �tes maintenant membre de la faction.");
        Player newLeaderPlayer = Bukkit.getPlayer(newLeaderUUID);
        if (newLeaderPlayer != null) {
            newLeaderPlayer.sendMessage("�aVous �tes maintenant le chef de la faction �b" + faction.getName() + "�a.");
        }
    }
    
    private void leaveFaction(Player player) {
        Faction faction = Faction.getPlayerFaction(player.getUniqueId());
        if (faction == null) {
            player.sendMessage("�cVous ne pouvez pas quitter de faction car vous n'en avez pas.");
            return;
        }

        UUID playerUUID = player.getUniqueId();
        if (faction.getLeader().equals(playerUUID)) {
            player.sendMessage("�cVous �tes le chef de la faction. Vous devez d'abord passer le r�le de chef � un autre membre pour quitter la faction.");
            return;
        }

        // Retirer le joueur de la faction
        faction.removeMember(playerUUID);

        // Sauvegarder les informations de la faction dans le fichier config.yml
        saveFactions();

        player.sendMessage("�aVous avez quitt� la faction �b" + faction.getName() + "�a.");

        // Informer le chef de la faction du d�part du membre (ancien chef)
        Player leaderPlayer = Bukkit.getPlayer(faction.getLeader());
        if (leaderPlayer != null) {
            leaderPlayer.sendMessage("�b" + player.getName() + "�a a quitt� la faction.");
        }
    }


}
