package org.mcsg.bot.command.commands;

import java.io.IOException;

import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.api.BotCommand;
import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.util.Arguments;
import org.mcsg.bot.util.StringUtils;
import org.mcsg.bot.util.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class WeatherCommand implements BotCommand{

	private static final String CONDITIONS = "http://api.wunderground.com/api/{0}/conditions/q/{1}.json";
	private static final String HOURLY = "http://api.wunderground.com/api/{0}/hourly/q/{1}.json";
	private static final String nl = "\n";


	@Override
	public void execute(String cmd, BotServer server, BotChannel chat, BotUser user, String[] args, String input)
			throws Exception {

		Arguments arge = new Arguments(args, "hourly h/args");
		input = StringUtils.implode(arge.getArgs());

		if(args.length > 0) {
			String key = server.getBot().getSettings().getString("wunderground.apikey");

			if(key == null) {
				chat.sendError("API Key not set!");
				return;
			}
			
			/*if(arge.hasSwitch("hourly")) {
				
			}*/
			
		
			JsonNode node = WebClient.getJson(StringUtils.replaceVars(CONDITIONS, key, input));
			JsonNode current = node.get("current_observation");
			JsonNode location = current.get("display_location");


			StringBuilder sb = new StringBuilder();

			sb.append("```").append(nl);
			sb.append("Current weather in ").append(location.get("full")).append(nl + nl);

			sb.append("Conditions: ").append(current.get("weather").asText()).append(nl);
			sb.append("Wind: ").append(current.get("wind_string").asText()).append(nl);
			sb.append("Tempature: ").append(current.get("temperature_string").asText()).append(", feels like ").append(current.get("feelslike_string").asText()).append(nl);
			sb.append("Humidity: ").append(current.get("relative_humidity").asText()).append(nl);
			sb.append("Percipitation: ").append(current.get("precip_today_string").asText()).append(nl);

			sb.append("```");
			
			chat.sendMessage(sb.toString());

		}


	}
	
	public void hourly(String key, BotChannel chat, String input) throws JsonProcessingException, IOException, UnirestException {
		JsonNode node = WebClient.getJson(StringUtils.replaceVars(HOURLY, key, input));
		
		if(node.get("response").has("error")) {
			chat.sendMessage(node.get("response").get("error").get("description").asText());
			return;
		}
		
		
		
	}

	@Override
	public String getPermission() {
		return "weather";
	}

	@Override
	public String[] getPrefix() {
		return a(".");
	}

	@Override
	public String[] getCommand() {
		return a("weather", "w");
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
