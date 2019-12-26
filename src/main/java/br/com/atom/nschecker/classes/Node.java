package br.com.atom.nschecker.classes;

public class Node {

	private String id;
	private String name;
	private CPU cpu;
	private Memory memory;
	private Storage storage;
	private SwitchingMatrix switchingMatrix;
	private NFVIPoP nfvipop;
	private int numInterfaces;
	private int maxNumVNFs;
	private int maxNumVCPUs;
	private int maxNumVMems;
	private int maxNumVStos;

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

	public SwitchingMatrix getSwitchingMatrix() {
		return switchingMatrix;
	}

	public void setSwitchingMatrix(SwitchingMatrix switchingMatrix) {
		this.switchingMatrix = switchingMatrix;
	}

	public NFVIPoP getNfvipop() {
		return nfvipop;
	}

	public void setNfvipop(NFVIPoP nfvipop) {
		this.nfvipop = nfvipop;
	}

	public int getNumInterfaces() {
		return numInterfaces;
	}

	public void setNumInterfaces(int numInterfaces) {
		this.numInterfaces = numInterfaces;
	}

	public int getMaxNumVNFs() {
		return maxNumVNFs;
	}

	public void setMaxNumVNFs(int maxNumVNFs) {
		this.maxNumVNFs = maxNumVNFs;
	}

	public int getMaxNumVCPUs() {
		return maxNumVCPUs;
	}

	public void setMaxNumVCPUs(int maxNumVCPUs) {
		this.maxNumVCPUs = maxNumVCPUs;
	}

	public int getMaxNumVMems() {
		return maxNumVMems;
	}

	public void setMaxNumVMems(int maxNumVMems) {
		this.maxNumVMems = maxNumVMems;
	}

	public int getMaxNumVStos() {
		return maxNumVStos;
	}

	public void setMaxNumVStos(int maxNumVStos) {
		this.maxNumVStos = maxNumVStos;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", name=" + name + ", cpu=" + cpu + ", memory=" + memory + ", storage=" + storage
				+ ", switchingMatrix=" + switchingMatrix + ", nfvipop=" + nfvipop + ", numInterfaces=" + numInterfaces
				+ ", maxNumVNFs=" + maxNumVNFs + ", maxNumVCPUs=" + maxNumVCPUs + ", maxNumVMems=" + maxNumVMems
				+ ", maxNumVStos=" + maxNumVStos + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpu == null) ? 0 : cpu.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + maxNumVCPUs;
		result = prime * result + maxNumVMems;
		result = prime * result + maxNumVNFs;
		result = prime * result + maxNumVStos;
		result = prime * result + ((memory == null) ? 0 : memory.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nfvipop == null) ? 0 : nfvipop.hashCode());
		result = prime * result + numInterfaces;
		result = prime * result + ((storage == null) ? 0 : storage.hashCode());
		result = prime * result + ((switchingMatrix == null) ? 0 : switchingMatrix.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (cpu == null) {
			if (other.cpu != null)
				return false;
		} else if (!cpu.equals(other.cpu))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (maxNumVCPUs != other.maxNumVCPUs)
			return false;
		if (maxNumVMems != other.maxNumVMems)
			return false;
		if (maxNumVNFs != other.maxNumVNFs)
			return false;
		if (maxNumVStos != other.maxNumVStos)
			return false;
		if (memory == null) {
			if (other.memory != null)
				return false;
		} else if (!memory.equals(other.memory))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nfvipop == null) {
			if (other.nfvipop != null)
				return false;
		} else if (!nfvipop.equals(other.nfvipop))
			return false;
		if (numInterfaces != other.numInterfaces)
			return false;
		if (storage == null) {
			if (other.storage != null)
				return false;
		} else if (!storage.equals(other.storage))
			return false;
		if (switchingMatrix == null) {
			if (other.switchingMatrix != null)
				return false;
		} else if (!switchingMatrix.equals(other.switchingMatrix))
			return false;
		return true;
	}	

}
