package br.com.atom.nschecker.classes;

public class VNFC {

	private String id;
	private String name;
	private CPU cpu;
	private Memory memory;
	private Storage storage;
	private int numInterfaces;
	private String netFunction;
	private Node guestNode;
	private VNF vnf;
	
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
	
	public CPU getCpu() {
		return cpu;
	}
	
	public void setCpu(CPU cpu) {
		this.cpu = cpu;
	}
	
	public Memory getMemory() {
		return memory;
	}
	
	public void setMemory(Memory memory) {
		this.memory = memory;
	}
	
	public Storage getStorage() {
		return storage;
	}
	
	public void setStorage(Storage storage) {
		this.storage = storage;
	}
	
	public int getNumInterfaces() {
		return numInterfaces;
	}
	
	public void setNumInterfaces(int numInterfaces) {
		this.numInterfaces = numInterfaces;
	}
	
	public String getNetFunction() {
		return netFunction;
	}
	
	public void setNetFunction(String netFunction) {
		this.netFunction = netFunction;
	}
	
	public Node getGuestNode() {
		return guestNode;
	}
	
	public void setGuestNode(Node guestNode) {
		this.guestNode = guestNode;
	}
	
	public VNF getVnf() {
		return vnf;
	}
	
	public void setVnf(VNF vnf) {
		this.vnf = vnf;
	}

	
	
}
