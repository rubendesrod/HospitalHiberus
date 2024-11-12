package com.hospitalhiberus.controller;

import com.hospitalhiberus.model.Paciente;
import com.hospitalhiberus.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
// Se puede añadir un @RequestMapping("/pacientes"),pero quiero seguis la estructura de la API Medicos
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping("/pacientes")
    public List<Paciente> getPacientes(){
        return pacienteRepository.findAll();
    }

    @PostMapping("/pacientes")
    public Paciente crearPaciente(@RequestBody Paciente paciente){
        return pacienteRepository.save(paciente);
    }

    @GetMapping("/pacientes/{dni}")
    public Paciente getPaciente(@PathVariable("dni") String dni){
        return pacienteRepository.findByDni(dni);
    }

    @PutMapping("/pacientes/{dni}")
    public Paciente actualizarPaciente(@PathVariable("dni") String dni, @RequestBody Paciente paciente){
        Paciente pacienteActual = pacienteRepository.findByDni(dni);
        pacienteActual.setNombre(paciente.getNombre());
        pacienteActual.setApellidos(paciente.getApellidos());
        pacienteActual.setFehcaNac(paciente.getFehcaNac());
        pacienteActual.setEmail(paciente.getEmail());
        pacienteActual.setDireccion(paciente.getDireccion());
        return pacienteRepository.save(pacienteActual);
    }

    @DeleteMapping("/pacientes/{dni}")
    @Transactional
    public ResponseEntity<Void> eliminarPaciente(@PathVariable("dni") String dni){
        pacienteRepository.deleteByDni(dni);
        return ResponseEntity.noContent().build();
    }

}
