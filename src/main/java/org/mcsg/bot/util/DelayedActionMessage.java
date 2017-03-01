package org.mcsg.bot.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.mcsg.bot.api.BotChat;
import org.mcsg.bot.api.BotSentMessage;

public class DelayedActionMessage {

	private final ScheduledExecutorService scheduler =
		     Executors.newSingleThreadScheduledExecutor();
	
	private BotSentMessage sent;
	private BotChat chat;
	private String msg;
	
	private boolean updating;
	
	public DelayedActionMessage(BotChat chat) {
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
		}, 5L, TimeUnit.SECONDS);
		updating = true;
	}
	
}
