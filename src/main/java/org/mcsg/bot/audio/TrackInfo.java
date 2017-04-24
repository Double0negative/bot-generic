package org.mcsg.bot.audio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

public @Getter @AllArgsConstructor class TrackInfo {

	private String url;
	private String name;
	private String artist;
	private long duration;
	
}
