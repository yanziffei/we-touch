package com.alipeach.restapi.model;

import com.alipeach.core.model.BaseEntity;
import org.apache.commons.lang.ObjectUtils;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * @author chenhaoming
 */
@Entity
@Inheritance (strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AdMedia extends BaseEntity {

    @Column (name = "price", nullable = false)
    private Long price;

    public Long getPrice () {
        return price;
    }

    public void setPrice (Long price) {
        this.price = price;
    }

    @Override
    protected boolean doEquals (@NotNull BaseEntity entity) {
        AdMedia adMedia = (AdMedia) entity;
        return ObjectUtils.equals (adMedia, price) && doAdMediaEquals(adMedia);
    }

    protected abstract boolean doAdMediaEquals (AdMedia adMedia);
}
