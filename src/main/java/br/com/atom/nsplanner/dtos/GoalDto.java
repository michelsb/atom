package br.com.atom.nsplanner.dtos;

import java.util.ArrayList;

public class GoalDto {
	
	private String name;
	private String nsrId;
	private ArrayList<String> vnfMemberIndexes;
	private String level;
	private ArrayList<String> attributes;
	
	
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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public ArrayList<String> getAttributes() {
		return attributes;
	}
	public void setAttributes(ArrayList<String> attributes) {
		this.attributes = attributes;
	}	
	
}
