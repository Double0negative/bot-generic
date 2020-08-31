package org.mcsg.bot.plugin;


public class PluginMetaData {

	public String getName() {
		return name;
	}

	public String getAuthor() {
		return author;
	}

	public String getMain() {
		return main;
	}

	public String getVersion() {
		return version;
	}

	public BotPlugin getInstance() {
		return instance;
	}

	public String name;
	public String author;
	public String main;
	public String version;
	
	private transient BotPlugin instance;

	public void setInstance(BotPlugin instance) {
		this.instance = instance;
	}
}
