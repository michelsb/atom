package br.com.atom.nsplanner.dtos;

import java.util.ArrayList;

public class HealingPolicyDto {

	private String name;
	private String healingType;
	private boolean enabled;
	private String healOperationType;
	private int thresholdTime;
	private int coolDownTime;
	private ArrayList<HealingCriteriaDto> healingCriterias;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHealingType() {
		return healingType;
	}
	public void setHealingType(String healingType) {
		this.healingType = healingType;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getHealOperationType() {
		return healOperationType;
	}
	public void setHealOperationType(String healOperationType) {
		this.healOperationType = healOperationType;
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
	public ArrayList<HealingCriteriaDto> getHealingCriterias() {
		return healingCriterias;
	}
	public void setHealingCriterias(ArrayList<HealingCriteriaDto> healingCriterias) {
		this.healingCriterias = healingCriterias;
	}
	
	
	
}
