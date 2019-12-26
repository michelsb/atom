package br.com.atom.nsplanner.dtos;

import java.util.ArrayList;

public class ConditionDto {

	private String key;
	private ArrayList<String> values;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

}
