package com.kamhoops.security;

import com.kamhoops.data.domain.Users;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.services.UsersService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * User Details Service
 * <p/>
 * Load the user account information in to a custom SecurityUserAccount for Spring security management
 */
public class KamhoopsUserDetailsService implements UserDetailsService {

    private UsersService usersService;

    public KamhoopsUserDetailsService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users userAccount = null;

        try {
            userAccount = usersService.findByEmail(email);
        } catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException("Email '" + email + "' not found");
        }

        return new SecurityUserAccount(userAccount);
    }
}
