package com.poixson.roads;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.poixson.commonmc.pxnCommonPlugin;
import com.poixson.roads.commands.Commands;
import com.poixson.tools.AppProps;


public class RoadsPlugin extends JavaPlugin {
	public static final String LOG_PREFIX  = "[Roads] ";
	public static final String CHAT_PREFIX = ChatColor.AQUA + "[Roads] " + ChatColor.WHITE;
	public static final Logger log = Logger.getLogger("Minecraft");
//TODO
	public static final int SPIGOT_PLUGIN_ID = 0;
	public static final int BSTATS_PLUGIN_ID = 17234;

	protected static final AtomicReference<RoadsPlugin> instance = new AtomicReference<RoadsPlugin>(null);
	protected static final AtomicReference<Metrics>     metrics  = new AtomicReference<Metrics>(null);
	protected final AppProps props;

	// listeners
	protected final AtomicReference<Commands> commandListener = new AtomicReference<Commands>(null);

	protected final CopyOnWriteArraySet<PlayerFollower> followers = new CopyOnWriteArraySet<PlayerFollower>();



	public RoadsPlugin() {
		super();
		try {
			this.props = AppProps.LoadFromClassRef(RoadsPlugin.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}



	@Override
	public void onEnable() {
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
		// bStats
		System.setProperty("bstats.relocatecheck","false");
		metrics.set(new Metrics(this, BSTATS_PLUGIN_ID));
		// update checker
		pxnCommonPlugin.GetPlugin()
			.getUpdateCheckManager()
				.addPlugin(this, SPIGOT_PLUGIN_ID, this.getPluginVersion());
	}

	@Override
	public void onDisable() {
		// update checker
		pxnCommonPlugin.GetPlugin()
			.getUpdateCheckManager()
				.removePlugin(SPIGOT_PLUGIN_ID);
		// commands listener
		{
			final Commands listener = this.commandListener.getAndSet(null);
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



	public String getPluginVersion() {
		return this.props.version;
	}



}
