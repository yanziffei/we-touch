package com.alipeach.core.service;

import com.alipeach.core.dao.GenericDao;
import com.alipeach.core.model.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Chen Haoming
 */
public class GenericModelServiceImpl<T extends BaseEntity, PK extends Serializable> implements GenericModelService<T, PK> {

    private GenericDao<T, PK> dao;

    public GenericModelServiceImpl (GenericDao<T, PK> dao) {
        if (null == dao) {
            throw new NullPointerException ("Dao should never be null!");
        }
        this.dao = dao;
    }

    /**
     * Save method adds 1 to t's version if it's updating t.
     * So client calling this method does not modify version itself.
     *
     * @param t
     *
     * @return
     */
    @Override
    public T save (T t) {
        checkVersion (t);
        return dao.save (t);
    }

    @SuppressWarnings ("unchecked")
    private void checkVersion (T t) {
        T foundT = dao.get ((PK) t.getUuid ());
        if (null != foundT) {
            t.setVersion (t.getVersion () + 1);
        }
    }

    @Override
    public T get (PK pk) {
        return dao.get (pk);
    }

    @Override
    public void delete (T t) {
        dao.delete (t);
    }

    @Override
    public List<T> save (List<T> list) {
        for (T t : list) {
            checkVersion (t);
        }
        return dao.save (list);
    }

    @Override
    public void delete (List<T> list) {
        dao.delete (list);
    }

    @Override
    public List<T> find (T t, int offset, int fetchSize) {
        return dao.find (t, offset, fetchSize);
    }

    @Override
    public List<T> findAll (T t) {
        return dao.findAll (t);
    }

    @Override
    public List<T> loadAll () {
        return dao.loadAll ();
    }

    @Override
    public long getCount () {
        return dao.getCount ();
    }

    @Override
    public long getCount (T t) {
        return dao.getCount (t);
    }
}
