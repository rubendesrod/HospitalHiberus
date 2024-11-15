package com.hospitalhiberus.service;

import com.hospitalhiberus.model.HistorialMedico;
import com.hospitalhiberus.repository.HistorialMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialMedicoService {

    @Autowired
    private HistorialMedicoRepository repository;

    // Obtener todos los historiales médicos
    public List<HistorialMedico> obtenerTodos(){
        return repository.findAll();
    }

    // Obtener un historial medico por paciente
    public HistorialMedico obtenerHistorialPaciente(String dni){
        return repository.findByIdPaciente(dni);
    }

}
