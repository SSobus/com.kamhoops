package com.kamhoops.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Authentication Failed Exception
 * <p/>
 * This exception is thrown by the Authentication Manager Service and is used to indicated that an error has occurred
 */
public class AuthenticationFailedException extends AuthenticationException {

    public static enum FailCause {
        UNKNOWN, INVALID_USERNAME_OR_PASSWORD, INVALID_ROLE, ACCOUNT_LOCKED, ACCOUNT_DISABLED, ACCOUNT_DELETED, USER_ACCOUNT_CONVERSION_ERROR
    }

    private FailCause failCause;

    public AuthenticationFailedException(FailCause failCause, String message, AuthenticationException cause) {
        super(message, cause);
        this.failCause = failCause;
    }

    public FailCause getFailCause() {
        return failCause;
    }
}
