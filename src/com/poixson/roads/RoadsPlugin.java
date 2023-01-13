package com.poixson.roads;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.poixson.commonmc.tools.plugin.xJavaPlugin;
import com.poixson.roads.commands.Commands;


public class RoadsPlugin extends xJavaPlugin {
	public static final String LOG_PREFIX  = "[Roads] ";
	public static final String CHAT_PREFIX = ChatColor.AQUA + "[Roads] " + ChatColor.WHITE;
	public static final Logger log = Logger.getLogger("Minecraft");
//TODO
	public static final int SPIGOT_PLUGIN_ID = 0;
	public static final int BSTATS_PLUGIN_ID = 17234;

	protected static final AtomicReference<RoadsPlugin> instance = new AtomicReference<RoadsPlugin>(null);

	// listeners
	protected final AtomicReference<Commands> commandListener = new AtomicReference<Commands>(null);

	protected final CopyOnWriteArraySet<PlayerFollower> followers = new CopyOnWriteArraySet<PlayerFollower>();



	public RoadsPlugin() {
		super(RoadsPlugin.class);
	}



	@Override
	public void onEnable() {
		super.onEnable();
		if (!instance.compareAndSet(null, this))
			throw new RuntimeException("Plugin instance already enabled?");
		// commands listener
		{
			final Commands listener = new Commands(this);
			final Commands previous = this.commandListener.getAndSet(listener);
			if (previous != null)
				previous.unregister();
			listener.register();
		}
	}

	@Override
	public void onDisable() {
		super.onDisable();
		// commands listener
		{
			final Commands listener = this.commandListener.getAndSet(null);
			if (listener != null)
				listener.unregister();
		}
		if (!instance.compareAndSet(this, null))
			throw new RuntimeException("Disable wrong instance of plugin?");
	}



	public void startFollower(final PlayerFollower follower) {
		this.stopFollower(follower.player);
		this.followers.add(follower);
		follower.start();
	}

	public boolean stopFollower(final Player player) {
		final Iterator<PlayerFollower> it = this.followers.iterator();
		while (it.hasNext()) {
			final PlayerFollower follower = it.next();
			if (follower.isPlayer(player))
				return this.stopFollower(follower);
		}
		return false;
	}
	public boolean stopFollower(final PlayerFollower follower) {
		follower.stop();
		return this.followers.remove(follower);
	}



	// -------------------------------------------------------------------------------



	@Override
	protected int getSpigotPluginID() {
		return SPIGOT_PLUGIN_ID;
	}
	@Override
	protected int getBStatsID() {
		return BSTATS_PLUGIN_ID;
	}



}
