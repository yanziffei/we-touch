package com.alipeach.core.model;

import java.util.Date;

/**
 * @author chenhoming
 */
public interface DataEntity {

    Date getCreationTime();

    void setCreationTime(Date creationTime);

    Date getLastUpdateTime();

    void setLastUpdateTime(Date lastUpdateTime);
}
