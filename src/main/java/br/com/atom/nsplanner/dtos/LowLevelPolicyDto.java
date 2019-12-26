package br.com.atom.nsplanner.dtos;

import java.util.ArrayList;

public class LowLevelPolicyDto {

	private String name;
	private ArrayList<HealingThresholdDto> healPols;
	private ArrayList<HorizontalScalingThresholdDto> horScalePols;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<HealingThresholdDto> getHealPols() {
		return healPols;
	}
	public void setHealPols(ArrayList<HealingThresholdDto> healPols) {
		this.healPols = healPols;
	}
	public ArrayList<HorizontalScalingThresholdDto> getHorScalePols() {
		return horScalePols;
	}
	public void setHorScalePols(ArrayList<HorizontalScalingThresholdDto> horScalePols) {
		this.horScalePols = horScalePols;
	}	
	
}
