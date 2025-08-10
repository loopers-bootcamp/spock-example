package com.loopers.spock.example.common.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "datasource.mysql")
    HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Primary
    HikariDataSource hikariDataSource(@Qualifier("hikariConfig") HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

}
