package org.mcsg.bot.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.mcsg.bot.api.Bot;
import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotUser;
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
import org.mcsg.bot.command.commands.PluginCommand;
import org.mcsg.bot.command.commands.ProfilerCommand;
import org.mcsg.bot.command.commands.QueueTestCommand;
import org.mcsg.bot.command.commands.RandomNumberCommand;
import org.mcsg.bot.command.commands.SayCommand;
import org.mcsg.bot.command.commands.ShellCommand;
import org.mcsg.bot.command.commands.ShellInputCommand;
import org.mcsg.bot.command.commands.StopCommand;
import org.mcsg.bot.command.commands.VersionCommand;
import org.mcsg.bot.plugin.BotPlugin;
import org.mcsg.bot.profiler.ProfileInstance;
import org.mcsg.bot.profiler.Profiler;
import org.mcsg.bot.util.StringUtils;

public class CommandHandler {

	private Bot bot;
	private String[] prefixes;
	private Map<String, BotCommand> commands = new HashMap<>();
	private Map<String, BotCommand> raw = new HashMap<>();
	private Map<BotPlugin, List<BotCommand>> plugins = new HashMap<>();
	
	private List<String> disabled = new ArrayList<>();
	private List<String> enabled = new ArrayList<>();
	
	private boolean allowNoPlugin = true;

	public CommandHandler(Bot bot) {
		this.bot = bot;
		prefixes = ((List<String>)bot.getSettings().getList("bot.prefixes")).toArray(new String[0]);
		enabled = ((List<String>)bot.getSettings().getList("bot.commands.enabled"));
		disabled = ((List<String>)bot.getSettings().getList("bot.commands.disabled"));

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
		registerCommand(new ProfilerCommand());
		registerCommand(new PluginCommand());
		
	}
	
	public void disableNullPluginRegistration() {
		this.allowNoPlugin = false;
	}

	public void executeCommand(String msg, BotChannel chat, BotUser user) {
		//String[] split = msg.split("\\s+");
		String[] split = msg.split(" ");

		ProfileInstance profileInstance = new ProfileInstance(msg, user, chat.getServer());
		Profiler.get().addInstance(profileInstance);

		final String input = msg.substring(msg.indexOf(" ") + 1);

		if(split.length > 0) {
			split[0] = split[0].toLowerCase();
			BotCommand command = commands.get(split[0]);
			if(command != null) {
				System.out.println(chat.getName() + "-" + user.getUsername() + ":" + split[0]);
				async(() -> {
					String cmd = StringUtils.replaceAll(split[0], "", prefixes);

					try {
						if(command.getPermission() == null || chat.getServer().getBot().getPermissionManager().hasPermission(chat.getServer(), user, command.getPermission())){
							command.execute(cmd, chat.getServer(), chat , user, getArgs(split), input);
							profileInstance.setCommandCompleted(cmd, true, false);
						} else {
							chat.sendMessage("("+user.getUsername() + ") You do not have permission to use " + split[0]);
							profileInstance.setCommandCompleted(cmd, false, false);
						}
					} catch (Exception e) {
						if(!bot.getSettings().getBoolean("bot.production", false))
							chat.sendThrowable(e);
						else
							chat.sendMessage("An error occured while executing this command");
						e.printStackTrace();
						profileInstance.setCommandCompleted(cmd, false, true);
					}
				});
			}
		}
	}
	
	private void registerCommand(BotCommand command) {
		this.registerCommand(null, command);
	}

	public void registerCommand(BotPlugin plugin, BotCommand command) {
		if(plugin == null && !allowNoPlugin) {
			throw new IllegalArgumentException("Not allowing null as plugin on command register");
		}
		for(String pre : prefixes) {
			for(String cmd : command.getCommand()) {
				if(disabled == null || !disabled.contains(cmd)){
					if(enabled == null || enabled.contains(cmd) ) {
						raw.put(cmd,  command);
						commands.put(pre + cmd, command);
						List<BotCommand> list = plugins.getOrDefault(plugin, new ArrayList<>());
						list.add(command);
						plugins.put(plugin, list);
					}
				}
			}
		}
	}

	public void unregisterCommand(BotCommand command) {
		for(String pre : prefixes) {
			for(String cmd : command.getCommand()) {
				raw.remove(cmd);
				commands.remove(pre + cmd);
			}
		}
	}
	
	public void unregisterPlugin(BotPlugin plugin) {
		this.plugins.getOrDefault(plugin, Collections.emptyList()).forEach(this::unregisterCommand);
		this.plugins.remove(plugin);
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
