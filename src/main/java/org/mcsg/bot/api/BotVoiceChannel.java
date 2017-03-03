package org.mcsg.bot.api;

import java.io.File;

public interface BotVoiceChannel extends BotChannel{

	public void connnect();
	
	public void disconnect();
	
	public void playFile(File file);
	
	public void queueFile(File file);
	
	public void skip();
	
	public void pause();
}
