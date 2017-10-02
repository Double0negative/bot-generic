package org.mcsg.bot.profiler;

import java.util.Date;

import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;

public class ProfileInstance {

	
	private long timestamp;
	private String userId;
	private String serverId;
	private String input;

	private boolean isCommand;
	private long executionTime;
	private String command;
	private boolean error;
	private boolean completed;

	
	
	public ProfileInstance(String message, BotUser user, BotServer server) {
		this.input = message;
		this.userId = user.getId();
		this.serverId = server.getId();
		this.timestamp = new Date().getTime();
	}
	
	public void setCommandCompleted(String command, boolean completed, boolean error) {
		this.isCommand = true;
		this.executionTime = new Date().getTime() - this.timestamp; 
		this.command = command;
		this.error = error;
		this.completed = completed;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getUserId() {
		return userId;
	}

	public String getServerId() {
		return serverId;
	}

	public String getInput() {
		return input;
	}

	public boolean isCommand() {
		return isCommand;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public String getCommand() {
		return command;
	}

	public boolean isCompleted() {
		return completed;
	}
	
	public boolean isError() {
		return error;
	}
	
}
