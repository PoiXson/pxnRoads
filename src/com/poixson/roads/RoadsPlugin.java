package com.poixson.roads;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.poixson.roads.listeners.RoadsCommands;


public class RoadsPlugin extends JavaPlugin {
	public static final String LOG_PREFIX  = "[Roads] ";
	public static final String CHAT_PREFIX = ChatColor.AQUA + "[Roads] " + ChatColor.WHITE;
	public static final Logger log = Logger.getLogger("Minecraft");

	protected static final AtomicReference<RoadsPlugin> instance = new AtomicReference<RoadsPlugin>(null);

	// listeners
	protected final AtomicReference<RoadsCommands> commandListener = new AtomicReference<RoadsCommands>(null);



	@Override
	public void onEnable() {
		if (!instance.compareAndSet(null, this))
			throw new RuntimeException("Plugin instance already enabled?");
		// commands listener
		{
			final RoadsCommands listener = new RoadsCommands(this);
			final RoadsCommands previous = this.commandListener.getAndSet(listener);
			if (previous != null)
				previous.unregister();
			listener.register();
		}
	}

	@Override
	public void onDisable() {
		// commands listener
		{
			final RoadsCommands listener = this.commandListener.getAndSet(null);
			if (listener != null)
				listener.unregister();
		}
		// stop schedulers
		try {
			Bukkit.getScheduler()
				.cancelTasks(this);
		} catch (Exception ignore) {}
		// stop listeners
		HandlerList.unregisterAll(this);
		if (!instance.compareAndSet(this, null))
			throw new RuntimeException("Disable wrong instance of plugin?");
	}



}
