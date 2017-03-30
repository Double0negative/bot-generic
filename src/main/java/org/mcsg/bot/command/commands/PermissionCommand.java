package org.mcsg.bot.command.commands;

import org.mcsg.bot.api.Bot;
import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;

public class PermissionCommand implements BotCommand{

	@Override
	public void execute(String cmd,  BotServer server, BotChannel chat, BotUser user, String[] args, String input) throws Exception {
		
		if(args.length == 3) {
			if(args[0].equalsIgnoreCase("add")) {
				//if()
			}
		}
		
	}

	@Override
	public String[] getPrefix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getCommand() {
		// TODO Auto-generated method stub
		return null;
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

}
