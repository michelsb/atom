package br.com.atom.nschecker.dtos;

import java.util.ArrayList;

public class NetworkServiceDto {

	private String name;
	private ArrayList<VNFDto> vnfs;
	private ArrayList<VirtConnBetweenVNFsDto> connVNFs;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<VNFDto> getVnfs() {
		return vnfs;
	}
	
	public void setVnfs(ArrayList<VNFDto> vnfs) {
		this.vnfs = vnfs;
	}
	
	public ArrayList<VirtConnBetweenVNFsDto> getConnVNFs() {
		return connVNFs;
	}
	
	public void setConnVNFs(ArrayList<VirtConnBetweenVNFsDto> connVNFs) {
		this.connVNFs = connVNFs;
	}	
	
}
