package br.com.atom.nschecker.dtos;

public class PhyConnBetweenPoPsDto {

	private String nameNode1;
	private String nameNode2;
	private int inteIndexNode1;
	private int inteIndexNode2;
	private float speed;
	private int vxlanId;
	
	public String getNameNode1() {
		return nameNode1;
	}
	
	public void setNameNode1(String nameNode1) {
		this.nameNode1 = nameNode1;
	}
	
	public String getNameNode2() {
		return nameNode2;
	}
	
	public void setNameNode2(String nameNode2) {
		this.nameNode2 = nameNode2;
	}
	
	public int getInteIndexNode1() {
		return inteIndexNode1;
	}
	
	public void setInteIndexNode1(int inteIndexNode1) {
		this.inteIndexNode1 = inteIndexNode1;
	}
	
	public int getInteIndexNode2() {
		return inteIndexNode2;
	}
	
	public void setInteIndexNode2(int inteIndexNode2) {
		this.inteIndexNode2 = inteIndexNode2;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public int getVxlanId() {
		return vxlanId;
	}
	
	public void setVxlanId(int vxlanId) {
		this.vxlanId = vxlanId;
	}	
	
}
