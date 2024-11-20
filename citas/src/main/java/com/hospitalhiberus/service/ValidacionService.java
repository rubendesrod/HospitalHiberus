package com.hospitalhiberus.service;

import com.hospitalhiberus.feign.MedicoClient;
import com.hospitalhiberus.feign.PacienteClient;
import com.hospitalhiberus.model.Cita;
import com.hospitalhiberus.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ValidacionService {

    @Autowired
    private MedicoClient medicoClient;

    @Autowired
    private PacienteClient pacienteClient;

    @Autowired
    private CitaRepository repository;

    public void verificarExistenciaMedico(String dniMedico) {
        try {
            ResponseEntity<Map<String, Object>> response = medicoClient.obtenerMedico(dniMedico);
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new RuntimeException("El médico con DNI " + dniMedico + " no existe.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar el microservicio de médicos: " + e.getMessage(), e);
        }
    }

    public void verificarExistenciaPaciente(String idPaciente) {
        try {
            ResponseEntity<Map<String, Object>> response = pacienteClient.obtenerPaciente(idPaciente);
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new RuntimeException("El paciente con DNI " + idPaciente + " no existe.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar el microservicio de pacientes: " + e.getMessage(), e);
        }
    }

    public Cita obtenerCitaPorId(String idCita) {
        return repository.findById(idCita)
                .orElseThrow(() -> new RuntimeException("La cita con ID " + idCita + " no existe."));
    }
}

