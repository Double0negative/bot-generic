package org.mcsg.bot;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.mcsg.bot.api.BotSettings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GenericSettings implements BotSettings{

	private File file;
	private Map<String, Object> settings = new HashMap<>();
	private ObjectMapper mapper = new ObjectMapper();

	public GenericSettings() {
		this.file = new File(getSettingsFolder(), "settings.json");

		try{
			if(!this.file.exists()) {
				save();
			}

			String json = FileUtils.readFileToString(file, Charset.defaultCharset());
			settings = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}

	}



	@Override
	public String get(String setting) {
		return settings.get(setting).toString();
	}

	@Override
	public int getInt(String setting) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getBoolean(String setting) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void set(String setting, Object val) {
		this.settings.put(setting,  val);
		this.save();
	}
	
	private void save() {
		try {
			file.delete();
			file.createNewFile();
			
			
			String json = mapper.writeValueAsString(settings);
			FileUtils.write(file, json, Charset.defaultCharset());
			
			System.out.println("Wrote settings to " + file.getAbsolutePath());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public File getSettingsFolder() {
		File settings = new File("settings/");
		if(!settings.exists()) {
			settings.mkdir();
		}
		return settings;
	}

	@Override
	public File getDataFolder() {
		File settings = new File("data/");
		if(!settings.exists()) {
			settings.mkdir();
		}
		return settings;
	}

}
