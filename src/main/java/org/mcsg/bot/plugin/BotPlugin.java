package org.mcsg.bot.plugin;

import org.mcsg.bot.api.Bot;

public interface BotPlugin {

	public void onEnable(Bot bot);
	
	public void onDisable();
	
}
