package com.graphapp.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Configuration for SQLite relational database.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.graphapp.repository.relational")
@EnableTransactionManagement
public class DatabaseConfig {

    private final Environment env;

    /**
     * Constructor for DatabaseConfig.
     * 
     * @param env The environment.
     */
    public DatabaseConfig(Environment env) {
        this.env = env;
    }

    /**
     * Create a DataSource for the SQLite database.
     * 
     * @return The DataSource.
     */
    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSourceBuilder.url(env.getProperty("spring.datasource.url"));
        return dataSourceBuilder.build();
    }

    /**
     * Initialize the database with schema and sample data.
     * 
     * @param dataSource The DataSource.
     * @return The DataSourceInitializer.
     */
    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("schema.sql"));
        
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }
}