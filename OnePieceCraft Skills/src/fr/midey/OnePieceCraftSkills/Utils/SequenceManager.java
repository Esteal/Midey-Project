package fr.midey.OnePieceCraftSkills.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;
import fr.midey.OnePieceCraftSkills.HakiManager.HakiArmement;
import fr.midey.OnePieceCraftSkills.HakiManager.HakiRoi;
import net.md_5.bungee.api.ChatColor;

public class SequenceManager implements Listener {

	private OnePieceCraftSkills plugin;
	private final String[] sequencesPossible = {"DGGDG", // -> Haki des rois
												"DGDGG",
												"DDDDG",
												"DDGGG",
												"DDGDG",
												"DDDGG", // -> Haki armement
												"DGDDG",
												"DGGGG"};

    public SequenceManager(OnePieceCraftSkills plugin) {
		this.plugin = plugin;
	}
    
    
	/**
     *LA SEQUENCE DOIT TJR COMMENCER PAR D ET FINIR PAR G 
     **/
    @EventHandler
    public void checkSequence(PlayerInteractEvent event) {
    	if (event.getHand() != EquipmentSlot.HAND) return;
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerData(player);
        if(!isClick(event)) return;
        
        String clickType = getClickType(event);
        String actualSequence = playerData.getSequence();
        
        actualSequence += clickType;
        
        if(actualSequence.equals("G"))
        	actualSequence = "";
        
        if (!isCorrectClick(actualSequence)) {
        	player.sendMessage(ChatColor.RED + actualSequence);
        	actualSequence = "";
        }
        
        if(actualSequence.length() >= 5) {
        	switch(actualSequence) {	
        		case "DGGDG": // -> Haki des rois
        			new HakiRoi(plugin).onPlayerUseHakiRoi(event);
        			break;
        		case "DGDGG":
        			break;
        		case "DDDDG":
        			break;
        		case "DDGGG":
        			break;
        		case "DDGDG":
        			break;
        		case "DDDGG": // -> Haki de l'armement
        			new HakiArmement(plugin).onPlayerUseHakiArmement(event);
        			break;
        		case "DGDDG":
        			break;
        		case "DGGGG":
        			break;
        		default :
        			break;
        	}
        	player.sendMessage(ChatColor.GREEN + actualSequence);
			actualSequence = "";
        }
        if(!actualSequence.equals(""))
        	player.sendMessage(ChatColor.GREEN + actualSequence);
        playerData.setSequence(actualSequence);
    }

    private boolean isCorrectClick(String acutalSequence) {
    	boolean isCorrect = false;
    	for(String possibility : sequencesPossible) {
    		if(possibility.startsWith(acutalSequence)) {
    			isCorrect = true;
    			break;
    		}
    	}
    	
    	return isCorrect;
    }
    
    private boolean isClick(PlayerInteractEvent event) {
    	Action action = event.getAction();
    	if((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) || (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK)) {
    		return true;
    	}
    	return false;
    }
    
    private String getClickType(PlayerInteractEvent event) {
        switch (event.getAction()) {
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                return "D";
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                return "G";
            default:
                return "";
        }
    }
}
