package fr.midey.MagicUHC;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class DisplayHotBarMessage {
	
	public void displayHotbarMessage(Player player, String message, int duration) {
		String hotbarMessage = ChatColor.translateAlternateColorCodes('&', message);

		CraftPlayer craftPlayer = (CraftPlayer) player;
		ChatComponentText componentText = new ChatComponentText(hotbarMessage);
		PacketPlayOutChat packet = new PacketPlayOutChat(componentText, (byte) 2);

		craftPlayer.getHandle().playerConnection.sendPacket(packet);

		// Effacer le message apr�s la dur�e sp�cifi�e
		
	}
}
