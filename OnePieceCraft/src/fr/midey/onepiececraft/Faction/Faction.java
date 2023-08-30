package fr.midey.onepiececraft.Faction;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.midey.onepiececraft.OnePieceCraft;

public class Faction {

    private String name;
    private UUID leader;
    private List<UUID> members;

    public Faction(String name, UUID leader) {
        this.name = name;
        this.leader = leader;
        this.members = new ArrayList<>();
        this.members.add(leader);
    }

    public String getName() {
        return name;
    }

    public UUID getLeader() {
        return leader;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public boolean isMember(UUID playerUUID) {
        return members.contains(playerUUID);
    }
    
    public void addMember(UUID playerName) {
        members.add(playerName);
    }
    
    public void deleteMember(UUID playerName) {
    	members.remove(playerName);
    }

    public void setLeader(UUID newLeader) {
        this.leader = newLeader;
    }
    
    public void removeMember(UUID playerUUID) {
        members.remove(playerUUID);
    }
    
    public static Faction getPlayerFaction(UUID playerUUID) {
        for (Faction faction : OnePieceCraft.getMAIN().factions) {
            if (faction.isMember(playerUUID)) {
                return faction;
            }
        }
        return null;
    }
}
