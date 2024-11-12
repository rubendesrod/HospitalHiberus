package com.hospitalhiberus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class ApplicationPacientes {
  public static void main(String[] args) {
    SpringApplication.run(ApplicationPacientes.class, args);
  }
}