package br.com.atom.nsplanner.dtos;

import java.util.ArrayList;

public class ScalingGroupDto {

	private String name;
	private ScalingPolicyDto scalingPolicy;
	private ArrayList<VNFDMemberDto> vnfdMembers;
	private int minInstanceCount;
	private int maxInstanceCount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	public ScalingPolicyDto getScalingPolicy() {
		return scalingPolicy;
	}

	public void setScalingPolicy(ScalingPolicyDto scalingPolicy) {
		this.scalingPolicy = scalingPolicy;
	}

	public ArrayList<VNFDMemberDto> getVnfdMembers() {
		return vnfdMembers;
	}

	public void setVnfdMembers(ArrayList<VNFDMemberDto> vnfdMembers) {
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
