package com.kamhoops.configuration;

import com.kamhoops.interceptors.BuildInformationInterceptorAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;

import java.util.Locale;


@Configuration
@EnableAsync
@EnableScheduling
@EnableWebMvc
@ComponentScan(basePackages = {
        "com.kamhoops.data",
        "com.kamhoops.data.repository",
        "com.kamhoops.configuration",
        "com.kamhoops.services",
        "com.kamhoops.security",
        "com.kamhoops.support",
        "com.kamhoops.listeners",
        "com.kamhoops.controllers"
})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");

        // Allow clients to download the partials
        registry.addResourceHandler("/partials/**/*.html").addResourceLocations("/WEB-INF/partials/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        BuildInformationInterceptorAdapter buildInformationInterceptorAdapter = new BuildInformationInterceptorAdapter();
        buildInformationInterceptorAdapter.setBuildInformationView(applicationConfig.getBuildInformationView());

        registry.addInterceptor(buildInformationInterceptorAdapter);
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames("WEB-INF/i18n/message");
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setCacheSeconds(1); // TODO : This is temporary so that the messages get reloaded constantly for dev

        return messageSource;
    }

    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("en", "CA"));

        return localeResolver;
    }

    @Bean
    public VelocityConfigurer velocityConfig() {
        VelocityConfigurer configurer = new VelocityConfigurer();
        configurer.setResourceLoaderPath("/WEB-INF/views");

        return configurer;
    }

    @Bean
    public VelocityLayoutViewResolver velocityLayoutViewResolver() {
        VelocityLayoutViewResolver viewResolver = new VelocityLayoutViewResolver();
        viewResolver.setViewClass(VelocityLayoutView.class);
        viewResolver.setLayoutUrl("template/site.vm");
        viewResolver.setCache(false); // TODO : Set to false to removing caching for dev.
        viewResolver.setSuffix(".vm");
        viewResolver.setOrder(1);

        return viewResolver;
    }

    /**
     * Configure the MultipartResolver. This is hit when a request to the servlet is detected that is a multipart file
     * <p/>
     * NOTE: This function MUST be named multipartResolver()
     *
     * @return a configured multipart resolver
     */
/*    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(30000000L); // ~30 MB

        return commonsMultipartResolver;
    }*/

}
