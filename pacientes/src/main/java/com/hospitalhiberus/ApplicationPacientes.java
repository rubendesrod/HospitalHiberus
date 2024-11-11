package com.hospitalhiberus;

import org.apache.servicecomb.springboot.starter.provider.EnableServiceComb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEurekaCliente
@SpringBootApplication
public class ApplicationPacientes {
  public static void main(String[] args) {
    SpringApplication.run(ApplicationPacientes.class, args);
  }
}