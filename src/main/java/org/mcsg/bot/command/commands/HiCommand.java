package org.mcsg.bot.command.commands;

import java.util.Random;

import org.mcsg.bot.api.Bot;
import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;

public class HiCommand implements BotCommand {

    String[] msg = { "Hi", "Hello", "How are you", "Greetings", "Pickles", "Whats up?", "Hi person" };

    @Override
    public void execute(String cmd, BotServer server,  BotChannel chat, BotUser sender, String[] args, String input) throws Exception {
        chat.sendMessage(msg[new Random().nextInt(msg.length)]);
    }
 
    @Override
    public String getHelp() {
        return "Greetings";
    }

    @Override
    public String getUsage() {
        return ".hi";
    }

    @Override
    public String[] getCommand() {
        return a("hi");
    }



	

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return null;
	}

   

}
