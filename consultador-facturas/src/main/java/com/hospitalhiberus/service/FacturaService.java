package com.hospitalhiberus.service;

import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.model.Factura;
import com.hospitalhiberus.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaService {

    @Autowired
    FacturaRepository repository;

    public List<Factura> obtenerTodas() {return repository.findAll();};

    public List<Factura> obtenerFacturasMedico(String medico) {return repository.finByIdMedico(medico);};

    public List<Factura> obtenerFacturasMedicoConEstado(String medico, ESTADOS estado){
        return repository.findByIdMedicoAndEstado(medico, estado);
    };

}
