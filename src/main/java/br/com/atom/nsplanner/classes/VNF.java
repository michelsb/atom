package br.com.atom.nsplanner.classes;

public class VNF {
 
	private String id;
	private String name;
	private String vnfMemberIndex;
	
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

	public String getVnfMemberIndex() {
		return vnfMemberIndex;
	}

	public void setVnfMemberIndex(String vnfMemberIndex) {
		this.vnfMemberIndex = vnfMemberIndex;
	}

	@Override
	public String toString() {
		return "VNF [id=" + id + ", name=" + name + ", vnfMemberIndex=" + vnfMemberIndex + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((vnfMemberIndex == null) ? 0 : vnfMemberIndex.hashCode());
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
		VNF other = (VNF) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (vnfMemberIndex == null) {
			if (other.vnfMemberIndex != null)
				return false;
		} else if (!vnfMemberIndex.equals(other.vnfMemberIndex))
			return false;
		return true;
	}

	
	
}
