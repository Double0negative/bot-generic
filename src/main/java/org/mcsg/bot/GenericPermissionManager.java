package org.mcsg.bot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mcsg.bot.api.Bot;
import org.mcsg.bot.api.BotServer;
import org.mcsg.bot.api.BotUser;
import org.mcsg.bot.api.PermissionManager;
import org.mcsg.bot.util.FileUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class GenericPermissionManager implements PermissionManager{

	protected File file;
	protected Bot bot;
	protected Map<String, PermissionNode> tree;

	protected ObjectMapper mapper = new ObjectMapper();

	public GenericPermissionManager(Bot bot) {
		this.bot = bot;
		this.file = new File(bot.getSettings().getSettingsFolder(), "permissions.json");

		if(!this.file.exists()) {
			try {
				this.file.createNewFile();
			} catch (IOException e) {
				bot.err("Failed to load permission manager, could not create file "  + e.getMessage());
				e.printStackTrace();
				return;
			}
		}


		try {
			String json = FileUtils.readFile(this.file);

			tree = mapper.readValue(json, new TypeReference<Map<String, PermissionNode>>(){});
		} catch (IOException e) {
			bot.err("Failed to load permission manager, could not load json "  + e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public boolean hasPermission(BotServer server, BotUser user, String perm) {
		List<String> perms = getPermissionList(perm);
		PermissionNode current = tree.get(server.getId());

		if(current == null) {
			return false;
		}
		
		while(perms.size() > 0) {
			current = traverseTree(current, perms);
			
			if(current.getUsers().contains(user.getId())) {
				return true;
			}
			for(String group : current.getGroups()) {
				if(current.getGroups().contains(group)){
					return true;
				}
			}
			if(current.getGroups().contains("_default")) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void addGroupPermission(BotServer server, String group, String perm) {
		PermissionNode node = getDestinationNode(server.getId(), perm);
		node.getGroups().add(group);
		save();
	}


	@Override
	public void removeGroupPermission(BotServer server, String group, String perm) {
		PermissionNode node = getDestinationNode(server.getId(), perm);
		node.getGroups().remove(group);
		save();
	}


	@Override
	public void addPermission(BotServer server, BotUser user, String perm) {
		PermissionNode node = getDestinationNode(server.getId(), perm);
		node.getUsers().add(user.getId());
		save();
	}


	@Override
	public void removePermission(BotServer server, BotUser user, String perm) {
		PermissionNode node = getDestinationNode(server.getId(), perm);
		node.getUsers().remove(user.getId());
		save();
	}

	private void save() {
		try {
			String json = mapper.writeValueAsString(tree);
			
			FileUtils.writeFile(this.file, json);
		} catch (IOException e) {
			bot.err("Failed to save permissions file. " + e.getMessage());
			e.printStackTrace();
		}
	}

	private List<String> getPermissionList(String perm) {
		return new ArrayList<>(Arrays.asList(perm.split("\\.")));
	}
	
	
	private PermissionNode traverseTree(PermissionNode node, List<String> perms) {
		String key = perms.remove(0);
		
		PermissionNode next = node.getNext().get(key);
		if(next == null) {
			next = new PermissionNode();
			node.getNext().put(key, next);
		}
		return next;
	}
	
	
	private PermissionNode getDestinationNode(String server, String perm) {

		if(perm == null) {
			return null;
		}

		if(perm.length() == 0 || perm.equalsIgnoreCase("*")) {
			PermissionNode node = tree.get(server);
			if(node == null) {
				node = new PermissionNode();
				tree.put(server, node);
			}
			return node;
		}


		List<String> perms = getPermissionList(perm);
		PermissionNode current = tree.get(server);
		
		if(current == null) {
			current = new PermissionNode();
			tree.put(server, current);
		}

		while(perms.size() > 0) {
			current = traverseTree(current, perms);
		}

		return current;
	}

	private class PermissionNode {
		@Getter private ArrayList<String> users = new ArrayList<>();
		@Getter private ArrayList<String> groups = new ArrayList<>();
		@Getter @Setter  private Map<String, PermissionNode> next = new HashMap<>();
	}



}
