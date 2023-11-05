package fr.midey.OnePieceCraftSkills.Commands;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;
import fr.midey.OnePieceCraftSkills.PlayerData;

public class InfoOnSkills implements Listener {

	public void onCommand(CommandSender sender, Command command, String label, String[] args, OnePieceCraftSkills plugin) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			PlayerData playerData = plugin.getPlayerData(player);
			Inventory inventory = Bukkit.createInventory(player, 54, "Compétences Disponibles"); // Taille de l'inventaire à modifier selon le nombre de compétences
			
			List<SkillItem> skillItems = new ArrayList<>();

	        // Ajouter les items (compétences) dans l'inventaire
			skillItems.add(new SkillItem("Demon Slash", playerData.getDemonSlashLevel(), "Charge sur l'ennemi en lui infligeant de lourdes blessures",
	        																			 "Temps de chargement de la compétence : 1s", ""));
			skillItems.add(new SkillItem("Slash", playerData.getSlashLevel(), "Effectue un large coup d'épée à l'horizontale"));
	        
			skillItems.add(new SkillItem("Incision", playerData.getIncisionLevel(), "Déplacez vous rapidement !"));
			skillItems.add(new SkillItem("Flambage Shoot", playerData.getFlambageShootLevel(), "Envoie un coup de pieds enflammé sur vos ennemis"));
			skillItems.add(new SkillItem("Pas de lune", playerData.getPasDeluneLevel(), "Déplace toi comme si la gravité n'existait pas"));
			skillItems.add(new SkillItem("Haki des rois", playerData.getHakiDesRoisLevel(), "Met à genous les plus faibles de tes ennemis"));
			skillItems.add(new SkillItem("Haki de l'armement", playerData.getHakiArmementLevel(), "Renforce ton corps pour que tes ennemis s'en souviennent !"));
			skillItems.add(new SkillItem("Haki de l'observation", playerData.getHakiObservationLevel(), "Repère toutes les personnes aux alentours !"));
	        // Ajouter plus de compétences ici si nécessaire

			skillItems.sort(Comparator.comparing(SkillItem::getSkillLevel).reversed());

		    // Ajouter les compétences triées à l'inventaire
		    skillItems.forEach(skillItem -> inventory.addItem(createSkillItem(
		            skillItem.getName(),
		            skillItem.getSkillLevel(),
		            skillItem.getDescription()
		    )));
	        // Ouvrir l'inventaire pour le joueur
	        player.openInventory(inventory);
		}
	}
	
	@EventHandler
	public void onClickInventory(InventoryClickEvent event) { if (event.getView().getTitle().equals("Compétences Disponibles")) event.setCancelled(true); }
	
	private static ItemStack createSkillItem(String skillName, int skillLevel, String[] description) {
	    // Choisir la couleur du verre selon le niveau de compétence
	    Material glassType = skillLevel > 0 ? Material.GREEN_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE;
	    
	    // Créer l'item stack avec le type de verre approprié
	    ItemStack item = new ItemStack(glassType);
	    ItemMeta meta = item.getItemMeta();

	    // Définir le nom de l'item (compétence)
	    meta.setDisplayName((skillLevel > 0 ? ChatColor.GREEN : ChatColor.RED) + skillName);

	    // Ajouter la description et les informations supplémentaires
	    List<String> lore = new ArrayList<>();
	    // Ajoute la description principale
	    for (String info : description) {
	        lore.add(ChatColor.GRAY + info); // Ajoute des informations supplémentaires
	    }
	    lore.add(ChatColor.YELLOW + "Rang de la compétence: " + ChatColor.AQUA + skillLevel); // Ajoute le niveau de compétence actuel
	    meta.setLore(lore);

	    // Appliquer les métadonnées à l'item stack
	    item.setItemMeta(meta);

	    return item;
	}
	
	private static class SkillItem {
	    private final String name;
	    private final int skillLevel;
	    private final String[] description;

	    public SkillItem(String name, int skillLevel, String... description) {
	        this.name = name;
	        this.skillLevel = skillLevel;
	        this.description = description;
	    }

	    public String getName() {
	        return name;
	    }

	    public int getSkillLevel() {
	        return skillLevel;
	    }

	    public String[] getDescription() {
	        return description;
	    }
	}
}
