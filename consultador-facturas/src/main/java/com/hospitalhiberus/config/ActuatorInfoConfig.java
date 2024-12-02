package com.hospitalhiberus.config;

import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ActuatorInfoConfig {

    @Bean
    public InfoContributor customInfoContributor() {
        return builder -> builder
                .withDetail("app",
                        Map.of(
                                "name", "Microservicio de Consultador-Facturas",
                                "description", "Este es el microservicio encargado de gestionar la devolución de los datos de las facturas.",
                                "version", "1.0.0",
                                "status", "Activo"
                        ))
                .build();
    }
}
