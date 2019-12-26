package br.com.atom.nschecker.classes;

public class BidirectionalLink {

	private String id;
	private String name;
	private Link linkForward;
	private Link linkBackward;

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

	public Link getLinkForward() {
		return linkForward;
	}

	public void setLinkForward(Link linkForward) {
		this.linkForward = linkForward;
	}

	public Link getLinkBackward() {
		return linkBackward;
	}

	public void setLinkBackward(Link linkBackward) {
		this.linkBackward = linkBackward;
	}

	@Override
	public String toString() {
		return "BidirectionalLink [id=" + id + ", name=" + name + ", linkForward=" + linkForward + ", linkBackward="
				+ linkBackward + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((linkBackward == null) ? 0 : linkBackward.hashCode());
		result = prime * result + ((linkForward == null) ? 0 : linkForward.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		BidirectionalLink other = (BidirectionalLink) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (linkBackward == null) {
			if (other.linkBackward != null)
				return false;
		} else if (!linkBackward.equals(other.linkBackward))
			return false;
		if (linkForward == null) {
			if (other.linkForward != null)
				return false;
		} else if (!linkForward.equals(other.linkForward))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
