package com.hospitalhiberus.controller;

import com.hospitalhiberus.model.Cita;
import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CitaController {

    @Autowired
    private CitaService service;

    @GetMapping("/citas")
    public ResponseEntity<List<Cita>> obtenerTodasLasCitas() {
        return service.obtenerTodasLasCitas();
    }

    @GetMapping("/citas/paciente/{idPaciente}")
    public ResponseEntity<List<Cita>> obtenerCitasPorIdPaciente(@PathVariable("idPaciente") String idPaciente) {
        return service.obtenerCitasPorIdPaciente(idPaciente);
    }

    @GetMapping("/citas/medico/{idMedico}")
    public ResponseEntity<List<Cita>> obtenerCitasPorIdMedico(@PathVariable("idMedico") String idMedico) {
        return service.obtenerCitasPorIdMedico(idMedico);
    }

    @GetMapping("/citas/medico/{idMedico}/estado/{estado}")
    public ResponseEntity<List<Cita>> obtenerCitasPorIdMedicoYEstado(
            @PathVariable("idMedico") String idMedico,
            @PathVariable("estado") ESTADOS estado
    ) {
        return service.obtenerCitasPorIdMedicoYEstado(idMedico, estado);
    }

    @PostMapping("/citas")
    public ResponseEntity<Cita> crearCita(@RequestBody Cita cita) {
        return service.crearCita(cita);
    }

    @PutMapping("/citas/completar/{idCita}")
    public ResponseEntity<Cita> completarCita(@PathVariable("idCita") String idCita, @RequestBody List<String> tratamiento) {
        return service.completarCita(idCita, tratamiento);
    }

    @PutMapping("/citas/cancelar/{idCita}")
    public ResponseEntity<Cita> cancelarCita(@PathVariable("idCita") String idCita) {
        return service.cancelarCita(idCita);
    }
}
