package com.kamhoops.services;


import com.kamhoops.data.domain.Users;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.UsersRepository;
import com.kamhoops.security.SecurityUserAccount;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsersService extends AbstractService<UsersRepository, Users> {

    @Autowired
    UsersRepository usersRepository;

    public Users findByEmail(String email) throws EntityNotFoundException {
        if (StringUtils.isBlank(email)) {
            throw new EntityNotFoundException(Users.class, email);
        }

        Users user = repository.findByEmail(email.toLowerCase());

        if (user == null) {
            throw new EntityNotFoundException(Users.class, email);
        }

        return user;
    }

    /**
     * Return the associated Users of the currently logged in user
     *
     * @return user account of the logged in user or null
     */
    public static Users getAuthenticatedUser() {
        SecurityUserAccount securityUserAccount = getSecurityUserAccount();

        return securityUserAccount != null ? securityUserAccount.getUserAccount() : null;
    }

    /**
     * Return the Users object from the principal (SecurityUserAccount) of the authenticated user. If the user is not authenticated
     * this will return null
     */
    private static SecurityUserAccount getSecurityUserAccount() {
        Authentication authentication = getAuthenticationFromSecurityContext();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            return (principal instanceof SecurityUserAccount ? (SecurityUserAccount) principal : null);
        }

        return null;
    }

    /**
     * Return the authentication object of the current session. This user name may be authenticated anonymously and
     * they will not have an assigned AbstractUserAccount object
     */
    private static Authentication getAuthenticationFromSecurityContext() {
        Object obj = SecurityContextHolder.getContext().getAuthentication();

        return (obj instanceof Authentication ? (Authentication) obj : null);
    }

    public Users updateLastLoginById(Long id) throws EntityNotFoundException {
        Users userAccount = findById(id);
        userAccount.updateLastLoginDate();

        return repository.save(userAccount);
    }

    @Override
    public UsersRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<Users> getEntityTypeClass() {
        return Users.class;
    }
}
