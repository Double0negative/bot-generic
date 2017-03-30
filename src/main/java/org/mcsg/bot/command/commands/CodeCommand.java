package org.mcsg.bot.command.commands;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.mcsg.bot.api.Bot;
import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.shell.ShellExecutor;
import org.mcsg.bot.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CodeCommand implements BotCommand{

	private Map<String, CodeTemplate> templates = new HashMap<>();

	public CodeCommand() {
		templates.put(".js", new CodeTemplate(null, "node {file}", null, ".js"));
		templates.put(".groovy", new CodeTemplate("cd {cfiledir}; groovyc {cfile}", "groovy {file}", ".groovy", ""));
		templates.put(".py", new CodeTemplate(null, "python3 {file}", null, ".py"));
		templates.put(".rb", new CodeTemplate(null, "ruby {file}", null, ".rb"));
		templates.put(".pl", new CodeTemplate(null, "perl {file}", null, ".pl"));
	}

	@Override
	public void execute(String cmd,  BotServer server, BotChannel chat, BotUser user, String[] args, String input) throws Exception {
		System.out.println(input);
		CodeTemplate temp = templates.get(cmd);
		if (temp != null) {
			long time = System.currentTimeMillis();
			File runFile = new File(chat.getServer().getBot().getSettings().getDataFolder(), time + temp.getRunExt());
			if (temp.getCompileLine() != null) {
				File compileFile = new File(time + temp.getCompileExt());
				FileUtils.write(compileFile, StringUtils.implode(args), Charset.defaultCharset());
				
				ShellExecutor exec = new ShellExecutor(chat.getServer().getBot());
				
				//exec.command(command)
				
				/*ShellCommand.exec(chat,
						temp.getCompileLine().replace("{cfiledir}", compileFile.getParentFile().getAbsolutePath())
						.replace("{cfile}", compileFile.getAbsolutePath()), 0, false, true);*/
			} else {
				FileUtils.write(runFile, input, Charset.defaultCharset());
			}

			ShellExecutor exec = new ShellExecutor(chat.getServer().getBot());
			
			exec.chat(chat).command(temp.getRunLine().replace("{file}", runFile.getAbsolutePath())).execute();
			
			
		}

	}

	@Override
	public String[] getPrefix() {
		return a(".");
	}

	@Override
	public String[] getCommand() {
		 return a("c", "js", "groovy", "py", "rb", "pl");
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

	private @Getter @AllArgsConstructor class CodeTemplate {
		private String compileLine;
		private String runLine;
		private String compileExt;
		private String runExt;
	}


}
