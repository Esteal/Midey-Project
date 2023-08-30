package fr.midey.buildrush.tools;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.midey.buildrush.BuildRush;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class DisplayHotBarMessage {

	private BuildRush main;

	public DisplayHotBarMessage(BuildRush main) {
		this.main = main;
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
