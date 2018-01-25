package main.application.utils.model;

@SuppressWarnings("serial")
public abstract class GenericEntityWithUuid extends GenericEntity {

    public GenericEntityWithUuid() {
        super();
    }

    public abstract String getUuid();

    @Override
    public boolean isNew() {
        return getId() == null && getUuid() == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
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
        GenericEntityWithUuid other = (GenericEntityWithUuid) obj;
        if (getId() == null) {
            if (other.getId() != null)
                return false;
        } else if (!getId().equals(other.getId()))
            return false;
        if (getUuid() == null) {
            if (other.getUuid() != null)
                return false;
        } else if (!getUuid().equals(other.getUuid()))
            return false;
        return true;
    }

}