package com.poixson.roads.commands;

import com.poixson.commonmc.tools.commands.pxnCommandsHandler;
import com.poixson.roads.RoadsPlugin;


public class Commands extends pxnCommandsHandler {



	public Commands(final RoadsPlugin plugin) {
		super(
			plugin,
			"roads"
		);
		this.addCommand(new Command_Start(plugin));
		this.addCommand(new Command_Stop(plugin));
	}



}
