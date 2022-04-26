package me.reflexlabs.randomspawn.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import me.reflexlabs.randomspawn.RandomSpawn;
import me.reflexlabs.randomspawn.classes.Point;

public class TabCompleter implements org.bukkit.command.TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> list = new ArrayList<String>();
		for(Point p : RandomSpawn.getInstance().getRandomManager().getDataManager().getPointsList()) {
			list.add(p.getId());
		}
		return list;
	}

}
