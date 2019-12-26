package br.com.atom.nschecker.dtos;

import java.util.ArrayList;

public class AffinityRestDto {

	private ArrayList<RelationshipDto> hasSameNFVINode = new ArrayList<RelationshipDto>();
	private ArrayList<RelationshipDto> hasSameNFVIPoP = new ArrayList<RelationshipDto>();
	private ArrayList<RelationshipDto> hasSameNFVI = new ArrayList<RelationshipDto>();
	
	public ArrayList<RelationshipDto> getHasSameNFVINode() {
		return hasSameNFVINode;
	}
	
	public void setHasSameNFVINode(ArrayList<RelationshipDto> hasSameNFVINode) {
		this.hasSameNFVINode = hasSameNFVINode;
	}
	
	public ArrayList<RelationshipDto> getHasSameNFVIPoP() {
		return hasSameNFVIPoP;
	}
	
	public void setHasSameNFVIPoP(ArrayList<RelationshipDto> hasSameNFVIPoP) {
		this.hasSameNFVIPoP = hasSameNFVIPoP;
	}
	
	public ArrayList<RelationshipDto> getHasSameNFVI() {
		return hasSameNFVI;
	}
	
	public void setHasSameNFVI(ArrayList<RelationshipDto> hasSameNFVI) {
		this.hasSameNFVI = hasSameNFVI;
	}
	
	
}
