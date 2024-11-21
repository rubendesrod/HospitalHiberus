package com.hospitalhiberus.service;

import com.hospitalhiberus.avro.HistorialMedicoValue;
import com.hospitalhiberus.avro.Visita;
import com.hospitalhiberus.model.Cita;
import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.KafkaException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class CitaService {

    @Autowired
    private CitaRepository repository;

    @Autowired
    private ValidacionService validacionService;

    @Autowired
    private KafkaProducerService kafkaService;

    public ResponseEntity<List<Cita>> obtenerTodasLasCitas(){
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK );
    }

    public ResponseEntity<List<Cita>> obtenerCitasPorIdPaciente(String idPaciente){
        validacionService.verificarExistenciaPaciente(idPaciente);
         return new ResponseEntity<>(repository.findCitaByIdPaciente(idPaciente), HttpStatus.OK);

    };

    public ResponseEntity<List<Cita>> obtenerCitasPorIdMedico(String idMedico){
        validacionService.verificarExistenciaMedico(idMedico);
        return new ResponseEntity<>(repository.findCitaByIdMedico(idMedico), HttpStatus.OK);
    }

    public ResponseEntity<List<Cita>> obtenerCitasPorIdMedicoYEstado(String idMedico, ESTADOS estado){
        validacionService.verificarExistenciaMedico(idMedico);
        return new ResponseEntity<>(repository.findCitaByIdMedicoAndEstado(idMedico, estado), HttpStatus.OK);
    }

    public ResponseEntity<Cita> crearCita(Cita cita) {
        validacionService.verificarExistenciaMedico(cita.getIdMedico());
        validacionService.verificarExistenciaPaciente(cita.getIdPaciente());
        return new ResponseEntity<>(repository.save(cita), HttpStatus.CREATED);
    }


    public ResponseEntity<Cita> completarCita(String idCita, List<String> tratamiento) {
        Cita cita = validacionService.obtenerCitaPorId(idCita);
        cita.setEstado(ESTADOS.completada);

        if (tratamiento == null || tratamiento.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tratamiento no puede estar vacío");
        }

        repository.save(cita);

        // Crear mensaje Avro Historial Medico
        HistorialMedicoValue historialMedico = HistorialMedicoValue.newBuilder()
            .setIdHistorial(cita.getId().hashCode())
            .setIdPaciente(cita.getIdPaciente())
            .setFecha(String.valueOf(LocalDate.now()))
            .setVisitas(List.of(
                Visita.newBuilder()
                    .setFechaVisita(cita.getFecha().toString())
                    .setHora(String.valueOf(cita.getHora()))
                    .setTratamiento(tratamiento)
                    .setMotivo(cita.getMotivo())
                    .build()
            ))
            .build();

        // Enviar al topic
        try {
            kafkaService.enviarHistorialMedico("historialMedico", historialMedico);
        } catch (KafkaException e) {
            System.out.println("No se ha podido enviar el historial al topic historialMedico");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar el historial médico");
        }


        return ResponseEntity.ok(cita);
    }

    public ResponseEntity<Cita> cancelarCita(String idCita) {
        Cita cita = validacionService.obtenerCitaPorId(idCita);
        cita.setEstado(ESTADOS.cancelada);
        return new ResponseEntity<>(repository.save(cita), HttpStatus.OK);
    }


}
