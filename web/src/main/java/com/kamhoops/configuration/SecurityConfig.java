package com.kamhoops.configuration;

import com.kamhoops.security.KamhoopsAuthenticationManager;
import com.kamhoops.security.KamhoopsUserDetailsService;
import com.kamhoops.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ImportResource({"classpath*:spring-security-context.xml"})
public class SecurityConfig {

    @Autowired
    private UserAccountService userAccountService;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provide the authentication manager configured to use our custom provider implementation
     */
    @Bean(name = "authenticationManager")
    public AuthenticationManager getAuthenticationManager(ApplicationEventPublisher eventPublisher, List<AuthenticationProvider> authenticationProviders) {
        DefaultAuthenticationEventPublisher authenticationEventPublisher = new DefaultAuthenticationEventPublisher();
        authenticationEventPublisher.setApplicationEventPublisher(eventPublisher);

        ProviderManager authenticationManager = new KamhoopsAuthenticationManager(authenticationProviders);
        authenticationManager.setAuthenticationEventPublisher(authenticationEventPublisher);

        return authenticationManager;
    }

    @Bean
    public List<AuthenticationProvider> getAuthenticationProviders(DaoAuthenticationProvider daoAuthenticationProvider) {
        List<AuthenticationProvider> authenticationProviders = new ArrayList<>();
        authenticationProviders.add(daoAuthenticationProvider);

        return authenticationProviders;
    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider(KamhoopsUserDetailsService kamhoopsUserDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(kamhoopsUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public KamhoopsUserDetailsService getKamhoopsUserDetailsService() {
        return new KamhoopsUserDetailsService(userAccountService);
    }

    @Bean(name = "failureHandler")
    public AuthenticationFailureHandler getFailureHandler() {
        SimpleUrlAuthenticationFailureHandler result = new SimpleUrlAuthenticationFailureHandler();
        result.setDefaultFailureUrl("/login?loginError=true");
        return result;
    }

    @Bean(name = "successHandler")
    public AuthenticationSuccessHandler getSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler result = new SimpleUrlAuthenticationSuccessHandler();
        result.setDefaultTargetUrl("/redirect");
        return result;
    }

    @Bean(name = "authenticationEntryPoint")
    public LoginUrlAuthenticationEntryPoint getAuthenticationEntryPoint() {
        LoginUrlAuthenticationEntryPoint entryPoint = new LoginUrlAuthenticationEntryPoint("/");
        entryPoint.setForceHttps(false);

        return entryPoint;
    }
}
