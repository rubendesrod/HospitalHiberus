package com.hospitalhiberus.service;

import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.model.Factura;
import com.hospitalhiberus.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository repository;

    public List<Factura> obtenerTodas() {
        return repository.findAll();
    }

    public List<Factura> obtenerFacturasMedico(String medico) {
        Assert.hasText(medico, "El ID del médico no puede estar vacío o ser nulo.");
        return repository.findByIdMedico(medico);
    }

    public List<Factura> obtenerFacturasMedicoConEstado(String medico, ESTADOS estado) {
        Assert.hasText(medico, "El ID del médico no puede estar vacío o ser nulo.");
        Assert.notNull(estado, "El estado no puede ser nulo.");
        return repository.findByIdMedicoAndEstado(medico, estado);
    }
}
