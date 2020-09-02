package org.mcsg.bot.plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.mcsg.bot.api.Bot;
import org.mcsg.bot.api.BotChannel;

public class PluginManager {

	private Bot bot;
	private Map<String, PluginMetaData> plugins = new HashMap<>();

	public PluginManager(Bot bot) {
		this.bot = bot;
	}


	public void load() {
		File folder = bot.getSettings().getPluginsFolder();

		for(File file : folder.listFiles()) {
			if(file.getName().endsWith(".jar")) {
				this.load(file);
			}
		}	
	}
	
	public PluginMetaData load(File file) {
		PluginLoader loader = new PluginLoader(bot, file);
		try {
			PluginMetaData data = loader.load();

			plugins.put(data.getName(), data);
			bot.log("PluginManager", "Loaded plugin " + data.getName() + ":" + data.getVersion());
			
			return data;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			bot.err("Could not load plugin " + file.getPath() + ", Could not load main class " + e.getMessage());
		} catch (IOException e) {
			bot.err("Could not load plugin " + file.getPath() + ", Could not load load file " + e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}

	public void enableAll(BotChannel channel) {
		for(PluginMetaData meta : plugins.values()) {
			enable(meta, channel);
		}
	}

	public void enable(PluginMetaData meta, BotChannel channel) {
		BotPlugin plugin = meta.getInstance();
		if(plugin != null) {
			try{
				plugin.onEnable(bot, channel == null ? this.bot.getDefaultChat() : channel);
				bot.log("PluginManager", "Enabled plugin " + meta.getName() + ":" + meta.getVersion());
			} catch (Exception e) {
				bot.log("PluginManager", "Exception occurred while enabling " + meta.getName() + ":" + meta.getVersion());
				e.printStackTrace();
			}
		}
	}

	public void disableAll() {
		for(PluginMetaData meta : plugins.values()) {
			disable(meta);
		}
	}

	public void disable(PluginMetaData meta) {
		BotPlugin plugin = meta.getInstance();
		if(plugin != null) {
			try{
				this.bot.getCommandHandler().unregisterPlugin(plugin);
				plugin.onDisable();
				bot.log("PluginManager", "Disabled plugin " + meta.getName() + ":" + meta.getVersion());
			} catch (Exception e) {
				bot.log("PluginManager", "Exception occurred while disabling " + meta.getName() + ":" + meta.getVersion());
				e.printStackTrace();
			}
		}
	}
	
	public PluginMetaData getPlugin(String name) {
		return this.plugins.get(name);
	}
}
