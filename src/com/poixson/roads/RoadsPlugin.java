package com.poixson.roads;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.bukkit.entity.Player;

import com.poixson.commonmc.tools.plugin.xJavaPlugin;
import com.poixson.roads.commands.Commands;


public class RoadsPlugin extends xJavaPlugin {
	protected static final String LOG_PREFIX  = "[Roads] ";
//TODO
	protected static final int SPIGOT_PLUGIN_ID = 0;
	protected static final int BSTATS_PLUGIN_ID = 17234;

	protected static final AtomicReference<RoadsPlugin> instance = new AtomicReference<RoadsPlugin>(null);

	// listeners
	protected final AtomicReference<Commands> commandListener = new AtomicReference<Commands>(null);

	protected final ConcurrentHashMap<UUID, PlayerFollower> followers = new ConcurrentHashMap<UUID, PlayerFollower>();



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
			(new RuntimeException("Disable wrong instance of plugin?")).printStackTrace();
	}



	// -------------------------------------------------------------------------------
	// player follower



	public void startFollower(final PlayerFollower follower) {
		this.stopFollower(follower.player);
		this.followers.put(follower.player.getUniqueId(), follower);
		follower.start();
	}

	public boolean stopFollower(final Player player) {
		if (player != null) {
			return this.stopFollower(player.getUniqueId());
		}
		return false;
	}
	public boolean stopFollower(final UUID uuid) {
		if (uuid != null) {
			final PlayerFollower follower = this.followers.get(uuid);
			if (follower != null) {
				follower.stop();
				this.followers.remove(uuid);
				return true;
			}
		}
		return false;
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
