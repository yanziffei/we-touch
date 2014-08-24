package com.alipeach.security.model;

import com.alipeach.core.model.BaseEntity;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.Date;

/**
 * @author wjy
 */
@Entity (name = "t_user")
public class User extends BaseEntity {

    @Column (name = "nick_name", unique = true, length = 60, nullable = false)
    private String nickName;

    @Column (name = "password", length = 32, nullable = false)
    private String password;

    private Status status = Status.NORMAL;

    public String getNickName () {
        return nickName;
    }

    public void setNickName (String nickName) {
        this.nickName = nickName;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public Integer getStatus () {
        return null == status ? null : status.getCode ();
    }

    @Column (name = "status", nullable = false)
    public void setStatus (Integer code) {
        this.status = (null == code) ? null : Status.getStatus (code);
    }

    @Override
    protected boolean checkInstance (@NotNull Object obj) {
        return obj instanceof User;
    }

    @Override
    protected boolean doEquals (@NotNull BaseEntity entity) {
        User user = (User) entity;
        return StringUtils.equals (this.getNickName (), user.getNickName ()) && StringUtils.equals (this.getPassword (), user.getPassword ()) && this.status == user.status;
    }

}
