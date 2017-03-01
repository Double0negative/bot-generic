package org.mcsg.bot.api;

import java.io.File;

public interface BotSettings {
	
	public File getSettingsFolder();
	
	public File getDataFolder();

	public String get(String setting) ;
	
	public int getInt(String setting);
	
	public boolean getBoolean(String setting);
	
	
	public void set(String setting, Object val);
	
}
