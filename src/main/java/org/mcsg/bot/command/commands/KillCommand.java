package org.mcsg.bot.command.commands;

import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.shell.ShellExecutor;

public class KillCommand implements BotCommand {

	@Override
	public void execute(String cmd, BotServer server, BotChannel chat, BotUser user, String[] args, String input)
			throws Exception {
		ShellExecutor exec = ShellExecutor.get(Integer.parseInt(args[0]));
		exec.kill("Killed by user");
		
	}

	@Override
	public String getPermission() {
		return "kill";
	}

	@Override
	public String[] getPrefix() {
		return a(".");
	}

	@Override
	public String[] getCommand() {
		return a("kill");
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
