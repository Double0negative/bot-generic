package org.mcsg.bot.command.commands;

import org.eclipse.jetty.util.StringUtil;
import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.util.StringUtils;

public class SayCommand implements BotCommand{

	@Override
	public void execute(String cmd, BotChannel chat, BotUser user, String[] args, String input) throws Exception {
		BotChannel chan = chat.getServer().getBot().getChat(args[0]);
		
		chan.sendMessage(StringUtils.implode(args).replace(args[0], ""));
	}

	@Override
	public String[] getPrefix() {
		return a(".");
	}

	@Override
	public String[] getCommand() {
		return a("say");
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
