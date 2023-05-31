package fr.midey.buildrush.ScoreBoardManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.midey.buildrush.BuildRush;

import java.util.UUID;
 
/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class PersonalScoreboard {
    @SuppressWarnings("unused")
	private Player player;
    private final UUID uuid;
    private final ObjectiveSign objectiveSign;
 
    PersonalScoreboard(Player player){
        this.player = player;
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "BuildRush");

        reloadData();
        objectiveSign.addReceiver(player);
    }
 
    public void reloadData(){}
 
    public void setLines(String ip){
        objectiveSign.setDisplayName("§6§lBUILDRUSH");
 
        objectiveSign.setLine(0, "§1");
        objectiveSign.setLine(1, "§7Temps de jeu: §b" + BuildRush.getInstance().getGameTime());
        objectiveSign.setLine(2, "§7Coins: §a");
        objectiveSign.setLine(3, "§3");
        objectiveSign.setLine(4, "§cRouge §7» ");
        objectiveSign.setLine(5, "§9Bleu §7» ");
        objectiveSign.setLine(6, "§6");
        objectiveSign.setLine(7, "§7Connectés: §e" + Bukkit.getOnlinePlayers().size());
        objectiveSign.setLine(8, "§8");
        //IP changeable dans la classe ScoreboardManager
        objectiveSign.setLine(9, ip);
 
        objectiveSign.updateLines();
    }
 
    public void onLogout(){
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }
}