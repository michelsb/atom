package br.com.atom.nschecker.classes;

import java.io.File;
import java.util.ArrayList;

public class TopologyStatus {

	private int totalNFVIPops;
	private int totalNFVINodes;
	private int totalInterPoPLinks;
	private File file;
	private ArrayList<String> namesNFVIPoPs;
	
	public int getTotalNFVIPops() {
		return totalNFVIPops;
	}
	public void setTotalNFVIPops(int totalNFVIPops) {
		this.totalNFVIPops = totalNFVIPops;
	}
	public int getTotalNFVINodes() {
		return totalNFVINodes;
	}
	public void setTotalNFVINodes(int totalNFVINodes) {
		this.totalNFVINodes = totalNFVINodes;
	}
	public int getTotalInterPoPLinks() {
		return totalInterPoPLinks;
	}
	public void setTotalInterPoPLinks(int totalInterPoPLinks) {
		this.totalInterPoPLinks = totalInterPoPLinks;
	}	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public ArrayList<String> getNamesNFVIPoPs() {
		return namesNFVIPoPs;
	}
	public void setNamesNFVIPoPs(ArrayList<String> namesNFVPoPs) {
		this.namesNFVIPoPs = namesNFVPoPs;
	}		
}
