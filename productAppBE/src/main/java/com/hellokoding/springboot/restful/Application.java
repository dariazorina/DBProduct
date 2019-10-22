package com.hellokoding.springboot.restful;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@SpringBootApplication
//@EnableConfigurationProperties({LiquibaseProperties.class})

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
