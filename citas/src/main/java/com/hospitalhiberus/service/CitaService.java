package com.hospitalhiberus.service;

import com.hospitalhiberus.avro.FacturaValue;
import com.hospitalhiberus.avro.HistorialMedicoValue;
import com.hospitalhiberus.avro.Visita;
import com.hospitalhiberus.model.Cita;
import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.repository.CitaRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Slf4j
@Service
public class CitaService {

    @Autowired
    private CitaRepository repository;

    @Autowired
    private ValidacionService validacionService;

    @Autowired
    private KafkaProducerService kafkaService;

    public List<Cita> obtenerTodasLasCitas(){
        return repository.findAll();
    }

    public List<Cita> obtenerCitasPorIdPaciente(String idPaciente){
        validacionService.verificarExistenciaPaciente(idPaciente);
        return repository.findCitaByIdPaciente(idPaciente);
    }

    public List<Cita> obtenerCitasPorIdMedico(String idMedico){
        validacionService.verificarExistenciaMedico(idMedico);
        return repository.findCitaByIdMedico(idMedico);
    }

    public List<Cita> obtenerCitasPorIdMedicoYEstado(String idMedico, ESTADOS estado){
        validacionService.verificarExistenciaMedico(idMedico);
        return repository.findCitaByIdMedicoAndEstado(idMedico, estado);
    }

    public Cita crearCita(Cita cita) {
        validacionService.verificarExistenciaMedico(cita.getIdMedico());
        validacionService.verificarExistenciaPaciente(cita.getIdPaciente());
        return repository.save(cita);
    }

    public Cita completarCita(String idCita, List<String> tratamiento) {
        Cita cita = validacionService.obtenerCitaPorId(idCita);
        
        validacionService.verificarExistenciaMedico(cita.getIdMedico());
        validacionService.verificarExistenciaPaciente(cita.getIdPaciente());
        validacionService.verificarCitaCompletada(cita.getEstado());
        
        cita.setEstado(ESTADOS.completada);
        if (tratamiento == null || tratamiento.isEmpty()) {
            throw new IllegalArgumentException("El tratamiento no puede estar vacío");
        }
    
        repository.save(cita);

        // Builder de Avro Historial Medico
        HistorialMedicoValue historialMedico = HistorialMedicoValue.newBuilder()
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

        // Builder del Avro Factura
        FacturaValue factura = FacturaValue.newBuilder()
                .setIdMedico(cita.getIdMedico())
                .setEstado(com.hospitalhiberus.avro.ESTADOS.pendiente)
                .setFechaEmision(String.valueOf(LocalDate.now()))
                .setTotalPagar(400)
                .build();

        // Enviar al topic el historial Medico y la factura
        try {
            kafkaService.enviarHistorialMedico("historialMedico", historialMedico);
            kafkaService.enviarFactura("facturas", factura);
        } catch (KafkaException e) {
            log.info("No se ha podido enviar el historial al topic historialMedico y factura");
            throw new RuntimeException("Error al procesar el historial médico y la factura");
        }

        return cita;
    }

    public Cita cancelarCita(String idCita) {
        Cita cita = validacionService.obtenerCitaPorId(idCita);
        cita.setEstado(ESTADOS.cancelada);
        return repository.save(cita);
    }
}
