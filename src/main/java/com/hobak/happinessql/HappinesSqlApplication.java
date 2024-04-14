package com.hobak.happinessql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HappinesSqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappinesSqlApplication.class, args);
    }

}
