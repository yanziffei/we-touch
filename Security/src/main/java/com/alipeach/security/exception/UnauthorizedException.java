package com.alipeach.security.exception;

import com.alipeach.core.exception.AlipeachException;

/**
 * @author yanziffei
 */
public class UnauthorizedException extends AlipeachException {

    public UnauthorizedException (String message) {
        super (message, ErrorCode.UNAUTHORIZED);
    }

}
