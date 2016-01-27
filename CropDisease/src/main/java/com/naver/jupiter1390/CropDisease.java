package com.naver.jupiter1390;

import org.bukkit.plugin.java.JavaPlugin;

public class CropDisease extends JavaPlugin {
	
	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new Events(this), this);
		
	}
	
}