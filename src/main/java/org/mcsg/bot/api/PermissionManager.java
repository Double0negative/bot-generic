package org.mcsg.bot.api;

public interface PermissionManager {
	
	public boolean hasPermission(BotServer server, BotUser user, String perm);
	
	public void addGroupPermission(BotServer server, String group, String perm);
	
	public void removeGroupPermission(BotServer server, String group, String perm);
	
	public void addPermission(BotServer server, BotUser user, String perm);
	
	public void removePermission(BotServer server, BotUser user, String perm);
	
	
}
