package org.mcsg.bot.shell;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;
import org.mcsg.bot.api.Bot;
import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotSentMessage;
import org.mcsg.bot.util.DelayedActionMessage;

public class ShellExecutor {

	private static final AtomicInteger _id = new AtomicInteger(0);
	private static final Map<Integer, ShellExecutor> running = new ConcurrentHashMap<>();

	private int _getId() {
		return _id.getAndIncrement();
	}

	public static ShellExecutor get(int id) {
		return running.get(id);
	}

	private int id;
	private Bot bot;
	private BotChannel chat;
	private List<String> commands;
	private long limit = -1;
	private Process process;
	private File file;
	private ConsoleOutput output;
	private ConsoleReader stdout;
	private ConsoleReader stderr;

	public ShellExecutor(Bot bot) {
		this.id = _getId();
		this.commands = new ArrayList<>();
		this.bot = bot;

		running.put(id, this);
	}

	public ShellExecutor chat(BotChannel chat){
		this.chat = chat;
		return this;
	}

	public ShellExecutor command(String command) {
		this.commands.add(command);
		return this;
	}

	public ShellExecutor limit(long limit) {
		this.limit = limit;
		return this;
	}

	public int execute() {
		if(this.chat != null) 
			this.output = new ConsoleOutput(chat, this);
		this.file = new File(bot.getSettings().getDataFolder(), "shell_" + System.currentTimeMillis());

		try {

			FileUtils.writeLines(file, commands);

			this.process = Runtime.getRuntime().exec("bash " + file.getAbsolutePath());

			if(this.chat != null) {
				stdout = new ConsoleReader(this.process.getInputStream(), output, false);
				stderr = new ConsoleReader(this.process.getErrorStream(), output, true);

				stdout.start();
				stderr.start();
			}


		} catch (IOException e) {
			e.printStackTrace();
		}


		return getId();
	}

	public void waitFor() {
		try {
			this.process.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeTo(String str) {
		PrintWriter writer = new PrintWriter(process.getOutputStream());
		writer.println(str);
		writer.flush();
	}

	public int getId() {
		return id;
	}



}
