package org.mcsg.bot.command.commands;

import org.mcsg.bot.api.Bot;
import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.api.PermissionManager;
import org.mcsg.bot.util.Arguments;

public class PermissionCommand implements BotCommand{

	@Override
	public void execute(String cmd,  BotServer server, BotChannel chat, BotUser user, String[] args, String input) throws Exception {
		PermissionManager manager = server.getBot().getPermissionManager();
		
		Arguments arge = new Arguments(args, "all");
		args = arge.getArgs();
		
		
		String id = args[1];
		String p = args[2];
		
		if(args.length == 3) {
			if(args[0].equalsIgnoreCase("add") && manager.hasPermission(server, user, "perm.add")) {
				manager.addPermission(server, id, p);
				
				chat.sendMessage("Permission added");
			} else if(args[0].equalsIgnoreCase("remove") && manager.hasPermission(server, user, "perm.remove")) {
				manager.removePermission(server, id, p);
				
				chat.sendMessage("Permission removed");
			} else if(args[0].equalsIgnoreCase("has") && manager.hasPermission(server, user, "perm.user")) { 
				chat.sendMessage(manager.hasPermission(server, id, p) ? "Has permission" : "No permission");
			}
			if(args[0].equalsIgnoreCase("addg") && manager.hasPermission(server, user, "perm.add")) {
				manager.addGroupPermission(server, id, p);
				
				chat.sendMessage("Permission added");
			} else if(args[0].equalsIgnoreCase("removeg") && manager.hasPermission(server, user, "perm.remove")) {
				manager.removeGroupPermission(server, id, p);
				
				chat.sendMessage("Permission removed");
			} 
		}
		
	}

	@Override
	public String[] getPrefix() {
		return a(".");
	}

	@Override
	public String[] getCommand() {
		return a("perm", "permission");
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPermission() {
		return "perm";
	}

}
