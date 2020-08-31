package org.mcsg.bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class PermissionNode {

	private ArrayList<String> users = new ArrayList<>();
	private ArrayList<String> groups = new ArrayList<>();
	private Map<String, PermissionNode> next = new HashMap<>();
	
	public PermissionNode() {

	}
	
	public List<String> getUsers() {
		return this.users;
	}
	
	public List<String> getGroups(){
		return this.groups;
	}
	
	public void setNext(Map<String, PermissionNode> next) {
		this.next = next;
	}
	
	public Map<String, PermissionNode> getNext() {
		return this.next;
	}
}
