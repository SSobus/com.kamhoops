package com.kamhoops.support;

import com.kamhoops.configuration.TestContextConfiguration;
import com.kamhoops.data.domain.UserAccount;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.exception.PrivateMethodInvocationException;
import com.kamhoops.exception.TestingValidationError;
import com.kamhoops.exceptions.EntityValidationException;
import com.kamhoops.security.SecurityUserAccount;
import com.kamhoops.services.DataGenerationService;
import com.kamhoops.services.UserAccountService;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.validation.BindingResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Base Test
 * <p/>
 * Extend all tests from this class
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {TestContextConfiguration.class})
@ActiveProfiles(profiles = {"test"})
public abstract class BaseTest {

    @Autowired
    UserAccountService userAccountService;

    protected UserAccount authenticatedUser;

    @Autowired
    protected DataGenerationService dataGenerator;

    @Autowired
    private EntityValidator validator;

    @After
    public void clearAuthenticatedUser() {
        authenticatedUser = null;
        SecurityContextHolder.clearContext();
    }

    public void removeAuthentication() {
        SecurityContextHolder.clearContext();
    }

    public void validateEntity(Object entity) throws TestingValidationError {
        BindingResult results = validator.validate(entity);

        if (results.hasErrors()) {
            throw new TestingValidationError(results.getAllErrors());
        }
    }

    public void setAuthenticatedUserAsAdminUser() throws EntityValidationException, EntityNotFoundException {
        this.authenticatedUser = dataGenerator.getRandomAdminUser();
        userAccountService.create(authenticatedUser);

        SecurityUserAccount securityUserAccount = new SecurityUserAccount(authenticatedUser);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUserAccount, authenticatedUser.getPassword(), securityUserAccount.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void setAuthenticatedUserAsCaptainUser() throws EntityValidationException, EntityNotFoundException {
        this.authenticatedUser = dataGenerator.getRandomCaptainUser();
        userAccountService.create(authenticatedUser);

        SecurityUserAccount securityUserAccount = new SecurityUserAccount(authenticatedUser);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUserAccount, authenticatedUser.getPassword(), securityUserAccount.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Invoke a private method on the supplied class using the supplied parameters. This is used to test private methods contain in various classes.
     * <p/>
     * NOTE: If you find yourself using this method often, you should reconsider refactoring the private method in question
     *
     * @param classForInvocation is an instantiated class for invocation
     * @param methodName         to call on the supplied class
     * @param parameters         option parameters
     * @return whatever the method call would return as an object (null if void)
     * @throws PrivateMethodInvocationException
     *
     */
    public Object invokePrivateMethod(Object classForInvocation, String methodName, Object[] parameters) throws PrivateMethodInvocationException {
        Class[] parameterTypes = new Class[parameters.length];
        Object invocationResult;

        for (int i = 0; i < parameters.length; i++) {
            parameterTypes[i] = parameters[i].getClass();
        }

        try {
            Method methodToInvoke = classForInvocation.getClass().getDeclaredMethod(methodName, parameterTypes);
            methodToInvoke.setAccessible(true);
            invocationResult = methodToInvoke.invoke(classForInvocation, parameters);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            throw new PrivateMethodInvocationException(exception.getMessage(), exception.getCause());
        }

        return invocationResult;
    }
}
