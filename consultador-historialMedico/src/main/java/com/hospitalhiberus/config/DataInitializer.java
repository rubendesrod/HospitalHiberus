package com.hospitalhiberus.config;

import com.hospitalhiberus.model.HistorialMedico;
import com.hospitalhiberus.model.Visita;
import com.hospitalhiberus.repository.HistorialMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

// @Configuration // Descomentar si se quieren probar las consultas
public class DataInitializer {

    @Autowired
    private HistorialMedicoRepository repository;

    @Bean
    CommandLineRunner initDatabase(){
        return args -> {
            repository.deleteAll(); // Limpio la coleccion para evitar duplicado

            // Creo datos de prueba
            HistorialMedico h1 = new HistorialMedico();
            h1.setIdPaciente("12508523L");
            h1.setFecha("2024-11-15");

            Visita v1 = new Visita();
            v1.setFechaVisita("2024-10-01");
            v1.setHora("10:30");
            v1.setTratamiento(Arrays.asList("Inhalador para el asma", "Antibiótico"));
            v1.setMotivo("Dificultad respiratoria");

            Visita v2 = new Visita();
            v2.setFechaVisita("2024-11-01");
            v2.setHora("14:00");
            v2.setTratamiento(Arrays.asList("Medicamento antihipertensivo"));
            v2.setMotivo("Chequeo de hipertensión");

            h1.setVisitas(Arrays.asList(v1, v2));

            repository.save(h1);

            System.out.println("Datos de prueba insertados en MongoDB");


        };
    }

}
