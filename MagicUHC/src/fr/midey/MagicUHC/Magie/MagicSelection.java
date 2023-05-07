package fr.midey.MagicUHC.Magie;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.midey.MagicUHC.MagicUHC;
import fr.midey.MagicUHC.Nature;
import fr.midey.MagicUHC.Items.MagicItems;

public class MagicSelection {

	private MagicUHC main;
	
	public MagicSelection(MagicUHC main) {
		this.main = main;
	}
	
	public void magieSelection() {
		Nature[] natures = { Nature.Air, Nature.Eau, Nature.Feu, Nature.Terre };
		for(Player p : Bukkit.getOnlinePlayers()) {
			int nbRandom = (int) (Math.random() * natures.length + 1);
			main.getPlayerNature().put(p, natures[nbRandom - 1]);
			p.sendMessage("Vous venez d'obtenir la magie de l'élément suivant : §6§l§n" + main.getPlayerNature().get(p).toString());
			MagicItems its = null;
			switch(main.getPlayerNature().get(p)) {
				case Air:
					its = new MagicItems("§bLame de vent", "§bMur de vent", "§bEnvolé céleste");
					break;
				case Eau:
					its = new MagicItems("§9Tsunami", "§9Geyser", "§9Dash aquatique");
					break;
				case Feu:
					its = new MagicItems("§4Griffe du dragon de feu", "§4Lance flamme", "§4Danse du feu");
					break;
				case Terre:
					its = new MagicItems("§6Séisme", "§6Pilier de pierre", "§6Choc sismique");
					break;
				default: break;
			}
			its.Item(p);
		}
	}
}
