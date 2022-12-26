package com.poixson.roads.listeners;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import com.poixson.roads.PlayerFollower;
import com.poixson.roads.RoadsPlugin;
import com.poixson.roads.builders.BuilderStreet;


public class RoadsCommands implements CommandExecutor {
	public static final String CHAT_PREFIX = RoadsPlugin.CHAT_PREFIX;

	protected final RoadsPlugin plugin;

	protected final ArrayList<PluginCommand> cmds = new ArrayList<PluginCommand>();



	public RoadsCommands(final RoadsPlugin plugin) {
		this.plugin = plugin;
	}



	public void register() {
		final PluginCommand cmd = this.plugin.getCommand("roads");
		cmd.setExecutor(this);
		this.cmds.add(cmd);
		cmd.setTabCompleter( new RoadsTabCompleter() );
	}
	public void unregister() {
		for (final PluginCommand cmd : this.cmds) {
			cmd.setExecutor(null);
		}
		this.cmds.clear();
	}



	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd,
			final String label, final String[] args) {
		final Player player = (sender instanceof Player ? (Player)sender : null);
		final int numargs = args.length;
		if (numargs >= 1) {
			switch (args[0]) {
			case "start": {
				final PlayerFollower follower = new PlayerFollower(this.plugin, player);
//TODO
				follower.snap = true;
				follower.builder = new BuilderStreet();
				this.plugin.startFollower(follower);
				return true;
			}
			case "stop":
				this.plugin.stopFollower(player);
				return true;
			default: break;
			}
		}
		return false;
	}



}
