package com.poixson.roads;

import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.poixson.commonmc.tools.plugin.xJavaPlugin;
import com.poixson.roads.commands.Commands;


public class RoadsPlugin extends xJavaPlugin {
	public static final String LOG_PREFIX  = "[Roads] ";
	public static final String CHAT_PREFIX = ChatColor.AQUA + LOG_PREFIX + ChatColor.WHITE;

	// listeners
	protected final AtomicReference<Commands> commandListener = new AtomicReference<Commands>(null);

	protected final ConcurrentHashMap<UUID, PlayerFollower> followers = new ConcurrentHashMap<UUID, PlayerFollower>();

	@Override public int getSpigotPluginID() { return 108151; }
	@Override public int getBStatsID() {       return 17234;  }



	public RoadsPlugin() {
		super(RoadsPlugin.class);
	}



	@Override
	public void onEnable() {
		super.onEnable();
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
		// stop followers
		{
			final Iterator<PlayerFollower> it = this.followers.values().iterator();
			while (it.hasNext()) {
				final PlayerFollower follower = it.next();
				follower.unregister();
			}
			this.followers.clear();
		}
	}



	// -------------------------------------------------------------------------------
	// player follower



	public void startFollower(final PlayerFollower follower) {
		this.stopFollower(follower.player);
		this.followers.put(follower.player.getUniqueId(), follower);
		follower.register();
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
				follower.unregister();
				this.followers.remove(uuid);
				return true;
			}
		}
		return false;
	}



}
