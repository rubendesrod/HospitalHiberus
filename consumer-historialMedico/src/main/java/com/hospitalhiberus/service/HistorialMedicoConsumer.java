package com.hospitalhiberus.service;

import com.hospitalhiberus.avro.HistorialMedico;
import com.hospitalhiberus.mapper.Mapper;
import com.hospitalhiberus.model.Visita;
import com.hospitalhiberus.repository.HistorialMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialMedicoConsumer {

    @Autowired
    private HistorialMedicoRepository repository;

    private Mapper mapper;

    @KafkaListener(topics = "historialMedico", groupId = "historial-medico-consumer")
    public void consume(HistorialMedico historialAvro){
        System.out.println("Se ha recibido el historial " + historialAvro);
        // Primero busco si ya existe un historial por ID del paciente
        com.hospitalhiberus.model.HistorialMedico historialExistente = repository.findByIdPaciente(historialAvro.getIdPaciente());

        // Mapeado de las visitas para el documento en MongoDB
        List<Visita> visitasMapeadas = mapper.mapperVisitas(historialAvro);

        if(historialExistente != null) {
            historialExistente.getVisitas().addAll(visitasMapeadas);
            repository.save(historialExistente);
            System.out.println("Visita añadida al historial existente" + historialExistente.getIdPaciente());
        } else {
            com.hospitalhiberus.model.HistorialMedico historial = mapper.mapperHistorial(historialAvro, visitasMapeadas);
            repository.save(historial);
            System.out.println("Nuevo historial médico creado: " + historial.getIdPaciente());
        }

    }


}
