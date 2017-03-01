package org.mcsg.bot.api;

public interface BotUser {

	public String getId();
	
	public String getUsername();
	
	public void sendMessage(String msg);
}
