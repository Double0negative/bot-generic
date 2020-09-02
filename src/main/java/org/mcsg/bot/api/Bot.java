package org.mcsg.bot.api;

import org.mcsg.bot.command.CommandHandler;
import org.mcsg.bot.plugin.PluginManager;

public interface Bot {

	public String getClientName();
	
	public CommandHandler getCommandHandler();
	
	public PluginManager getPluginManager();
	
	public BotUser getUser(String id);
	
	public void setStatus(String status);
		
	public BotChannel getChat(String id);
	
	public BotServer getServer(String id);
	
	public BotVoiceChannel getVoiceChannel(BotChannel channel);
	
	public BotSettings getSettings();
	
	public String getRepo();
	
	public String getVersion();
	
	public String getBrandingString();
	
	public BotChannel getDefaultChat();
	
	public PermissionManager getPermissionManager();
	
	public String getAdminId();
	
	public void log(String prefix, String log);
	
	public void log(String log);
	
	public void err(String prefix, String log);
	
	public void err(String log);
	
	public void throwable(Throwable t);
	
	public void stop();
	
}
