package org.mcsg.bot.game;

import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotUser;

public interface Game {

	public boolean makeMove(BotChannel chat, String player, String [] move);
	
	public void start(BotChannel chat);
	
	public void setPlayer1(Player player);
	
	public void setPlayer2(Player player);
	
	public Player getPlayer1();
	
	public Player getPlayer2();
	
	public Player getMover();
	
	public Tile[][] getState();
	
	public boolean isPlayersTurn(String player);
	
	public default  Tile[][] clone2DArray(Tile[][] array) {
	    int rows=array.length ;
	    //Tile rowIs=array[0].length ;

	    //clone the 'shallow' structure of array
	    Tile[][] newArray =(Tile[][]) array.clone();
	    //clone the 'deep' structure of array
	    for(int row=0;row<rows;row++){
	        newArray[row]=(Tile[]) array[row].clone();
	    }

	    return newArray;
	}
}
