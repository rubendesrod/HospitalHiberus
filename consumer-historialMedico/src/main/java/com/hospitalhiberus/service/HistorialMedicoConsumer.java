package com.hospitalhiberus.service;

import com.hospitalhiberus.avro.HistorialMedicoKey;
import com.hospitalhiberus.avro.HistorialMedicoValue;
import com.hospitalhiberus.mapper.Mapper;
import com.hospitalhiberus.model.HistorialMedico;
import com.hospitalhiberus.model.Visita;
import com.hospitalhiberus.repository.HistorialMedicoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class HistorialMedicoConsumer {

    @Autowired
    private HistorialMedicoRepository repository;

    @Autowired
    private Mapper mapper;

    @KafkaListener(topics = "historialMedico", groupId = "historial-medico-consumer")
    public void consume(ConsumerRecord<HistorialMedicoKey,HistorialMedicoValue> historial) {
        try {
            log.info("Key: " + historial.key());
            log.info("Value: " + historial.value());
            log.info("Visitas: " + historial.value().getVisitas());

            /*// Buscar historial existente
            HistorialMedico historialExistente = repository.findByIdPaciente(historial.key().getIdPaciente());

            // Mapeado de las visitas
            List<Visita> visitasMapeadas = mapper.mapperVisitas(historialAvro);

            if (historialExistente != null) {
                actualizarHistorialExistente(historialExistente, visitasMapeadas);
            } else {
                crearNuevoHistorial(historialAvro, visitasMapeadas);
            }*/
        } catch (Exception e) {
            System.err.println("Error al procesar el historial médico: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void actualizarHistorialExistente(
        HistorialMedico historialExistente,
        List<Visita> visitasMapeadas) {

        // Asegurarse de que la lista de visitas es mutable o no viene vacia
        if (historialExistente.getVisitas() == null) {
            historialExistente.setVisitas(new ArrayList<>());
        } else if (!(historialExistente.getVisitas() instanceof ArrayList)) {
            historialExistente.setVisitas(new ArrayList<>(historialExistente.getVisitas()));
        }

        // Agregar las nuevas visitas al historial existente
        historialExistente.getVisitas().addAll(visitasMapeadas);

        // Guardar el historial actualizado
        repository.save(historialExistente);

        System.out.println("Visitas añadidas al historial existente para el paciente: "
                + historialExistente.getIdPaciente());
    }

    private void crearNuevoHistorial(HistorialMedicoValue historialAvro, List<Visita> visitasMapeadas) {
        // Crear un nuevo historial médico
        HistorialMedico nuevoHistorial = mapper.mapperHistorial(historialAvro, visitasMapeadas);

        // Guardar el nuevo historial en la base de datos
        repository.save(nuevoHistorial);

        System.out.println("Nuevo historial médico creado para el paciente: " + nuevoHistorial.getIdPaciente());
    }
}
