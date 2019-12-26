package br.com.atom.nschecker.dtos;

import java.util.ArrayList;

import br.com.atom.nschecker.classes.VNFC;

public class NSReqDto {

	private ArrayList<VNFC> vnfcs = new ArrayList<VNFC>();
	private ArrayList<RelationshipDto> netFuncPrec = new ArrayList<RelationshipDto>();
	private AffinityRestDto affRestVNFC = new AffinityRestDto();
	private AffinityRestDto affRestNetFunc = new AffinityRestDto();
	private AntiAffinityRestDto antiAffRestVNFC = new AntiAffinityRestDto();
	private AntiAffinityRestDto antiAffRestNetFunc = new AntiAffinityRestDto();
	
	public ArrayList<VNFC> getVnfcs() {
		return vnfcs;
	}
	
	public void setVnfcs(ArrayList<VNFC> vnfcs) {
		this.vnfcs = vnfcs;
	}
	
	public ArrayList<RelationshipDto> getNetFuncPrec() {
		return netFuncPrec;
	}
	
	public void setNetFuncPrec(ArrayList<RelationshipDto> netFuncPrec) {
		this.netFuncPrec = netFuncPrec;
	}
	
	public AffinityRestDto getAffRestVNFC() {
		return affRestVNFC;
	}
	
	public void setAffRestVNFC(AffinityRestDto affRestVNFC) {
		this.affRestVNFC = affRestVNFC;
	}
	
	public AffinityRestDto getAffRestNetFunc() {
		return affRestNetFunc;
	}
	
	public void setAffRestNetFunc(AffinityRestDto affRestNetFunc) {
		this.affRestNetFunc = affRestNetFunc;
	}
	
	public AntiAffinityRestDto getAntiAffRestVNFC() {
		return antiAffRestVNFC;
	}
	
	public void setAntiAffRestVNFC(AntiAffinityRestDto antiAffRestVNFC) {
		this.antiAffRestVNFC = antiAffRestVNFC;
	}
	
	public AntiAffinityRestDto getAntiAffRestNetFunc() {
		return antiAffRestNetFunc;
	}
	
	public void setAntiAffRestNetFunc(AntiAffinityRestDto antiAffRestNetFunc) {
		this.antiAffRestNetFunc = antiAffRestNetFunc;
	}	
		
}
