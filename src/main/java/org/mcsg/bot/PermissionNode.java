package org.mcsg.bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public @ToString  class PermissionNode {
		
		public PermissionNode() {
			
		}
		
		@Getter private ArrayList<String> users = new ArrayList<>();
		@Getter private ArrayList<String> groups = new ArrayList<>();
		@Getter @Setter  private Map<String, PermissionNode> next = new HashMap<>();
		
		
	}