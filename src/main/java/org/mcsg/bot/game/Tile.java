package org.mcsg.bot.game;

public enum Tile {

	CIRCLE("O"),
	SQUARE("▢"),
	CROSS("X"),
	CHECK("✓");
	
	
	private String symbol;
	
	Tile(String str) {
		this.symbol = str;
	}
	
	public String getSymbol() {
		return symbol;
	}
}
