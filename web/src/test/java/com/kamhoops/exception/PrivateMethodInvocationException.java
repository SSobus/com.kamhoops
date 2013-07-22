package com.kamhoops.exception;

public class PrivateMethodInvocationException extends Exception {
    public PrivateMethodInvocationException(String message) {
        super(message);
    }

    public PrivateMethodInvocationException(Throwable cause) {
        super(cause);
    }

    public PrivateMethodInvocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
