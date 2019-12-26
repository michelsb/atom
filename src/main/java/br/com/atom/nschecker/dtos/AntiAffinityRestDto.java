package br.com.atom.nschecker.dtos;

import java.util.ArrayList;

public class AntiAffinityRestDto {

	private ArrayList<RelationshipDto> hasNotSameNFVINode = new ArrayList<RelationshipDto>();
	private ArrayList<RelationshipDto> hasNotSameNFVIPoP = new ArrayList<RelationshipDto>();
	private ArrayList<RelationshipDto> hasNotSameNFVI = new ArrayList<RelationshipDto>();
	
	public ArrayList<RelationshipDto> getHasNotSameNFVINode() {
		return hasNotSameNFVINode;
	}
	
	public void setHasNotSameNFVINode(ArrayList<RelationshipDto> hasNotSameNFVINode) {
		this.hasNotSameNFVINode = hasNotSameNFVINode;
	}
	
	public ArrayList<RelationshipDto> getHasNotSameNFVIPoP() {
		return hasNotSameNFVIPoP;
	}
	
	public void setHasNotSameNFVIPoP(ArrayList<RelationshipDto> hasNotSameNFVIPoP) {
		this.hasNotSameNFVIPoP = hasNotSameNFVIPoP;
	}
	
	public ArrayList<RelationshipDto> getHasNotSameNFVI() {
		return hasNotSameNFVI;
	}
	
	public void setHasNotSameNFVI(ArrayList<RelationshipDto> hasNotSameNFVI) {
		this.hasNotSameNFVI = hasNotSameNFVI;
	}	
	
}
