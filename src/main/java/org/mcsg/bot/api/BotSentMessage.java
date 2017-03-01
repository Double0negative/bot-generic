package org.mcsg.bot.api;

public interface BotSentMessage {

	public String getMessage();
	
	public BotChat getChat();
	
	public BotSentMessage append(String msg);
	
	public void edit(String msg); 
	
	public void delete();
}
