package org.mcsg.bot.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.command.commands.CodeCommand;
import org.mcsg.bot.command.commands.HiCommand;
import org.mcsg.bot.command.commands.IsCommand;
import org.mcsg.bot.command.commands.MusicBotCommand;
import org.mcsg.bot.command.commands.PingCommand;
import org.mcsg.bot.command.commands.QueueTestCommand;
import org.mcsg.bot.command.commands.RandomNumberCommand;
import org.mcsg.bot.command.commands.ShellCommand;
import org.mcsg.bot.command.commands.ShellInputCommand;
import org.mcsg.bot.command.commands.VersionCommand;

public class CommandHandler {

	Map<String, BotCommand> commands = new HashMap<>();

	public CommandHandler() {
		registerCommand(new RandomNumberCommand());
		registerCommand(new IsCommand());
		registerCommand(new QueueTestCommand());
		registerCommand(new ShellCommand());
		registerCommand(new VersionCommand());
		registerCommand(new CodeCommand());
		registerCommand(new PingCommand());
		registerCommand(new HiCommand());
		registerCommand(new ShellInputCommand());
		registerCommand(new MusicBotCommand());
	}

	public void executeCommand(String msg, BotChannel chat, BotUser user) {
		String[] split = msg.split("\\s+");

		Arrays.toString(split);
		
		final String input = msg.substring(msg.indexOf(" ") + 1);

		if(split.length > 0) {
			BotCommand command = commands.get(split[0]);
			if(command != null) {
				async(() -> {
					try {
						command.execute(split[0], chat , user, getArgs(split), input);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			}
		}
	}

	public void registerCommand(BotCommand command) {
		for(String pre : command.getPrefix()) {
			for(String cmd : command.getCommand()) {
				commands.put(pre + cmd, command);
			}
		}
	}

	public void unregisterCommand(BotCommand command) {
		for(String pre : command.getPrefix()) {
			for(String cmd : command.getCommand()) {
				commands.remove(pre + cmd);
			}
		}
	}

	private void async(Runnable run) {
		new Thread(run).start();
	}

	private String[] getArgs(String[] split) {
		List<String> t = new ArrayList<String>(Arrays.asList(split));
		t.remove(0);
		return t.toArray(new String[t.size()]);
	}

}
