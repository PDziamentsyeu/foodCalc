package main.application.utils.model;

import java.io.Serializable;

/**
 * Generic Entity for using id as primary index.
 * 
 */
@SuppressWarnings("serial")
public abstract class GenericEntity implements Serializable {

	public GenericEntity() {
		super();
	}

	public abstract Long getId();

	public boolean isNew() {
		return getId() == null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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
		GenericEntity other = (GenericEntity) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
