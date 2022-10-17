package com.ayrton.pessoacrud.testConfig;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public class TestContainerIntegrationConfig {

    private static MySQLContainer container = (MySQLContainer) new MySQLContainer("mysql:8.0.26").withReuse(true);

    @BeforeAll
    static void inicializa(){
        container.start();
    }
    @AfterAll
    static void finaliza(){
        container.stop();
    }

    @DynamicPropertySource
    public  static  void sobres(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",container::getJdbcUrl);
        registry.add("spring.datasource.username",container::getUsername);
        registry.add("spring.datasource.password",container::getPassword);
    }
}
