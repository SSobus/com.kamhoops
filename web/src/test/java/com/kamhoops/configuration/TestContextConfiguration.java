package com.kamhoops.configuration;

import com.kamhoops.support.EntityValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Test Context Configuration for JUnit
 * <p/>
 * This class imports and scans the required classes for unit testing along with providing
 * bean configuration values for some dependency injection
 */
@Profile("test")
@ContextConfiguration
@ComponentScan(basePackages = {
        "com.kamhoops.services",
        "com.kamhoops.security"
})
@Import({
        ApplicationConfig.class,
        PersistenceJpaConfig.class,
        PropertiesConfig.class,
        EntityValidator.class
})
public class TestContextConfiguration {
    @Bean
    public Validator getTestValidator() {
        return new LocalValidatorFactoryBean();
    }
}
