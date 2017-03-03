package org.mcsg.bot.shell;

import java.util.ArrayList;
import java.util.List;

import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.util.DelayedActionMessage;

public class ConsoleOutput {
	private DelayedActionMessage msg;
	private BotChannel chat;
	private long start = 0;
	private long time = 30 * 1000;
	private long maxLines = 30;
	private String enclose = "```";
	private ShellExecutor exec;
	
	private List<String> lines = new ArrayList<String>();
	
	
	public ConsoleOutput(BotChannel chat, ShellExecutor exec) {
		this.chat = chat;
		this.msg = new DelayedActionMessage(chat);
		this.exec = exec;
	}
	
	public void sendMessage(String msg) {
		
		if(this.msg == null || System.currentTimeMillis() > start + time || lines.size() > maxLines) {
			lines.clear();
			this.start = System.currentTimeMillis();
			this.msg = new DelayedActionMessage(chat);
		}
		
		lines.add(msg);
		
		StringBuilder builder = new StringBuilder();
		builder.append(enclose).append("\n").append("Output: ").append(exec.getId()).append("\n");
		for(String line : lines) {
			builder.append(line).append("\n");
		}
		builder.append(enclose);
		
		this.msg.setMessage(builder.toString());
	}
}