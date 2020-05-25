package org.linty.plugins.fpgametrics.gsonData;

public class JsonMetric {
	
	private String name;
	private String type;
	private String description;
	private String domain;
	private double worstValue;
	private double bestValue;
	private boolean qualitative;
	private int direction;
	private boolean optimizedBestValue;
	  
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	public boolean isQualitative() {
		return qualitative;
	}
	
	public String getDomain() {
		return domain;
	}

	public double getWorstValue() {
		return worstValue;
	}

	public double getBestValue() {
		return bestValue;
	}
	
	public boolean isOptimizedBestValue() {
		return optimizedBestValue;
	}
	public int getDirection() {
		return direction;
	}
	  
}
