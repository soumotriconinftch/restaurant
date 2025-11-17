package com.fooddelivery.restaurant_api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        logger.info("========================================");
        logger.info("Configuring MySQL DataSource for DEV");
        logger.info("========================================");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/food_delivery_dev?createDatabaseIfNotExist=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        logger.info("MySQL Database URL: {}", dataSource.getUrl());
        return dataSource;
    }

    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        logger.info("========================================");
        logger.info("Configuring PostgreSQL DataSource for PROD");
        logger.info("========================================");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/food_delivery_prod");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        logger.info("PostgreSQL Database URL: {}", dataSource.getUrl());
        return dataSource;
    }
}