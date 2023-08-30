package fr.midey.buildrush.WAITING;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.buildrush.BuildRush;
import fr.midey.buildrush.GameCycle;
import fr.midey.buildrush.PLAYING.TimerPlaying;
import fr.midey.buildrush.tools.DisplayHotBarMessage;
import fr.midey.buildrush.tools.PlayerState;

public class TimerLaunching extends BukkitRunnable{

	private BuildRush main;
	private DisplayHotBarMessage displayHotBarMessage;
	List<Integer> values = Arrays.asList(3, 2, 1, 0);
	List<Integer> values2 = Arrays.asList(1, 0);
	List<Integer> playersNb;
	private World world;
	
	private int timer = 10;
	
	public TimerLaunching(BuildRush main) {
		this.main = main;
		this.displayHotBarMessage = new DisplayHotBarMessage(this.main);
		playersNb = Arrays.asList(main.getPlayers().size(), main.getPlayers().size() -1);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		main.saveDefaultConfig();
		for(Player players : main.getPlayers()) {
			if(!values.contains(timer)) displayHotBarMessage.displayHotbarMessage(players, "§6La partie se lance dans " + timer + "s", 20);
			else if(values.contains(timer)) {
				if(timer !=0) {
					players.playSound(players.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
					switch(timer) {
						case 3: 
								displayHotBarMessage.displayHotbarMessage(players, "§c3", 20);
								break;
						case 2 : 
								displayHotBarMessage.displayHotbarMessage(players, "§e2", 20);
								break;
						case 1: 
								displayHotBarMessage.displayHotbarMessage(players, "§a1", 20);
								break;
						default:
							break;
					}
				}
				else {
					if(!(main.getBlueTeam().hasPlayer(players) || main.getRedTeam().hasPlayer(players))) {
						if(main.getBlueTeam().getSize() <= main.getRedTeam().getSize()) {
							main.getPlayersStates().get(players).setTeam("Blue");
							main.getPlayersStates().get(players).colorArmor(Color.AQUA);
							main.getBlueTeam().addPlayer(players);
							players.sendMessage("§6[BuildRush] §7Vous avez rejoint l'équipe §9Bleu§7.");
						}
						else {
							main.getPlayersStates().get(players).setTeam("Red");
							main.getPlayersStates().get(players).colorArmor(Color.RED);
							main.getRedTeam().addPlayer(players);
							players.sendMessage("§6[BuildRush] §7Vous avez rejoint l'équipe §cRouge§7.");
						}
					}
					
					players.sendTitle("§cGo !","");
					world = players.getWorld();
					PlayerState.clearALL(players);
					main.getPlayersStates().get(players).stuffLoad();
					if(main.getBlueTeam().hasPlayer(players)) {
						Location blueSpawn = new Location(players.getWorld(), main.getConfig().getDouble("spawn.blue.x"), main.getConfig().getDouble("spawn.blue.y"), main.getConfig().getDouble("spawn.blue.z"), (float) main.getConfig().getDouble("spawn.blue.pitch"), (float) main.getConfig().getDouble("spawn.blue.yaw"));
						players.teleport(blueSpawn);
					}
					else if (main.getRedTeam().hasPlayer(players)) {
						Location redSpawn = new Location(players.getWorld(), main.getConfig().getDouble("spawn.red.x"), main.getConfig().getDouble("spawn.red.y"), main.getConfig().getDouble("spawn.red.z"), (float) main.getConfig().getDouble("spawn.red.pitch"), (float) main.getConfig().getDouble("spawn.red.yaw"));
						players.teleport(redSpawn);
					}
					players.playSound(players.getLocation(), Sound.ENDERDRAGON_GROWL, 1f, 1f);
				}
				if( values2.contains(main.getPlayers().size()) ||(!playersNb.contains(main.getPlayers().size()) && main.getNumberPerTeam() != 1)) {
					main.setGameCycle(GameCycle.WAITING);
					cancel();
				}
			}
		}
		if(timer <= 0) {
			addPnj(new Location(world, main.getConfig().getDouble("spawn.red.x"), main.getConfig().getDouble("spawn.red.y"), main.getConfig().getDouble("spawn.red.z"), (float) main.getConfig().getDouble("spawn.red.pitch"), (float) main.getConfig().getDouble("spawn.red.yaw")));
			main.setGameCycle(GameCycle.PLAYING);
			cancel();
			TimerPlaying timerPlaying = new TimerPlaying(main);
			timerPlaying.runTaskTimer(main, 0, 20);
		}
		timer--;
	}

	private void addPnj(Location npcLocation) {
        // Créez une entité PNJ (Villager dans cet exemple)
        LivingEntity npc = (LivingEntity) npcLocation.getWorld().spawnEntity(npcLocation, EntityType.VILLAGER);

        // Assurez-vous que l'entité PNJ ne puisse pas être attaquée ou interagie avec le joueur
        // Facultatif : Modifiez l'apparence ou le métier du PNJ
        if (npc instanceof Villager) {
            Villager villager = (Villager) npc;
            villager.setProfession(Villager.Profession.LIBRARIAN);
            villager.setCustomName("SHOP");
            villager.setCustomNameVisible(true);
            
        }
    }
}
