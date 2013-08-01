package com.kamhoops.security;

import com.kamhoops.data.domain.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Security User Account
 * <p/>
 * Simple wrapper around AbstractUserAccount for Spring Security
 */
public class SecurityUserAccount implements UserDetails {

    private UserAccount userAccount;
    private List<GrantedAuthority> grantedAuthorities;

    public SecurityUserAccount(UserAccount userAccount) {
        assert (userAccount != null);

        this.userAccount = userAccount;
        this.grantedAuthorities = new ArrayList<>();

        addGrantedAuthority("ROLE_AUTHENTICATED");
        //addGrantedAuthority("ROLE_" + userAccount.getUserRole());
    }

    @Override
    public String getPassword() {
        return userAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return userAccount.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !userAccount.isDeleted();
    }

    /**
     * Accounts become locked when a password reset has been requested
     */
    @Override
    public boolean isAccountNonLocked() {
        return !userAccount.isPasswordChangeRequired();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !userAccount.isDeleted();
    }

    @Override
    public boolean isEnabled() {
        return userAccount.isActive() && !userAccount.isDeleted() && !userAccount.isPasswordChangeRequired();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    public void addGrantedAuthority(String role) {
        this.grantedAuthorities.add(new SimpleGrantedAuthority(role));
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public Long getId() {
        return userAccount.getId();
    }
}
