package org.mcsg.bot.api;

import java.io.File;
import java.util.List;

public interface BotChannel {

	public String getId();
	
	public BotServer getServer();
	
	public List<BotUser> getUsers();
	
	public BotSentMessage sendMessage(String msg);
	
	public void queueMessage(String msg);
	
	public BotSentMessage commitMessage();
	
	public BotSentMessage sendError(String error);
	
	public void sendThrowable(Throwable throwable);
	
	public void sendFile(File file);
	
}
