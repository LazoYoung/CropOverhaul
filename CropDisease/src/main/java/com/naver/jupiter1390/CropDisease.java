package com.naver.jupiter1390;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CropDisease extends JavaPlugin {
	
	private FileConfiguration config;
	private Events events;
	
	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		
		if(getConfig().getBoolean("Enable")) {
			events = new Events(this);
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
		
		config = getConfig();
		events.reloadConfig();
		
	}
	
	public FileConfiguration getConfiguration() {
		return config;
	}
	
}