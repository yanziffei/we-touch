package com.alipeach.restapi.model;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;

/**
 * @author chenhaoming
 */
@Entity (name = "t_weibo")
public class Weibo extends AdMedia {



    @Override
    protected boolean doAdMediaEquals (AdMedia adMedia) {
        throw new UnsupportedOperationException ();
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected boolean checkInstance (@NotNull Object obj) {
        return obj instanceof Weibo;
    }
}
