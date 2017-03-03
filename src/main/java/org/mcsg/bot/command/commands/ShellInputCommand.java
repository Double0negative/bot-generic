package org.mcsg.bot.command.commands;

import java.util.Arrays;

import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.shell.ShellExecutor;
import org.mcsg.bot.util.StringUtils;

public class ShellInputCommand implements BotCommand {

	@Override
	public void execute(String cmd, BotChannel chat, BotUser user, String[] args, String input) throws Exception {
		int id = Integer.parseInt(args[0]);
		ShellExecutor exec = ShellExecutor.get(id);
		
		String [] copy = new String[args.length - 1];
		
		System.arraycopy(args, 1, copy, 0, args.length - 1);
		
		exec.writeTo(StringUtils.implode(copy));
	}

	@Override
	public String[] getPrefix() {
		return a(".") ;
	}

	@Override
	public String[] getCommand() {
		return a("in");
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
