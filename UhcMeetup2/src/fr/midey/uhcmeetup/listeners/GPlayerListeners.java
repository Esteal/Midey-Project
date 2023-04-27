package fr.midey.uhcmeetup.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

import fr.midey.uhcmeetup.GState;
import fr.midey.uhcmeetup.Gmain;
import fr.midey.uhcmeetup.tasks.GAutoStart;

public class GPlayerListeners implements Listener {

	private Gmain main;
	public GPlayerListeners(Gmain main) {
		this.main = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		player.setFoodLevel(20);
		if (!Gmain.isState(GState.WAITING) && !Gmain.isState(GState.STARTING)) {
			player.setGameMode(GameMode.SPECTATOR);
			player.sendMessage("§4La partie a déjà commencé");
			event.setJoinMessage(null);
			return;
		}
		
		if(!main.getPlayers().contains(player)) {
			main.getPlayers().add(player);
		}
		Gmain.clearALL(player);
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		player.setGameMode(GameMode.ADVENTURE);
		player.getInventory().clear();
		event.setJoinMessage("§4[§eUhcMeetup§4] §rle joueur §4" + player.getName() + " §r a rejoint la partie ! §a(" + main.getPlayers().size() + "/" + Bukkit.getMaxPlayers() + ")");
		Bukkit.broadcastMessage("Il faut " + main.getConfig().getInt("playerbeforestart") + " joueur pour que la partie commence + (" + main.getPlayers().size() + "/" + main.getConfig().getInt("playerbeforestart") + ")");
		if (Gmain.isState(GState.WAITING) && (main.getPlayers().size() >= main.getConfig().getInt("playerbeforestart"))) {
			GAutoStart start = new GAutoStart(main);
			start.runTaskTimer(main, 0, 20);
			main.setState(GState.STARTING);
		}
	}
	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (main.getPlayers().contains(player)) {
			if (Gmain.isState(GState.WAITING) || Gmain.isState(GState.STARTING)) {
				main.getPlayers().remove(player);
			}
			if(Gmain.isState(GState.PLAYING)) {
				main.getPlayers().remove(player);
				Gmain.eliminate(player);
				main.checkwin();
			}
		}
	}
}








