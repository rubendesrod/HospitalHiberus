package com.hospitalhiberus.repository;

import com.hospitalhiberus.model.ESTADOS;
import com.hospitalhiberus.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {


    List<Factura> findByIdMedico(String id_medico);

    List<Factura> findByIdMedicoAndEstado(String idMedico, ESTADOS estado);
}
