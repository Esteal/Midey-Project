package fr.midey.starcraft.itemsPackage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.midey.starcraft.Stats;

public class ItemWand {

	private ItemStack it;
	private ItemsConstructor itC = new ItemsConstructor(Material.BARRIER);
	private Stats main;
	private List<String> spell = new ArrayList<>();
	
	public ItemWand(Stats main) {
		this.main = main;
	}


	public ItemStack getWand() {
		
		itC.applyName("�lForce");
		itC.applyLore("�fClick gauche pour changer de comp�tence");
		itC.applyLore("�fClick droit pour activer une comp�tence");
		it = itC.getItem();
		return it;
	}
	
	public void spellAvailabe(Player p) {
		
		String grade = main.getPlayerGrade().get(p.getUniqueId());
		spell.add("�lAttraction");
		spell.add("�lR�pulsion");
		if(grade.equalsIgnoreCase("Chevalier jedi") || grade.equalsIgnoreCase("Ma�tre jedi")) {
		
			spell.add("�lVitesse surhumaine");
			spell.add("�lBouclier de force");
		}
		
		else if (grade.equalsIgnoreCase("Chevalier sith") || grade.equalsIgnoreCase("Seigneur sith")) {
			spell.add("�l�tranglement");
			spell.add("�l�clairs");
		}
		
		if(grade.equalsIgnoreCase("Ma�tre jedi")) {
			
			spell.add("�lGu�rison");
			spell.add("�lSabre de force");
		}
		
		else if (grade.equalsIgnoreCase("Seigneur sith")) {
			spell.add("�lRage");
			spell.add("�lDrain de vie");
		}
		main.getPlayerSpellAvailable().put(p, spell);
	}
}
