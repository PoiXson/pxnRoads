package com.poixson.roads.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.poixson.commonbukkit.tools.commands.pxnCommandsHandler;
import com.poixson.roads.RoadsPlugin;


public class RoadsCommands extends pxnCommandsHandler {



	public RoadsCommands(final RoadsPlugin plugin) {
		super(
			plugin,
			"roads"
		);
		this.addCommand(new CommandStart(plugin));
		this.addCommand(new CommandStop(plugin));
	}



	@Override
	public List<String> onTabComplete(
			final CommandSender sender, final Command cmd,
			final String label, final String[] args) {
		final List<String> matches = new ArrayList<String>();
		final int size = args.length;
		switch (size) {
		case 1:
			if ("start".startsWith(args[0])) matches.add("start");
			if ("stop".startsWith(args[0]))  matches.add("stop");
			break;
		default:
		}
		return matches;
	}



}
