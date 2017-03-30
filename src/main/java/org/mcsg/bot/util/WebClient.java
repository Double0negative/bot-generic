package org.mcsg.bot.util;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class WebClient {
	
	public static String get(String url) throws UnirestException {
		return Unirest.get(url).asString().getBody();
	}
	
	public static String postJson(String url, String body) throws Exception {
		return Unirest.post(url).header("Content-Type", "application/json").body(body).asString().getBody();
	}
	
}
