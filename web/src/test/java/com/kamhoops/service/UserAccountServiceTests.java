package com.kamhoops.service;


import com.kamhoops.data.domain.UserAccount;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.UserAccountRepository;
import com.kamhoops.exceptions.DuplicateEntityException;
import com.kamhoops.exceptions.EntityValidationException;
import com.kamhoops.services.UserAccountService;
import com.kamhoops.support.BaseTest;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class UserAccountServiceTests extends BaseTest {

    @Autowired
    private UserAccountService userAccountService;

    private UserAccountRepository userAccountRepository;

    @Before
    public void setup() {
        this.dataGenerator.deleteAll();
        this.userAccountRepository = userAccountService.getRepository();
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionIfTheUserAccountDoesNotExist() throws EntityNotFoundException {
        userAccountService.findById(0L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionIfTheUserIdIsNull() throws EntityNotFoundException {
        userAccountService.findById(null);
    }

    @Test
    public void shouldFindAdminUserByItsIDIfItsNotDeleted() throws EntityValidationException, EntityNotFoundException {
        UserAccount userAccount = userAccountService.create(dataGenerator.getRandomAdminUser());
        UserAccount foundUserAccount;

        foundUserAccount = userAccountService.findById(userAccount.getId());

        assertThat(foundUserAccount).isInstanceOf(UserAccount.class);
        assertThat(foundUserAccount.getUsername()).isEqualTo(userAccount.getUsername());
    }

    @Test
    public void shouldCreateAUserAccount() throws EntityValidationException {
        UserAccount userAccount = dataGenerator.getRandomAdminUser();

        userAccount = userAccountService.create(userAccount);
        assertThat(userAccountRepository.findAll().size()).isEqualTo(1);
        assertTrue(userAccount.getId() != null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionIfFindByUsernameIsPassedNull() throws EntityNotFoundException {
        userAccountService.findByUsername(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionIfNoUserExistsWithTheSuppliedUsername() throws EntityNotFoundException {
        userAccountService.findByUsername("test");
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionIfFindByEmailIsPassedNull() throws EntityNotFoundException {
        userAccountService.findByEmail(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundExceptionIfNoUserExistsWithTheSuppliedEmail() throws EntityNotFoundException {
        userAccountService.findByEmail("mytest@test.ca");
    }

    @Test
    public void shouldFindAUserAccountByItsUsername() throws EntityNotFoundException, EntityValidationException {
        UserAccount adminUserAccount = dataGenerator.getRandomAdminUser();
        adminUserAccount = userAccountService.create(adminUserAccount);

        UserAccount captainUserAccount = dataGenerator.getRandomCaptainUser();
        captainUserAccount = userAccountService.create(captainUserAccount);

        UserAccount userAccount = userAccountService.findByUsername(adminUserAccount.getUsername());
        assertThat(userAccount.getId()).isEqualTo(adminUserAccount.getId());

        userAccount = userAccountService.findByUsername(captainUserAccount.getUsername());
        assertThat(userAccount.getId()).isEqualTo(captainUserAccount.getId());
    }

    @Test
    public void shouldFindAUserAccountByItsEmail() throws EntityNotFoundException, EntityValidationException {
        UserAccount adminUserAccount = dataGenerator.getRandomAdminUser();
        adminUserAccount = userAccountService.create(adminUserAccount);

        UserAccount captainUserAccount = dataGenerator.getRandomCaptainUser();
        captainUserAccount = userAccountService.create(captainUserAccount);

        UserAccount userAccount = userAccountService.findByEmail(adminUserAccount.getEmail());
        assertThat(userAccount.getId()).isEqualTo(adminUserAccount.getId());

        userAccount = userAccountService.findByEmail(captainUserAccount.getEmail());
        assertThat(userAccount.getId()).isEqualTo(captainUserAccount.getId());
    }

    @Test
    public void shouldFindAllUsers() throws EntityValidationException {
        userAccountService.create(dataGenerator.getRandomAdminUser());
        userAccountService.create(dataGenerator.getRandomCaptainUser());
        userAccountService.create(dataGenerator.getRandomCaptainUser());

        List<UserAccount> userAccounts = userAccountService.findAll();

        assertThat(userAccounts).hasSize(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreatingWithAnId() throws EntityValidationException {
        UserAccount userAccount = dataGenerator.getRandomAdminUser();
        userAccount.setId(5L);

        userAccountService.create(userAccount);
    }

    @Test(expected = DuplicateEntityException.class)
    public void shouldNotCreateADuplicateAccountWhenTheUsernameAlreadyExists() throws EntityValidationException {
        UserAccount userAccount = dataGenerator.getRandomCaptainUser();
        userAccountService.create(userAccount);

        userAccount.setId(null);
        userAccount.setEmail(dataGenerator.getRandomEmail());
        userAccountService.create(userAccount);
    }

    @Test(expected = DuplicateEntityException.class)
    public void shouldNotCreateADuplicateAccountWhenTheEmailAlreadyExists() throws EntityValidationException {
        UserAccount userAccount = dataGenerator.getRandomCaptainUser();
        userAccountService.create(userAccount);

        userAccount.setId(null);
        userAccount.setUsername(RandomStringUtils.randomAlphabetic(5));
        userAccountService.create(userAccount);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldNotCreateAnAccountWithABlankUsername() throws EntityValidationException {
        UserAccount userAccount = dataGenerator.getRandomAdminUser();

        userAccount.setUsername("   ");
        userAccountService.create(userAccount);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldNotCreateAnAccountWithABlankEmail() throws EntityValidationException {
        UserAccount userAccount = dataGenerator.getRandomAdminUser();

        userAccount.setEmail("   ");
        userAccountService.create(userAccount);
    }

    @Test
    public void shouldUpdateAUserAccountLastLoginDate() throws EntityValidationException, EntityNotFoundException {
        UserAccount userAccount = dataGenerator.getRandomCaptainUser();
        userAccount = userAccountService.create(userAccount);
        assertThat(userAccount.getLastLoginDate()).isEqualTo(null);

        userAccount = userAccountService.updateLastLoginById(userAccount.getId());
        assertThat(userAccount.getLastLoginDate()).isNotEqualTo(null);
    }

    @Test(expected = EntityValidationException.class)
    public void nonAuthenticatedUsersCannotDeleteUserAccounts() throws EntityValidationException, EntityNotFoundException {
        UserAccount userAccount = dataGenerator.getRandomAdminUser();
        userAccount = userAccountService.create(userAccount);
        assertThat(userAccount.isDeleted()).isFalse();

        userAccountService.deleteById(userAccount.getId());
    }


    @Test
    public void shouldDeleteAUserAccountByItsId() throws EntityValidationException, EntityNotFoundException {
        setAuthenticatedUserAsAdminUser();
        UserAccount userAccount = dataGenerator.getRandomCaptainUser();
        userAccount = userAccountService.create(userAccount);
        assertThat(userAccount.isDeleted()).isFalse();

        userAccount = userAccountService.deleteById(userAccount.getId());
        assertThat(userAccount.isDeleted()).isTrue();
    }

    @Test
    public void shouldToggleTheActiveStatusOfAUserByItsId() throws EntityValidationException, EntityNotFoundException {
        UserAccount userAccount = dataGenerator.getRandomCaptainUser();
        userAccount = userAccountService.create(userAccount);
        assertThat(userAccount.isActive()).isTrue();

        userAccount = userAccountService.toggleActiveById(userAccount.getId());
        assertThat(userAccount.isActive()).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void attemptingToUpdateAUserThatIsNullShouldThrowException() throws EntityNotFoundException, EntityValidationException {
        UserAccount userAccount = null;

        userAccountService.update(userAccount);
    }

    @Test(expected = EntityNotFoundException.class)
    public void attemptingToUpdateAUserThatDoesNotExistShouldThrowException() throws EntityNotFoundException, EntityValidationException {
        UserAccount userAccount = dataGenerator.getRandomCaptainUser();
        userAccount = userAccountService.create(userAccount);
        userAccount.setId(0L);

        userAccountService.update(userAccount);
    }

    @Test
    public void shouldUpdateAUsersPasswordIfTheySupplyANonNullPassword() throws EntityValidationException, EntityNotFoundException {
        String password;
        UserAccount userAccount = dataGenerator.getRandomCaptainUser();
        userAccount = userAccountService.create(userAccount);
        password = userAccount.getPassword();

        userAccount.setPassword("9876");
        userAccount = userAccountService.update(userAccount);

        assertThat(userAccount.getPassword()).isNotEqualTo(password);
    }

    @Test
    public void shouldUpdateAUsersUsernameIfTheyChangeTheirUsername() throws EntityValidationException, EntityNotFoundException {
        String username;
        UserAccount userAccount = dataGenerator.getRandomCaptainUser();
        userAccount = userAccountService.create(userAccount);
        username = userAccount.getUsername();
        userAccount.setPassword(null);

        userAccount.setUsername("new");
        userAccount = userAccountService.update(userAccount);

        assertThat(userAccount.getUsername()).isNotEqualTo(username);
    }

    @Test
    public void shouldUpdateAUsersEmailIfTheyChangeTheirEmail() throws EntityValidationException, EntityNotFoundException {
        String email;
        UserAccount userAccount = dataGenerator.getRandomCaptainUser();
        userAccount = userAccountService.create(userAccount);
        email = userAccount.getUsername();
        userAccount.setPassword(null);

        userAccount.setUsername("new@kamhoops.com");
        userAccount = userAccountService.update(userAccount);

        assertThat(userAccount.getUsername()).isNotEqualTo(email);
    }

    @Test
    public void shouldUpdateAUserAccount() throws EntityNotFoundException, EntityValidationException {
        UserAccount userAccount = dataGenerator.getRandomCaptainUser();
        userAccount = userAccountService.create(userAccount);

        String username = userAccount.getUsername(), email = userAccount.getEmail();
        Date modifiedDate = userAccount.getLastModifiedDate();

        userAccount.setPassword(null);
        userAccount.setUsername("New Username");
        userAccount.setEmail("New@Email.com");
        userAccount = userAccountService.update(userAccount);

        assertThat(userAccount.getUsername()).isNotEqualTo(username);
        assertThat(userAccount.getEmail()).isNotEqualTo(email);
        assertThat(userAccount.getLastModifiedDate()).isNotEqualTo(modifiedDate);
    }

}
