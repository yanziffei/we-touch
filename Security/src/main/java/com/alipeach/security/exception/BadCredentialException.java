package com.alipeach.security.exception;

import com.alipeach.core.exception.AlipeachException;

/**
 * @author Chen Haoming
 */
public class BadCredentialException extends AlipeachException {

    public BadCredentialException (String message) {
        super (message, ErrorCode.BAD_CREDENTIAL);
    }
}
