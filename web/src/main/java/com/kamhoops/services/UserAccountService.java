package com.kamhoops.services;


import com.kamhoops.data.domain.UserAccount;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.UserAccountRepository;
import com.kamhoops.exceptions.DuplicateEntityException;
import com.kamhoops.exceptions.EntityValidationException;
import com.kamhoops.security.SecurityUserAccount;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Service
public class UserAccountService extends AbstractService<UserAccountRepository, UserAccount> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserAccount createUserAccount(UserAccount userAccount) throws EntityValidationException {
        preCreateUserAccountChecks(userAccount);
        encryptUserAccountPassword(userAccount);

        return repository.save(userAccount);
    }

    private void preCreateUserAccountChecks(UserAccount userAccount) throws EntityValidationException {
        if (userAccount.getId() != null) {
            throw new EntityValidationException(new FieldError("userAccount", "id", "Supplied account ID must be null"));
        }

        validateEntity(userAccount);
        checkForDuplicateUserByUsername(userAccount.getUsername());
        checkForDuplicateUserByEmail(userAccount.getEmail());
    }

    private void checkForDuplicateUserByUsername(String username) throws EntityValidationException {
        if (StringUtils.isBlank(username)) {
            throw new EntityValidationException(new FieldError("userAccount", "username", "Invalid username of null or empty"));
        }

        UserAccount userAccount = repository.findByUsername(username.toLowerCase());

        if (userAccount != null) {
            throw new DuplicateEntityException(userAccount, "userAccount", "username");
        }
    }

    private void checkForDuplicateUserByEmail(String email) throws EntityValidationException {
        if (StringUtils.isBlank(email)) {
            throw new EntityValidationException(new FieldError("userAccount", "email", "Invalid email of null or empty"));
        }

        UserAccount userAccount = repository.findByEmail(email.toLowerCase());

        if (userAccount != null) {
            throw new DuplicateEntityException(userAccount, "userAccount", "email");
        }
    }

    private void encryptUserAccountPassword(UserAccount userAccount) {
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
    }

    public UserAccount updateUserAccount(UserAccount modifiedUserAccount) throws EntityNotFoundException, EntityValidationException {
        UserAccount existingUserAccount = findById(modifiedUserAccount.getId());

        if (existingUserAccount == null) {
            throw new EntityValidationException(new FieldError("userAccount", "id", "Supplied account id is not a UserAccount"));
        }

        preUpdateUserAccountChecksAndMerge(existingUserAccount, modifiedUserAccount);

        return repository.save(existingUserAccount);
    }

    private void preUpdateUserAccountChecksAndMerge(UserAccount existingUserAccount, UserAccount modifiedUserAccount) throws EntityValidationException {
        assert (modifiedUserAccount.getId() != null);

        // User has changed their password? Need to re-encrypt
        if (!StringUtils.isBlank(modifiedUserAccount.getPassword())) {
            encryptUserAccountPassword(modifiedUserAccount);
            existingUserAccount.setPassword(modifiedUserAccount.getPassword());
        } else {
            // Purely for validation simplicity
            modifiedUserAccount.setPassword(existingUserAccount.getPassword());
        }

        // User has requested a username change? Need to ensure the new username isn't taken.
        String newUsername = modifiedUserAccount.getUsername();
        if (!StringUtils.isBlank(newUsername) && !existingUserAccount.getUsername().equals(newUsername)) {
            checkForDuplicateUserByUsername(newUsername);
            existingUserAccount.setUsername(modifiedUserAccount.getUsername());
        } else {
            modifiedUserAccount.setUsername(existingUserAccount.getUsername());
        }

        // User has requested a username change? Need to ensure the new username isn't taken.
        String newEmail = modifiedUserAccount.getEmail();
        if (!StringUtils.isBlank(newEmail) && !existingUserAccount.getEmail().equals(newEmail)) {
            checkForDuplicateUserByEmail(newEmail);
            existingUserAccount.setEmail(modifiedUserAccount.getEmail());
        } else {
            modifiedUserAccount.setEmail(existingUserAccount.getEmail());
        }

        validateEntity(modifiedUserAccount);

        existingUserAccount.setEmail(modifiedUserAccount.getEmail());
        existingUserAccount.setUsername(modifiedUserAccount.getUsername());
    }

    public UserAccount findByUsername(String username) throws EntityNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new EntityNotFoundException(UserAccount.class, username);
        }

        UserAccount user = repository.findByUsername(username.toLowerCase());

        if (user == null) {
            throw new EntityNotFoundException(UserAccount.class, username);
        }

        return user;
    }

    public UserAccount findByEmail(String email) throws EntityNotFoundException {
        if (StringUtils.isBlank(email)) {
            throw new EntityNotFoundException(UserAccount.class, email);
        }

        UserAccount user = repository.findByEmail(email.toLowerCase());

        if (user == null) {
            throw new EntityNotFoundException(UserAccount.class, email);
        }

        return user;
    }

    /**
     * Return the associated UserAccount of the currently logged in user
     *
     * @return user account of the logged in user or null
     */
    public static UserAccount getAuthenticatedUser() {
        SecurityUserAccount securityUserAccount = getSecurityUserAccount();

        return securityUserAccount != null ? securityUserAccount.getUserAccount() : null;
    }

    /**
     * Return the UserAccount object from the principal (SecurityUserAccount) of the authenticated user. If the user is not authenticated
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

        return (obj != null ? (Authentication) obj : null);
    }

    public UserAccount updateLastLoginById(Long id) throws EntityNotFoundException {
        UserAccount userAccount = findById(id);
        userAccount.updateLastLoginDate();

        return repository.save(userAccount);
    }

    @Override
    public UserAccount deleteById(Long id) throws EntityNotFoundException, EntityValidationException {
        UserAccount deletedBy = getAuthenticatedUser();

        if (deletedBy == null || !deletedBy.canDeleteEntities()) {
            throw new EntityValidationException(new ObjectError("authenticatedUser", "The user is not authenticated or does not have permission to delete this entity"));
        }

        UserAccount userAccount = findById(id);
        userAccount.setDeleted(true);

        return repository.save(userAccount);
    }

    @Override
    public UserAccountRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(UserAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<UserAccount> getEntityTypeClass() {
        return UserAccount.class;
    }
}
