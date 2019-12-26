package br.com.atom.nsplanner.dtos;

public class HorizontalScalingThresholdDto {
	
	private String name;
	private String metric;
	private int highScaleOutThresholdValue;
	private int highScaleInThresholdValue;	
	private int mediumScaleOutThresholdValue;
	private int mediumScaleInThresholdValue;	
	private int lowScaleOutThresholdValue;
	private int lowScaleInThresholdValue;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMetric() {
		return metric;
	}
	public void setMetric(String metric) {
		this.metric = metric;
	}
	public int getHighScaleOutThresholdValue() {
		return highScaleOutThresholdValue;
	}
	public void setHighScaleOutThresholdValue(int highScaleOutThresholdValue) {
		this.highScaleOutThresholdValue = highScaleOutThresholdValue;
	}
	public int getHighScaleInThresholdValue() {
		return highScaleInThresholdValue;
	}
	public void setHighScaleInThresholdValue(int highScaleInThresholdValue) {
		this.highScaleInThresholdValue = highScaleInThresholdValue;
	}
	public int getMediumScaleOutThresholdValue() {
		return mediumScaleOutThresholdValue;
	}
	public void setMediumScaleOutThresholdValue(int mediumScaleOutThresholdValue) {
		this.mediumScaleOutThresholdValue = mediumScaleOutThresholdValue;
	}
	public int getMediumScaleInThresholdValue() {
		return mediumScaleInThresholdValue;
	}
	public void setMediumScaleInThresholdValue(int mediumScaleInThresholdValue) {
		this.mediumScaleInThresholdValue = mediumScaleInThresholdValue;
	}
	public int getLowScaleOutThresholdValue() {
		return lowScaleOutThresholdValue;
	}
	public void setLowScaleOutThresholdValue(int lowScaleOutThresholdValue) {
		this.lowScaleOutThresholdValue = lowScaleOutThresholdValue;
	}
	public int getLowScaleInThresholdValue() {
		return lowScaleInThresholdValue;
	}
	public void setLowScaleInThresholdValue(int lowScaleInThresholdValue) {
		this.lowScaleInThresholdValue = lowScaleInThresholdValue;
	}
	
	
	
}
