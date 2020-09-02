package org.mcsg.bot.api;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface BotSettings {
	
	public File getBaseFolder();
	
	public File getSettingsFolder();
	
	public File getDataFolder();
	
	public File getPluginsFolder();
	
	public Object get(String setting);

	public String getString(String setting) ;
	
	public String getString(String setting, String def) ;
		
	public int getInt(String setting, int def);
		
	public boolean getBoolean(String setting, boolean def);

	public List getList(String setting);
	
	public void set(String setting, Object val);
	
	public Map getMap(String setting);
	
}
