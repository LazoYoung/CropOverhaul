package com.naver.idealproduction;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * This file is part of Crop++.
 *
 * Crop++ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Crop++ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Crop++. If not, see <https://www.gnu.org/licenses/>.
 */

public class Main extends JavaPlugin {
	
	private Events events;
	
	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		getCommand("crop").setExecutor(new Commands(this));
		
		events = new Events(this);
		
		if(getConfig().getBoolean("Enable")) {
			getServer().getPluginManager().registerEvents(events, this);
		}
		
	}
	
	public void logDebug(String... str) {
		
		Logger log = getLogger();
		
		if(!getConfig().getBoolean("Debug"))
			return;
		
		for(String s : str) {
			
			log.info(s);
			
		}
		
	}

	public void reloadConfiguration() {
		events.reloadConfig();
	}
	
}