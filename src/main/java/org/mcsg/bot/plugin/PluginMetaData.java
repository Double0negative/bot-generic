package org.mcsg.bot.plugin;

import lombok.Data;

public @Data class PluginMetaData {

	public String name;
	public String author;
	public String main;
	public String version;
	
	private transient BotPlugin instance;
}
