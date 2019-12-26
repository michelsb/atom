package br.com.atom.nschecker.classes;

public class Link {

	private String id;
	private String name;
	private float capacity; // Mbps
	private float availableCapacity; // Mbps

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

	public float getCapacity() {
		return capacity;
	}

	public void setCapacity(float capacity) {
		this.capacity = capacity;
	}

	public float getAvailableCapacity() {
		return availableCapacity;
	}

	public void setAvailableCapacity(float availableCapacity) {
		this.availableCapacity = availableCapacity;
	}

	@Override
	public String toString() {
		return "Link [id=" + id + ", name=" + name + ", capacity=" + capacity + ", availableCapacity="
				+ availableCapacity + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(availableCapacity);
		result = prime * result + Float.floatToIntBits(capacity);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Link other = (Link) obj;
		if (Float.floatToIntBits(availableCapacity) != Float.floatToIntBits(other.availableCapacity))
			return false;
		if (Float.floatToIntBits(capacity) != Float.floatToIntBits(other.capacity))
			return false;
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
		return true;
	}

}
