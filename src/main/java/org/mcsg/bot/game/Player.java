package org.mcsg.bot.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

public @Data class Player {
	
	public Player(String id, String name, Tile tile) {
		this.id = id;
		this.name = name;
		this.tile = tile;
	}

	private String id;
	private String name;
	private Tile tile;
	
	@Setter private TileAi ai;
	
}
