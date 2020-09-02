package org.mcsg.bot.plugin;

import org.mcsg.bot.api.Bot;
import org.mcsg.bot.api.BotChannel;

public interface BotPlugin {

	public void onEnable(Bot bot, BotChannel channel);
	
	public void onDisable();
	
}
