package fr.midey.starcraft.gradeManager;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import fr.midey.starcraft.Stats;
import fr.midey.starcraft.itemsPackage.ItemsConstructor;

public class GradeLoader extends BukkitRunnable{

	private static Stats main;
	public GradeLoader(Stats main) {
		GradeLoader.main = main;
	}
	
	@Override
	public void run() {
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			
			float s = main.getStatsControler().getSpeedPlayer().get(p);
			float f = main.getStatsControler().getForcePlayer().get(p);
			float r = main.getStatsControler().getResistancePlayer().get(p);
			String g = main.getPlayerGrade().get(p.getUniqueId());
			
			if(s >= 0.26f && f >= 0.30f && r >= 0.30f && g.equalsIgnoreCase("Vagabond")) {
				//Choix sith ou jedi
				Inventory choice = Bukkit.createInventory(p, 9, "Force");
				
				ItemsConstructor obscure = new ItemsConstructor(Material.STAINED_GLASS);
				obscure.colorGlass((short) 14);
				obscure.applyName("§4Côté obscure");
				
				ItemsConstructor lumineux = new ItemsConstructor(Material.STAINED_GLASS);
				lumineux.colorGlass((short) 3);
				lumineux.applyName("§bCôté lumineux");
				
				choice.setItem(0, obscure.getItem());
				choice .setItem(1, lumineux.getItem());
				p.openInventory(choice);
			}
			else if((s >= 0.29f && f >= 0.60f && r >= 0.60f) && (s < 0.40f && f < 0.90f && r < 0.90f)) {
				if(g.equalsIgnoreCase("Apprenti sith")) {
					updateGrade(p, "Chevalier sith");
					Bukkit.broadcastMessage("Le joueur §e" + p.getDisplayName() + "§r vient de passer §4Chevalier sith");
				}
				else if(g.equalsIgnoreCase("Apprenti jedi")) {
					updateGrade(p, "Chevalier jedi");
					Bukkit.broadcastMessage("Le joueur §e" + p.getDisplayName() + "§r vient de passer §bChevalier jedi");
				}
			}
			else if (s >= 0.32f && f >= 0.90f && r >= 0.90f) {
				if(g.equalsIgnoreCase("Chevalier sith")) {
					updateGrade(p, "Seigneur sith");
					Bukkit.broadcastMessage("Le joueur §e" + p.getDisplayName() + "§r vient de passer §4Seigneur sith");
				}
				else if(g.equalsIgnoreCase("Chevalier jedi")) {
					updateGrade(p, "Maître jedi");
					Bukkit.broadcastMessage("Le joueur §e" + p.getDisplayName() + "§r vient de passer §bMaître jedi");
				}
			}
		}
	}
	
	public static void updateGrade(Player p, String grade) {

		UUID uuid = p.getUniqueId();
		main.getPlayerGrade().replace(uuid, grade);
		main.getDbUpdate().updateBDD(p);
		p.sendMessage("Félicitation votre §6§lgrade§r vient de passer à un niveau supérieur !");
		p.sendMessage("Vous êtes maintenant un §6§l" + grade);
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 1f, 1f);
	}
}
