package com.hospitalhiberus.config;

import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.model.Factura;
import com.hospitalhiberus.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
public class DataInitlzr {

    @Autowired
    private FacturaRepository repository;


    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            repository.deleteAll(); // Limpio la tabla/DB para evitar duplicado

            // Creo datos de prueba
            Factura f1 = new Factura();
            f1.setId_medico("12345678A");
            f1.setFechaEmision(LocalDate.now());
            f1.setEstado(ESTADOS.PENDIENTE);
            f1.setTotalPagar(400);

            Factura f2 = new Factura();
            f2.setId_medico("12345678A");
            f2.setFechaEmision(LocalDate.now());
            f2.setEstado(ESTADOS.PAGADA);
            f2.setTotalPagar(322);
            f2.setFechaPago(LocalDate.now());

            Factura f3 = new Factura();
            f3.setId_medico("87654321A");
            f3.setFechaEmision(LocalDate.now());
            f3.setEstado(ESTADOS.PENDIENTE);
            f3.setTotalPagar(650);


            repository.save(f1);
            repository.save(f2);
            repository.save(f3);

            System.out.println("Datos de prueba insertados en MySQL_Facturas");


        };
    }
}
