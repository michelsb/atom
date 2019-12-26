package br.com.atom.nsplanner.dtos;

import java.util.ArrayList;

public class HealingGroupDto {

	private String name;
	private HealingPolicyDto healingPolicy;
	private ArrayList<VNFDMemberDto> vnfdMembers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HealingPolicyDto getHealingPolicy() {
		return healingPolicy;
	}

	public void setHealingPolicy(HealingPolicyDto healingPolicy) {
		this.healingPolicy = healingPolicy;
	}

	public ArrayList<VNFDMemberDto> getVnfdMembers() {
		return vnfdMembers;
	}

	public void setVnfdMembers(ArrayList<VNFDMemberDto> vnfdMembers) {
		this.vnfdMembers = vnfdMembers;
	}

}
