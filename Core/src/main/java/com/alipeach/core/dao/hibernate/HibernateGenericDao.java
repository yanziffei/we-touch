package com.alipeach.core.dao.hibernate;

import com.alipeach.core.dao.GenericDao;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * @author Chen Haoming
 */
public class HibernateGenericDao<T, PK extends Serializable> implements GenericDao<T, PK> {

    private Class<T> persistentClass;

    private SessionFactory sessionFactory;

    public HibernateGenericDao (@NotNull Class<T> persistentClass, @NotNull SessionFactory sessionFactory) {
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T save (T t) {
        sessionFactory.getCurrentSession ().save (t);
        return t;
    }

    @SuppressWarnings ("unchecked")
    @Override
    public T get (PK pk) {
        return (T) sessionFactory.getCurrentSession ().get (persistentClass, pk);
    }

    @Override
    public void delete (T t) {
        sessionFactory.getCurrentSession ().delete (t);
    }

    @Override
    public List<T> save (List<T> list) {
        Session session = sessionFactory.getCurrentSession ();
        for (T t : list) {
            session.save (t);
        }
        return list;
    }

    @Override
    public void delete (List<T> list) {
        Session session = sessionFactory.getCurrentSession ();
        for (T t : list) {
            session.delete (t);
        }
    }

    @SuppressWarnings ("unchecked")
    @Override
    public List<T> findAll (T t) {
        return sessionFactory.getCurrentSession ().createCriteria (persistentClass).add (Example.create (t)).list ();
    }

    @SuppressWarnings ("unchecked")
    @Override
    public List<T> loadAll () {
        return sessionFactory.getCurrentSession ().createCriteria (persistentClass).list ();
    }

    @SuppressWarnings ("unchecked")
    @Override
    public List<T> find (T t, int offset, int fetchSize) {
        Criteria criteria = sessionFactory.getCurrentSession ().createCriteria (persistentClass).add (Example.create (t));
        return criteria.setFirstResult (offset).setFetchSize (fetchSize).list ();
    }

    @Override
    public long getCount () {
        Query query = sessionFactory.getCurrentSession ().createQuery ("select count(*) from " + persistentClass);
        query.executeUpdate ();
        return ((Number) query.uniqueResult ()).longValue ();
    }

    @Override
    public long getCount (T t) {
        Criteria criteria = sessionFactory.getCurrentSession ().createCriteria (persistentClass).add (Example.create (t));
        return ((Number)criteria.setProjection (Projections.rowCount ()).uniqueResult ()).longValue();
    }

    protected Class<T> getPersistentClass () {
        return persistentClass;
    }

    protected SessionFactory getSessionFactory () {
        return sessionFactory;
    }
}
