package org.mcsg.bot.api;

import java.util.List;

public interface BotServer {
	
	public String getId();
	
	public String getName();
	
	public Bot getBot();
	
	public BotUser getUserByName(String name);

	public List<BotChannel> getChats();
	
	public List<BotUser> getUsers();
	
	
}
