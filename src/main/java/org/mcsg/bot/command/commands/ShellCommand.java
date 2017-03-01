package org.mcsg.bot.command.commands;

import org.mcsg.bot.api.BotChat;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.shell.ShellExecutor;
import org.mcsg.bot.util.StringUtils;

public class ShellCommand implements BotCommand{

	@Override
	public void execute(String cmd, BotChat chat, BotUser user, String[] args) throws Exception {
		String command = StringUtils.implode(args);
		
		ShellExecutor exec = new ShellExecutor(chat.getServer().getBot());
		
		exec.chat(chat).command(command).execute();
	}

	@Override
	public String[] getPrefix() {
		return a(".");
	}

	@Override
	public String[] getCommand() {
		return a("sh", "shell");
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
