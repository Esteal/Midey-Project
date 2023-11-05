package fr.midey.OnePieceCraftSkills.Skills.UtilsSkill;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;

public class SkillLevelUpManager {
    
    private OnePieceCraftSkills plugin;
    
    public SkillLevelUpManager(OnePieceCraftSkills plugin) {
        this.plugin = plugin;
    }

    private PlayerData getPlayerData(Player player) {
        return plugin.getPlayerData(player);
    }

    private void handleSkillLevelUp(Player player, 
                                    DoubleSupplier getChanceToLevelUp, 
                                    DoubleConsumer setChanceToLevelUp, 
                                    IntSupplier getSkillLevel, 
                                    IntConsumer setSkillLevel) {
        double chance = getChanceToLevelUp.getAsDouble();
        int level = getSkillLevel.getAsInt();

        double randomNumber = Math.random() / 4.0;
        if (level >= 3 && (chance + randomNumber) >= 100) {
        	setChanceToLevelUp.accept(100);
        	return;
        }
        setChanceToLevelUp.accept(chance + randomNumber);

        if (chance + randomNumber >= 100) {
            setSkillLevel.accept(level + 1);
            setChanceToLevelUp.accept(0);
            player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
            if(level == 0)
            	player.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.GRAY + "Vous venez d'acquérir une nouvelle compétence !" );
            else
            	player.sendMessage(ChatColor.GREEN + "➤ " + ChatColor.GRAY + "Une compétence viens de monter de niveau !" );
        }
    }

    public void handleDemonSlashLevelUp(Player player) {
        PlayerData playerData = getPlayerData(player);
        handleSkillLevelUp(player, 
            playerData::getChanceToDemonSlashLevelUp, 
            playerData::setChanceToDemonSlashLevelUp, 
            playerData::getDemonSlashLevel, 
            playerData::setDemonSlashLevel);
    }

    public void handleSlashLevelUp(Player player) {
        PlayerData playerData = getPlayerData(player);
        handleSkillLevelUp(player, 
            playerData::getChanceToSlashLevelUp, 
            playerData::setChanceToSlashLevelUp, 
            playerData::getSlashLevel, 
            playerData::setSlashLevel);
    }

    public void handleIncisionLevelUp(Player player) {
        PlayerData playerData = getPlayerData(player);
        handleSkillLevelUp(player, 
            playerData::getChanceToIncisionLevelUp, 
            playerData::setChanceToIncisionLevelUp, 
            playerData::getIncisionLevel, 
            playerData::setIncisionLevel);
    }

    public void handleFlambageShootLevelUp(Player player) {
        PlayerData playerData = getPlayerData(player);
        handleSkillLevelUp(player, 
            playerData::getChanceToFlambageShootLevelUp, 
            playerData::setChanceToFlambageShootLevelUp, 
            playerData::getFlambageShootLevel, 
            playerData::setFlambageShootLevel);
    }

    public void handlePasDeluneLevelUp(Player player) {
        PlayerData playerData = getPlayerData(player);
        handleSkillLevelUp(player, 
            playerData::getChanceToPasDeluneLevelUp, 
            playerData::setChanceToPasDeluneLevelUp, 
            playerData::getPasDeluneLevel, 
            playerData::setPasDeluneLevel);
    }
    
    public void handleHakiDesRoisLevelUp(Player player) {
        PlayerData playerData = getPlayerData(player);
        handleSkillLevelUp(player, 
            playerData::getChanceToHakiDesRoisLevelUp, 
            playerData::setChanceToHakiDesRoisLevelUp, 
            playerData::getHakiDesRoisLevel, 
            playerData::setHakiDesRoisLevel);
    }

    public void handleHakiArmementLevelUp(Player player) {
        PlayerData playerData = getPlayerData(player);
        handleSkillLevelUp(player, 
            playerData::getChanceToHakiArmementLevelUp, 
            playerData::setChanceToHakiArmementLevelUp, 
            playerData::getHakiArmementLevel, 
            playerData::setHakiArmementLevel);
    }
    
    public void handleHakiObservationLevelUp(Player player) {
        PlayerData playerData = getPlayerData(player);
        handleSkillLevelUp(player, 
            playerData::getChanceToHakiObservationLevelUp, 
            playerData::setChanceToHakiObservationLevelUp, 
            playerData::getHakiObservationLevel, 
            playerData::setHakiObservationLevel);
    }
}
