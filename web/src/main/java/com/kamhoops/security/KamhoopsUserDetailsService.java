package com.kamhoops.security;

import com.kamhoops.data.domain.UserAccount;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.services.UserAccountService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * User Details Service
 * <p/>
 * Load the user account information in to a custom SecurityUserAccount for Spring security management
 */
public class KamhoopsUserDetailsService implements UserDetailsService {

    private UserAccountService userAccountService;

    public KamhoopsUserDetailsService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount userAccount = null;

        try {
            userAccount = userAccountService.findByEmail(email);
        } catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException("Email '" + email + "' not found");
        }

        return new SecurityUserAccount(userAccount);
    }
}
