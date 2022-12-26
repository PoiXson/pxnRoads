package com.poixson.roads.listeners;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import com.poixson.roads.RoadsPlugin;


public class RoadsCommands implements CommandExecutor {
	public static final String CHAT_PREFIX = RoadsPlugin.CHAT_PREFIX;

	protected final RoadsPlugin plugin;

	protected final ArrayList<PluginCommand> cmds = new ArrayList<PluginCommand>();



	public RoadsCommands(final RoadsPlugin plugin) {
		this.plugin = plugin;
	}



	public void register() {
		final PluginCommand cmd = this.plugin.getCommand("backrooms");
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
//TODO
		return false;
	}



}
