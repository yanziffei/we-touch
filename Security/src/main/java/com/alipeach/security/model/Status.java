package com.alipeach.security.model;

/**
 * @author wjy
 */
public enum Status {

    NORMAL (0),
    LOCKED (1),
    DELETE (2);

    private final int code;

    private Status (int code) {
        this.code = code;
    }

    public int getCode () {
        return code;
    }

    public static Status getStatus (int code) {
        for (Status status : Status.values ()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalStateException (code + " is not a valid Status code.");
    }
}
