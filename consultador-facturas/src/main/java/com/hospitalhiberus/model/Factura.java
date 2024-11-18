package com.hospitalhiberus.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "facturas")
@EntityListeners(AuditingEntityListener.class)
public class Factura {

    @Id
    @GeneratedValue
    @Column(name = "id_factura")
    private Integer id_factura;

    @Column(name = "id_medico")
    private String id_medico;

    @Column(name = "fechaEmision")
    private LocalDate fechaEmision;

    @Column(name = "totalPagar")
    private Integer totalPagar;

    @Column(name = "estado")
    private ESTADOS estado;

    @Column(name = "fechaPago")
    private LocalDate fechaPago;


}
