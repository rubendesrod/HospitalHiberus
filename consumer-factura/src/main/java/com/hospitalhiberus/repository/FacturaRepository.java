package com.hospitalhiberus.repository;

import com.hospitalhiberus.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {}
