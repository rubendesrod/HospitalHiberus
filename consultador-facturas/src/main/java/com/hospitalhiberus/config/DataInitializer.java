package com.hospitalhiberus.config;

import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.model.Factura;
import com.hospitalhiberus.repository.FacturaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;

@Slf4j
// @Configuration // quitar los comentarios si que quiere iniciar desde el principio
public class DataInitializer {

    @Autowired
    private FacturaRepository repository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            repository.deleteAll(); // Limpio la tabla/DB para evitar duplicado

            Factura f1 = new Factura();
            f1.setIdMedico("12345678A");
            f1.setFechaEmision(LocalDate.now());
            f1.setEstado(ESTADOS.pendiente);
            f1.setTotalPagar(400);

            Factura f2 = new Factura();
            f2.setIdMedico("12345678A");
            f2.setFechaEmision(LocalDate.now());
            f2.setEstado(ESTADOS.pagado);
            f2.setTotalPagar(322);
            f2.setFechaPago(LocalDate.now());

            Factura f3 = new Factura();
            f3.setIdMedico("87654321A");
            f3.setFechaEmision(LocalDate.now());
            f3.setEstado(ESTADOS.pendiente);
            f3.setTotalPagar(650);

            log.info("Insertando datos de prueba...");
            log.info("Factura 1" + f1);
            log.info("Factura 2" + f1);
            log.info("Factura 3" + f1);
            repository.saveAll(Arrays.asList(f1,f2,f3));
            log.info("Datos de prueba insertados en MySQL_Facturas");
        };
    }
}
