package org.mcsg.bot.command.commands;

import java.util.HashMap;
import java.util.Map;

import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.game.C4MiniMaxAi;
import org.mcsg.bot.game.Connect4Game;
import org.mcsg.bot.game.Game;
import org.mcsg.bot.game.Player;
import org.mcsg.bot.game.Tile;

public class GameCommand implements BotCommand{

	private Map<String, Game> games = new HashMap<>();

	@Override
	public void execute(String cmd, BotServer server, BotChannel chat, BotUser user, String[] args, String input)
			throws Exception {

		Game game = games.get(gameKey(chat, user.getId()));

		if(args.length == 0) {

		} else if(args.length > 1 && (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("new"))) {


			if(cmd.equalsIgnoreCase("c4")) {
				Connect4Game ngame = new Connect4Game();
				Player player1 = null;
				Player player2 = null;
				if(args[1].equalsIgnoreCase("ai")) {
					player1 = new Player(user.getId(), user.getUsername(), Tile.CIRCLE);
					player2 = new Player("ai", "ai", Tile.CROSS);

					player2.setAi(new C4MiniMaxAi(player2, player1, ngame, chat));
				} else {
					BotUser p2 = chat.getUserByName(args[1]);

					player1 = new Player(user.getId(), user.getUsername(), Tile.CIRCLE);
					player2 = new Player(p2.getId(), p2.getUsername(), Tile.CROSS);
				}

				ngame.setPlayer1(player1);
				ngame.setPlayer2(player2);
				
				games.put(gameKey(chat, user.getId()), ngame);
				games.put(gameKey(chat, player2.getId()), ngame);

				ngame.start(chat);
			}
		} else if(args.length > 1 && (args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("forfeit"))) {

		} else if(game != null){
			boolean win = game.makeMove(chat, user.getId(), args);
			if(win) {
				games.remove(gameKey(chat, game.getPlayer1().getId()));
				games.remove(gameKey(chat, game.getPlayer2().getId()));
			} else {
				if(game.getMover().getAi() != null) {
					
					game.makeMove(chat, game.getMover().getId(), game.getMover().getAi().makeMove(game.getState()));
				}
			}
		} else {
			chat.sendMessage("You are not part of a game");
		}

	}

	private String gameKey(BotChannel chat, String id) {
		return chat.getId()+":"+id;
	}

	@Override
	public String getPermission() {
		return "game";
	}

	@Override
	public String[] getPrefix() {
		return a(".");
	}

	@Override
	public String[] getCommand() {
		return a("c4");
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

}
