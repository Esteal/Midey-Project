package fr.midey.OnePieceCraftSkills.Utils;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;
import fr.midey.OnePieceCraftSkills.Endurance.EnduranceManager;
import fr.midey.OnePieceCraftSkills.HakiManager.HakiArmement;
import fr.midey.OnePieceCraftSkills.HakiManager.HakiObservation;
import fr.midey.OnePieceCraftSkills.HakiManager.HakiRoi;
import fr.midey.OnePieceCraftSkills.Skills.SkillHighRank.DemonSlash;
import fr.midey.OnePieceCraftSkills.Skills.SkillHighRank.FlambageShoot;
import fr.midey.OnePieceCraftSkills.Skills.SkillLowRank.Incision;
import fr.midey.OnePieceCraftSkills.Skills.SkillLowRank.Slash;
import fr.midey.OnePieceCraftSkills.Skills.UtilsSkill.CheckSkill;
import fr.midey.OnePieceCraftSkills.Skills.UtilsSkill.SkillLevelUpManager;
import net.md_5.bungee.api.ChatColor;

public class SequenceManager implements Listener {

	private OnePieceCraftSkills plugin;
	
    private int demonSlashCost = 65, demonSlashSurchauffe = 45; // Cout : 65 | surchauffe si moins de 45 d'endurance
    private int slashCost = 30, slashSurchauffe = 15; // Cout : 30 | surchauffe si moins de 15 d'endurance
    private int flambageShotCost = 65, flambageShotSurchauffe = 45; // Cout : 65 | surchauffe si moins de 45 d'endurance
    private int incisionCost = 40, incisionShotSurchauffe = 20; // Cout : 40 | surchauffe si moins de 20 d'endurance
    private int hakiRoiCost = 90, hakiRoiSurchauffe = 90; // Cout : 90 | surchauffe si moins de 100 d'endurance
    private int hakiArmementCost = 20, hakiArmementSurchauffe = 10; // Cout : 20 | surchauffe si moins de 10 d'endurance
    private int hakiObservationCost = 35, hakiObservationSurchauffe = 10; // Cout : 35 | surchauffe si moins de 10 d'endurance

    public SequenceManager(OnePieceCraftSkills plugin) {
		this.plugin = plugin;
	}
    
    
	/**
     *LA SEQUENCE DOIT TJR COMMENCER PAR D ET FINIR PAR G 
     **/
    @EventHandler
    public void checkSequence(PlayerInteractEvent event) {
    	if(event.getItem() == null) return;
    	Material material = event.getItem().getType();
    	if(!plugin.getTools().contains(material)) return;
    	if (event.getHand() != EquipmentSlot.HAND || material == Material.BOW) return;
        if(!isClick(event)) return;
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerData(player);
        
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
        	Consumer<PlayerInteractEvent> skill = playerData.getSequenceToSkillMap().get(actualSequence);
            if (skill != null) {
                skill.accept(event);
            }
        	player.sendMessage(ChatColor.GREEN + actualSequence);
			actualSequence = "";
        }
        if(!actualSequence.equals(""))
        	player.sendMessage(ChatColor.GREEN + actualSequence);
        playerData.setSequence(actualSequence);
    }
    
    public void setSequenceForSkill(OnePieceCraftSkills plugin, Player player, String sequence, String skill) {
        PlayerData playerData = plugin.getPlayerData(player);
        EnduranceManager enduranceManager = plugin.getEnduranceManager();
        
        if(!plugin.getAllSkills().contains(skill)) return;

        Consumer<PlayerInteractEvent> action = getSkillAction(plugin, player, skill, enduranceManager);

        if (action == null) {
            player.sendMessage(ChatColor.RED + "➤ " + ChatColor.GRAY + "La compétence n'a pas été associée à la séquence, veuillez contacter un administrateur");
            return;
        }

        playerData.setSkillForSequence(sequence, action);
        player.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.GRAY + "Compétence " + "§f" + skill + "§7 associée à la séquence " + "§f" + sequence + "§7 pour le joueur " + player.getName());
    }

    private Consumer<PlayerInteractEvent> getSkillAction(OnePieceCraftSkills plugin, Player player, String skill, EnduranceManager enduranceManager) {
        SkillLevelUpManager levelUpManager = new SkillLevelUpManager(plugin);

        switch (skill) {
	        case "Haki des rois":
	            return generateSkillConsumer(plugin, player, hakiRoiSurchauffe, hakiRoiCost,
	                (p) -> new HakiRoi(plugin).onPlayerUseHakiRoi(p),
	                levelUpManager::handleHakiDesRoisLevelUp);
	        case "Haki de l'observation":
	            return generateSkillConsumer(plugin, player, hakiObservationSurchauffe, hakiObservationCost,
	                (p) -> new HakiObservation(plugin).hakiObservation(p),
	                levelUpManager::handleHakiObservationLevelUp);
	        case "Haki de l'armement":
	            return generateSkillConsumer(plugin, player, hakiArmementSurchauffe, hakiArmementCost,
	                (p) -> new HakiArmement(plugin).onPlayerUseHakiArmement(p),
	                levelUpManager::handleHakiArmementLevelUp);
            case "demon slash":
                return generateSkillConsumer(plugin, player, demonSlashSurchauffe, demonSlashCost, 
                    (p) -> new DemonSlash(plugin, new CheckSkill(plugin)).demonSlash(p),
                    levelUpManager::handleDemonSlashLevelUp);
            case "slash":
                return generateSkillConsumer(plugin, player, slashSurchauffe, slashCost, 
                    (p) -> new Slash(plugin, new CheckSkill(plugin)).createSlash(p),
                    levelUpManager::handleSlashLevelUp);
            case "incision":
                return generateSkillConsumer(plugin, player, incisionShotSurchauffe, incisionCost, 
                    (p) -> new Incision(plugin).incision(p),
                    levelUpManager::handleIncisionLevelUp);
            case "flambage shoot":
                return generateSkillConsumer(plugin, player, flambageShotSurchauffe, flambageShotCost, 
                    (p) -> new FlambageShoot(plugin, new CheckSkill(plugin)).flambageShoot(p),
                    levelUpManager::handleFlambageShootLevelUp);
            // Ajouter d'autres compétences ici
            default:
                return null;
        }
    }

    private Consumer<PlayerInteractEvent> generateSkillConsumer(OnePieceCraftSkills plugin, Player player, int surchauffe, int cost, Consumer<Player> skillFunction, Consumer<Player> levelUpFunction) {
        return (event) -> {
            if (canExecuteSkill(player, surchauffe)) {
                plugin.getEnduranceManager().useEndurance(player, cost);
                
                // Exécute la fonction de compétence si elle n'est pas null
                if (skillFunction != null) {
                    skillFunction.accept(player);
                }

                // Exécute la fonction d'augmentation de niveau si elle n'est pas null
                if (levelUpFunction != null) {
                    levelUpFunction.accept(player);
                }
            }
        };
    }
    
    private boolean canExecuteSkill(Player player, int surchauffe) {
	    if (!plugin.getEnduranceManager().canUseSkill(player, surchauffe) || plugin.getPlayerData(player).isInCooldown()) {
	        putInCooldown(player);
	        return false;
	    }
	    return true;
	}
    
    public void putInCooldown(Player player) {
 		player.sendMessage(ChatColor.RED + "➤ " + ChatColor.GRAY + "Votre corps a surchauffé, vous aurez besoin de temps pour vous en remettre.");	
         plugin.getPlayerData(player).setInCooldown(true);
         Bukkit.getScheduler().runTaskLater(plugin, () -> {
     		player.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.GRAY + "Vous êtes de nouveau prêt à combattre !");
	            plugin.getPlayerData(player).setInCooldown(false);
         }, 20 * 15);
	}
    
    private boolean isCorrectClick(String acutalSequence) {
    	boolean isCorrect = false;
    	for(String possibility : plugin.getSequencesPossible()) {
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
