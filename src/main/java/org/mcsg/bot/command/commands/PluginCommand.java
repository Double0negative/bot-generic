package org.mcsg.bot.command.commands;

import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotSentMessage;
import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.plugin.PluginMetaData;
import org.mcsg.bot.plugin.importers.GistPluginImporter;
import org.mcsg.bot.plugin.importers.PluginImporter;
import org.mcsg.bot.util.Arguments;
import org.mcsg.bot.util.StringUtils;

public class PluginCommand implements BotCommand {

	@Override
	public void execute(String cmd, BotServer server, BotChannel chat, BotUser user, String[] args, String input)
			throws Exception {
		if ("import".equalsIgnoreCase(args[0])) {
			importPlugin(cmd, server, chat, user, args, input);
		} else if ("disable".equalsIgnoreCase(args[0])) {
			if (args.length == 0) {
				chat.sendMessage("Missing plugin name");
				return;
			}
			String name = StringUtils.implode(args).replace(args[0], "").trim();
			disable(cmd, server, chat, name);
		} else if ("enable".equalsIgnoreCase(args[0])) {
			if (args.length == 0) {
				chat.sendMessage("Missing plugin name");
				return;
			}
			String name = StringUtils.implode(args).replace(args[0], "").trim();
			enable(cmd, server, chat, name);
		}
	}

	private void disable(String cmd, BotServer server, BotChannel chat, String name) throws Exception {
		PluginMetaData meta = server.getBot().getPluginManager().getPlugin(name);
		if (meta == null) {
			chat.sendMessage("Invalid plugin name");
			return;
		}
		server.getBot().getPluginManager().disable(meta);
	}

	private void enable(String cmd, BotServer server, BotChannel chat, String name) throws Exception {
		PluginMetaData meta = server.getBot().getPluginManager().getPlugin(name);
		if (meta == null) {
			chat.sendMessage("Invalid plugin name");
			return;
		}
		server.getBot().getPluginManager().enable(meta, chat);
	}

	private void importPlugin(String cmd, BotServer server, BotChannel chat, BotUser user, String[] args, String input)
			throws Exception {
		Arguments arguments = new Arguments(args, "overwrite/o");

		PluginImporter importer = null;
		String type = args[1];

		if ("gist".equalsIgnoreCase(type)) {
			String url = args[2];

			importer = new GistPluginImporter(server.getBot(), chat, url);
		}

		BotSentMessage message = null;
		PluginMetaData meta = null;

		try {
			message = chat.sendMessage("Downloading...");
			meta = importer.importPlugin();
			message.edit("Downloading... Done");
		} catch (Exception e) {
			message.edit("Downloading Failed: " + e.getMessage());
			e.printStackTrace();
			return;
		}

		PluginMetaData existingPlugin = server.getBot().getPluginManager().getPlugin(meta.name);
		if (existingPlugin != null && !arguments.hasSwitch("overwrite")) {
			chat.sendMessage("Plugin with this name already exists.. Use the flag -o to overwrite");
			return;
		} else if (existingPlugin != null) {
			server.getBot().getPluginManager().disable(existingPlugin);
		}

		try {
			message = chat.sendMessage("Building...");
			importer.buildPlugin();
			message.edit("Building... Done");
		} catch (Exception e) {
			message.edit("Building Failed: " + e.getMessage());
			e.printStackTrace();
			return;
		}

		try {
			message = chat.sendMessage("Installing...");
			importer.installPlugin();
			message.edit("Installing... Done");
		} catch (Exception e) {
			message.edit("Installing Failed: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}

	@Override
	public String getPermission() {
		return "plugin";
	}

	@Override
	public String[] getCommand() {
		return a("plugin");
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
