package com.alipeach.security.service.impl;

import com.alipeach.core.service.GenericModelServiceImpl;
import com.alipeach.security.encrypt.IrreversibleEncryptors;
import com.alipeach.security.dao.UserDao;
import com.alipeach.security.model.User;
import com.alipeach.security.service.UserModelService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Chen Haoming
 */
@Service ("userService")
public class DefaultUserModelService extends GenericModelServiceImpl<User, String> implements UserModelService {

    private UserDao dao;
    private IrreversibleEncryptors encryptors;

    @Autowired
    public DefaultUserModelService (UserDao dao) {
        super (dao);
        this.dao = dao;
    }

    @Override
    public User findByNickName (String nickName) {
        return dao.findByNickName (nickName);
    }

    @Override
    public User save (User user) {
        encryptPassword (user);
        return super.save (user);
    }

    private void encryptPassword (User user) {
        if (null != encryptors) {
            String password = user.getPassword ();
            user.setPassword (null != password ? encryptors.encrypt (password) : null);
        }
    }

    @Override
    public List<User> save (@NotNull List<User> list) {
        list.forEach (this::encryptPassword);
        return super.save (list);
    }

    @Autowired
    public void setEncryptors (IrreversibleEncryptors encryptors) {
        this.encryptors = encryptors;
    }
}
