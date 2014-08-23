package com.alipeach.core.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author Chen Haoming
 */
public interface GenericDao<T, PK extends Serializable> {

    T save (T t);

    T get (PK pk);

    void delete (T t);

    List<T> save (List<T> list);

    void delete (List<T> list);

    List<T> findAll (T t);

    List<T> loadAll();

    List<T> find (T t, int offset, int fetchSize);

    long getCount();

    long getCount(T t);
}
