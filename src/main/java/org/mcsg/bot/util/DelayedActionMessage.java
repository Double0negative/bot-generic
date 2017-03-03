package org.mcsg.bot.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotSentMessage;

public class DelayedActionMessage {

	private final ScheduledExecutorService scheduler =
		     Executors.newSingleThreadScheduledExecutor();
	
	private BotSentMessage sent;
	private BotChannel chat;
	private String msg;
	
	private boolean updating;
	private boolean first = true;
	
	public DelayedActionMessage(BotChannel chat) {
		this.chat = chat;
	}
	
	public void setMessage(String msg) {
		this.msg = msg;
		update();
	}
	
	private void update() {
		if(updating) {
			return;
		}
		scheduler.schedule(() -> {
			if(sent != null) {
				sent.edit(msg);
			} else {
				sent = chat.sendMessage(msg);
			}
			updating = false;
		}, first ? 1L : 5L, TimeUnit.SECONDS);
		updating = true;
		first = false;
	}
	
}
