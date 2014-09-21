package com.alipeach.restapi.springwebmvc;

import com.alipeach.core.callback.Callback;
import com.alipeach.core.exception.AlipeachException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

/**
 * @author chenhaoming
 */
public class SpringWebMVCActionTemplate<T> implements SpringWebMVCAction<T> {

    private static final transient Logger LOG = LoggerFactory.getLogger (SpringWebMVCActionTemplate.class);

    private BindingResultHandler bindingResultHandler = new BindingResultHandler () {

    };

    private Callback<T> callback;

    @Override
    public void doAction (T target, BindingResult bindingResult) throws AlipeachException {
        LOG.debug ("Enter doAction;target:{},bindingResult:{}", target, bindingResult);
        try {
            if (null != bindingResultHandler) {
                LOG.debug ("call BindingResultHandler:{}", bindingResultHandler);
                bindingResultHandler.handle (bindingResult);
            }

            if (null != callback) {
                LOG.debug ("call Callback:{}", callback);
                callback.execute (target);
            }

        } catch (Throwable e) {
            LOG.warn ("Caught exception in doAction().", e);
            throw e instanceof AlipeachException ? (AlipeachException) e : new AlipeachException (e);
        }
    }

    public void setBindingResultHandler (BindingResultHandler bindingResultHandler) {
        this.bindingResultHandler = bindingResultHandler;
    }

    public void setCallback (Callback<T> callback) {
        this.callback = callback;
    }
}
