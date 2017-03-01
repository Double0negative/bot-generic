package org.mcsg.bot.api;

import org.mcsg.bot.command.CommandHandler;

public interface Bot {

	public String getClientName();
	
	public CommandHandler getCommandHandler();
	
	public BotUser getUser(String id);
	
	public BotChat getChat(String id);
	
	public BotServer getServer(String id);
	
	public BotSettings getSettings();
	
	public String getRepo();
	
	public String getVersion();
	
	
}
