package br.com.atom.nschecker.dtos;

import java.util.ArrayList;

import br.com.atom.nschecker.classes.Node;

public class NFVIPoPDto {
	
	private String name;
	private ArrayList<Node> nfviNodes;
	private ArrayList<Node> switchNodes;
	private ArrayList<PhyConnBetweenHostsDto> connHosts;	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Node> getNfviNodes() {
		return nfviNodes;
	}
	
	public void setNfviNodes(ArrayList<Node> nfviNodes) {
		this.nfviNodes = nfviNodes;
	}

	public ArrayList<Node> getSwitchNodes() {
		return switchNodes;
	}

	public void setSwitchNodes(ArrayList<Node> switchNodes) {
		this.switchNodes = switchNodes;
	}

	public ArrayList<PhyConnBetweenHostsDto> getConnHosts() {
		return connHosts;
	}

	public void setConnHosts(ArrayList<PhyConnBetweenHostsDto> connHosts) {
		this.connHosts = connHosts;
	}
	
}
