package org.mcsg.bot.command.commands;

import java.io.File;

import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.shell.ShellExecutor;
import org.mcsg.bot.util.Arguments;
import org.mcsg.bot.util.FileUtils;
import org.mcsg.bot.util.GistAPI;
import org.mcsg.bot.util.StringUtils;
import org.mcsg.bot.util.WebClient;

import lombok.AllArgsConstructor;
import lombok.Data;

public class JavaCommand implements BotCommand{

	@Override
	public void execute(String cmd, BotServer server, BotChannel chat, BotUser user, String[] args, String input)
			throws Exception {
		if (server.getBot().getPermissionManager().hasPermission(server, user, "code.java")) {
			String templatelink = "https://gist.githubusercontent.com/Double0negative/37eb50e13e35596ca1ebfd29162def49/raw/7a37811331a8c7b254213368ff3b8ce9789265a5/template.java";
			String template = "";
			String code = "";
			long execap = 1000;

			Arguments arge = new Arguments(args, "template/temp/t args", "paste/p args", "nolimit", "limit/l args",
					"code/c", "imports/import/i args");
			args = arge.getArgs();

			input = StringUtils.implode(arge.getArgs());


			if (arge.hasSwitch("paste")) {
				String url = arge.getSwitch("paste");
				String link = getPasteLink(url);
				// System.out.println(link);
				code = WebClient.get(link);
				code = code.replace("$code", "");
			} else {
				code = StringUtils.implode(args);

				if (arge.hasSwitch("template")) {
					String link = getPasteLink(arge.getSwitch("template"));
					// System.out.println(link);
					templatelink = link;
				}

				template = WebClient.get(templatelink);
				code = template.replace("$code", input);

			}

			if (arge.hasSwitch("imports")) {
				String[] split = arge.getSwitch("imports").split(":");
				StringBuilder sb = new StringBuilder();
				for (String imp : split) {
					if (imp.startsWith("s/")) {
						sb.append("import static " + imp.substring(2) + ";");
					} else {
						sb.append("import " + imp + ";");
					}
					sb.append("\n");
				}
				code = code.replace("$imports", sb.toString());
			} else {
				code = code.replace("$imports", "");
			}

			if (server.getBot().getPermissionManager().hasPermission(server, user, "code.java.cap")) {
				if (arge.hasSwitch("nolimit")) {
					execap = 0;
				} else if (arge.hasSwitch("limit")) {
					execap = Long.parseLong(arge.getSwitch("limit"));
				}
			}

			runCode(chat, user, code, execap, arge.hasSwitch("code"));

		} else {
			chat.sendMessage("No permission to execute command");
		}

	}
	public void runCode(BotChannel chat, BotUser sender, String code, long cap, boolean b) throws Exception {
		int cindex = code.indexOf("class") + "class ".length();
		// chat.send(ChatManager.createPaste(cindex+" "+code.indexOf(" ",
		// cindex)+"\n"+code));
		String name = code.substring(cindex, code.indexOf(" ", cindex)).trim();

		File javaf = new File(chat.getServer().getBot().getSettings().getDataFolder(), name + ".java");
		File javac = new File(chat.getServer().getBot().getSettings().getDataFolder(), name + ".class");

		FileUtils.writeFile(javaf, code);

		ShellExecutor exec = new ShellExecutor(chat.getServer().getBot());

		exec.chat(chat);

		exec.command("cd " + javac.getParentFile().getPath());
		exec.command("javac -classpath \"../java_libs/*:\" " + name
				+ ".java && java -classpath \"../java_libs/*:\" " + name);

		int id = exec.limit(cap).execute();

		if (b)
			chat.sendMessage( "Running java code. ID " + id + ". Code: " + GistAPI.paste("code.java", code));

		javaf.deleteOnExit();
		javac.deleteOnExit();
	}

	public String getPasteLink(String url) {
		String gist = "https://gist.githubusercontent.com/$id/raw";
		String paste = "http://pastebin.com/raw.php?i=$id";

		String id = "";

		if (url.contains("pastebin")) {
			id = url.substring(url.lastIndexOf("/") + 1);
			return paste.replace("$id", id);
		} else if (url.contains("github")) {
			id = url.substring(url.indexOf("/", 15) + 1);
			return gist.replace("$id", id);
		}
		return "";
	}
	@Override
	public String getPermission() {
		return "code";
	}

	@Override
	public String[] getPrefix() {
		return a(".");
	}

	@Override
	public String[] getCommand() {
		return a("java");
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
	
	private @Data @AllArgsConstructor class CompileTemplate {
		private String template;
		private String compile;
		private String execute;
		
		private String cext, eext;
	}
	
	
}
