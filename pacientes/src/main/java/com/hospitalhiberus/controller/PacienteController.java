package com.hospitalhiberus.controller;

import com.hospitalhiberus.model.Paciente;
import com.hospitalhiberus.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
// Se puede añadir un @RequestMapping("/pacientes"),pero quiero seguis la estructura de la API Medicos
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping("/pacientes")
    public ResponseEntity<List<Paciente>> getPacientes(){
        return new ResponseEntity<>(pacienteRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/pacientes")
    public ResponseEntity<Paciente> crearPaciente(@RequestBody Paciente paciente){
        System.out.println("Datos recibidos: " + paciente);
        if (pacienteRepository.existsByDni(paciente.getDni())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Si el DNI ya existe
        }
        return new ResponseEntity<>(pacienteRepository.save(paciente), HttpStatus.CREATED);
    }


    @GetMapping("/pacientes/{dni}")
    public ResponseEntity<Paciente> getPaciente(@PathVariable("dni") String dni){
        Paciente paciente = pacienteRepository.findByDni(dni);
        if (paciente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paciente, HttpStatus.OK);
    }


    @PutMapping("/pacientes/{dni}")
    public ResponseEntity<Paciente> actualizarPaciente(@PathVariable("dni") String dni, @RequestBody Paciente paciente) {
        Paciente pacienteActual = pacienteRepository.findByDni(dni);
        if (pacienteActual == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        pacienteActual.setNombre(paciente.getNombre());
        pacienteActual.setApellidos(paciente.getApellidos());
        pacienteActual.setFechanac(paciente.getFechanac());
        pacienteActual.setEmail(paciente.getEmail());
        pacienteActual.setDireccion(paciente.getDireccion());

        return new ResponseEntity<>(pacienteRepository.save(pacienteActual), HttpStatus.OK);
    }


    @DeleteMapping("/pacientes/{dni}")
    @Transactional
    public ResponseEntity<Void> eliminarPaciente(@PathVariable("dni") String dni){
        if (!pacienteRepository.existsByDni(dni)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        pacienteRepository.deleteByDni(dni);
        return ResponseEntity.noContent().build();
    }


}
