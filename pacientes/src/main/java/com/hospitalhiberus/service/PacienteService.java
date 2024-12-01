package com.hospitalhiberus.service;

import com.hospitalhiberus.model.Paciente;
import com.hospitalhiberus.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Paciente> obtenerTodosLosPacientes() {
        return pacienteRepository.findAll();
    }

    public boolean existePacientePorDni(String dni) {
        return pacienteRepository.existsByDni(dni);
    }

    public Paciente crearPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente obtenerPacientePorDni(String dni) {
        return pacienteRepository.findByDni(dni);
    }

    public Paciente actualizarPaciente(String dni, Paciente paciente) {
        Paciente pacienteActual = pacienteRepository.findByDni(dni);
        if (pacienteActual == null) {
            return null;
        }
        pacienteActual.setNombre(paciente.getNombre());
        pacienteActual.setApellidos(paciente.getApellidos());
        pacienteActual.setFechanac(paciente.getFechanac());
        pacienteActual.setEmail(paciente.getEmail());
        pacienteActual.setDireccion(paciente.getDireccion());
        return pacienteRepository.save(pacienteActual);
    }

    public void eliminarPacientePorDni(String dni) {
        pacienteRepository.deleteByDni(dni);
    }
}
