package com.kamhoops.configuration;

import org.hibernate.ejb.HibernatePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.kamhoops.data.repository")
@ComponentScan(basePackages = {"com.kamhoops.data.repository"})
public class PersistenceJpaConfig {
    private static final Logger logger = LoggerFactory.getLogger(PersistenceJpaConfig.class);

    @Value("${jpa.database.type=POSTGRES}")
    private PersistenceJpaConfig.DatabaseType databaseType;

    public static enum DatabaseType {
        POSTGRES, H2
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        logger.info("Starting up with {} database ...", databaseType);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.kamhoops.data.domain");

        AbstractJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(false);

        switch (databaseType) {
            case H2: {
                vendorAdapter.setDatabase(Database.H2);
                vendorAdapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
                break;
            }
            case POSTGRES: {
                vendorAdapter.setDatabase(Database.POSTGRESQL);
                vendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQL82Dialect");
                break;
            }
            default: {
                vendorAdapter.setDatabase(Database.POSTGRESQL);
                vendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQL82Dialect");
                break;
            }
        }

        factoryBean.setPersistenceProvider(new HibernatePersistence());
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        properties.put("hibernate.bytecode.provider", "cglib");   // Suppose to help java pergem space issues with hibernate
        factoryBean.setJpaPropertyMap(properties);

        factoryBean.setPersistenceUnitName("kamhoops");
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    @Bean
    public PersistenceExceptionTranslator persistenceExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        DataSource dataSource;

        switch (databaseType) {
            case H2: {
                SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
                simpleDriverDataSource.setDriver(new org.h2.Driver());
                simpleDriverDataSource.setUrl("jdbc:h2:mem:kamhoopsTest;mode=PostgreSQL;DB_CLOSE_DELAY=-1");
                simpleDriverDataSource.setUsername("h2");
                simpleDriverDataSource.setPassword("h2");

                dataSource = new LazyConnectionDataSourceProxy(simpleDriverDataSource);
                break;
            }
            case POSTGRES:
            default: {
                dataSource = lookup.getDataSource("java:comp/env/jdbc/postgres/kamhoops");
                break;
            }
        }

        return dataSource;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }
}
