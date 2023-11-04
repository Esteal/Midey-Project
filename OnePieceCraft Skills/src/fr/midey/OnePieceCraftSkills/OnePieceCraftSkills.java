package fr.midey.OnePieceCraftSkills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.midey.OnePieceCraftSkills.Commands.MasterCommand;
import fr.midey.OnePieceCraftSkills.Endurance.EnduranceManager;
import fr.midey.OnePieceCraftSkills.HakiManager.HakiObservation;
import fr.midey.OnePieceCraftSkills.HakiManager.HakiOnOff;
import fr.midey.OnePieceCraftSkills.LevelManager.LevelSystem;
import fr.midey.OnePieceCraftSkills.Skills.SecondSword;
import fr.midey.OnePieceCraftSkills.Skills.SkillHighRank.DemonSlash;
import fr.midey.OnePieceCraftSkills.Skills.SkillLowRank.PasDeLune;
import fr.midey.OnePieceCraftSkills.Skills.UtilsSkill.CheckSkill;
import fr.midey.OnePieceCraftSkills.Utils.SequenceManager;
import fr.midey.OnePieceCraftSkills.Utils.TheTabCompleter;

/**
 * Main class of the OnePieceCraftSkills plugin.
 */
public class OnePieceCraftSkills extends JavaPlugin implements Listener {

    // Maps to keep track of various player data
	private final HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();
    private final HashSet<UUID> activeHakiArmement = new HashSet<>();
	private final HashSet<UUID> activeHakiRoi = new HashSet<>();
	private List<String> lowSkills;
	private List<String> swordSkills;
	private List<String> highSkills;
    private List<String> allSkills;
    private List<Material> tools;
    private List<LivingEntity> entityTouchByWeaponSkill_1;
    private List<LivingEntity> entityTouchByWeaponSkill_2;
    private List<Material> airAndFlowers;
	private List<String> sequencesPossible;
	private EnduranceManager enduranceManager;
	private SequenceManager sequenceManager;
	
    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        registerEvents();
        registerCommands();
        registerVar();
        for(Player players : Bukkit.getOnlinePlayers()) {
        	if(!playerDataMap.containsKey(players.getUniqueId()))
        		playerDataMap.put(players.getUniqueId(), new PlayerData());
        	removeAttackDelay(players);
    	}
    }

	private void registerVar() {
		HakiObservation hakiObservation = new HakiObservation(this);
        hakiObservation.HakiObservations();
        this.setEnduranceManager(new EnduranceManager(this));
        sequenceManager = new SequenceManager(this);
        this.entityTouchByWeaponSkill_1 = new ArrayList<LivingEntity>();
        this.entityTouchByWeaponSkill_2 = new ArrayList<LivingEntity>();
        highSkills = Arrays.asList("demon slash", "flambage shoot");
        lowSkills = Arrays.asList("slash", "pas de lune", "incision");
        swordSkills = Arrays.asList("slash", "demon slash");
        allSkills = new ArrayList<> (highSkills);
        allSkills.addAll(lowSkills);
        allSkills.add("Haki des rois");
        allSkills.add("Haki de l'armement");
        this.airAndFlowers = Arrays.asList(
        	    Material.AIR,  Material.CAVE_AIR,  Material.VOID_AIR, Material.DANDELION,
        	    Material.POPPY, Material.BLUE_ORCHID, Material.ALLIUM, Material.AZURE_BLUET,
        	    Material.RED_TULIP, Material.ORANGE_TULIP, Material.WHITE_TULIP, Material.PINK_TULIP,
        	    Material.OXEYE_DAISY, Material.CORNFLOWER, Material.LILY_OF_THE_VALLEY, Material.WITHER_ROSE, 
        	    Material.SUNFLOWER, Material.LILAC, Material.ROSE_BUSH, Material.PEONY,
        	    Material.GRASS ,Material.TALL_GRASS, Material.FERN, Material.LARGE_FERN, 
        	    Material.VINE, Material.SEAGRASS, Material.TALL_SEAGRASS, Material.KELP, 
        	    Material.KELP_PLANT, Material.MOSS_CARPET, Material.MOSS_BLOCK, Material.DEAD_BUSH,
        	    Material.BAMBOO, Material.BAMBOO_SAPLING, Material.WATER
        	);
        this.tools = Arrays.asList(
        	    // Épées
        	    Material.DIAMOND_SWORD, Material.GOLDEN_SWORD, Material.IRON_SWORD,
        	    Material.NETHERITE_SWORD, Material.STONE_SWORD, Material.WOODEN_SWORD,
        	    // Pelles
        	    Material.DIAMOND_SHOVEL, Material.GOLDEN_SHOVEL, Material.IRON_SHOVEL,
        	    Material.NETHERITE_SHOVEL, Material.STONE_SHOVEL, Material.WOODEN_SHOVEL,
        	    // Pioches
        	    Material.DIAMOND_PICKAXE, Material.GOLDEN_PICKAXE, Material.IRON_PICKAXE,
        	    Material.NETHERITE_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE,
        	    // Haches
        	    Material.DIAMOND_AXE, Material.GOLDEN_AXE, Material.IRON_AXE,
        	    Material.NETHERITE_AXE, Material.STONE_AXE, Material.WOODEN_AXE,
        	    // Hoes
        	    Material.DIAMOND_HOE, Material.GOLDEN_HOE, Material.IRON_HOE,
        	    Material.NETHERITE_HOE, Material.STONE_HOE, Material.WOODEN_HOE
        	);
        this.sequencesPossible = Arrays.asList("DGGDG", "DGDGG", "DDDDG", "DDGGG", 
        									   "DDGDG", "DDDGG", "DGDDG", "DGGGG");
	}

	/**
     * Register all event classes.
     */
    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new LevelSystem(this), this);
        pm.registerEvents(new SequenceManager(this), this);
        pm.registerEvents(new DamageManager(this), this);
        pm.registerEvents(new CheckSkill(this), this);
        pm.registerEvents(new SecondSword(this), this);
        pm.registerEvents(new PasDeLune(this), this);
        pm.registerEvents(new DemonSlash(this, new CheckSkill(this)), this);
        pm.registerEvents(this, this);
    }

    
    /**
     * Register all command executors.
     */
    private void registerCommands() {
        this.getCommand("opc").setExecutor(new MasterCommand(this));
        this.getCommand("haki").setExecutor(new HakiOnOff(this));
        this.getCommand("opc").setTabCompleter(new TheTabCompleter(this));
        this.getCommand("haki").setTabCompleter(new TheTabCompleter(this));
    }
    
    /**
     * Add Haki points to a player.
     *
     * @param player   The player to add points to.
     * @param hakiType The type of Haki.
     * @param points   The number of points to add.
     */
    public void addHakiPoints(UUID player, String hakiType, int points) {
        PlayerData data = playerDataMap.getOrDefault(player, new PlayerData());
        int currentPoints;
        switch(hakiType.toLowerCase()) {
            case "observation":
                currentPoints = data.getHakiObservationLevel();
                data.setHakiObservationLevel(Math.min(3, currentPoints + points));
                break;
            case "armement":
                currentPoints = data.getHakiArmementLevel();
                data.setHakiArmementLevel(Math.min(3, currentPoints + points));
                break;
            case "roi":
                currentPoints = data.getHakiDesRoisLevel();
                data.setHakiDesRoisLevel(Math.min(3, currentPoints + points));
                break;
            default:
                throw new IllegalArgumentException("Type de Haki inconnu: " + hakiType);
        }
        playerDataMap.put(player, data);
    }
    
    /**
     * Get the number of Haki points a player has.
     *
     * @param player   The player.
     * @param hakiType The type of Haki.
     * @return The number of Haki points the player has.
     */
    public int getHakiPoints(Player player, String hakiType) {
        PlayerData data = playerDataMap.getOrDefault(player.getUniqueId(), new PlayerData());
        switch(hakiType.toLowerCase()) {
            case "observation":
                return data.getHakiObservationLevel();
            case "armement":
                return data.getHakiArmementLevel();
            case "roi":
                return data.getHakiDesRoisLevel();
            default:
                throw new IllegalArgumentException("Type de Haki inconnu: " + hakiType);
        }
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	Player players = event.getPlayer();
    	if(!playerDataMap.containsKey(players.getUniqueId()))
    		playerDataMap.put(players.getUniqueId(), new PlayerData());
    	removeAttackDelay(event.getPlayer()); 
    }
    
    //Permet d'enlever le délai entre les coups
    public void removeAttackDelay(Player player) {
        AttributeInstance attackSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (attackSpeed != null)
            attackSpeed.setBaseValue(1024.0);
    }
    
    public HashSet<UUID> getActiveHakiArmement() { return activeHakiArmement; }
	public HashSet<UUID> getActiveHakiRoi() { return activeHakiRoi; }
	
	public HashMap<UUID, PlayerData> getPlayerDataMap() { return playerDataMap; }
	public PlayerData getPlayerData(Player player) { return getPlayerDataMap().getOrDefault(player.getUniqueId(), new PlayerData()); }
	
	public EnduranceManager getEnduranceManager() { return enduranceManager; }
	public void setEnduranceManager(EnduranceManager enduranceManager) { this.enduranceManager = enduranceManager; }

	public List<Material> getTools() {	return tools; }

	public List<LivingEntity> getEntityTouchByWeaponSkill_1() { return entityTouchByWeaponSkill_1; }
	public void setEntityTouchByWeaponSkill_1(List<LivingEntity> touchByWeaponSkill_1) { this.entityTouchByWeaponSkill_1 = touchByWeaponSkill_1; }
	public List<LivingEntity> getEntityTouchByWeaponSkill_2() { return entityTouchByWeaponSkill_2; }
	public void setEntityTouchByWeaponSkill_2(List<LivingEntity> touchByWeaponSkill_2) { this.entityTouchByWeaponSkill_2 = touchByWeaponSkill_2; }

	public List<Material> getAirAndFlowers() { return airAndFlowers; }

	public List<String> getLowSkills() { return lowSkills; }
	public List<String> getHighSkills() { return highSkills; }
	public List<String> getSwordSkills() { return swordSkills; }

	public SequenceManager getSequenceManager() { return sequenceManager; }

	public List<String> getSequencesPossible() {
		return sequencesPossible;
	}

	public List<String> getAllSkills() {
		return allSkills;
	}
}
