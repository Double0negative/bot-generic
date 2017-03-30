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
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
			bot.err("Failed to load permission manager, could not load json");
			bot.throwable(e);
			e.printStackTrace();
		}

	}

	@Override
	public boolean hasPermission(BotServer server, BotUser user, String perm) {
		return hasPermission(server, user.getId(), perm);
	}
	
	@Override
	public boolean hasPermission(BotServer server, String user, String perm) {
		List<String> perms = getPermissionList(perm);
		PermissionNode current = tree.get(server.getId());

		if(user != null && user.equalsIgnoreCase(bot.getAdminId())) {
			return true;
		}
		
		if(current == null || user == null || perm == null) {
			return false;
		}
		
		
		while(perms.size() > 0) {
			
			if(current.getUsers().contains(user)) {
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
			
			current = traverseTree(current, perms);
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
	public void addPermission(BotServer server, String user, String perm) {
		PermissionNode node = getDestinationNode(server.getId(), perm);
		node.getUsers().add(user);
		save();
	}


	@Override
	public void removePermission(BotServer server, String user, String perm) {
		PermissionNode node = getDestinationNode(server.getId(), perm);
		node.getUsers().remove(user);
		save();
	}

	private void save() {
		try {
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree);
			
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
				
		if(node == null) {
			throw null;
		}
		
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

	





}
