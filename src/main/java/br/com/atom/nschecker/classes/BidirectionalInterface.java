package br.com.atom.nschecker.classes;

public class BidirectionalInterface {

	private String id;
	private String name;
	private Interface inboundInterface;
	private Interface outboundInterface;

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

	public Interface getInboundInterface() {
		return inboundInterface;
	}

	public void setInboundInterface(Interface inboundInterface) {
		this.inboundInterface = inboundInterface;
	}

	public Interface getOutboundInterface() {
		return outboundInterface;
	}

	public void setOutboundInterface(Interface outboundInterface) {
		this.outboundInterface = outboundInterface;
	}

	@Override
	public String toString() {
		return "BidirectionalInterface [id=" + id + ", name=" + name + ", inboundInterface=" + inboundInterface
				+ ", outboundInterface=" + outboundInterface + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inboundInterface == null) ? 0 : inboundInterface.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((outboundInterface == null) ? 0 : outboundInterface.hashCode());
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
		BidirectionalInterface other = (BidirectionalInterface) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inboundInterface == null) {
			if (other.inboundInterface != null)
				return false;
		} else if (!inboundInterface.equals(other.inboundInterface))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (outboundInterface == null) {
			if (other.outboundInterface != null)
				return false;
		} else if (!outboundInterface.equals(other.outboundInterface))
			return false;
		return true;
	}

}
