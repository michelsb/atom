package br.com.atom.nschecker.evaluation.classes;

import java.util.ArrayList;

public class VNFFG {

	private String id;
	private String name;
	private ArrayList<VNF> vnfs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<VNF> getVnfs() {
		return vnfs;
	}

	public void setVnfs(ArrayList<VNF> vnfs) {
		this.vnfs = vnfs;
	}

}
