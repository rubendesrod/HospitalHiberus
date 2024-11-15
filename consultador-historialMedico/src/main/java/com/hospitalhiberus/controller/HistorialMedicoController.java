package com.hospitalhiberus.controller;

import com.hospitalhiberus.model.HistorialMedico;
import com.hospitalhiberus.service.HistorialMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HistorialMedicoController {

    @Autowired
    private HistorialMedicoService service;

    @GetMapping("/historiales")
    public List<HistorialMedico> obtenerTodos(){
        return service.obtenerTodos();
    }

    @GetMapping("/historiales/{dni}")
    public HistorialMedico buscarPacientePorDni(@PathVariable("dni") String dni){
        return service.obtenerHistorialPaciente(dni);
    }

}
