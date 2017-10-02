package org.mcsg.bot.profiler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Report {


	private long duration;
	private long reportStart;


	private int total;
	private int totalCommands;
	private int totalCompleted;
	private long minExecTime = Long.MAX_VALUE;
	private double avgExecTime;
	private long maxExecTime;
	private ProfileInstance maxInstance;
	private Map<String, CommandProfile> commandGraph = new HashMap<>();


	public Report(long duration) {
		this.duration = duration;
		this.reportStart = new Date().getTime();
	}


	public void addInstance(ProfileInstance instance) {
		if(instance.getTimestamp() > this.reportStart - this.duration) {

			if(instance.isCommand()) {
				if(instance.isCompleted()){
					if(this.maxExecTime < instance.getExecutionTime()) {
						this.maxInstance = instance;
						this.maxExecTime = instance.getExecutionTime();
					}

					this.minExecTime = Math.min(instance.getExecutionTime(), minExecTime);
					this.avgExecTime = (((this.avgExecTime * this.totalCompleted) + (instance.getExecutionTime() + 0.0)) / (this.totalCompleted + 1));
					this.totalCompleted++;
				}
				CommandProfile cmd = this.commandGraph.getOrDefault(instance.getCommand(), new CommandProfile(instance.getCommand()));
				cmd.add(instance.getExecutionTime());
				this.commandGraph.put(instance.getCommand(), cmd);

				this.totalCommands++;
			}
			this.total ++;
		}
	}

	class CommandProfile {
		private String command;
		private long count;
		private double avg;

		public CommandProfile(String command) {
			this.command = command;
		}

		public void add(long time) {
			this.avg = ((this.count * this.avg) + (time + 0.0)) / (this.count + 1.0) ;
		}

		public String getCommand() {
			return command;
		}

		public long getCount() {
			return count;
		}

		public double getAvg() {
			return avg;
		}

	}

	public long getDuration() {
		return duration;
	}


	public long getReportStart() {
		return reportStart;
	}


	public int getTotal() {
		return total;
	}


	public int getTotalCommands() {
		return totalCommands;
	}


	public int getTotalCompleted() {
		return totalCompleted;
	}


	public long getMinExecTime() {
		return minExecTime;
	}


	public double getAvgExecTime() {
		return avgExecTime;
	}


	public long getMaxExecTime() {
		return maxExecTime;
	}


	public ProfileInstance getMaxInstance() {
		return maxInstance;
	}


	public Map<String, CommandProfile> getCommandGraph() {
		return commandGraph;
	}



}
