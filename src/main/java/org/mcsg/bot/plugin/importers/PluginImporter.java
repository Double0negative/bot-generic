package org.mcsg.bot.plugin.importers;

import org.mcsg.bot.plugin.PluginMetaData;

public interface PluginImporter {

	public PluginMetaData importPlugin() throws Exception;
	
	public void buildPlugin() throws Exception;
	
	public void installPlugin() throws Exception;
	
}
