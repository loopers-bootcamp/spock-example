package com.loopers.spock.example.common.configuration.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan("com.loopers.spock.example.domain")
@EnableJpaRepositories("com.loopers.spock.example.infrastructure")
public class JpaConfiguration {
}
