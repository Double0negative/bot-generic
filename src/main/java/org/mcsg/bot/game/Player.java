package org.mcsg.bot.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

public  class Player {
	
	public Player(String id, String name, Tile tile) {
		this.id = id;
		this.name = name;
		this.tile = tile;
	}

	private String id;
	private String name;
	private Tile tile;
	
	 private TileAi ai;

	public TileAi getAi() {
		return ai;
	}

	public void setAi(TileAi ai) {
		this.ai = ai;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Tile getTile() {
		return tile;
	}
	 
	 
	
}
