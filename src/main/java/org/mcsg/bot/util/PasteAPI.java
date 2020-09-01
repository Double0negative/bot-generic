package org.mcsg.bot.util;

import java.util.HashMap;

import org.mcsg.bot.util.PasteAPI.GistPaste.Paste;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.mashape.unirest.http.exceptions.UnirestException;

public class PasteAPI {

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();


	public static String paste(String msg) throws Exception {
		return paste("file.txt", msg);
	}

	public static String paste(String name, String msg) throws Exception{
		GistPaste gitpaste = new GistPaste();
		Paste paste = new Paste();
		paste.content = msg;
		gitpaste.files.put(name, paste);

		return paste(gitpaste);
	}

	public static String paste(GistPaste paste) throws Exception{
		String json = gson.toJson(paste,  GistPaste.class);
		String json2 = WebClient.postJson("https://api.github.com/gists", json);
		GistResponse response = gson.fromJson(json2, GistResponse.class);
		return response.html_url;
	}


	public static GistResponse getGist(String id) throws Exception{
		String json;
		try {
			json = WebClient.get("https://api.github.com/gists/"+id);
			return gson.fromJson(json, GistResponse.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getPaste(String url) {
		String gist = "https://gist.githubusercontent.com/$id/raw";
		String paste = "http://pastebin.com/raw.php?i=$id";

		String id = "";

		if (url.contains("pastebin")) {
			id = url.substring(url.lastIndexOf("/") + 1);
			return paste.replace("$id", id);
		} else if (url.contains("github")) {
			id = url.substring(url.indexOf("/", 15) + 1);
			return gist.replace("$id", id);
		}
		return "";
	}

	public static class GistPaste {
		public String description = "";
		@SerializedName("public")
		public boolean isPublic = false;
		public HashMap<String, Paste> files = new HashMap<>(); 


		public static class Paste {
			public String content;
		}

	}

	public static class GistResponse {
		public String html_url;

		public HashMap<String, GistFile> files;

		public static class GistFile{
			public String filename;
			public String type; 
			public String language;
			public long size;
			public String raw_url;
			public boolean truncated;
			public String content;
		}
	}
}