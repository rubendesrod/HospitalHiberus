package com.hospitalhiberus.service;

import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.model.Factura;
import com.hospitalhiberus.repository.FacturaRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaService {

    private final FacturaRepository repository;

    public FacturaService(FacturaRepository repository) {
        this.repository = repository;
    }

    public List<Factura> obtenerTodas() {
        return repository.findAll();
    }

    public List<Factura> obtenerFacturasMedico(String medico) {
        if (StringUtils.isBlank(medico)) {
            throw new IllegalArgumentException("El ID del médico no puede estar vacío o ser nulo.");
        }
        return repository.findByIdMedico(medico);
    }

    public List<Factura> obtenerFacturasMedicoConEstado(String medico, ESTADOS estado) {
        if (StringUtils.isBlank(medico)) {
            throw new IllegalArgumentException("El ID del médico no puede estar vacío o ser nulo.");
        }
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo.");
        }
        return repository.findByIdMedicoAndEstado(medico, estado);
    }
}