package org.mcsg.bot.command.commands;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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

public class CompiledCodeCommand implements BotCommand {

	private Map<String, CompileTemplate> temps = new HashMap<>();

	public CompiledCodeCommand() {
		String jtemp = "https://gist.githubusercontent.com/Double0negative/37eb50e13e35596ca1ebfd29162def49/raw/d01a0a0a37fc9b32fbe8c850ed5a709a4696674b/template.java";
		String jscript = "cd {dir} && javac -classpath \"../java_libs/*:\" {name}.java && java -classpath \"../java_libs/*:\" {name}";
		temps.put("java", new CompileTemplate(jtemp, jscript, "class ", "import", ".java", ".class"));

		String cstemp = "https://gist.githubusercontent.com/Double0negative/9bd350556e6a026e0e469c98bff0627e/raw/128487cfd34b2a6e43809a9b925cdddb3a5c05c0/template.cs";
		String csscript = "cd {dir} && mcs {name}.cs && mono {name}.exe";
		temps.put("cs", new CompileTemplate(cstemp, csscript, "class ", "using", ".cs", ".exe"));
	}

	@Override
	public void execute(String cmd, BotServer server, BotChannel chat, BotUser user, String[] args, String input)
			throws Exception {
		System.out.println(input);
		if (server.getBot().getPermissionManager().hasPermission(server, user, "code." + cmd)) {

			CompileTemplate temp = temps.get(cmd);

			if (temp == null) {
				return;
			}

			String templatelink = temp.getTemplate();
			String template = "";
			String code = "";
			long execap = 10000;

			Arguments arge = new Arguments(args, "template/temp/t args", "paste/p args", "nolimit", "limit/l args",
					"code/c", "imports/import/i args");
			args = arge.getArgs();

			input = StringUtils.implode(arge.getArgs());

			System.out.println(input);

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
						sb.append(temp.getImportLabel() + " static " + imp.substring(2) + ";");
					} else {
						sb.append(temp.getImportLabel() + " " + imp + ";");
					}
					sb.append("\n");
				}
				code = code.replace("$imports", sb.toString());
			} else {
				code = code.replace("$imports", "");
			}

			if (server.getBot().getPermissionManager().hasPermission(server, user, "code.exec.cap")) {
				if (arge.hasSwitch("nolimit")) {
					execap = 0;
				} else if (arge.hasSwitch("limit")) {
					execap = Long.parseLong(arge.getSwitch("limit"));
				}
			}

			runCode(chat, user, temp, code, execap, arge.hasSwitch("code"));

		} else {
			chat.sendMessage("No permission to execute command");
		}

	}

	public void runCode(BotChannel chat, BotUser sender, CompileTemplate temp, String code, long cap, boolean b)
			throws Exception {
		int cindex = code.indexOf(temp.getNameIdent()) + temp.getNameIdent().length();

		String name = code.substring(cindex, code.indexOf(" ", cindex)).trim();

		File javaf = new File(chat.getServer().getBot().getSettings().getDataFolder(), name + temp.getEext());
		File javac = new File(chat.getServer().getBot().getSettings().getDataFolder(), name + temp.getCext());

		FileUtils.writeFile(javac, code);

		ShellExecutor exec = new ShellExecutor(chat.getServer().getBot());

		exec.chat(chat);

		String cmd = temp.getScript();
		cmd = cmd.replace("{name}", name);
		cmd = cmd.replace("{dir}", javac.getParentFile().getPath());

		exec.command(cmd);

		int id = exec.limit(cap).execute();

		if (b)
			chat.sendMessage("Running code. ID " + id + ". Code: " + GistAPI.paste("code" + temp.getCext(), code));

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
	public String[] getCommand() {
		return a("java", "cs");
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

	protected class CompileTemplate {
		private String template;
		private String script;

		private String nameIdent;

		private String importLabel;

		private String cext, eext;

		public CompileTemplate(String template, String script, String nameIdent, String importLabel, String cext,
				String eext) {
			super();
			this.template = template;
			this.script = script;
			this.nameIdent = nameIdent;
			this.importLabel = importLabel;
			this.cext = cext;
			this.eext = eext;
		}

		public String getTemplate() {
			return template;
		}

		public void setTemplate(String template) {
			this.template = template;
		}

		public String getScript() {
			return script;
		}

		public void setScript(String script) {
			this.script = script;
		}

		public String getNameIdent() {
			return nameIdent;
		}

		public void setNameIdent(String nameIdent) {
			this.nameIdent = nameIdent;
		}

		public String getImportLabel() {
			return importLabel;
		}

		public void setImportLabel(String importLabel) {
			this.importLabel = importLabel;
		}

		public String getCext() {
			return cext;
		}

		public void setCext(String cext) {
			this.cext = cext;
		}

		public String getEext() {
			return eext;
		}

		public void setEext(String eext) {
			this.eext = eext;
		}
	}

}
