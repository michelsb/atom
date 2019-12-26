package br.com.atom.nschecker.dtos;

import java.util.ArrayList;

import br.com.atom.nschecker.classes.VNFC;

public class VNFDto {
	
	private String name;
	private ArrayList<VNFC> vnfcs;	
	private ArrayList<VirtConnBetweenVNFCsDto> connVNFCs;
	private VNFPortDto ingressPort;
	private VNFPortDto egressPort;
	private VNFC ingressVNFC;
	private VNFC egressVNFC;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<VNFC> getVnfcs() {
		return vnfcs;
	}
	
	public void setVnfcs(ArrayList<VNFC> vnfcs) {
		this.vnfcs = vnfcs;
	}
	
	public ArrayList<VirtConnBetweenVNFCsDto> getConnVNFCs() {
		return connVNFCs;
	}
	
	public void setConnVNFCs(ArrayList<VirtConnBetweenVNFCsDto> connVNFCs) {
		this.connVNFCs = connVNFCs;
	}

	public VNFPortDto getIngressPort() {
		return ingressPort;
	}

	public void setIngressPort(VNFPortDto ingressPort) {
		this.ingressPort = ingressPort;
	}

	public VNFPortDto getEgressPort() {
		return egressPort;
	}

	public void setEgressPort(VNFPortDto egressPort) {
		this.egressPort = egressPort;
	}

	public VNFC getIngressVNFC() {
		return ingressVNFC;
	}

	public void setIngressVNFC(VNFC ingressVNFC) {
		this.ingressVNFC = ingressVNFC;
	}

	public VNFC getEgressVNFC() {
		return egressVNFC;
	}

	public void setEgressVNFC(VNFC egressVNFC) {
		this.egressVNFC = egressVNFC;
	}

	
	
}
