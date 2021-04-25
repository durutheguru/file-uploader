package com.julianduru.fileuploader.config;


import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import javax.sql.DataSource;

/**
 * created by julian
 */
@TestConfiguration
public class TestDataSourceConfig {


    @Bean
    public MySQLContainer mySQLContainer() {
        var container = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("file_upload")
            .withUsername("username")
            .withPassword("password")
            .withUrlParam("createDatabaseIfNotExist", "true")
            .withUrlParam("serverTimezone", "UTC")
            .withLogConsumer(
                new Slf4jLogConsumer(
                    LoggerFactory.getLogger(getClass())
                )
            );

        container.start();

        return container;
    }


    @Bean
    @Primary
    public DataSource dataSource(MySQLContainer mySQLContainer) {
        var dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(mySQLContainer.getJdbcUrl());
        dataSource.setUsername(mySQLContainer.getUsername());
        dataSource.setPassword(mySQLContainer.getPassword());
        dataSource.setDriverClassName(mySQLContainer.getDriverClassName());

        return dataSource;
    }


}

