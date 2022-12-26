package com.poixson.roads.builders;

import org.bukkit.Axis;
import org.bukkit.Location;


public abstract class RoadBuilder {



	public RoadBuilder() {
	}



	public abstract void build(final Axis axis, final Location loc);



}
