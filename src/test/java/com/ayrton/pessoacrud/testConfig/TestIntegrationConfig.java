package com.ayrton.pessoacrud.testConfig;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestIntegrationConfig {

    private static MySQLContainer container = (MySQLContainer) new MySQLContainer("mysql:8.0.26")
            .withReuse(true);

    @DynamicPropertySource
    public  static  void sobrescreveProps(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",container::getJdbcUrl);
        registry.add("spring.datasource.username",container::getUsername);
        registry.add("spring.datasource.password",container::getPassword);
    }

    @LocalServerPort
    protected int porta;

    @Autowired
    private DbIntegrationConfig dbIntegration;
    private static DbIntegrationConfig staticDbIntegration;

    private static List<Class> classes;

    @PostConstruct
    protected void init() {
        staticDbIntegration = dbIntegration;
    }

    @BeforeAll
    static void inicializa(){ container.start(); }

    @AfterAll
    static void finaliza(){
        staticDbIntegration.resetaBase(classes);
    }

    protected static void registraClasse(Class classe) {
        if(isNull(classes))
            classes = new ArrayList<>();
        classes.add(classe);
    }
}
