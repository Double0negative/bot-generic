package org.mcsg.bot.command.commands;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import org.mcsg.bot.api.Bot;
import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.api.BotVoiceChannel;
import org.mcsg.bot.shell.ShellExecutor;
import org.mcsg.bot.util.StringUtils;

public class MusicBotCommand implements BotCommand{

	private static final String YT_DOWNLOAD = "youtube-dl --audio-format mp3 --extract-audio -o \"{0}/music_{1}.%(etx)s\"  \"{2}\"";
	private static final AtomicInteger id = new AtomicInteger(0);
	
	@Override
	public void execute(String cmd, BotServer server,  BotChannel chat, BotUser user, String[] args, String input) throws Exception {
		Bot bot = server.getBot();
		BotVoiceChannel voice = chat.getServer().getBot().getVoiceChannel(chat);
		if(voice != null) {
			if(cmd.contains("play")) {
				if(args.length == 0) {
					BotVoiceChannel channel = bot.getVoiceChannel(chat);
					channel.play();
					return;
				}
				
				//probably not the most secure thing ever..
				
				int i = id.getAndIncrement();
				
				ShellExecutor exec = new ShellExecutor(chat.getServer().getBot());
				exec.command(StringUtils.replaceVars(YT_DOWNLOAD, bot.getSettings().getDataFolder(), i, args[0]));
				exec.execute();
				exec.waitFor();
				
				
				
				BotVoiceChannel channel = bot.getVoiceChannel(chat);
				channel.queueFile(new File(bot.getSettings().getDataFolder(), "music_" + i + ".mp3"));
			} else if(cmd.contains("pause")) {
				BotVoiceChannel channel = bot.getVoiceChannel(chat);
				channel.pause();
			} else if(cmd.contains("skip")) {
				BotVoiceChannel channel = bot.getVoiceChannel(chat);
				channel.skip();
			}
		} else {
			chat.sendMessage("Bot is not connected to a voice channel for this channel" );
		}

	}


	@Override
	public String[] getCommand() {
		return a("play", "pause", "skip");
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

	@Override
	public String getPermission() {
		return "player";
	}




}
