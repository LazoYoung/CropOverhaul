package com.naver.jupiter1390;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;

public class Events implements Listener {
	
	private CropDisease plugin;
	private FileConfiguration config;
	
	public Events(CropDisease plugin) {
		
		this.plugin = plugin;
		
	}
	
	@EventHandler
	public void onBlockGrow(BlockGrowEvent event) {
		
		final Location loc = event.getBlock().getLocation();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				cropGrow(loc.getBlock());
			}
		});
		
	}
	
	public void reloadConfig() {
		
		config = plugin.getConfiguration();
		
	}
	
	private void cropGrow(Block block) {
		
		Material mat = block.getType();
		Set<String> types = config.getConfigurationSection("Types").getKeys(false);
		
		for(String t : types) {
			
			Material type = Material.getMaterial(t);
			
			if(type != null && mat.equals(type)) {
				
				String alias = config.getString("Types." + t);
				String infect = config.getString("Infect." + alias);
				Material result = Material.AIR;
				double chance = 0;
				double c = Math.random();
				
				if(infect.contains(" ")) {
					chance = Double.valueOf(infect.split(" ") [0]);
					result = Material.getMaterial(infect.split(" ") [1]);
				} else {
					chance = Double.valueOf(infect);
				}
				
				if(chance > c) {
					
					Location loc = block.getLocation();
					
					for(int i=1;i<4;i++) {
						
						Block b = loc.getWorld().getBlockAt(loc.subtract(0, 1, 0));
						
						if(!b.getType().equals(type)) {
							
							final Block floor = loc.getBlock();
							final Block btm = loc.clone().add(0, 1, 0).getBlock();
							final Material result_ = result;
							
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								@Override
								public void run() {
									
									btm.setType(result_);
									if(result_.equals(Material.DEAD_BUSH)
											&& config.getBoolean("PlaceSandUnderDeadbush")) {
										floor.setType(Material.SAND);
									}
									
								}
							});
							break;
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
}