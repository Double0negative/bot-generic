package org.mcsg.bot.command.commands;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.profiler.Profiler;
import org.mcsg.bot.profiler.Report;

public class ProfilerCommand implements BotCommand{

	@Override
	public void execute(String cmd, BotServer server, BotChannel chat, BotUser user, String[] args, String input)
			throws Exception {
		List< Report> reports = Profiler.get().generateReports(hoursToMili(1), hoursToMili(3), hoursToMili(24), new Date().getTime() - Profiler.get().getStartTime());
		
		
		StringBuilder str = new StringBuilder();
		str.append("```\n");
		
		for(Report report : reports){
			str.append(String.format("%.1f %d %d %d %.2f %d %s", 
					miliToHours(report.getDuration()), 
					report.getTotal(), 
					report.getTotalCommands(), 
					report.getMinExecTime(), 
					report.getAvgExecTime(), 
					report.getMaxExecTime(), 
					report.getMaxInstance() != null ? report.getMaxInstance().getCommand() : "null"));
			str.append("\n");
		}
		
		str.append(("```"));
		
		chat.sendMessage(str.toString());
		
	}
	
	private long hoursToMili(int hours) {
		return hours * 60 * 60 * 1000;
	}

	private double miliToHours(long mili) {
		return (mili + 0.0) / 60 / 60 / 1000;
	}
	
	@Override
	public String getPermission() {
		return "profiler";
	}

	@Override
	public String[] getCommand() {
		return a("debug", "profiler", "profile");
	}

	@Override
	public String getHelp() {
		return "";
	}

	@Override
	public String getUsage() {
		return "";
	}

}
