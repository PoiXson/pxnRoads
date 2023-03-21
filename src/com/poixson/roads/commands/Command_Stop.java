package com.poixson.roads.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.poixson.commonmc.tools.commands.pxnCommand;
import com.poixson.roads.RoadsPlugin;


public class CommandStop extends pxnCommand {

	protected final RoadsPlugin plugin;



	public CommandStop(final RoadsPlugin plugin) {
		super(
			"stop"
		);
		this.plugin = plugin;
	}



	@Override
	public boolean run(final CommandSender sender,
			final Command cmd, final String[] args) {
		final Player player = (sender instanceof Player ? (Player)sender : null);
		this.plugin.stopFollower(player);
		return true;
	}



}
