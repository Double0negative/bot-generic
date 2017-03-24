package org.mcsg.bot.api;

public interface PermissionManager {
	
	public boolean hasPermission(BotUser user, String perm);
	
	public void addGroupPermission(String group, String perm);
	
	public void removeGroupPermission(String group, String perm);
	
	public void addPermission(BotUser user, String perm);
	
	public void removePermission(BotUser user, String perm);
	
	
}
