package br.com.atom.nsplanner.classes;

import java.util.ArrayList;

public class HealingGroup {

	private String name;
	private ArrayList<HealingPolicy> healingPolicies;
	private ArrayList<VNFDMember> vnfdMembers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<HealingPolicy> getHealingPolicies() {
		return healingPolicies;
	}

	public void setHealingPolicies(ArrayList<HealingPolicy> healingPolicies) {
		this.healingPolicies = healingPolicies;
	}

	public ArrayList<VNFDMember> getVnfdMembers() {
		return vnfdMembers;
	}

	public void setVnfdMembers(ArrayList<VNFDMember> vnfdMembers) {
		this.vnfdMembers = vnfdMembers;
	}

}
