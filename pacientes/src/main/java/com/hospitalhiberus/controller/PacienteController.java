package com.hospitalhiberus.controller;

import com.hospitalhiberus.model.Paciente;
import com.hospitalhiberus.service.PacienteService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RestController
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/pacientes")
    public ResponseEntity<List<Paciente>> getPacientes() {
        List<Paciente> pacientes = pacienteService.obtenerTodosLosPacientes();
        if (pacientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pacientes);
    }

    @PostMapping("/pacientes")
    public ResponseEntity<Paciente> crearPaciente(@RequestBody Paciente paciente) {
        log.info("Datos recibidos: " + paciente);
        if (pacienteService.existePacientePorDni(paciente.getDni())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Si el DNI ya existe
        }
        Paciente nuevoPaciente = pacienteService.crearPaciente(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPaciente);
    }

    @GetMapping("/pacientes/{dni}")
    public ResponseEntity<Paciente> getPaciente(@PathVariable("dni") String dni) {
        Paciente paciente = pacienteService.obtenerPacientePorDni(dni);
        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paciente);
    }

    @PutMapping("/pacientes/{dni}")
    public ResponseEntity<Paciente> actualizarPaciente(@PathVariable("dni") String dni, @RequestBody Paciente paciente) {
        Paciente pacienteActualizado = pacienteService.actualizarPaciente(dni, paciente);
        if (pacienteActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pacienteActualizado);
    }

    @DeleteMapping("/pacientes/{dni}")
    @Transactional
    public ResponseEntity<Void> eliminarPaciente(@PathVariable("dni") String dni) {
        if (!pacienteService.existePacientePorDni(dni)) {
            return ResponseEntity.notFound().build();
        }
        pacienteService.eliminarPacientePorDni(dni);
        return ResponseEntity.noContent().build();
    }
}
