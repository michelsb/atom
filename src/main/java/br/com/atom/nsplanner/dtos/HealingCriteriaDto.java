package br.com.atom.nsplanner.dtos;

public class HealingCriteriaDto {

	private String name;
	private String actionType;
	private int healThreshold;
	private String healRelationalOperation;
	private String nsMonitoringParamRef;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public int getHealThreshold() {
		return healThreshold;
	}

	public void setHealThreshold(int healThreshold) {
		this.healThreshold = healThreshold;
	}

	public String getHealRelationalOperation() {
		return healRelationalOperation;
	}

	public void setHealRelationalOperation(String healRelationalOperation) {
		this.healRelationalOperation = healRelationalOperation;
	}

	public String getNsMonitoringParamRef() {
		return nsMonitoringParamRef;
	}

	public void setNsMonitoringParamRef(String nsMonitoringParamRef) {
		this.nsMonitoringParamRef = nsMonitoringParamRef;
	}

}
