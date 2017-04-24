package org.mcsg.bot.api;

import java.io.File;
import java.util.List;

import org.mcsg.bot.audio.TrackInfo;

public interface BotVoiceChannel extends BotChannel{

	public void connnect();
	
	public void disconnect();
	
	public void play(String query);
	
	public void skip();
	
	public void pause();
	
	public void resume();
}
