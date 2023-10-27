package fr.midey.OnePieceCraftSkills;

import java.util.UUID;

public class PlayerData {
	private UUID uuid;
    private int hakiArmement = 0;
    private int hakiRoi = 0;
    private int hakiObservation = 0;
    private int weaponPoints = 0;
    private String weaponSkillLow = "";
    private String weaponSkillHigh = "";
    private int experience = 0;
    private int level = 0;
    private int skillPoints = 0;
    private int directionLowSkill = 1; //1 -> left to right | -1 -> right to left
    private int endurance = 0;
    private boolean observationHakiActive = false;
    private boolean hakiRoiActive = false;
    private long cooldownsHakiArmement = 0;
    private long cooldownsHakiRoi;
	private int experienceToNextLevel = 0;
	private boolean isInCooldown = false;
	private boolean isInDemonSlash = false;

    // Getters et setters
    public int getHakiArmement() { return hakiArmement; }
    public void setHakiArmement(int value) { this.hakiArmement = value; }

    public int getHakiRoi() { return hakiRoi; }
    public void setHakiRoi(int value) { this.hakiRoi = value; }

    public int getHakiObservation() { return hakiObservation; }
    public void setHakiObservation(int value) { this.hakiObservation = value; }

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
	
	public int getWeaponPoints() { return weaponPoints; }
	public void setWeaponPoints(int weaponPoints) { this.weaponPoints = weaponPoints; }
	
	public String getWeaponSkillLow() { return weaponSkillLow; }
	public void setWeaponSkillLow(String weaponSkillLow) { this.weaponSkillLow = weaponSkillLow; }
	
	public String getWeaponSkillHigh() { return weaponSkillHigh; }
	public void setWeaponSkillHigh(String weaponSkillHigh) { this.weaponSkillHigh = weaponSkillHigh; }
	
	public int getDirectionLowSkill() { return directionLowSkill; }
	public void setDirectionLowSkill(int directionLowSkill) { this.directionLowSkill = directionLowSkill; }
	
	public int getEndurance() { return endurance; }
	public void setEndurance(int endurance) { this.endurance = endurance; }
	
	public int getExperienceToNextLevel() { return experienceToNextLevel; }
	public void setExperienceToNextLevel(int experienceToNextLevel) { this.experienceToNextLevel = experienceToNextLevel; }
	
	public boolean isInCooldown() { return isInCooldown; }
	public void setInCooldown(boolean isInCooldown) { this.isInCooldown = isInCooldown; }
	
	public boolean isInDemonSlash() { return isInDemonSlash; }
	public void setInDemonSlash(boolean isInDemonSlash) { this.isInDemonSlash = isInDemonSlash; }
	
}
