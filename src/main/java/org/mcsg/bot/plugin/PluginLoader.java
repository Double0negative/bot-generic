package org.mcsg.bot.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.commons.io.IOUtils;
import org.mcsg.bot.api.Bot;

import com.google.gson.Gson;

public class PluginLoader {

	private Bot bot;
	private File pluginFile;
	private PluginMetaData meta;

	public PluginLoader(Bot bot, File pluginFile) {
		this.bot = bot;
		this.pluginFile = pluginFile;
	}

	public PluginMetaData load() throws IOException, ClassNotFoundException {
		PluginMetaData meta = getMetaData();
		ClassLoader loader = new URLClassLoader(new URL[] {pluginFile.toURI().toURL()});
		Class<?> clazz = Class.forName(meta.main, true, loader);

		try{
			BotPlugin plugin = clazz.asSubclass(BotPlugin.class).newInstance();
			
			meta.setInstance(plugin);
		} catch (Exception e) {
			e.printStackTrace();
			bot.err("Failed to load plugin " + meta.getName() + " init exception");
		}
		return meta;
	}

	private PluginMetaData getMetaData() throws IOException {
		if(meta != null) {
			return meta;
		}
		JarFile jar = new JarFile(pluginFile);
		InputStream metaStream = jar.getInputStream(jar.getEntry("meta.json"));
		String metaJson = IOUtils.toString(metaStream, StandardCharsets.UTF_8);
		this.meta =  new Gson().fromJson(metaJson, PluginMetaData.class);
		jar.close();
		return meta;
	}

}
