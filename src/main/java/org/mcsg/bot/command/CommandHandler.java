package org.mcsg.bot.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.api.WeatherCommand;
import org.mcsg.bot.command.commands.ScriptCommand;
import org.mcsg.bot.command.commands.GameCommand;
import org.mcsg.bot.command.commands.HiCommand;
import org.mcsg.bot.command.commands.ImagePainterCommand;
import org.mcsg.bot.command.commands.IsCommand;
import org.mcsg.bot.command.commands.CompiledCodeCommand;
import org.mcsg.bot.command.commands.KillCommand;
import org.mcsg.bot.command.commands.MusicBotCommand;
import org.mcsg.bot.command.commands.PermissionCommand;
import org.mcsg.bot.command.commands.PingCommand;
import org.mcsg.bot.command.commands.QueueTestCommand;
import org.mcsg.bot.command.commands.RandomNumberCommand;
import org.mcsg.bot.command.commands.SayCommand;
import org.mcsg.bot.command.commands.ShellCommand;
import org.mcsg.bot.command.commands.ShellInputCommand;
import org.mcsg.bot.command.commands.StopCommand;
import org.mcsg.bot.command.commands.VersionCommand;
import org.mcsg.bot.util.StringUtils;

public class CommandHandler {

	Map<String, BotCommand> commands = new HashMap<>();
	Map<String, BotCommand> raw = new HashMap<>();

	public CommandHandler() {
		registerCommand(new RandomNumberCommand());
		registerCommand(new IsCommand());
		registerCommand(new QueueTestCommand());
		registerCommand(new ShellCommand());
		registerCommand(new VersionCommand());
		registerCommand(new ScriptCommand());
		registerCommand(new PingCommand());
		registerCommand(new HiCommand());
		registerCommand(new ShellInputCommand());
		registerCommand(new MusicBotCommand());
		registerCommand(new ImagePainterCommand());
		registerCommand(new SayCommand());
		registerCommand(new PermissionCommand());
		registerCommand(new StopCommand());
		registerCommand(new CompiledCodeCommand());
		registerCommand(new KillCommand());
		registerCommand(new GameCommand());
		registerCommand(new WeatherCommand());
	}

	public void executeCommand(String msg, BotChannel chat, BotUser user) {
		String[] split = msg.split("\\s+");

		Arrays.toString(split);

		final String input = msg.substring(msg.indexOf(" ") + 1);

		if(split.length > 0) {
			split[0] = split[0].toLowerCase();
			BotCommand command = commands.get(split[0]);
			if(command != null) {
				async(() -> {
					try {
						if(command.getPermission() == null || chat.getServer().getBot().getPermissionManager().hasPermission(chat.getServer(), user, command.getPermission() + ".use")){
							command.execute(StringUtils.replaceAll(split[0], "", command.getPrefix()), chat.getServer(), chat , user, getArgs(split), input);
						} else {
							chat.sendMessage("("+user.getUsername() + ") You do not have permission to use " + split[0]);
						}
					} catch (Exception e) {
						chat.sendThrowable(e);
					}
				});
			}
		}
	}

	public void registerCommand(BotCommand command) {
		for(String pre : command.getPrefix()) {
			for(String cmd : command.getCommand()) {
				raw.put(cmd,  command);
				commands.put(pre + cmd, command);
			}
		}
	}

	public void unregisterCommand(BotCommand command) {
		for(String pre : command.getPrefix()) {
			for(String cmd : command.getCommand()) {
				raw.remove(cmd);
				commands.remove(pre + cmd);
			}
		}
	}
	
	private BotCommand getCommand(String cmd) {
		return commands.get(cmd);
	}
	
	public BotCommand getRawCommand(String cmd) {
		return raw.get(cmd);
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
