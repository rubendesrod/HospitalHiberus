package com.hospitalhiberus.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "facturas")
@EntityListeners(AuditingEntityListener.class)
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Integer idFactura;

    @Column(name = "id_medico")
    private String idMedico;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "total_pagar")
    private Integer totalPagar;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private ESTADOS estado;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;
}
