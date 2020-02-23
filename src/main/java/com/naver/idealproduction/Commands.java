package com.naver.idealproduction;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

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

public class Commands implements CommandExecutor {

	private Main plugin;
	
	public Commands(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equals("crop")) {
			
			if(args.length < 1) {
				PluginDescriptionFile pdf = plugin.getDescription();
				sender.sendMessage(new String[] {
						"Crop++ v" + pdf.getVersion() + " is licensed\n" +
						"under GNU General Public License v3." + "\n" +
						"Inspired by jupiter1390 and coded by LazoYoung.\n\n" +
						"Type '/crop help' to list available commands."
				});
				return false;
			}
			
			String arg = args[0].toLowerCase();
			
			if(arg.equals("help")) {
				sender.sendMessage(new String[] {
						"/crop - Displays plugin info.\n" +
						"/crop help - Lists commands.\n" +
						"/crop reload - Reloads config."
				});
				return true;
			}
			
			if(arg.equals("reload")) {
				if (!sender.hasPermission("crop.reload")) {
					sender.sendMessage("You are not authorized.");
					return true;
				}

				plugin.reloadConfiguration();
				sender.sendMessage("Configurations have been reloaded.");
				return true;
			}
			
		}
		return false;
		
	}

}