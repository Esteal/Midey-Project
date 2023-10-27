package fr.midey.OnePieceCraftSkills.Utils;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import fr.midey.OnePieceCraftSkills.OnePieceCraftSkills;

public class TheTabCompleter implements TabCompleter {

	private OnePieceCraftSkills plugin;

	public TheTabCompleter(OnePieceCraftSkills plugin) {
		this.plugin = plugin;
	}
	
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("opc")) {
            if (args.length == 1) {
                list.add("give");
                list.add("view");
                list.add("help");
                list.add("weaponskill");
            } else if (args[0].equalsIgnoreCase("view") && args.length == 2) {
            	list.add("points");
            } else if (args[0].equalsIgnoreCase("give")) {
                if (args.length == 2) {
                    list.add("haki");
                    list.add("exp");
                    list.add("level");
                    list.add("sp");
                    list.add("weapon");
                } else if (args[1].equalsIgnoreCase("haki")) {
                    if (args.length == 3) {
                        list.add("armement");
                        list.add("roi");
                        list.add("observation");
                    }
                }
            } else if (args[0].equalsIgnoreCase("weaponskill") && args.length == 2) {
            	list.add("low");
            	list.add("high");
            } else if (args[1].equalsIgnoreCase("low") && args.length == 4) {
            	for (String string : plugin.getLowSkills())
            		list.add(string);
            } else if (args[1].equalsIgnoreCase("high") && args.length == 4) {
            	for (String string : plugin.getHighSkills())
            		list.add(string);
            }
        } else if (command.getName().equalsIgnoreCase("haki")) {
            if (args.length == 1) {
                list.add("observation");
                list.add("roi");
                list.add("armement");
            } else if (args.length == 2) {
                list.add("on");
                list.add("off");
            }
        }

        return list;
    }
}
