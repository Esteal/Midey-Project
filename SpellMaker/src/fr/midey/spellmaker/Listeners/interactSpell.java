package fr.midey.spellmaker.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import fr.midey.spellmaker.Main;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class interactSpell implements Listener {

	final String formeList[] = {
			"line",
			"square"
	};
	
	final String effectList[] = {
			"bump",
			"flame"
	};
	
	private Main main;
	public interactSpell(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onSpell(PlayerInteractEvent event) {
		main.saveDefaultConfig();
		Player p = event.getPlayer();
		ItemStack it = event.getItem();
		String forme = "";
		String effect = "";
		String name = "";
		double size = -1.0;
		if(it == null) return;
		ConfigurationSection ItemSection = main.getConfig().getConfigurationSection("item");
		for (String key : ItemSection.getKeys(false)) {
			Material matKey = Material.getMaterial(key);
			if (it.getType() == matKey) {
				ConfigurationSection optionSection = ItemSection.getConfigurationSection(key);
				for(String optionKey : optionSection.getKeys(false)) {
					if (optionKey.equalsIgnoreCase("name") && optionSection.getString(optionKey).equalsIgnoreCase(it.getItemMeta().getDisplayName())) {
						name = optionSection.getString(optionKey);
						p.sendMessage(name);
					}
					else if(optionKey.equalsIgnoreCase("forme")) {
						forme = onForme(optionSection, optionKey);
						p.sendMessage(forme);
					}
					else if(optionKey.equalsIgnoreCase("effect")) {
						effect = onEffect(optionSection, optionKey);
						p.sendMessage(effect);
					}
					else if(optionKey.equalsIgnoreCase("size")) {
						size = optionSection.getDouble(optionKey);
						p.sendMessage("" + size);
					}
				}
				if ((forme != "") && (effect != "") && (name != "") && (size != -1.0)) {
					p.sendMessage("Successfull");
					createSpell(p, effect, forme, size);
					
				}
				break;
			}
		}
	}
	
	public void createSpell(Player p, String effect, String forme, double size) {
		
		switch(effect) {
			case "flame":
				createSpecificSpell(p, forme, size, EnumParticle.FLAME);
				break;
			case "bump":
				createSpecificSpell(p, forme, size, EnumParticle.CRIT_MAGIC);
				break;
			default: break;
		}
	}
	
	public void createSpecificSpell(Player p, String forme, double size, EnumParticle particle) {
		Location loc = p.getLocation();
		double xP = loc.getX();
		double yP = loc.getY();
		double zP = loc.getZ();
		Vector v = p.getLocation().getDirection();
		switch(forme) {
			case "square":
				break;
			case "line":
				for (int i = 0; i < 999;i++) {
					double xV = xP + v.getX();
					double yV = yP + v.getY();
					double zV = zP + v.getZ();
					for(Player player :Bukkit.getOnlinePlayers()) {
						PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) xV, (float) yV + 1.5f, (float) zV, 0, 0, 0, 0, 1);
						((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
					}
					p.sendMessage("x : " +xV + "| z : " + zV);
					if ((xV - xP >= size || xV + xP <= size) && (zV > zP + size || zV < zP - size)) break;
					v.multiply(1.2);
				}
				
		}
		
	}
	
	public String onForme(ConfigurationSection formeType, String optionKey) {
		for (String s : formeList) {
			if (formeType.getString(optionKey).equalsIgnoreCase(s)) return formeType.getString(optionKey);
		}
		return "";
	}
	public String onEffect(ConfigurationSection effectType, String optionKey) {
		for (String s : effectList) {
			if (effectType.getString(optionKey).equalsIgnoreCase(s)) return effectType.getString(optionKey);
		}
		return "";
	}
}
