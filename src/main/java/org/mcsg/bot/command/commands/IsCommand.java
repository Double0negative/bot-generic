package org.mcsg.bot.command.commands;

import java.util.Random;

import org.mcsg.bot.api.Bot;
import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.util.StringUtils;

public class IsCommand implements BotCommand {

    @Override
    public void execute(String cmd, BotServer server,  BotChannel chat, BotUser sender, String[] args, String input) throws Exception {
        if (new Random(StringUtils.implode(args).hashCode()).nextBoolean()) {
            chat.sendMessage("Yes");
        } else {
            chat.sendMessage("No");
        }

    }

    
    
    @Override
    public String getHelp() {
        return "Is it?";
    }

    @Override
    public String getUsage() {
        return ".is <query>";
    }

    @Override
    public String[] getCommand() {
        return a("is", "are");
    }



	@Override
	public String[] getPrefix() {
		return a(".");
	}



	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return null;
	}


}