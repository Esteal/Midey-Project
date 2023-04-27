package fr.midey.starcraft.gradeManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.midey.starcraft.Stats;

public class GradeChat implements Listener {

	private Stats main;
	
	public GradeChat(Stats main) {
		this.main = main;
	}

	@EventHandler
	public void onGradeChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(main.getPlayerGrade().containsKey(p.getUniqueId())) {
			String grade = main.getPlayerGrade().get(p.getUniqueId());
			
			if(grade.equalsIgnoreCase("Seigneur sith") || grade.equalsIgnoreCase("Chevalier sith") || grade.equalsIgnoreCase("Apprenti sith")) {
				e.setFormat("§4[" + grade + "] " + "§r" +p.getDisplayName() + " - §7" + e.getMessage());
			}
			else if(grade.equalsIgnoreCase("Maître jedi") || grade.equalsIgnoreCase("Chevalier jedi") || grade.equalsIgnoreCase("Apprenti jedi")) {
				e.setFormat("§b[" + grade + "] " + "§r" +p.getDisplayName() + " - §7" + e.getMessage());
			}
		}
		
	}
}
