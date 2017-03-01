package org.mcsg.bot.api;

import java.util.List;

public interface BotServer {
	
	public Bot getBot();

	public List<BotChat> getChats();
	
	public List<BotUser> getUsers();
	
	
}
