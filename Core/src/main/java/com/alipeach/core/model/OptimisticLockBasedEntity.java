package com.alipeach.core.model;

/**
 * @author Chen Haoming
 */
public interface OptimisticLockBasedEntity {

    void setVersion(Integer version);

    Integer getVersion ();
}
