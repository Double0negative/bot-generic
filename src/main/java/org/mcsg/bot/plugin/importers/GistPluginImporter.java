package org.mcsg.bot.plugin.importers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import org.mcsg.bot.api.Bot;
import org.mcsg.bot.api.BotChannel;
import org.mcsg.bot.plugin.PluginMetaData;
import org.mcsg.bot.shell.ShellExecutor;
import org.mcsg.bot.util.FileUtils;
import org.mcsg.bot.util.JarCreator;
import org.mcsg.bot.util.PasteAPI;
import org.mcsg.bot.util.PasteAPI.GistResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GistPluginImporter implements PluginImporter {

	private String url;
	private Bot bot;
	private BotChannel channel;
	private ObjectMapper mapper = new ObjectMapper();

	private PluginMetaData meta;
	private File buildFolder;
	private File jarFile;

	public GistPluginImporter(Bot bot, BotChannel channel, String url) {
		this.url = url;
		this.bot = bot;
		this.channel = channel;
	}

	@Override
	public PluginMetaData importPlugin() throws Exception {
		GistResponse gist = null;
		try {
			gist = PasteAPI.getGist(url);
		} catch (Exception e) {
			throw new FileNotFoundException(url + " doesn't exist");
		}
		if (!gist.files.containsKey("meta.json")) {
			throw new FileNotFoundException("Missing meta.json");
		}

		this.meta = mapper.readValue(gist.files.get("meta.json").content, PluginMetaData.class);

		this.buildFolder = new File(bot.getSettings().getDataFolder() + File.separator + "plugin_build", meta.name);
		this.buildFolder.delete();
		this.buildFolder.mkdirs();

		for (String fileName : gist.files.keySet()) {
			File file = new File(this.buildFolder, fileName);
			FileUtils.writeFile(file, gist.files.get(fileName).content);
		}

		return this.meta;
	}

	@Override
	public void buildPlugin() throws Exception {
		ShellExecutor exec = new ShellExecutor(this.bot).chat(this.channel)
				.command("javac -proc:none -cp \"" + this.bot.getSettings().getBaseFolder().getAbsolutePath() + File.separator
						+ "java_libs" + File.separator + "*" + File.pathSeparator + "." + File.pathSeparator
						+ this.bot.getSettings().getBaseFolder().getAbsolutePath() + File.separator + "target"
						+ File.separator + "*" + File.pathSeparator + this.buildFolder.getAbsolutePath() + "\" \""
						+ this.buildFolder.getAbsolutePath() + File.separator + this.meta.main + ".java\"");

		exec.execute();
		exec.waitFor();

		if (exec.getExitCode() != 0) {
			throw new RuntimeException("Compile Error");
		}

		this.jarFile = new File(this.buildFolder, this.meta.name + ".jar");
		JarCreator creator = new JarCreator(this.jarFile);

		for (File file : this.buildFolder.listFiles()) {
			if (!file.getName().endsWith(".jar") && !file.getName().endsWith(".java")) {
				creator.add(this.buildFolder.getPath() + File.separator, file);
				System.out.println(this.jarFile.getParent() + " adding file " + file);
			}
		}
		creator.close();
	}

	@Override
	public void installPlugin() throws Exception {
		File pluginFile = new File(this.bot.getSettings().getPluginsFolder(), this.jarFile.getName());
		this.jarFile.renameTo(pluginFile);

		PluginMetaData data = this.bot.getPluginManager().load(pluginFile);
		this.bot.getPluginManager().enable(data, channel);
	}

}
