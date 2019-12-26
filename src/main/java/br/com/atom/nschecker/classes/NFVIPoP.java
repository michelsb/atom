package br.com.atom.nschecker.classes;

public class NFVIPoP {

	private String id;
	private String name;
	private NFVI nfvi;

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

	public NFVI getNfvi() {
		return nfvi;
	}

	public void setNfvi(NFVI nfvi) {
		this.nfvi = nfvi;
	}

	@Override
	public String toString() {
		return "NFVIPoP [id=" + id + ", name=" + name + ", nfvi=" + nfvi + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nfvi == null) ? 0 : nfvi.hashCode());
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
		NFVIPoP other = (NFVIPoP) obj;
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
		if (nfvi == null) {
			if (other.nfvi != null)
				return false;
		} else if (!nfvi.equals(other.nfvi))
			return false;
		return true;
	}

}
