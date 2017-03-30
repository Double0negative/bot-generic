package org.mcsg.bot.command.commands;

import org.mcsg.bot.api.Bot;
import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotSentMessage;
import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;

public class QueueTestCommand implements BotCommand{

	@Override
	public void execute(String cmd,  BotServer server, BotChannel chat, BotUser user, String[] args, String input) throws Exception {
		for(int a = 0; a < 15; a++) {
			chat.queueMessage("Test Queue " + a);
		}
		BotSentMessage msg = chat.commitMessage();
		
		sleep(5000);
		msg.edit("Cleared");
	}

	@Override
	public String[] getPrefix() {
		return a(",");
	}

	@Override
	public String[] getCommand() {
		return a("queue");
	}

	@Override
	public String getHelp() {
		return null;
	}

	@Override
	public String getUsage() {
		return null;
	}

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return null;
	}

}
