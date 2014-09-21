package com.alipeach.restapi.springwebmvc;

import com.alipeach.restapi.exception.ValidationException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * @author chenhaoming
 */
public interface BindingResultHandler {

    default void handle (BindingResult bindingResult) {
        if (! bindingResult.hasErrors ()) {
            return;
        }

        List<ObjectError> allErrors = bindingResult.getAllErrors ();
        if (CollectionUtils.isEmpty (allErrors)) {
            return;
        }

        StringBuilder sb = new StringBuilder ();
        for (ObjectError error : allErrors) {
            sb.append (error);
            sb.append ("\n");
        }
        throw new ValidationException (sb.toString ());
    }
}
