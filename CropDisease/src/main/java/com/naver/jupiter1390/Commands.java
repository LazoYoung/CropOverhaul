package com.naver.jupiter1390;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class Commands implements CommandExecutor {

	private CropDisease plugin;
	
	public Commands(CropDisease plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equals("cropdisease")) {
			
			if(args.length < 1) {
				PluginDescriptionFile pdf = getDescription();
				sender.sendMessage(new String[] {
						"CropDisease plugin v" + pdf.getVersion() + "\n" +
						"Coded and copyrighted by LazoCreations." + "\n" +
						"Idea inspired by jupiter1390.\n "
				});
				return false;
			}
			
			String arg = args[0].toLowerCase();
			
			if(arg.equals("help")) {
				sender.sendMessage(new String[] {
						"/cropdisease - Displays plugin info.\n" +
						"/cropdisease help - Lists commands.\n" +
						"/cropdisease reload - Reloads config."
				});
				return true;
			}
			
			if(arg.equals("reload")) {
				plugin.reloadConfiguration();
				sender.sendMessage("Configurations have been reloaded.");
				return true;
			}
			
		}
		return false;
		
	}
	
	private PluginDescriptionFile getDescription() {
		return plugin.getDescription();
	}
	
}