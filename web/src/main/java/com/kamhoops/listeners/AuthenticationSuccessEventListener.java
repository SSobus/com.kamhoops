package com.kamhoops.listeners;

import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.security.SecurityUserAccount;
import com.kamhoops.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Authentication Success Event Listener
 * <p/>
 * Handles post authentication success actions
 */
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private UsersService userAccountService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessEventListener.class);

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {

        Authentication authentication = event.getAuthentication();

        if (authentication == null) {
            logger.error("Authentication Success event occurred but the authentication object is null. This is an API error.");
            return;
        }

        if (authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof SecurityUserAccount)) {
            logger.error("Authentication Success event occurred but there was no principle object or it was not of type SecurityUserAccount. This is an API error.");
            return;
        }

        SecurityUserAccount securityUserAccount = (SecurityUserAccount) authentication.getPrincipal();
        WebAuthenticationDetails authenticationDetails = (WebAuthenticationDetails) authentication.getDetails();

        logger.info(String.format("Successfully authenticated %s, Session: '%s', Remote Address: '%s', at %s", securityUserAccount.getUserAccount(), authenticationDetails.getSessionId(), authenticationDetails.getRemoteAddress(), new Date()));

        try {
            userAccountService.updateLastLoginById(securityUserAccount.getId());
        } catch (EntityNotFoundException e) {
            logger.error("Could not update the last login date for user id: " + securityUserAccount.getId() + ". Extended exception: " + e.getMessage());
        }
    }
}
