package org.mcsg.bot;

import org.mcsg.bot.api.Bot;
import org.mcsg.bot.api.BotSettings;
import org.mcsg.bot.command.CommandHandler;

public abstract class GenericBot implements Bot{

	private GenericSettings settings;
	private CommandHandler handler;
	
	public GenericBot() {
		this.settings = new GenericSettings();
		this.handler = new CommandHandler();
	}
	
	
	

	@Override
	public CommandHandler getCommandHandler() {
		return handler;
	}

	@Override
	public BotSettings getSettings() {
		return settings;
	}

	@Override
	public String getClientName() {
		return "Generic Bot (Double0negative)";
	}
	
	
	@Override
	public String getRepo() {
		return "https://github.com/Double0negative/bot-generic";
	}

	@Override
	public String getVersion() {
		return "0.0.1";
	}

}
