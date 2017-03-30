package org.mcsg.bot.api;

import java.util.List;

public interface BotUser {

	public String getId();
	
	public String getUsername();
	
	public List<String> getGroups(BotServer server);
	
	public void sendMessage(String msg);
}
