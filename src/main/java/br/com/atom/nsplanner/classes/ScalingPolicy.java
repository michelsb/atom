package br.com.atom.nsplanner.classes;

import java.util.ArrayList;

public class ScalingPolicy {

	private String name;
	private String scalingType;
	private boolean enabled;
	private String scaleInOperationType;
	private String scaleOutOperationType;
	private int thresholdTime;
	private int coolDownTime;
	private ArrayList<ScalingCriteria> scalingCriterias;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScalingType() {
		return scalingType;
	}
	public void setScalingType(String scalingType) {
		this.scalingType = scalingType;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getScaleInOperationType() {
		return scaleInOperationType;
	}
	public void setScaleInOperationType(String scaleInOperationType) {
		this.scaleInOperationType = scaleInOperationType;
	}
	public String getScaleOutOperationType() {
		return scaleOutOperationType;
	}
	public void setScaleOutOperationType(String scaleOutOperationType) {
		this.scaleOutOperationType = scaleOutOperationType;
	}
	public int getThresholdTime() {
		return thresholdTime;
	}
	public void setThresholdTime(int thresholdTime) {
		this.thresholdTime = thresholdTime;
	}
	public int getCoolDownTime() {
		return coolDownTime;
	}
	public void setCoolDownTime(int coolDownTime) {
		this.coolDownTime = coolDownTime;
	}
	public ArrayList<ScalingCriteria> getScalingCriterias() {
		return scalingCriterias;
	}
	public void setScalingCriterias(ArrayList<ScalingCriteria> scalingCriterias) {
		this.scalingCriterias = scalingCriterias;
	}
	
	
	
}
