package br.com.atom.nsplanner.classes;

import java.util.ArrayList;

public class ScalingGroup {

	private String name;
	private ArrayList<ScalingPolicy> scalingPolicies;
	private ArrayList<VNFDMember> vnfdMembers;
	private int minInstanceCount;
	private int maxInstanceCount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<ScalingPolicy> getScalingPolicies() {
		return scalingPolicies;
	}

	public void setScalingPolicies(ArrayList<ScalingPolicy> scalingPolicies) {
		this.scalingPolicies = scalingPolicies;
	}

	public ArrayList<VNFDMember> getVnfdMembers() {
		return vnfdMembers;
	}

	public void setVnfdMembers(ArrayList<VNFDMember> vnfdMembers) {
		this.vnfdMembers = vnfdMembers;
	}

	public int getMinInstanceCount() {
		return minInstanceCount;
	}

	public void setMinInstanceCount(int minInstanceCount) {
		this.minInstanceCount = minInstanceCount;
	}

	public int getMaxInstanceCount() {
		return maxInstanceCount;
	}

	public void setMaxInstanceCount(int maxInstanceCount) {
		this.maxInstanceCount = maxInstanceCount;
	}

}
