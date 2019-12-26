package br.com.atom.nschecker.dtos;

import java.util.ArrayList;

public class NFVIDto {
	
	private String name;
	private ArrayList<NFVIPoPDto> nfviPoPTops;
	private ArrayList<PhyConnBetweenPoPsDto> connPoPs;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<NFVIPoPDto> getNfviPoPTops() {
		return nfviPoPTops;
	}
	
	public void setNfviPoPTops(ArrayList<NFVIPoPDto> nfviPoPTops) {
		this.nfviPoPTops = nfviPoPTops;
	}

	public ArrayList<PhyConnBetweenPoPsDto> getConnPoPs() {
		return connPoPs;
	}

	public void setConnPoPs(ArrayList<PhyConnBetweenPoPsDto> connPoPs) {
		this.connPoPs = connPoPs;
	}	
	
}
