package br.com.atom.nsplanner.dtos;

import java.util.ArrayList;

public class PlanDto {

	private String name;
	private String nsrId;
	private ArrayList<String> vnfMemberIndexes;
	private ArrayList<RuleDto> rules;
	private ArrayList<ScalingGroupDto> scalingGroups;
	private ArrayList<HealingGroupDto> healingGroups;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNsrId() {
		return nsrId;
	}

	public void setNsrId(String nsrId) {
		this.nsrId = nsrId;
	}

	public ArrayList<String> getVnfMemberIndexes() {
		return vnfMemberIndexes;
	}

	public void setVnfMemberIndexes(ArrayList<String> vnfMemberIndexes) {
		this.vnfMemberIndexes = vnfMemberIndexes;
	}

	public ArrayList<RuleDto> getRules() {
		return rules;
	}

	public void setRules(ArrayList<RuleDto> rules) {
		this.rules = rules;
	}

	public ArrayList<ScalingGroupDto> getScalingGroups() {
		return scalingGroups;
	}

	public void setScalingGroups(ArrayList<ScalingGroupDto> scalingGroups) {
		this.scalingGroups = scalingGroups;
	}

	public ArrayList<HealingGroupDto> getHealingGroups() {
		return healingGroups;
	}

	public void setHealingGroups(ArrayList<HealingGroupDto> healingGroups) {
		this.healingGroups = healingGroups;
	}

}
