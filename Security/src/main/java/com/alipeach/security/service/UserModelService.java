package com.alipeach.security.service;

import com.alipeach.core.service.GenericModelService;
import com.alipeach.security.model.User;

/**
 * @author Chen Haoming
 */
public interface UserModelService extends GenericModelService<User, String> {

    User findByNickName(String nickName);

}
