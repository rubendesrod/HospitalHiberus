package com.hospitalhiberus.controller;

import com.hospitalhiberus.model.Cita;
import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private CitaService service;

    @GetMapping
    public ResponseEntity<List<Cita>> obtenerTodasLasCitas() {
        List<Cita> citas = service.obtenerTodasLasCitas();
        if (citas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<Cita>> obtenerCitasPorIdPaciente(@PathVariable("idPaciente") String idPaciente) {
        List<Cita> citas = service.obtenerCitasPorIdPaciente(idPaciente);
        if (citas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<List<Cita>> obtenerCitasPorIdMedico(@PathVariable("idMedico") String idMedico) {
        List<Cita> citas = service.obtenerCitasPorIdMedico(idMedico);
        if (citas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/medico/{idMedico}/estado/{estado}")
    public ResponseEntity<List<Cita>> obtenerCitasPorIdMedicoYEstado(
            @PathVariable("idMedico") String idMedico,
            @PathVariable("estado") ESTADOS estado
    ) {
        List<Cita> citas = service.obtenerCitasPorIdMedicoYEstado(idMedico, estado);
        if (citas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(citas);
    }

    @PostMapping
    public ResponseEntity<Cita> crearCita(@RequestBody Cita cita) {
        Cita nuevaCita = service.crearCita(cita);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
    }

    @PutMapping("/completar/{idCita}")
    public ResponseEntity<Cita> completarCita(@PathVariable("idCita") String idCita, @RequestBody List<String> tratamiento) {
        try {
            Cita citaActualizada = service.completarCita(idCita, tratamiento);
            return ResponseEntity.ok(citaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/cancelar/{idCita}")
    public ResponseEntity<Cita> cancelarCita(@PathVariable("idCita") String idCita) {
        Cita citaCancelada = service.cancelarCita(idCita);
        return ResponseEntity.ok(citaCancelada);
    }
}

