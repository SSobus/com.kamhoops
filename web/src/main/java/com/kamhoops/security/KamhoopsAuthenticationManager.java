package com.kamhoops.security;

import com.kamhoops.exceptions.AuthenticationFailedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

import static com.kamhoops.exceptions.AuthenticationFailedException.FailCause;

/**
 * Authentication Manager Service
 * <p/>
 * Delegates to the default Provider Manager and wraps exceptions in to  AuthenticationFailedExceptions
 */
public class KamhoopsAuthenticationManager extends ProviderManager {

    public KamhoopsAuthenticationManager(List<AuthenticationProvider> providers) {
        super(providers);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticationException lastException = null;

        try {
            return super.authenticate(authentication);
        } catch (AuthenticationException ex) {
            lastException = ex;
        }

        throw wrapAuthenticationException(lastException);
    }

    private AuthenticationFailedException wrapAuthenticationException(AuthenticationException ex) {

        if (ex instanceof BadCredentialsException) {
            return new AuthenticationFailedException(FailCause.INVALID_USERNAME_OR_PASSWORD, "Invalid username or password", ex);
        } else if (ex instanceof LockedException) {
            return new AuthenticationFailedException(FailCause.ACCOUNT_LOCKED, "User account is currently locked, please reset your password", ex);
        } else if (ex instanceof DisabledException) {
            return new AuthenticationFailedException(FailCause.ACCOUNT_DISABLED, "User account is disabled, please contact support", ex);
        } else if (ex instanceof AccountExpiredException) {
            return new AuthenticationFailedException(FailCause.ACCOUNT_DELETED, "User account has been removed, please contact support", ex);
        }

        return new AuthenticationFailedException(FailCause.UNKNOWN, "Authentication failed", ex);
    }
}
