package org.mcsg.bot.profiler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profiler {

	private static Profiler profiler = new Profiler();
	
	
	public static Profiler get() {
		return profiler;
	}
	
	private Profiler() {
		this.startTime = new Date().getTime();
	}

	private List<ProfileInstance> list = new ArrayList<>();
	private long startTime;
	
	public void addInstance(ProfileInstance instance) {
		this.list.add(instance);
	}
	
	public List<ProfileInstance> getList() {
		return list;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	
	public List<Report> generateReports(long ... times) {
		List< Report> reports = new ArrayList<>();
		for(long time : times) {
			reports.add(new Report(time));
		}
		
		for(ProfileInstance instance : list) {
			for(Report report : reports) {
				report.addInstance(instance);
			}
		}
		
		return reports;
	}
	
	
}
