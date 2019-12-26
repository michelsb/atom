package br.com.atom.nsplanner.dtos;

import java.util.ArrayList;

public class RuleDto {

	private String name;
	private ArrayList<String> events;
	private ArrayList<ConditionDto> conditions;
	private ArrayList<ActionDto> actions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<String> events) {
		this.events = events;
	}

	public ArrayList<ConditionDto> getConditions() {
		return conditions;
	}

	public void setConditions(ArrayList<ConditionDto> conditions) {
		this.conditions = conditions;
	}

	public ArrayList<ActionDto> getActions() {
		return actions;
	}

	public void setActions(ArrayList<ActionDto> actions) {
		this.actions = actions;
	}

}
