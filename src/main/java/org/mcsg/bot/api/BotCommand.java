package org.mcsg.bot.api;

public interface BotCommand {

    /**
     * Execute command
     * @param chat
     * @param sender
     * @param args
     * @throws Exception
     */
    public void execute(String cmd, BotServer server, BotChannel chat, BotUser user, String[] args, String input) throws Exception;

    public String getPermission();
    
 
    
    /**
     * Get the command name, this is what will be used as the command
     * @return The command
     */
    public String[] getCommand();

    /**
     * Explain what this command does
     * @return
     */
    public String getHelp();

    /**
     * Get command usage for this command in MD format
     * @return
     */
    public String getUsage();

    /**
     * Helper method for creating array
     * @param a
     * @return
     */
    public default String[] a(String... a) {
        return a;
    }
    
    public default void sleep(long time) {
    	try{
    		Thread.sleep(time);
    	} catch (Exception e) {
			
		}
    }
}
