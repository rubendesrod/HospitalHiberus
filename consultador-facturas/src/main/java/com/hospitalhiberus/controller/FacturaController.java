package com.hospitalhiberus.controller;

import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.model.Factura;
import com.hospitalhiberus.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService service;

    @GetMapping
    public ResponseEntity<List<Factura>> obtenerTodas() {
        List<Factura> facturas = service.obtenerTodas();
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<List<Factura>> obtenerFacturasDeUnMedico(@PathVariable(name = "dni") String dni) {
        List<Factura> facturas = service.obtenerFacturasMedico(dni);
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/{dni}/{estado}")
    public ResponseEntity<List<Factura>> obtenerFacturasDeUnMedicoYEstados(@PathVariable(name = "dni") String dni, @PathVariable(name = "estado") ESTADOS estado) {
        List<Factura> facturas = service.obtenerFacturasMedicoConEstado(dni, estado);
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }
}
