package br.com.atom.nsplanner.dtos;

public class HealingThresholdDto {
	
	private String name;
	private String action;
	private String metric;
	private int highThresholdValue;
	private int mediumThresholdValue;
	private int lowThresholdValue;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getMetric() {
		return metric;
	}
	public void setMetric(String metric) {
		this.metric = metric;
	}
	public int getHighThresholdValue() {
		return highThresholdValue;
	}
	public void setHighThresholdValue(int highThresholdValue) {
		this.highThresholdValue = highThresholdValue;
	}
	public int getMediumThresholdValue() {
		return mediumThresholdValue;
	}
	public void setMediumThresholdValue(int mediumThresholdValue) {
		this.mediumThresholdValue = mediumThresholdValue;
	}
	public int getLowThresholdValue() {
		return lowThresholdValue;
	}
	public void setLowThresholdValue(int lowThresholdValue) {
		this.lowThresholdValue = lowThresholdValue;
	}	
	
}


