package org.mcsg.bot;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activity.InvalidActivityException;

import org.apache.commons.io.FileUtils;
import org.mcsg.bot.api.BotSettings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
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

			System.out.println("Settings Loaded");

		}catch (Exception e) {
			// TODO: handle exception
		}

	}



	public Object get(String setting) {
		String [] split = setting.split("\\.");
		if(split.length == 0) {
			Object o =  settings.get(setting);
			if(o != null) {
				return o;
			} else {
				return null;
			}
		} else {
			Object current = settings;
			for(String str : split) {
				if(current instanceof Map) {
					current = ((Map)current).get(str);
				} else {
					break;
				}
			}
			return current;
		}

	}

	public String getString(String setting, String def) {
		Object o = get(setting);
		return o != def ? o.toString() : def;
	}
	
	public String getString(String setting) {
		Object o = get(setting);
		return o != null ? o.toString() : null;
	}

	@Override
	public int getInt(String setting, int def) {
		Object o = get(setting);
		if(o instanceof Number) {
			Number n = (Number)o;
			if(n != null) {
				return n.intValue();
			}
		}
		return def;
	}

	@Override
	public boolean getBoolean(String setting, boolean def) {
		Object o = get(setting);
		if(o instanceof Boolean) {
			Boolean n = (Boolean)o;
			if(n != null) {
				return n.booleanValue();
			}
		}
		return def;
	}

	@Override
	public  List getList(String setting) {
		Object o = get(setting);
		if(o instanceof List) {
			return (List)o;
		}
		return null;
	}
	
	@Override
	public  Map getMap(String setting) {
		Object o = get(setting);
		if(o instanceof Map) {
			return (Map)o;
		}
		return null;
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



	@Override
	public File getPluginsFolder() {
		File settings = new File("plugins/");
		if(!settings.exists()) {
			settings.mkdir();
		}
		return settings;
	}




}
