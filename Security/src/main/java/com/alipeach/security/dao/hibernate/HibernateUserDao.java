package com.alipeach.security.dao.hibernate;


import com.alipeach.core.dao.hibernate.HibernateGenericDao;
import com.alipeach.security.dao.UserDao;
import com.alipeach.security.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Chen Haoming
 */
@Repository ("userDao")
public class HibernateUserDao extends HibernateGenericDao<User, String> implements UserDao {

    @Autowired
    public HibernateUserDao (SessionFactory sessionFactory) {
        super (User.class, sessionFactory);
    }

    @SuppressWarnings ({"unchecked", "JpaQlInspection"})
    @Override
    public User findByNickName (String nickname) {
        List<User> list = getSessionFactory ().getCurrentSession ().createQuery ("from User u where u.nickName = ?").setString (0, nickname).list ();
        return  list.get (0);
    }
}
