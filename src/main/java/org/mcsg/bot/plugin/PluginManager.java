package org.mcsg.bot.plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.mcsg.bot.api.Bot;

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
				PluginLoader loader = new PluginLoader(bot, file);
				try {
					PluginMetaData data = loader.load();

					plugins.put(data.getName(), data);
					bot.log("PluginManager", "Loaded plugin " + data.getName() + ":" + data.getVersion());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					bot.err("Could not load plugin " + file.getPath() + ", Could not load main class " + e.getMessage());
				} catch (IOException e) {
					bot.err("Could not load plugin " + file.getPath() + ", Could not load load file " + e.getMessage());
					e.printStackTrace();
				}
			}
		}	
	}

	public void enableAll() {
		for(PluginMetaData meta : plugins.values()) {
			enable(meta);
		}
	}

	public void enable(PluginMetaData meta) {
		BotPlugin plugin = meta.getInstance();
		if(plugin != null) {
			try{
				plugin.onEnable(bot);
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
				plugin.onDisable();
				bot.log("PluginManager", "Disabled plugin " + meta.getName() + ":" + meta.getVersion());
			} catch (Exception e) {
				bot.log("PluginManager", "Exception occurred while disabling " + meta.getName() + ":" + meta.getVersion());

			}
		}
	}
}
