package org.mcsg.bot.command.commands;

import java.util.Random;

import org.mcsg.bot.api.BotChat;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.util.StringUtils;

public class IsCommand implements BotCommand {

    @Override
    public void execute(String cmd, BotChat chat, BotUser sender, String[] args) throws Exception {
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
        return a("is");
    }



	@Override
	public String[] getPrefix() {
		return a(".");
	}


}