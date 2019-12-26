package br.com.atom.nsplanner.dtos;

import java.util.ArrayList;

public class HighLevelPolicyDto {

	private String name;
	private ArrayList<GoalDto> goals;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<GoalDto> getGoals() {
		return goals;
	}
	public void setGoals(ArrayList<GoalDto> goals) {
		this.goals = goals;
	}	
	
}
