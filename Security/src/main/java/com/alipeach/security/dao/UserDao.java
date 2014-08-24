package com.alipeach.security.dao;

import com.alipeach.core.dao.GenericDao;
import com.alipeach.security.model.User;

/**
 * @author Chen Haoming
 */
public interface UserDao extends GenericDao<User, String> {

    User findByNickName (String nickname);

}
