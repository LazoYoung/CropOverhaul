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
		getCommand("cropdisease").setExecutor(new Commands(this));
		
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
		
		config = getConfig();
		events.reloadConfig();
		
	}
	
	public FileConfiguration getConfiguration() {
		return config;
	}
	
}