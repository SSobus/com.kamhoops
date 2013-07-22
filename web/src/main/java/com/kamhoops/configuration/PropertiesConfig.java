package com.kamhoops.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Properties Configuration
 * <p/>
 * Resolves the given profile into a set of properties configuration files
 */
@Configuration
public class PropertiesConfig {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesConfig.class);

    private static final String[] PROPERTY_FILES = {"application.properties"};

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(Environment environment) {
        PropertySourcesPlaceholderConfigurer config = new PropertySourcesPlaceholderConfigurer();

        String activeProfile = getActiveProfileNameFromEnvironment(environment);
        logger.info("Using '{}' Properties Configuration", activeProfile);

        List<ClassPathResource> classPathResources = new ArrayList<>();

        for (String PROPERTY_FILE : PROPERTY_FILES) {
            classPathResources.add(new ClassPathResource(String.format("/profile/%s/%s", activeProfile, PROPERTY_FILE)));
        }

        classPathResources.add(new ClassPathResource("/common/git.properties"));

        ClassPathResource[] resources = new ClassPathResource[classPathResources.size()];
        classPathResources.toArray(resources);

        config.setLocations(resources);

        // Note, this value separator is used in the .properties files and also to denote defaults: @Value('${myValue=default}')
        config.setValueSeparator("=");

        return config;
    }

    private static String getActiveProfileNameFromEnvironment(Environment environment) {
        String[] profiles = environment.getActiveProfiles();

        if (profiles == null || profiles.length == 0) {
            logger.warn("Warning: No active profile was set -- defaulting to 'development' ...");

            return "development";
        } else if (profiles.length > 1) {
            throw new RuntimeException("Kamhoops is not currently setup to use more than one active profile at a given time. Please refactor the PropertiesConfig class.");
        }

        return profiles[0].toLowerCase();
    }
}
