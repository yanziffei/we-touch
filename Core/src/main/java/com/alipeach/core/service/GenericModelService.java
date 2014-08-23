package com.alipeach.core.service;

import com.alipeach.core.model.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Chen Haoming
 */
public interface GenericModelService<T extends BaseEntity, PK extends Serializable> {

    T save (T t);

    T get (PK pk);

    void delete (T t);

    List<T> save (List<T> list);

    void delete (List<T> list);

    List<T> find(T t, int offset, int fetchSize);

    List<T> findAll (T t);

    List<T> loadAll();

    long getCount();

    long getCount(T t);
}
