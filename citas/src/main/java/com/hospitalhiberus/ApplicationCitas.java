package com.hospitalhiberus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.hospitalhiberus.feign")
public class ApplicationCitas {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationCitas.class, args);
    }
}
