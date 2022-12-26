package com.poixson.roads.builders;

import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.Material;

import com.poixson.commonbukkit.tools.BlockPlotter;


public class BuilderStreet extends RoadBuilder {



	public BuilderStreet() {
		super();
	}



	@Override
	public void build(final Axis axis, final Location loc) {
		final BlockPlotter plotter = new BlockPlotter(loc.getWorld(),
				loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		plotter.type('x', Material.BLACK_WOOL);
		plotter.type('-', Material.SMOOTH_STONE_SLAB);
		final String ax;
		switch (axis) {
		case X: ax = "yn"; break;
		case Z: ax = "yw"; break;
		default: throw new RuntimeException("Unknown road axis");
		}
		final StringBuilder[] matrix = plotter.getEmptyMatrix2D(2);
		matrix[0].append("-   -");
		matrix[1].append("xxxxx");
		plotter.place2D(ax, matrix);
	}



}
