package com.naver.jupiter1390;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.plugin.Plugin;

public class Events implements Listener {
	
	private Plugin plugin;
	
	public Events(Plugin plugin) {
		
		this.plugin = plugin;
		
	}
	
	@EventHandler
	public void onCropGrow(BlockGrowEvent event) {
		
		FileConfiguration config = plugin.getConfig();
		Block block = event.getBlock();
		Material mat = block.getType();
		ConfigurationSection cs = config.getConfigurationSection("Types");
		Set<String> types = cs.getKeys(false);
		
		for(String t : types) {
			
			Material type = Material.getMaterial(t);
			
			if(mat.equals(type)) {
				
				// Matched
				
				double c = config.getInt("Types." + t);
				double c1 = Math.random();
				
				if(c1 > c) {
					
					// The chance
					Location loc = block.getLocation();
					
					// DEBUG 디버그 메세지
					plugin.getLogger().info("CropGrowEvent on world " + loc.getWorld()
							+ ", X" + loc.getBlockX() + ", Y" + loc.getBlockY()+", Z" + loc.getBlockZ());
					
					// 아래블럭 감지 루프 (목표위치 아래블럭부터 그 3블럭 아래까지)
					for(int i=1;i<4;i++) {
						
						Block b = loc.getWorld().getBlockAt(loc.add(0, -i, 0));
						
						if(!b.getType().equals(type)) {
							
							// 가장 밑둥의 사탕수수를 변경
							loc.add(0, 1, 0).getBlock().setType(Material.DEAD_BUSH);
							break;
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
}