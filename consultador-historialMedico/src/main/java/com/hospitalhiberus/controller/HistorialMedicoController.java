package com.hospitalhiberus.controller;

import com.hospitalhiberus.model.HistorialMedico;
import com.hospitalhiberus.service.HistorialMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/historiales")
public class HistorialMedicoController {

    @Autowired
    private HistorialMedicoService service;

    @GetMapping
    public ResponseEntity<List<HistorialMedico>> obtenerTodos() {
        List<HistorialMedico> historiales = service.obtenerTodos();
        if (historiales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historiales);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<HistorialMedico> buscarPacientePorDni(@PathVariable("dni") String dni) {
        HistorialMedico historial = service.obtenerHistorialPaciente(dni);
        if (historial == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(historial);
    }
}
