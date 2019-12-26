package br.com.atom.nsplanner.dtos;

public class ScalingCriteriaDto {

	private String name;
	private int scaleInThreshold;
	private String scaleInRelationalOperation;
	private int scaleOutThreshold;
	private String scaleOutRelationalOperation;
	private String nsMonitoringParamRef;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScaleInThreshold() {
		return scaleInThreshold;
	}
	public void setScaleInThreshold(int scaleInThreshold) {
		this.scaleInThreshold = scaleInThreshold;
	}
	public String getScaleInRelationalOperation() {
		return scaleInRelationalOperation;
	}
	public void setScaleInRelationalOperation(String scaleInRelationalOperation) {
		this.scaleInRelationalOperation = scaleInRelationalOperation;
	}
	public int getScaleOutThreshold() {
		return scaleOutThreshold;
	}
	public void setScaleOutThreshold(int scaleOutThreshold) {
		this.scaleOutThreshold = scaleOutThreshold;
	}
	public String getScaleOutRelationalOperation() {
		return scaleOutRelationalOperation;
	}
	public void setScaleOutRelationalOperation(String scaleOutRelationalOperation) {
		this.scaleOutRelationalOperation = scaleOutRelationalOperation;
	}
	public String getNsMonitoringParamRef() {
		return nsMonitoringParamRef;
	}
	public void setNsMonitoringParamRef(String nsMonitoringParamRef) {
		this.nsMonitoringParamRef = nsMonitoringParamRef;
	}	
	
}
