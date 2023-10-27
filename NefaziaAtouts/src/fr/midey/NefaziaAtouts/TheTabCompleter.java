package fr.midey.NefaziaAtouts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TheTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("atouts") && args.length <= 1) {
        	list.add("speed");
        	list.add("fireres");
        	list.add("nofall");
        	list.add("force");
        	list.add("nofall");
        	list.add("norod");
        	list.add("anticleanup");
        } else if (command.getName().equalsIgnoreCase("atouts") && args.length == 2) {
        	list.add("enable");
        	list.add("disable");
        }
    	return list;
    }
}