package com.alipeach.restapi.springwebmvc;

import com.alipeach.core.exception.AlipeachException;
import org.springframework.validation.BindingResult;

/**
 * @author chenhaoming
 */
public interface SpringWebMVCAction<T> {

    void doAction (T t, BindingResult b) throws AlipeachException;
}
