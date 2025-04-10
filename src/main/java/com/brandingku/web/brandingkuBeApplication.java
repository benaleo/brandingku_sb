package com.brandingku.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.brandingku.web")
@EntityScan(basePackages = "com.brandingku.web.entity")
@EnableJpaRepositories(basePackages = "com.brandingku.web.repository")
public class brandingkuBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(brandingkuBeApplication.class, args);
    }

}
