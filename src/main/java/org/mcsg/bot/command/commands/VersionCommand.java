package org.mcsg.bot.command.commands;

import org.mcsg.bot.api.Bot;
import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;

public class VersionCommand implements BotCommand {

	@Override
	public void execute(String cmd,  BotServer server, BotChannel chat, BotUser user, String[] args, String input) throws Exception {
		chat.sendMessage(chat.getServer().getBot().getBrandingString());
	}

	@Override
	public String[] getPrefix() {
		return a(".");
	}

	@Override
	public String[] getCommand() {
		return a("v","version", "brand");
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
