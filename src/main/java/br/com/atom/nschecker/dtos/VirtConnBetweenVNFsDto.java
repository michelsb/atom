package br.com.atom.nschecker.dtos;

public class VirtConnBetweenVNFsDto {

	private String nameVNF1;
	private String nameVNF2;	
	private float speed;
	
	public String getNameVNF1() {
		return nameVNF1;
	}
	
	public void setNameVNF1(String nameVNF1) {
		this.nameVNF1 = nameVNF1;
	}
	
	public String getNameVNF2() {
		return nameVNF2;
	}
	
	public void setNameVNF2(String nameVNF2) {
		this.nameVNF2 = nameVNF2;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}	
	
}
