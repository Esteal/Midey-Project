package fr.midey.buildrush.WAITING;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.buildrush.BuildRush;
import fr.midey.buildrush.GameCycle;
import fr.midey.buildrush.PLAYING.TimerPlaying;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class TimerLaunching extends BukkitRunnable{

	private BuildRush main;
	List<Integer> values = Arrays.asList(3, 2, 1, 0);
	List<Integer> values2 = Arrays.asList(1, 0);
	List<Integer> playersNb;
	
	private int timer = 10;
	
	public TimerLaunching(BuildRush main) {
		this.main = main;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		if(timer == 3)  
			playersNb = Arrays.asList(main.getPlayers().size(), main.getPlayers().size() -1);
		
		for(Player players : main.getPlayers()) {
			if(!values.contains(timer))displayHotbarMessage(players, "§6La partie se lance dans " + timer + "s", 20);
			else if(values.contains(timer)) {
				if(timer !=0) {
					players.playSound(players.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
					switch(timer) {
						case 3: 
								displayHotbarMessage(players, "§c3", 20);
								break;
						case 2 : 
								displayHotbarMessage(players, "§e2", 20);
								break;
						case 1: 
								displayHotbarMessage(players, "§a1", 20);
								break;
						default:
							break;
					}
				}
				else {
					if(!(main.getBlueTeam().hasPlayer(players) || main.getRedTeam().hasPlayer(players))) {
						if(main.getBlueTeam().getSize() <= main.getRedTeam().getSize()) {
							main.getPlayersStates().get(players).setTeam("Blue");
							main.getBlueTeam().addPlayer(players);
							players.sendMessage("§6[BuildRush] §7Vous avez rejoint l'équipe §9Bleu§7.");
						}
						else {
							main.getPlayersStates().get(players).setTeam("Red");
							main.getRedTeam().addPlayer(players);
							players.sendMessage("§6[BuildRush] §7Vous avez rejoint l'équipe §cRouge§7.");
						}
					}
					
					players.sendTitle("§cGo !","");
					players.getInventory().setArmorContents(main.getPlayersStates().get(players).getArmorContent());
					players.playSound(players.getLocation(), Sound.ENDERDRAGON_GROWL, 1f, 1f);
				}
				if( values2.contains(main.getPlayers().size()) ||(!playersNb.contains(main.getPlayers().size()) && main.getNumberPerTeam() != 1)) {
					main.setGameCycle(GameCycle.WAITING);
					cancel();
				}
			}
		}
		if(timer <= 0) {
			main.setGameCycle(GameCycle.PLAYING);
			cancel();
			TimerPlaying timerPlaying = new TimerPlaying(main);
			timerPlaying.runTaskTimer(main, 0, 20);
		}
		timer--;
	}
	
	public void displayHotbarMessage(Player player, String message, int duration) {
		String hotbarMessage = ChatColor.translateAlternateColorCodes('&', message);

		CraftPlayer craftPlayer = (CraftPlayer) player;
		ChatComponentText componentText = new ChatComponentText(hotbarMessage);
		PacketPlayOutChat packet = new PacketPlayOutChat(componentText, (byte) 2);

		craftPlayer.getHandle().playerConnection.sendPacket(packet);

		// Effacer le message après la durée spécifiée
		main.getServer().getScheduler().runTaskLater(main, () -> {
			PacketPlayOutChat clearPacket = new PacketPlayOutChat(new ChatComponentText(""), (byte) 2);
			craftPlayer.getHandle().playerConnection.sendPacket(clearPacket);
		}, duration);
	}
}
