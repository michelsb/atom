package br.com.atom.common.responses;

import java.util.ArrayList;

public class ConsistencyResponse {

	private boolean consistent;
	private ArrayList<ArrayList<String>> explanations;
	
	public boolean isConsistent() {
		return consistent;
	}
	public void setConsitent(boolean consistent) {
		this.consistent = consistent;
	}
	public ArrayList<ArrayList<String>> getExplanations() {
		return explanations;
	}
	public void setExplanations(ArrayList<ArrayList<String>> explanations) {
		this.explanations = explanations;
	}	
	
}
