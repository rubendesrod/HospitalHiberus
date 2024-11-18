package com.hospitalhiberus.controller;

import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.model.Factura;
import com.hospitalhiberus.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Path;
import java.util.List;

@RestController
public class FacturaController {

    @Autowired
    private FacturaService service;

    @GetMapping("/facturas")
    public List<Factura> obtenerTodas(){
        return service.obtenerTodas();
    }

    @GetMapping("/facturas/{dni}")
    public List<Factura> obtenerFacturasDeUnMedico(@PathVariable(name = "dni") String dni){
        return service.obtenerFacturasMedico(dni);
    }

    @GetMapping("/facturas/{dni}{estado}")
    public  List<Factura> obtenerFacturasDeUnMedicoYEstados(@PathVariable(name = "dni") String dni, @PathVariable (name="estado") ESTADOS estado){
        return service.obtenerFacturasMedicoConEstado(dni, estado);
    }

}
