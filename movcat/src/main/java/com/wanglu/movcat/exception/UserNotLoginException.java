package com.wanglu.movcat.exception;

public class UserNotLoginException extends RuntimeException {
    private static final long serialVersionUID = -3173607918780955199L;

    public UserNotLoginException() {
    }

    public UserNotLoginException(String msg) {
        super(msg);
    }
}