package com.alipeach.restapi.exception;

import com.alipeach.core.exception.AlipeachException;

/**
 * @author chenhaoming
 */
public class ValidationException extends AlipeachException {

    public static final String PARAMETER_INVALID = "parameter_invalid";

    public ValidationException (String message, Throwable cause) {
        super (message, cause, PARAMETER_INVALID);
    }

    public ValidationException (Throwable cause) {
        super (cause, PARAMETER_INVALID);
    }

    public ValidationException (String message) {
        super (message, PARAMETER_INVALID);
    }

}
