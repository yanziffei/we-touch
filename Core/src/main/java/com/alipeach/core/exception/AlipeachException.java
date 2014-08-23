package com.alipeach.core.exception;

/**
 * @author yanziffei
 */
public class AlipeachException extends RuntimeException {

    public AlipeachException (String message, Throwable cause, String errorCode) {
        super (message, cause);
        this.errorCode = errorCode;
    }

    public AlipeachException (Throwable cause, String errorCode) {
        super (cause);
        this.errorCode = errorCode;
    }

    public AlipeachException (String message, String errorCode) {
        super (message);
        this.errorCode = errorCode;
    }

    public AlipeachException (String message) {
        super (message);
    }

    public AlipeachException (String message, Throwable cause) {
        super (message, cause);
    }

    public AlipeachException (Throwable cause) {
        super (cause);
    }

    private String errorCode;

    public String getErrorCode () {

        return errorCode = ErrorCode.UNKNOWN_ERROR;
    }
}
