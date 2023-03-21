package com.poixson.roads.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.poixson.commonmc.tools.commands.pxnCommand;
import com.poixson.roads.PlayerFollower;
import com.poixson.roads.RoadsPlugin;
import com.poixson.roads.builders.BuilderStreet;


public class CommandStart extends pxnCommand {

	protected final RoadsPlugin plugin;



	public CommandStart(final RoadsPlugin plugin) {
		super(
			"start"
		);
		this.plugin = plugin;
	}



	@Override
	public boolean run(final CommandSender sender,
			final Command cmd, final String[] args) {
		final Player player = (sender instanceof Player ? (Player)sender : null);
		final PlayerFollower follower = new PlayerFollower(this.plugin, player);
//TODO
		follower.snap = true;
		follower.builder = new BuilderStreet();
		this.plugin.startFollower(follower);
		return true;
	}



}
