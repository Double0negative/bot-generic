package org.mcsg.bot.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConsoleReader {
	private BufferedReader stream;
	private ConsoleOutput output;
	private boolean error;
	
	public ConsoleReader(InputStream stream, ConsoleOutput output, boolean error) {
		this.stream = new BufferedReader(new InputStreamReader(stream));
		this.output = output;
	}
	
	public void start() {
		new Thread(() -> {
			String line = null;
			try {
				while((line = stream.readLine()) != null) {
					System.out.println(line);
					output.sendMessage((error ? "err" : "") + line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
}
