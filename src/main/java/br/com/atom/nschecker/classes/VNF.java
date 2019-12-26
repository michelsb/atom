package br.com.atom.nschecker.classes;

public class VNF {

	private String id;
	private String name;
	private VNFFG vnffg; 
	//private ArrayList<VNFC> vnfcs;
	//private String vnfIndName;
	//private String ingressPortIndName;
	//private String egressPortIndName;

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

	public VNFFG getVnffg() {
		return vnffg;
	}

	public void setVnffg(VNFFG vnffg) {
		this.vnffg = vnffg;
	}

	
	
	/*public ArrayList<VNFC> getVnfcs() {
		return vnfcs;
	}

	public void setVnfcs(ArrayList<VNFC> vnfcs) {
		this.vnfcs = vnfcs;
	}

	public String getVnfIndName() {
		return vnfIndName;
	}

	public void setVnfIndName(String vnfIndName) {
		this.vnfIndName = vnfIndName;
	}

	public String getIngressPortIndName() {
		return ingressPortIndName;
	}

	public void setIngressPortIndName(String ingressPortIndName) {
		this.ingressPortIndName = ingressPortIndName;
	}

	public String getEgressPortIndName() {
		return egressPortIndName;
	}

	public void setEgressPortIndName(String egressPortIndName) {
		this.egressPortIndName = egressPortIndName;
	}*/

}
