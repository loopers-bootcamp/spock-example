package com.loopers.spock.example.test.testcontainers;

import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class MySqlTestContainersConfiguration {

    private static final MySQLContainer<?> mySqlContainer;

    static {
        mySqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
                .withDatabaseName("loopers")
                .withUsername("test")
                .withPassword("test")
                .withExposedPorts(3306)
                .withCommand(
                        "--character-set-server=utf8mb4",
                        "--collation-server=utf8mb4_general_ci",
                        "--skip-character-set-client-handshake"
                );
        mySqlContainer.start();

        String mySqlJdbcUrl = String.format(
                "jdbc:mysql://%s:%d/%s",
                mySqlContainer.getHost(),
                mySqlContainer.getFirstMappedPort(),
                mySqlContainer.getDatabaseName()
        );

        System.setProperty("datasource.mysql.jdbc-url", mySqlJdbcUrl);
        System.setProperty("datasource.mysql.username", mySqlContainer.getUsername());
        System.setProperty("datasource.mysql.password", mySqlContainer.getPassword());
    }

}
