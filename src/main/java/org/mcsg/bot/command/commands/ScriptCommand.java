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
import org.mcsg.bot.util.Arguments;
import org.mcsg.bot.util.PasteAPI;
import org.mcsg.bot.util.StringUtils;

public class ScriptCommand implements BotCommand{

	private Map<String, CodeTemplate> templates = new HashMap<>();

	public ScriptCommand() {
		templates.put("js", new CodeTemplate(null, "node {file}", null, ".js"));
		templates.put("groovy", new CodeTemplate("cd {cfiledir}; groovyc {cfile}", "groovy {file}", ".groovy", ""));
		templates.put("py", new CodeTemplate(null, "python3 {file}", null, ".py"));
		templates.put("rb", new CodeTemplate(null, "ruby {file}", null, ".rb"));
		templates.put("pl", new CodeTemplate(null, "perl {file}", null, ".pl"));
	}

	@Override
	public void execute(String cmd,  BotServer server, BotChannel chat, BotUser user, String[] args, String input) throws Exception {
		System.out.println(input);
		CodeTemplate temp = templates.get(cmd);
		
		Arguments arge = new Arguments(args, "paste/p args", "nolimit", "limit/l args");
		
		long limit = arge.hasSwitch("nolimit") ? 0 : Long.parseLong(arge.getSwitch("limit", "10000"));
		
		if(arge.hasSwitch("paste")) {
			input = PasteAPI.getPaste(arge.getSwitch("paste"));
		}
		
		input = input.replace("```", "");
		
		if (temp != null) {
			long time = System.currentTimeMillis();
			File runFile = new File(chat.getServer().getBot().getSettings().getDataFolder(), time + temp.getRunExt());
			if (temp.getCompileLine() != null) {
				File compileFile = new File(server.getBot().getSettings().getDataFolder(), time + temp.getCompileExt());
				FileUtils.write(compileFile, StringUtils.implode(args), Charset.defaultCharset());
				
				ShellExecutor exec = new ShellExecutor(chat.getServer().getBot());
				
				System.out.println(compileFile.getParentFile());
				
				exec.chat(chat)
				.command(
						temp.getCompileLine().replace("{cfiledir}", 
								compileFile.getParentFile().getAbsolutePath())
						.replace("{cfile}", compileFile.getAbsolutePath())).execute();
				
				/*ShellCommand.exec(chat,
						, 0, false, true);*/
			} else {
				FileUtils.write(runFile, input, Charset.defaultCharset());
			}

			ShellExecutor exec = new ShellExecutor(chat.getServer().getBot());
			
			exec.chat(chat).limit(limit).command(temp.getRunLine().replace("{file}", runFile.getAbsolutePath())).execute();
		}

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

	private  class CodeTemplate {
		public CodeTemplate(String compileLine, String runLine, String compileExt, String runExt) {
			super();
			this.compileLine = compileLine;
			this.runLine = runLine;
			this.compileExt = compileExt;
			this.runExt = runExt;
		}
		public String getCompileLine() {
			return compileLine;
		}
		public void setCompileLine(String compileLine) {
			this.compileLine = compileLine;
		}
		public String getRunLine() {
			return runLine;
		}
		public void setRunLine(String runLine) {
			this.runLine = runLine;
		}
		public String getCompileExt() {
			return compileExt;
		}
		public void setCompileExt(String compileExt) {
			this.compileExt = compileExt;
		}
		public String getRunExt() {
			return runExt;
		}
		public void setRunExt(String runExt) {
			this.runExt = runExt;
		}
		private String compileLine;
		private String runLine;
		private String compileExt;
		private String runExt;
	}

	@Override
	public String getPermission() {
		return "code";
	}


}
