package org.mcsg.bot.command.commands;
import java.net.InetAddress;
import java.nio.charset.Charset;

import org.mcsg.bot.api.BotChat;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.shell.ShellExecutor;
import org.mcsg.bot.util.StringUtils;


public class PingCommand implements BotCommand {

    @Override
    public void execute(String cmd, BotChat chat, BotUser sender, String[] args) throws Exception {
        if (args.length == 0) {
            chat.sendMessage("Pong!");
        } else if (args.length == 1) {
            try {
                InetAddress addr = InetAddress.getByName(args[0]);
                ShellExecutor exec = new ShellExecutor(chat.getServer().getBot());
                exec.chat(chat).command("printf 'Ping " + args[0] + " (" + addr.getHostAddress()
                        + "). Average time ';ping -c 5 " + addr.getHostAddress()
                        + "| tail -1| awk '{print $4}' | cut -d '/' -f 2| awk '{printf \"%sms\", $1}'").execute();

            } catch (Exception e) {
                chat.sendMessage("Failed to resolve " + args[0]
                        + ". Host does not exist or is not reachable");
            }
        }
    }

    @Override
    public String getHelp() {
        return "PONG!";
    }

    @Override
    public String getUsage() {
        return ".ping [server]";
    }

    @Override
    public String[] getCommand() {
        return a("ping");
    }

	@Override
	public String[] getPrefix() {
		return a(".");
	}

    
}