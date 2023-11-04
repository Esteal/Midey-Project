package fr.midey.OnePieceCraftSkills;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerData {
	
	private UUID uuid;
    
    private long cooldownsHakiRoi;
    private boolean observationHakiActive = false;
    private boolean hakiRoiActive = false;
    private long cooldownsHakiArmement = 0;
    private long cooldownFailSequence = 0;
    
    private String sequence = ""; //Stock les cliques de souris pour activer des compétences
    private Map<String, Consumer<PlayerInteractEvent>> sequenceToSkillMap = new HashMap<>();

    private int experience = 0;
    private int experienceToNextLevel = 1000;
    private int level = 1;
    private int skillPoints = 0;
    
    private int directionLowSkill = 1; //1 -> left to right | -1 -> right to left
    private double endurance = 0;
    private double enduranceMax = 100;
	
    //Niveau des compétences
    private int demonSlashLevel = 0;
    private int slashLevel = 1;
    private int incisionLevel = 0;
    private int pasDeluneLevel = 0;
    private int flambageShootLevel = 0;
    private int hakiDesRoisLevel = 0;
    private int hakiArmementLevel = 0;
    private int hakiObservationLevel = 0;
    
    private double chanceToDemonSlashLevelUp = 0;
    private double chanceToSlashLevelUp = 0;
    private double chanceToIncisionLevelUp = 0;
    private double chanceToPasDeluneLevelUp = 0;
    private double chanceToFlambageShootLevelUp = 0;
    private double chanceToHakiDesRoisLevelUp = 0;
    private double chanceToHakiArmementLevelUp = 0;
    private double chanceToHakiObservationLevelUp = 0;

    private boolean isInCooldown = false;
	private boolean isInDemonSlash = false;

    // Getters et setters

    public int getExperience() { return experience; }
    public void setExperience(int value) { this.experience = value; }

    public int getLevel() { return level; }
    public void setLevel(int value) { this.level = value; }

    public int getSkillPoints() { return skillPoints; }
    public void setSkillPoints(int value) { this.skillPoints = value; }

    public boolean isObservationHakiActive() { return observationHakiActive; }
    public void setObservationHakiActive(boolean value) { this.observationHakiActive = value; }

    public long getCooldownsHakiArmement() { return cooldownsHakiArmement; }
    public void setCooldownsHakiArmement(long value) { this.cooldownsHakiArmement = value; }
	
    public UUID getUuid() { return uuid; }
	public void setUuid(UUID uuid) { this.uuid = uuid; }
	
	public boolean isHakiRoiActive() { return hakiRoiActive; }
	public void setHakiRoiActive(boolean hakiRoiActive) { this.hakiRoiActive = hakiRoiActive; }
	
	public long getCooldownsHakiRoi() { return cooldownsHakiRoi; }
	public void setCooldownsHakiRoi(long cooldownsHakiRoi) { this.cooldownsHakiRoi = cooldownsHakiRoi; }
	
	public int getDirectionLowSkill() { return directionLowSkill; }
	public void setDirectionLowSkill(int directionLowSkill) { this.directionLowSkill = directionLowSkill; }
	
	public double getEndurance() { return endurance; }
	public double getEnduranceMax() { return enduranceMax;}
	public void setEnduranceMax(double enduranceMax) { this.enduranceMax = enduranceMax; }
	public void setEndurance(double endurance) { this.endurance = endurance; }
	
	public int getExperienceToNextLevel() { return experienceToNextLevel; }
	public void setExperienceToNextLevel(int experienceToNextLevel) { this.experienceToNextLevel = experienceToNextLevel; }
	
	public boolean isInCooldown() { return isInCooldown; }
	public void setInCooldown(boolean isInCooldown) { this.isInCooldown = isInCooldown; }
	
	public boolean isInDemonSlash() { return isInDemonSlash; }
	public void setInDemonSlash(boolean isInDemonSlash) { this.isInDemonSlash = isInDemonSlash; }
	
	public String getSequence() { return sequence; }
	public void setSequence(String sequence) { this.sequence = sequence; }
	
	public long getCooldownFailSequence() { return cooldownFailSequence; }
	public void setCooldownFailSequence(long cooldownFailSequence) { this.cooldownFailSequence = cooldownFailSequence; }
	
    public Map<String, Consumer<PlayerInteractEvent>> getSequenceToSkillMap() { return sequenceToSkillMap; }
    public void setSkillForSequence(String sequence, Consumer<PlayerInteractEvent> skill) { sequenceToSkillMap.put(sequence, skill); }
	
    public int getDemonSlashLevel() { return demonSlashLevel; }
	public void setDemonSlashLevel(int demonSlashLevel) { this.demonSlashLevel = demonSlashLevel; }
	public int getSlashLevel() { return slashLevel; }
	public void setSlashLevel(int slashLevel) { this.slashLevel = slashLevel; }
	public int getIncisionLevel() { return incisionLevel; }
	public void setIncisionLevel(int incisionLevel) { this.incisionLevel = incisionLevel; }
	public int getFlambageShootLevel() { return flambageShootLevel; }
	public void setFlambageShootLevel(int flambageShootLevel) {this.flambageShootLevel = flambageShootLevel; }
	public int getPasDeluneLevel() { return pasDeluneLevel; }
	public void setPasDeluneLevel(int pasDeluneLevel) { this.pasDeluneLevel = pasDeluneLevel; }
	public int getHakiDesRoisLevel() { return hakiDesRoisLevel; }
	public void setHakiDesRoisLevel(int hakiDesRoisLevel) { this.hakiDesRoisLevel = hakiDesRoisLevel; }
	public int getHakiArmementLevel() { return hakiArmementLevel; }
	public void setHakiArmementLevel(int hakiArmementLevel) { this.hakiArmementLevel = hakiArmementLevel; }
	public int getHakiObservationLevel() { return hakiObservationLevel; }
	public void setHakiObservationLevel(int hakiObservationLevel) { this.hakiObservationLevel = hakiObservationLevel; }
	
	public double getChanceToDemonSlashLevelUp() { return chanceToDemonSlashLevelUp; }
	public void setChanceToDemonSlashLevelUp(double chanceToDemonSlashLevelUp) { this.chanceToDemonSlashLevelUp = chanceToDemonSlashLevelUp; }
	public double getChanceToSlashLevelUp() { return chanceToSlashLevelUp; }
	public void setChanceToSlashLevelUp(double chanceToSlashLevelUp) { this.chanceToSlashLevelUp = chanceToSlashLevelUp; }
	public double getChanceToIncisionLevelUp() { return chanceToIncisionLevelUp; }
	public void setChanceToIncisionLevelUp(double chanceToIncisionLevelUp) { this.chanceToIncisionLevelUp = chanceToIncisionLevelUp; }
	public double getChanceToPasDeluneLevelUp() { return chanceToPasDeluneLevelUp; }
	public void setChanceToPasDeluneLevelUp(double chanceToPasDeluneLevelUp) { this.chanceToPasDeluneLevelUp = chanceToPasDeluneLevelUp; }
	public double getChanceToFlambageShootLevelUp() { return chanceToFlambageShootLevelUp; }
	public void setChanceToFlambageShootLevelUp(double chanceToFlambageShootLevelUp) { this.chanceToFlambageShootLevelUp = chanceToFlambageShootLevelUp; }
	public double getChanceToHakiDesRoisLevelUp() { return chanceToHakiDesRoisLevelUp; }
	public void setChanceToHakiDesRoisLevelUp(double chanceToHakiDesRoisLevelUp) { this.chanceToHakiDesRoisLevelUp = chanceToHakiDesRoisLevelUp; }
	public double getChanceToHakiArmementLevelUp() { return chanceToHakiArmementLevelUp; }
	public void setChanceToHakiArmementLevelUp(double chanceToHakiArmementLevelUp) { this.chanceToHakiArmementLevelUp = chanceToHakiArmementLevelUp; }
	public double getChanceToHakiObservationLevelUp() { return chanceToHakiObservationLevelUp; }
	public void setChanceToHakiObservationLevelUp(double chanceToHakiObservationLevelUp) { this.chanceToHakiObservationLevelUp = chanceToHakiObservationLevelUp; }
	


}
