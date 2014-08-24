package com.alipeach.core.model;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author Chen Haoming
 */
@Entity
@Inheritance (strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity implements UUIDBasedEntity, OptimisticLockBasedEntity, DataEntity, Serializable {

    @Id
    @Column (name = "uuid", length = 36)
    private String uuid = UUID.randomUUID ().toString ();

    @Column (name = "version", nullable = false)
    private Integer version = 0;

    @Column (name = "creation_time", nullable = false)
    private Date creationTime = new Date ();

    @Column (name = "last_update_time", nullable = false)
    private Date lastUpdateTime = new Date ();

    @Override
    public int hashCode () {
        String uuid = getUuid ();
        return (null == uuid) ? 0 : uuid.hashCode ();
    }

    @SuppressWarnings ("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals (Object obj) {
        if (obj == null || ! checkInstance (obj)) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        BaseEntity entity = (BaseEntity) obj;
        return StringUtils.equals (entity.getUuid (), this.getUuid ())
            && ObjectUtils.equals (getVersion (), entity.getVersion ()) && doEquals (entity);
    }

    protected abstract boolean checkInstance (@NotNull Object obj);

    protected abstract boolean doEquals (@NotNull BaseEntity entity);

    @Override
    public String getUuid () {
        return uuid;
    }

    @Override
    public void setUuid (String uuid) {
        this.uuid = uuid;
    }

    @Override
    public Integer getVersion () {
        return version;
    }

    @Override
    public void setVersion (Integer version) {
        this.version = version;
    }

    @Override
    public Date getCreationTime () {
        return creationTime;
    }

    @Override
    public void setCreationTime (Date creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public Date getLastUpdateTime () {
        return lastUpdateTime;
    }

    @Override
    public void setLastUpdateTime (Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
