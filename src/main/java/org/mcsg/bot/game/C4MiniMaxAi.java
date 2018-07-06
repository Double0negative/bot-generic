package org.mcsg.bot.game;

import java.util.Arrays;
import java.util.TreeMap;

import org.mcsg.bot.api.BotChannel;

public class C4MiniMaxAi implements TileAi{

	private static final int MAX_DEPTH = 5;
	private static final int MAX_VALUE = 100;

	private static final int[][] SCORE = new int[][] { 
		new int[] { 1, 2, 3, 4, 3, 2, 1 }, new int[] { 2, 3, 4, 5, 4, 3, 2 }, 
		new int[] { 3, 4, 5, 6, 5, 4, 3 }, new int[] { 3, 4, 5, 6, 5, 4, 3 },
		new int[] { 1, 2, 3, 4, 3, 2, 1 }, new int[] { 0, 1, 2, 3, 2, 1, 0 } 
	};


	private Player aiPlayer;
	private Player otherPlayer;
	private Connect4Game game;
	private BotChannel chat;


	public C4MiniMaxAi (Player aiPlayer, Player otherPlayer, Connect4Game game, BotChannel chat) {
		this.aiPlayer = aiPlayer;
		this.otherPlayer = otherPlayer;
		this.game = game;
		this.chat = chat;
	}


	private int getScore(Tile[][] tiles) {
		int score = 0;
		for (int row = 0; row < Connect4Game.ROWS; row++) {
			for (int col = 0; col < Connect4Game.COLS; col++) {
				if (tiles[col][row] == aiPlayer.getTile()) {
					score -= SCORE[row][col]; //thjese are backwards
				} else if (tiles[col][row] != null) {
					score += SCORE[row][col];
				}
			}
		}
		return score;
	}

	@Override
	public String[] makeMove(Tile[][] tiles) {
		TreeMap<Integer, Integer> results = new TreeMap<Integer, Integer>();


		for (int a = 0; a < Connect4Game.COLS; a++) {
			Tile[][] current = game.clone2DArray(tiles);

			int row = game.getRow(tiles, a, 0);

			if (row != -1) {
				current[a][row] = aiPlayer.getTile();

				int value = alphabet(current, true, MAX_DEPTH);

				if (!results.containsKey(value))
					results.put(value, a);
			}

		}
		int move = results.firstEntry().getValue();

//		chat.sendMessage(results.toString());

		return new String[] {Integer.toString(move + 1)};

	}
	private int alphabet(Tile[][] tiles, boolean max, int depth) {


		boolean aiwin = game.checkForVictory(aiPlayer.getTile(), tiles);
		boolean owin = game.checkForVictory(otherPlayer.getTile(), tiles);

//		if(aiwin || owin)
//			System.out.println("AI: " + aiwin + "  owin:" + owin);
//		
		if (aiwin )
			return -MAX_VALUE;
		else if (owin)
			return MAX_VALUE;

		if (depth <= 0) {
			// ChatManager.chat(chat, "Score: "+getScore(tiles));
//			System.out.println();
//			for (int c = 0; c < tiles[0].length; c++) {
//				System.out.print("[");
//				for (int b = 0; b < tiles.length; b++) {
//					System.out.print(tiles[b][c] + ",");
//				}
//				System.out.println("] ");
//			}
			return getScore(tiles);
		}

		if (max) {
			int value = -MAX_VALUE;
			for (int a = 0; a < Connect4Game.COLS; a++) {
				int row = game.getRow(tiles, a, 0);
				if (row != -1) {
					tiles[a][row] = otherPlayer.getTile();
					value = Math.max(value, alphabet(tiles, false, depth - 1));
					tiles[a][row] = null;
				}
//				System.out.println("max " +a + " : " +  value);

			}
			return value;
		} else {
			int value = MAX_VALUE;
			for (int a = 0; a < Connect4Game.COLS; a++) {
				int row = game.getRow(tiles, a, 0);
				if (row != -1) {
					tiles[a][row] = aiPlayer.getTile();
					value = Math.min(value, alphabet(tiles, true,depth - 1));
					tiles[a][row] = null;
				}
//				System.out.println("max " +a + " : " +  value);

			}

			return value;
		}
	}



}
