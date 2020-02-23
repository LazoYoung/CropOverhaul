package com.naver.idealproduction;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;

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

public class Events implements Listener {
	
	private Main plugin;
	private FileConfiguration config;
	private Set<String> types;
	
	public Events(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockGrow(BlockGrowEvent event) {
		
		final Location loc = event.getBlock().getLocation();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> cropGrow(loc.getBlock()));
		
	}
	
	public void reloadConfig() {
		
		config = plugin.getConfig();
		types = config.getConfigurationSection("Types").getKeys(false);
		
	}
	
	private void cropGrow(Block block) {
		
		Material mat = block.getType();
		
		for(String t : types) {
			
			Material type = Material.getMaterial(t);
			
			if(mat.equals(type)) {
				
				String alias = config.getString("Types." + t);
				String infect = config.getString("Infect." + alias);
				Material result = Material.AIR;
				double chance;
				double c = Math.random();
				
				if(infect.contains(" ")) {
					chance = Double.parseDouble(infect.split(" ") [0]);
					result = Material.getMaterial(infect.split(" ") [1]);
				} else {
					chance = Double.parseDouble(infect);
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