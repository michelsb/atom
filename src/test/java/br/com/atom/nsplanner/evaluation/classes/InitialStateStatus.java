package br.com.atom.nsplanner.evaluation.classes;

import java.io.File;

public class InitialStateStatus {

	private int totalThresholds;
	private int totalGoals;
	private File file;

	public int getTotalThresholds() {
		return totalThresholds;
	}

	public void setTotalThresholds(int totalThresholds) {
		this.totalThresholds = totalThresholds;
	}

	public int getTotalGoals() {
		return totalGoals;
	}

	public void setTotalGoals(int totalGoals) {
		this.totalGoals = totalGoals;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
