package br.com.atom.nschecker.classes;

public class InterPoPSwitch extends Link {

	private int vxlanId;

	public int getVxlanId() {
		return vxlanId;
	}

	public void setVxlanId(int vxlanId) {
		this.vxlanId = vxlanId;
	}

	@Override
	public String toString() {
		return "InterPoPSwitch [vxlanId=" + vxlanId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + vxlanId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		InterPoPSwitch other = (InterPoPSwitch) obj;
		if (vxlanId != other.vxlanId)
			return false;
		return true;
	}

}
