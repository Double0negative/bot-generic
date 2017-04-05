package org.mcsg.bot.game;

import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotSentMessage;

public class Connect4Game implements Game{

	public static final int ROWS = 6;
	public static final int COLS = 7;

	private Player player1;
	private Player player2;

	private int move = 0;

	private Player winner;

	Tile [][] board = new Tile[COLS][ROWS];
	Tile [][] shown = new Tile[COLS][ROWS];


	BotSentMessage msg = null;
	int moves = 0;



	public void start(BotChannel chat) {
		paintBoard(chat, board);
	}

	@Override
	public boolean makeMove(BotChannel chat, String player, String []move) {
		if(!player.equals(getMover().getId())) {
			chat.sendMessage("It's not your turn!");
			return false;
		}

		int col = 0;
		try{
			col = Integer.parseInt(move[0]) - 1;
		} catch(Exception e) {
			chat.sendMessage("Invalid move");
			return false;
		}

		if (col < 0 || col > board.length) {
			chat.sendMessage("Invalid Column");
			return false;
		}
		if (board[col][0] != null) {
			chat.sendMessage("Column is full!");
			return false;
		}

		int row = getRow(board, col, 0);
		board[col][row] = getMoverTile();
		
		
		boolean win = checkForVictory(getMoverTile(), board);
		if(win) {
			winner = getMover();
		}
		nextMove();
		
		paintBoard(chat, board);

		return win;
	}

	public int getRow(Tile[][] board,int col,  int row) {
		if(board[col][row] != null && row < board[col].length) {
			return row - 1;
		} else if(row == board[col].length - 1) {
			return row;
		} else {
			return getRow(board, col, ++row);
		}
	}

	private void paintBoard(BotChannel chat, Tile[][] tiles) {
		StringBuilder sb = new StringBuilder();
		StringBuilder border = new StringBuilder();

		sb.append("```CSS\n");
		sb.append(" 1 2 3 4 5 6 7\n");

		for (int a = 0; a < 15; a++) {
			border.append("-");
		}

		sb.append("").append(border).append("\n");


		for(int r = 0; r < board[0].length; r++) {
			sb.append("|");
			for(int c = 0; c < board.length; c++) {
				Tile tile = tiles[c][r];
				if (tile == null) {
					sb.append(" ");
				} else {
					sb.append(tile.getSymbol());
				}
				sb.append("|");
			}
			sb.append("\n");
		}
		sb.append("").append(border);
		
        sb.append("\n");
        if (winner == null)
            sb.append("Move: " + getMover().getName());
        else
            sb.append("WINNER: " + winner.getName());
        
        sb.append("```");

		if(msg == null || moves % 6 ==0) {
			if(msg != null) {
				msg.delete();
			}
			this.msg = chat.sendMessage(sb.toString());
		} else {
			msg.edit(sb.toString());
		}
	}

	public boolean checkForVictory(Tile tile, Tile[][] tiles) {
		int count = 0;


		/*
			    x = 0 //patern[0]
				y = 0 //patern[1]

				while (x and y are inside grid) {
				    posx = x
				    posy = y
				        while (posx and posy are inside grid) {
				           posx += 0 //patern[4]
				           posy += 1 //patern[5]

				           if(posx and posy == the tile we are searching for) {
				                 increment count
				                      if(count > 3) 
				                           we have  a winner
				           } 
				    }
				   x += 1//patern[2]
				   y += 0 //patern[3]
				}
		 */

		int[][] patterns = { new int[] { 0, 0, 1, 0, 0, 1 }, new int[] { 0, 0, 0, 1, 1, 0 },
				new int[] { 0, 0, 1, 0, -1, 1 }, new int[] { ROWS - 1, COLS - 1, 0, -1, 1, -1 },
				new int[] { ROWS - 1, 0, -1, 0, 1, 1 }, new int[] { 0, COLS - 1, 0, -1, 1, 1 } };

		for (int[] pattern : patterns) {
			int rowi = pattern[0];
			int coli = pattern[1];
			while (rowi >= 0 && rowi < ROWS && coli >= 0 && coli < COLS) {
				count = 0;
				int row = rowi;
				int col = coli;
				while (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
					if (tiles[col][row] == tile) {
						count++;
						if (count > 3)
							return true;
					} else
						count = 0;
					row += pattern[4];
					col += pattern[5];
				}
				rowi += pattern[2];
				coli += pattern[3];
			}
		}
		return false;
	}

	@Override
	public Player getPlayer1() {
		return player1;
	}

	@Override
	public Player getPlayer2() {
		return player2;
	}

	public void nextMove() {
		move = Math.abs(move - 1);
		moves++;
	}


	@Override
	public Player getMover() {
		return (move == 0) ? player1 : player2;
	}

	public Tile getMoverTile() {
		return (move == 0) ? player1.getTile() : player2.getTile();
	}

	@Override
	public boolean isPlayersTurn(String player) {
		return move == 0 && player.equals(player1) || move == 1 && player.equals(player2);
	}

	@Override
	public Tile[][] getState() {
		return clone2DArray(board);
	}

	@Override
	public void setPlayer1(Player player) {
		this.player1 = player;
	}

	@Override
	public void setPlayer2(Player player) {
		this.player2 = player;
	}


}
