package com.kamhoops.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.stereotype.Component;

/**
 * Authentication Failed Event Listener
 * <p/>
 * Handles failed logins
 */
@Component
public class AuthenticationFailedEventListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFailedEventListener.class);

    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        logger.info("Auth Failed Event!");
    }
}
