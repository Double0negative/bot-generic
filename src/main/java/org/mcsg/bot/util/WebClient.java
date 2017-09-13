package org.mcsg.bot.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class WebClient {
	
	protected static ObjectMapper mapper = new ObjectMapper();
	
	public static String get(String url) throws UnirestException {
		System.out.println("GET " + url);
		return Unirest.get(url).asString().getBody();
	}
	
	public static JsonNode getJson(String url) throws JsonProcessingException, IOException, UnirestException {
		System.out.println("GET " + url);

		String json = get(url);
		return mapper.readTree(json);
	}
	
	public static String postJson(String url, String body) throws Exception {
		System.out.println("POST " + url);

		return Unirest.post(url).header("Content-Type", "application/json").body(body).asString().getBody();
	}
	
}
